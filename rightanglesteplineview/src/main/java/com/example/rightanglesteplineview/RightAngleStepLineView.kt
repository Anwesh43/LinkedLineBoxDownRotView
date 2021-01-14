package com.example.rightanglesteplineview

import android.view.View
import android.view.MotionEvent
import android.app.Activity
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.content.Context

val steps : Int = 4
val parts : Int = 2 + steps
val rot : Float = 90f
val scGap : Float = 0.02f / parts
val sizeFactor : Float = 2.9f
val strokeFactor : Float = 90f
val delay : Long = 20
val colors : Array<Int> = arrayOf(
    "#F44336",
    "#673AB7",
    "#4CAF50",
    "#FFC107",
    "#795548"
).map {
    Color.parseColor(it)
}.toTypedArray()
val backColor : Int = Color.parseColor("#BDBDBD")
