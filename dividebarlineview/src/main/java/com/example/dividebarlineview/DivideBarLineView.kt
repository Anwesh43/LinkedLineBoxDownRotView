package com.example.dividebarlineview

import android.view.View
import android.view.MotionEvent
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF

val parts : Int = 3
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val sizeFactor : Float = 2.9f
val bars : Int = 3
val delay : Long = 20
val colors : Array<Int> = arrayOf(
    "#F44336",
    "#4CAF50",
    "#FF9800",
    "#795548",
    "#2196F3"
).map {
    Color.parseColor(it)
}.toTypedArray()
val backColor : Int = Color.parseColor("#BDBDBD")