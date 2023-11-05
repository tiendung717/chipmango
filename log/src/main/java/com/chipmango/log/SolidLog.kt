package com.chipmango.log

import android.util.Log

class SolidLog {
    fun check() {
        d("nt.dung", "Working!!!")
    }

    fun d(tag: String, message: String) {
        Log.d(tag, message)
    }
}