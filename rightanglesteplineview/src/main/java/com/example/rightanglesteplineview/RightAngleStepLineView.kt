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

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawRightAngleStepLine(scale : Float, w : Float,h : Float, paint : Paint) {
    val size : Float = Math.min(w, h) / sizeFactor
    val sf : Float = scale.sinify()
    save()
    translate(w / 2, h / 2)

    for (j in 0..1) {
        save()
        rotate(rot * sf.divideScale(1, parts))
        drawLine(0f, 0f, 0f, -size * sf.divideScale(0, parts), paint)
        restore()
    }
    for (j in 0..(steps - 1)) {
        val sfk : Float = sf.divideScale(2 + j, parts)
        val gap : Float = size / steps
        save()
        translate(0f, -gap * (j + 1))
        drawLine(0f, 0f, gap * (j + 1) * sfk, gap * (j + 1) * sfk, paint)
        restore()
    }
    restore()
}

fun Canvas.drawRSALNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawRightAngleStepLine(scale, w, h, paint)
}

class RightAngleStepLineView(ctx : Context) : View(ctx) {

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