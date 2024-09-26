package io.actinis.opengl.sample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.fonts.Font
import android.graphics.text.MeasuredText
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toRect
import kotlin.random.Random

class MovingSquareView(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    private val paint = Paint()
    private val path = Path()
    private val rect = RectF()
    private val random = Random(System.currentTimeMillis())

    private lateinit var bitmap: Bitmap
    private lateinit var ninePatch: NinePatch
    private lateinit var picture: Picture

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w / 4, h / 4, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.YELLOW)
        canvas.drawCircle(bitmap.width / 2f, bitmap.height / 2f, 50f, paint)

        val ninePatchBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val ninePatchCanvas = Canvas(ninePatchBitmap)
        ninePatchCanvas.drawColor(Color.BLUE)
        ninePatch = NinePatch(ninePatchBitmap, ninePatchBitmap.ninePatchChunk, null)

        picture = Picture()
        val pictureCanvas = picture.beginRecording(200, 200)
        pictureCanvas.drawColor(Color.GREEN)
        pictureCanvas.drawCircle(100f, 100f, 50f, paint)
        picture.endRecording()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawARGB(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))

        // clipPath with Region.Op
        path.reset()
        path.addCircle(width / 2f, height / 2f, 200f, Path.Direction.CW)
        canvas.clipPath(path, Region.Op.DIFFERENCE)

        // drawPicture
        canvas.drawPicture(picture)
        canvas.drawPicture(picture, RectF(0f, 0f, 300f, 300f))
        canvas.drawPicture(picture, Rect(300, 0, 600, 300))

        // drawArc
        paint.style = Paint.Style.STROKE
        canvas.drawArc(100f, 100f, 300f, 300f, 0f, 90f, false, paint)
        canvas.drawArc(RectF(300f, 100f, 500f, 300f), 0f, 180f, true, paint)

        // drawBitmap
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        canvas.drawBitmap(bitmap, Rect(0, 0, bitmap.width, bitmap.height), RectF(100f, 400f, 300f, 600f), paint)

        // skew and setDrawFilter
        canvas.skew(0.2f, 0.2f)
        canvas.setDrawFilter(PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG))

        // drawBitmap with colors array
        val colors = IntArray(100) { Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)) }
        canvas.drawBitmap(colors, 0, 10, 400f, 400f, 10, 10, true, paint)
        canvas.drawBitmap(colors, 0, 10, 400, 500, 10, 10, true, paint)

        // drawBitmap with matrix
        val matrix = Matrix()
        matrix.setRotate(45f)
        canvas.drawBitmap(bitmap, matrix, paint)

        // drawBitmapMesh
        val verts = FloatArray(8) { random.nextFloat() * 100 }
        canvas.drawBitmapMesh(bitmap, 1, 1, verts, 0, null, 0, paint)

        // drawLine and drawLines
        canvas.drawLine(0f, 0f, width.toFloat(), height.toFloat(), paint)
        canvas.drawLines(floatArrayOf(0f, height.toFloat(), width.toFloat(), 0f), paint)

        // drawPaint
        paint.style = Paint.Style.FILL
        paint.color = Color.RED
        canvas.drawPaint(paint)

        // drawPatch
        canvas.drawPatch(ninePatch, Rect(50, 50, 150, 150), paint)
        canvas.drawPatch(ninePatch, RectF(200f, 50f, 300f, 150f), paint)

        // drawPath
        path.reset()
        path.moveTo(0f, 0f)
        path.quadTo(width / 2f, height / 2f, width.toFloat(), 0f)
        canvas.drawPath(path, paint)

        // drawPoint and drawPoints
        paint.strokeWidth = 10f
        canvas.drawPoint(width / 2f, height / 2f, paint)
        canvas.drawPoints(floatArrayOf(100f, 100f, 200f, 200f, 300f, 300f), paint)

        // drawPosText
        val text = "Hello, World!"
        val pos = FloatArray(text.length * 2) { index -> if (index % 2 == 0) random.nextFloat() * width else random.nextFloat() * height }
        canvas.drawPosText(text, pos, paint)

        // drawRGB
        canvas.drawRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256))

        // drawDoubleRoundRect
        canvas.drawDoubleRoundRect(
            RectF(50f, 50f, 250f, 250f), 20f, 20f,
            RectF(100f, 100f, 200f, 200f), 10f, 10f,
            paint
        )

        // drawGlyphs
        val font = Font.Builder(context.assets, "fonts/EduAUVICWANTGuides-Regular.ttf").build()
        val glyphIds = IntArray(5) { random.nextInt(100) }
        val positions = FloatArray(10) { random.nextFloat() * 100 }
        canvas.drawGlyphs(glyphIds, 0, positions, 0, 5, font, paint)

        // drawTextOnPath
        canvas.drawTextOnPath("Text on path", path, 0f, 0f, paint)

        // drawTextRun
        val measuredText = MeasuredText.Builder("Measured text".toCharArray()).build()
        canvas.drawTextRun(measuredText, 0, 13, 0, 13, 100f, 100f, false, paint)

        // drawVertices
        val vertexCount = 3
        val vertices = floatArrayOf(0f, 0f, 100f, 100f, 200f, 0f)
        canvas.drawVertices(Canvas.VertexMode.TRIANGLES, vertexCount * 2, vertices, 0, null, 0, null, 0, null, 0, 0, paint)

        // clipOutRect and clipOutPath
        canvas.clipOutRect(RectF(0f, 0f, 100f, 100f))
        canvas.clipOutRect(Rect(100, 100, 200, 200))
        canvas.clipOutRect(200f, 200f, 300f, 300f)
        canvas.clipOutRect(300, 300, 400, 400)
        path.reset()
        path.addCircle(width / 2f, height / 2f, 100f, Path.Direction.CW)
        canvas.clipOutPath(path)

        val layerCount = canvas.saveLayer(RectF(0f, 0f, width.toFloat(), height.toFloat()), paint)
        canvas.restoreToCount(layerCount)

        // saveLayerAlpha
        canvas.saveLayerAlpha(RectF(0f, 0f, width.toFloat(), height.toFloat()), 128)
        canvas.saveLayerAlpha(0f, 0f, width.toFloat(), height.toFloat(), 128)

        // scale and setMatrix
        canvas.scale(0.5f, 0.5f)
        canvas.setMatrix(Matrix())
    }

    fun redraw() {
        invalidate()
    }
}
