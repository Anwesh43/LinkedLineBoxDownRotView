package com.example.concentriccircleballjoinview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.app.Activity
import android.content.Context

val parts : Int = 5
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val sizeFactor : Float = 2.9f
val delay : Long = 20
val colors : Array<Int> = arrayOf(
    "#F44336",
    "#009688",
    "#4CAF50",
    "#3F51B5",
    "#FF5722"
).map {
    Color.parseColor(it)
}.toTypedArray()
val backColor : Int = Color.parseColor("#BDBDBD")
val rFactor : Float = 14.8f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawConcentricBallJoin(scale : Float, w : Float, h : Float, paint : Paint) {
    val size : Float = Math.min(w, h) / sizeFactor
    var sck : Float = 0f
    val gap : Float = size / parts
    save()
    translate(w / 2, h / 2)
    paint.style = Paint.Style.STROKE
    for (j in 0..(parts - 1)) {
        val r : Float = gap * (j + 1)
        val scj = scale.divideScale(1, 3).divideScale(j, parts).sinify()
        drawArc(RectF(-r, -r, r, r), 0f, 360f * scj, false, paint)
        sck += (r * scj)
    }
    val sc1 : Float = scale.divideScale(0, 3)
    val sc2 : Float = scale.divideScale(2, 3)
    paint.style = Paint.Style.FILL
    drawCircle(sck, 0f, (sc1 - sc2) * Math.min(w, h) / rFactor, paint)
    restore()
}

fun Canvas.drawCCBJNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawConcentricBallJoin(scale, w, h, paint)
}

class ConcentricCircleBallJoinView(ctx : Context) : View(ctx) {

    private val renderer : Renderer = Renderer(this)

    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas)
    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(delay)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class CCBJNode(var i : Int, val state : State = State()) {

        private var next : CCBJNode? = null
        private var prev : CCBJNode? = null

        init {
            addNeighbor()
        }

        fun addNeighbor() {
            if (i < colors.size - 1) {
                next = CCBJNode(i + 1)
                next?.prev = this
            }
        }

        fun draw(canvas : Canvas, paint : Paint) {
            canvas.drawCCBJNode(i, state.scale, paint)
        }

        fun update(cb : (Float) -> Unit) {
            state.update(cb)
        }

        fun startUpdating(cb : () -> Unit) {
            state.startUpdating(cb)
        }

        fun getNext(dir : Int, cb : () -> Unit) : CCBJNode {
            var curr : CCBJNode? = prev
            if (dir == 1) {
                curr = next
            }
            if (curr != null) {
                return curr
            }
            cb()
            return this
        }
    }

    data class ConcentricCircleBallJoin(var i : Int) {

        private var curr : CCBJNode = CCBJNode(0)
        private var dir : Int = 1

        fun draw(canvas : Canvas, paint : Paint) {
            curr.draw(canvas, paint)
        }

        fun update(cb : (Float) -> Unit) {
            curr.update {
                curr = curr.getNext(dir) {
                    dir *= -1
                }
                cb(it)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            curr.startUpdating(cb)
        }
    }

    data class Renderer(var view : ConcentricCircleBallJoinView) {

        private val animator : Animator = Animator(view)
        private val ccbj : ConcentricCircleBallJoin = ConcentricCircleBallJoin(0)
        private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

        fun render(canvas : Canvas) {
            canvas.drawColor(backColor)
            ccbj.draw(canvas, paint)
            animator.animate {
                ccbj.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            ccbj.startUpdating {
                animator.start()
            }
        }
    }

    companion object {

        fun create(activity: Activity) : ConcentricCircleBallJoinView {
            val view : ConcentricCircleBallJoinView = ConcentricCircleBallJoinView(activity)
            activity.setContentView(view)
            return view
        }
    }
}