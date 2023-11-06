package com.chipmango.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chipmango.log.SolidLog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SolidLog().check()
    }
}