package com.example.grootclub.data.Remote.Repository.Home

import com.example.grootclub.data.CoachListModelItem
import com.example.grootclub.data.Remote.Service
import com.example.grootclub.data.Request.SignIn.ReqSignIn

class CoachRepository(private val service: Service) {
    suspend fun getAllCoach(): List<CoachListModelItem> {
        return try {
            val response = service.getAllCoach()
            if (response.isNotEmpty()) {
                response
            } else {
                // สามารถจัดการกรณีอื่น ๆ ที่คุณต้องการทำได้ตามที่เหมาะสม
                emptyList()
            }
        } catch (e: Exception) {
            // จัดการข้อผิดพลาดเนื่องจากระบบหรือข้อผิดพลาดที่เกิดขึ้นในขณะที่ทำงาน
            e.printStackTrace()
            emptyList()
        }
    }
}

