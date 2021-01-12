package com.example.linkedlineboxdownrotview

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.dividebarlineview.DivideBarLineView

class DivideBarLineActivity : AppCompatActivity() {

    override fun onCreate(sis : Bundle?) {
        super.onCreate(sis)
        DivideBarLineView.create(this)
        fullScreen()
    }
}

fun DivideBarLineActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}