package com.example.linkedlineboxdownrotview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.lineboxcirclepasserview.LineBoxCirclePasserView

//import com.example.lineboxdownrotview.LineBoxDownRotView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        LineBoxDownRotView.create(this)
        LineBoxCirclePasserView.create(this)
        fullScreen()
    }
}

fun MainActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}