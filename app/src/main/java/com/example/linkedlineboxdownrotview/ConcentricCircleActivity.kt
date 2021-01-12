package com.example.linkedlineboxdownrotview

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.concentriccircleballjoinview.ConcentricCircleBallJoinView

class ConcentricCircleActivity: AppCompatActivity() {

    override fun onCreate(sis : Bundle?) {
        super.onCreate(sis)
        ConcentricCircleBallJoinView.create(this)
        fullScreen()
    }
}

fun ConcentricCircleActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}