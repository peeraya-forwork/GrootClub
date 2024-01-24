package com.example.grootclub.utils

import com.orhanobut.hawk.Hawk

object GlobalVar {
    var email: String
        get() = Hawk.get("email", "")
        set(value) {
            Hawk.put("email", value)
        }
    fun deleteHawk(str: String) {
        Hawk.delete(str)
    }
}