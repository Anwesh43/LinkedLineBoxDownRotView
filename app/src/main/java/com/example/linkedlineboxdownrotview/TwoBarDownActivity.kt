package com.example.linkedlineboxdownrotview

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.twolinebardownview.TwoLineBarDownView

class TwoBarDownActivity() : AppCompatActivity() {

    override fun onCreate(sis : Bundle?) {
        super.onCreate(sis)
        TwoLineBarDownView.create(this)
        fullScreen()
    }
}

fun TwoBarDownActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}