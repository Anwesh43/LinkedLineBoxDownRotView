package com.example.linkedlineboxdownrotview

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.rightanglesteplineview.RightAngleStepLineView


class RightAngleStepLineActivity:AppCompatActivity() {

    override fun onCreate(sis : Bundle?) {
        super.onCreate(sis)
        RightAngleStepLineView.create(this)
        fullScreen()
    }
}

fun RightAngleStepLineActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}