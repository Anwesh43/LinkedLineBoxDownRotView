package com.example.linkedlineboxdownrotview

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.lineboxcirclepasserview.LineBoxCirclePasserView

//import com.example.lineboxdownrotview.LineBoxDownRotView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        LineBoxDownRotView.create(this)
        LineBoxCirclePasserView.create(this)
        val i1 = Intent(this, BoxDownActivity::class.java)
        val i2 = Intent(this, BoxCircleActivity::class.java)
        val i3 = Intent(this, DivideBarLineActivity::class.java)
        val i4 = Intent(this, ConcentricCircleActivity::class.java)
        val i5 = Intent(this, TwoBarDownActivity::class.java)
        val b1 : Button = Button(this).apply {
            setText("BoxDown")
            setOnClickListener{
                startActivity(i1)
            }
        }
        val b2 : Button = Button(this).apply {
            setText("BoxCircle")
            setOnClickListener{
                startActivity(i2)
            }
        }
        b2.x = 400f
        val b3 : Button = Button(this).apply {
            setText("DivideLine")
            setOnClickListener {
                startActivity(i3)
            }
        }
        b3.x = 600f
        val b4 = Button(this).apply {
            setText("ConcenctricCircle")
            setOnClickListener {
                startActivity(i4)
            }
        }
        b4.x = 0f
        b4.y = 200f
        val b5  = Button(this).apply {
            setText("TwoBarDown")
            setOnClickListener {
                startActivity(i5)
            }
        }
        b5.x = 300f
        b5.y = 200f
        val coordinatorLayout : CoordinatorLayout = CoordinatorLayout(this).apply {
            addView(b1)
            addView(b2)
            addView(b3)
            addView(b4)
            addView(b5)
        }
        setContentView(coordinatorLayout)
        fullScreen()
    }
}

fun MainActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}