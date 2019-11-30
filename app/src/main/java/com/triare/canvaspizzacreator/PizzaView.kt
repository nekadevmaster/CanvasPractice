package com.triare.canvaspizzacreator

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

class PizzaView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawFirstLayer(canvas)
        drawSecondLayer(canvas)
        drawThirdLayer(canvas)
        drawFourLayer(canvas)
    }

    private fun drawFirstLayer(canvas: Canvas?) {

        //canvas?.save()

        /*canvas?.clipOutPath(Path().apply {
            reset()
            addCircle(
                width / 2f,
                height / 2f,
                width / 6f,
                Path.Direction.CW
            )
        })*/

        val paint = Paint().apply {
            color = Color.parseColor("#F1BE4A")
        }

        canvas?.drawCircle(
            width / 2f,
            height / 2f,
            width / 2f,
            paint)

        //canvas?.restore()

    }

    private fun drawSecondLayer(canvas: Canvas?) {

        //canvas?.save()

        canvas?.drawCircle(
            width / 2f,
            height / 2f,
            width / 2.2f,
            Paint().apply {
                color = Color.parseColor("#B6301D")
                pathEffect = ComposePathEffect (
                    CornerPathEffect(40f),
                    DiscretePathEffect(60f, 25f)
                )
            })

        //canvas?.restore()

    }

    private fun drawThirdLayer(canvas: Canvas?) {
        canvas?.save()
        canvas?.clipPath(Path().apply {
            reset()
            addCircle(
                width / 2f,
                height / 2f,
                width / 2.3f,
                Path.Direction.CW
            )
        })

        drawCheeses(canvas)

        //canvas?.restore()
    }

    private fun drawFourLayer(canvas: Canvas?) {
        val bitmap = Bitmap.createBitmap(
            width / 10,
            width / 10,
            Bitmap.Config.ARGB_8888
        )
        val meatCanvas = Canvas(bitmap)
        meatCanvas.drawCircle(
            meatCanvas.width / 2f,
            meatCanvas.height / 2f,
            meatCanvas.width / 2f,
            Paint().apply {
                color = Color.parseColor("#680000")
            }
        )
        meatCanvas.clipPath(Path().apply {
            reset()
            addCircle(
                meatCanvas.width / 2f,
                meatCanvas.height / 2f,
                meatCanvas.width / 2f,
                Path.Direction.CW
            )
        })
        repeat(10) {
            val x = (10f..meatCanvas.width.toFloat()).random()
            val y = (10f..meatCanvas.height.toFloat()).random()
            meatCanvas.drawCircle(
                x,
                y,
                meatCanvas.width / 15f,
                Paint().apply {
                    color = Color.WHITE
                }
            )
        }
        var left = 10f
        var top = height / 4f
        val maxLeft = width
        val maxTop = height - height / 4f + 40
        while (left < maxLeft) {
            canvas?.drawBitmap(bitmap, left, top, null)
            top += bitmap.height + 40
            if (top > maxTop) {
                left += bitmap.width + 60
                top = height / 4f
            }
        }
    }

    private val cheesePosTypes = listOf(
        CheesePosTypes.MINUS,
        CheesePosTypes.PLUS,
        CheesePosTypes.ZEPO
    )

    private var cheesePosTypesIndex = 0

    private fun getStopPos(start: Float): Float {
        if (cheesePosTypes.lastIndex == cheesePosTypesIndex)
            cheesePosTypesIndex = 0

        val stop = when (cheesePosTypes[cheesePosTypesIndex]) {
            CheesePosTypes.MINUS -> {
                start - 40f
            }
            CheesePosTypes.PLUS -> {
                start + 40f
            }
            else -> {
                start + 0f
            }
        }
        cheesePosTypesIndex += 1
        return stop
    }

    private fun drawCheeses(canvas: Canvas?) {
        repeat(500) {
            val startX = (0f .. width.toFloat()).random()
            val startY = (height / 2f + (width / 2f) .. height / 2f - (width / 2f)).random()
            val stopX = getStopPos(startX)
            val stopY = getStopPos(startY)

            Log.d("nek", "startX: $startX, startY: $startY, stopX: $stopX, stopY: $stopY")

            canvas?.drawLine(
                startX,
                startY,
                stopX,
                stopY,
                Paint().apply {
                    color = Color.YELLOW
                    strokeWidth = 15f
                }
            )
        }
    }

    enum class CheesePosTypes {
        MINUS, PLUS, ZEPO
    }

}

fun ClosedFloatingPointRange<Float>.random() : Float =
    (start + Math.random() * (endInclusive - start)).toFloat()