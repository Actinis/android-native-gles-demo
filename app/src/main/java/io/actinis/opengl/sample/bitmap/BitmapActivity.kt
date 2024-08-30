package io.actinis.opengl.sample.bitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import io.actinis.opengl.sample.R

class BitmapActivity : AppCompatActivity() {

    private val bitmaps = mutableMapOf<String, Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bitmap)

        findViewById<View>(R.id.imageIv).setOnClickListener {
            nextImage()
        }

        cacheImages()
        nextImage()
    }

    private fun cacheImages() {
        images.forEach { imagePath ->
            assets.open(imagePath).use { stream ->
                val bitmap = BitmapFactory.decodeStream(stream)
                bitmaps[imagePath] = bitmap

                Log.i(TAG, "Bitmap size = ${bitmap.width}x${bitmap.height}")
            }
        }
    }

    private fun nextImage() {
        val imagePath = images.random()

        val bitmap = bitmaps[imagePath]
        findViewById<ImageView>(R.id.imageIv).setImageBitmap(bitmap)
    }

    private companion object {
        private const val TAG = "BitmapActivity"

        private val images = listOf(
            "images/bitmap_1.jpg",
            "images/bitmap_2.jpg",
            "images/bitmap_3.jpg",
        )
    }
}
