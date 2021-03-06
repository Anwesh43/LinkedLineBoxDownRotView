package com.example.lineboxdownrotview

import android.view.View
import android.view.MotionEvent
import android.content.Context
import android.app.Activity
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF

val parts : Int = 4
val scGap : Float = 0.02f
val strokeFactor : Float = 90f
val lSizeFactor : Float = 3.2f
val rSizeFactor : Float = 9.8f
val delay : Long = 20
val colors : Array<Int> = arrayOf(
    "#F44336",
    "#4CAF50",
    "#795548",
    "#FF5722",
    "#03A9F4"
).map {
    Color.parseColor(it)
}.toTypedArray()
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n

fun Canvas.drawLineBoxRot(scale : Float, w : Float, h : Float, paint : Paint) {
    val lSize : Float = Math.min(w, h) / lSizeFactor
    val rSize : Float = Math.min(w, h) / rSizeFactor
    val sc1 : Float = scale.divideScale(0, parts)
    val sc2 : Float = scale.divideScale(1, parts)
    val sc3 : Float = scale.divideScale(2, parts)
    val sc4 : Float = scale.divideScale(3, parts)
    save()
    translate(0f, 0f)
    save()
    translate(-lSize + (w / 2) * (sc1 - sc4), h   / 2)
    drawLine(0f, 0f, lSize, 0f, paint)
    restore()
    save()
    translate(w / 2, (h / 2) * (sc2 + sc4) + (rSize * sc4))
    rotate(90f * sc3)
    drawRect(RectF(-rSize, -rSize, 0f, 0f), paint)
    restore()
    restore()
}

fun Canvas.drawLBRDNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawLineBoxRot(scale, w, h, paint)
}

class LineBoxDownRotView(ctx : Context) : View(ctx) {

    private val renderer : Renderer = Renderer(this)

    override fun onDraw(canvas: Canvas) {
        renderer.render(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }

    data class State(var scale: Float = 0f, var prevScale: Float = 0f, var dir: Float = 0f) {

        fun update(cb: (Float) -> Unit) {
            scale += dir * scGap
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb: () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view: View, var animated: Boolean = false) {

        fun animate(cb: () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(delay)
                    view.invalidate()
                } catch (ex: Exception) {

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

    data class LBRDNode(var i: Int, val state: State = State()) {

        private var prev: LBRDNode? = null
        private var next: LBRDNode? = null

        init {
            addNeighbor()
        }

        fun addNeighbor() {
            if (i < colors.size - 1) {
                next = LBRDNode(i + 1)
                next?.prev = this
            }
        }


        fun draw(canvas: Canvas, paint: Paint) {
            canvas.drawLBRDNode(i, state.scale, paint)
        }

        fun update(cb: (Float) -> Unit) {
            state.update(cb)
        }

        fun startUpdating(cb: () -> Unit) {
            state.startUpdating(cb)
        }

        fun getNext(dir: Int, cb: () -> Unit): LBRDNode {
            var curr: LBRDNode? = prev
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

    data class LineBoxRotDown(var i: Int) {

        private var curr: LBRDNode = LBRDNode(0)
        private var dir: Int = 1

        fun draw(canvas: Canvas, paint: Paint) {
            curr.draw(canvas, paint)
        }

        fun update(cb: (Float) -> Unit) {
            curr.update {
                curr = curr.getNext(dir) {
                    dir *= -1
                }
                cb(it)
            }
        }

        fun startUpdating(cb: () -> Unit) {
            curr.startUpdating(cb)
        }
    }

    data class Renderer(var view: LineBoxDownRotView) {

        private val lbrd: LineBoxRotDown = LineBoxRotDown(0)
        private val animator: Animator = Animator(view)
        private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

        fun render(canvas: Canvas) {
            canvas.drawColor(backColor)
            lbrd.draw(canvas, paint)
            animator.animate {
                lbrd.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            lbrd.startUpdating {
                animator.start()
            }
        }
    }

    companion object {
        fun create(activity : Activity) : LineBoxDownRotView {
            val view : LineBoxDownRotView = LineBoxDownRotView(activity)
            activity.setContentView(view)
            return view
        }
    }
}