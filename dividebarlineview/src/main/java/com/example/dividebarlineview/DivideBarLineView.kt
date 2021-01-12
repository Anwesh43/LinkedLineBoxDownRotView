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

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawDivideBarLine(scale : Float, w : Float, h : Float, paint : Paint) {
    val sc1 : Float = scale.divideScale(0, parts)
    val sc2 : Float = scale.divideScale(1, parts)
    val sc3 : Float = scale.divideScale(2, parts)
    val size : Float = Math.min(w, h) / sizeFactor
    val barGap : Float = size / bars
    val barH : Float = barGap / 2
    save()
    translate(w / 2 - size / 2, h)
    for (j in 0..1) {
        save()
        translate(size * j, 0f)
        drawLine(0f, -size * 0.5f * sc3, 0f, -size * 0.5f * sc1, paint)
        restore()
    }
    for (j in 0..(bars - 1)) {
        val sc21 : Float = sc2.sinify().divideScale(0, 2)
        val sc22 : Float = sc2.sinify().divideScale(1, 2)
        val sc21j : Float = sc21.divideScale(j, bars)
        val sc22j : Float = sc22.divideScale(j, bars)
        save()
        translate(barGap * j, -(size / 2 - barH) * sc22j)
        drawRect(RectF(0f, -barH, barGap * sc21j, 0f), paint)
        restore()
    }
    restore()
}

fun Canvas.drawDBLNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawDivideBarLine(scale, w, h, paint)
}

class DivideBarLineView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}