package com.example.twolinebardownview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.content.Context
import android.app.Activity

val parts : Int = 5
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val sizeFactor : Float = 2.9f
val barWFactor : Float = 9.8f
val delay : Long = 20
val barHFactor : Float = 6f
val colors : Array<Int> = arrayOf(
    "#F44336",
    "#4CAF50",
    "#795548",
    "#2196F3",
    "#FFEB3B"
).map {
    Color.parseColor(it)
}.toTypedArray()
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawTwoLineBarDown(scale : Float, w : Float, h : Float, paint : Paint) {
    val size : Float = Math.min(w, h) / sizeFactor
    val barW : Float = Math.min(w, h) / barWFactor
    val barH : Float = size / barHFactor
    val sc1 : Float = scale.divideScale(0, parts)
    val sc2 : Float = scale.divideScale(1, parts)
    val sc3 : Float = scale.divideScale(2, parts)
    val sc5 : Float = scale.divideScale(4, parts)
    val sc4 : Float = scale.divideScale(3, parts)
    save()
    translate(w / 2 - barW / 2, h)
    for (j in 0..1) {
        save()
        translate(-barW * j, 0f)
        drawLine(0f, -size * sc5, 0f, -size * sc1, paint)
        restore()
    }
    save()
    translate(0f, -(size) + (size - barH) * sc3)
    drawRect(RectF(barW * sc2, 0f, barW * sc4, barH), paint)
    restore()
    restore()
}

fun Canvas.drawTLBDNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawTwoLineBarDown(scale, w, h, paint)
}