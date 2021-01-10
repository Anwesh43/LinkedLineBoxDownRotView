package com.example.linkedlineboxdownrotview

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.lineboxcirclepasserview.LineBoxCirclePasserView

class BoxCircleActivity() : AppCompatActivity() {

    override fun onCreate(sis : Bundle?) {
        super.onCreate(sis);
        setContentView(LineBoxCirclePasserView.create(this))
        fullScreen()
    }
}

fun BoxCircleActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}