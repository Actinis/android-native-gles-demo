package io.actinis.opengl.sample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class MovingSquareView(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    private val paintText = Paint().apply {
        color = Color.BLACK
        textSize = 40f
        isAntiAlias = true
    }

    private val paintSquare = Paint().apply {
        color = Color.RED
        isAntiAlias = true
    }

    private var squareX = 0f
    private var squareY = 0f
    private val squareSize = 50f

    private var dx = 5f
    private var dy = 5f

    init {
        // Set the initial position of the square
        squareX = Random.nextFloat() * (context.resources.displayMetrics.widthPixels - squareSize)
        squareY = Random.nextFloat() * (dpToPx(100) - squareSize)

        // Update the position periodically
        postDelayed({ updatePosition() }, 1000 / 60)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = dpToPx(100)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.YELLOW)
        canvas.drawText("Actinis", 10f, 50f, paintText)

        // Draw the moving square
        canvas.drawRect(squareX, squareY, squareX + squareSize, squareY + squareSize, paintSquare)
    }

    private fun updatePosition() {
        // Update the square's position
        squareX += dx
        squareY += dy

        // Ensure the square stays within bounds and bounces off the edges
        if (squareX < 0) {
            squareX = 0f
            dx = -dx
        }
        if (squareY < 0) {
            squareY = 0f
            dy = -dy
        }
        if (squareX + squareSize > width) {
            squareX = (width - squareSize)
            dx = -dx
        }
        if (squareY + squareSize > height) {
            squareY = (height - squareSize)
            dy = -dy
        }

        // Redraw the view
        invalidate()

        // Post a new update
        postDelayed({ updatePosition() }, 1000 / 60)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
