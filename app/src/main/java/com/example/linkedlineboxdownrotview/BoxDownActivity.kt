package com.example.linkedlineboxdownrotview

import com.example.lineboxdownrotview.LineBoxDownRotView
import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class BoxDownActivity() : AppCompatActivity() {

    override fun onCreate(sis : Bundle?) {
        super.onCreate(sis);
        setContentView(LineBoxDownRotView.create(this))
        fullScreen()
    }
}

fun BoxDownActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}