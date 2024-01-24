package com.example.grootclub

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.grootclub.components.dialog.MyDialog
import com.example.grootclub.components.navigator.Navigator
import com.example.grootclub.data.ProductModel
import com.example.grootclub.databinding.ActivityMainBinding
import com.example.grootclub.ui.detail.DetailActivity
import com.example.grootclub.ui.home.HomeFragment
import com.example.grootclub.ui.signIn.SignInActivity
import com.example.grootclub.ui.signup.SignUpActivity
import com.example.grootclub.ui.slideshow.SlideshowFragment
import com.example.grootclub.utils.sharedpreferences.SharedPreference

class MainActivity : AppCompatActivity(), HomeFragment.HomeItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreference
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var sharedViewModel: SharedViewModel
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        sharedPref = SharedPreference(applicationContext)
        setContentView(binding.root)

        if (sharedPref.isLogIn()) {
            binding.appBarMain.toolbar.btnIconEnd.visibility = View.GONE
            initNavigationView()
            showMain()

            setSupportActionBar(binding.appBarMain.toolbar.toolbarMain) // ใช้ custom toolbar
//            supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            supportActionBar?.setDisplayShowHomeEnabled(true)
            invalidateOptionsMenu()

        }

        initToolBar()
        observeData()

    }

    private fun observeData() {
        sharedViewModel.getSelectedStadium().observe(this, Observer { stadium ->
            // ทำสิ่งที่คุณต้องการเมื่อมีการเลือก Stadium ใน HomeFragment
            // stadium คือข้อมูลที่ถูกส่งมาจาก HomeFragment
            if (sharedPref.isLogIn()) {
                Toast.makeText(
                    this,
                    "ยังไม่บ่ทันเฮ็ดจ้า พส. ${stadium.section}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, "Please log in", Toast.LENGTH_SHORT).show()
                val i = Intent(applicationContext, SignInActivity::class.java)
                startActivity(i)
            }
        })
    }

    private fun initToolBar() {
        binding.appBarMain.toolbar.btnIconEnd.setOnClickListener {
            if (!sharedPref.isLogIn()) {
                val i = Intent(applicationContext, SignUpActivity::class.java)
                startActivity(i)
//                finish()
            }
        }
    }

    private fun showMain() {
        binding.navView.visibility = View.VISIBLE
        binding.appBarMain.toolbar.btnIconStart.visibility = View.VISIBLE

        binding.appBarMain.toolbar.btnIconStart.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun initNavigationView() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        binding.apply {
            toggle = ActionBarDrawerToggle(
                this@MainActivity, drawerLayout, R.string.open, R.string.close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            navView.setupWithNavController(navController)
        }

        // ดึงชื่อจาก SharedPreference
        val email = sharedPref.getUserDetails()

        if (email.isNotEmpty()) {
            // แสดงชื่อใน TextView ใน nav_header_main
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tvEmail).text =
                email[SharedPreference.KEY_MAIL].toString()
        }

    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "กดอีกครั้งเพื่อออก", Toast.LENGTH_SHORT).show()
            val handler = Handler(Looper.getMainLooper())

            handler.postDelayed({
                doubleBackToExitPressedOnce = false
            }, 2000)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                // ดำเนินการออกจากระบบ
                logout()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun updateOptionsMenu() {
        invalidateOptionsMenu()
    }

    private fun logout() {
        sharedPref.logoutUser()
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
        val i = Intent(applicationContext, MainActivity::class.java)

        // ตั้งค่า flags เพื่อลบประวัติการเรียกของ SignInActivity
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(i)
        finish()
    }



    override fun onItemClicked(item: ProductModel.Stadium) {}
}