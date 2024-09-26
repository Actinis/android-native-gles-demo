package io.actinis.opengl.sample

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import io.actinis.opengl.sample.bitmap.BitmapActivity
import io.actinis.opengl.sample.dialog.DialogActivity
import io.actinis.opengl.sample.messenger.MessengerActivity


class MainActivity : AppCompatActivity() {

    private lateinit var glSurfaceView: GLSurfaceView
    private lateinit var nativeRenderer: NativeRenderer

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    private val colors = listOf(
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.CYAN,
        Color.MAGENTA,
        Color.WHITE,
        Color.YELLOW,
    )

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val view = MovingSquareView(this)
//        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
//        setContentView(view)

//        startActivity(Intent(this, BitmapActivity::class.java))
//        startActivity(Intent(this, MessengerActivity::class.java))
//        startActivity(Intent(this, DialogActivity::class.java))
        finish()
        return

//        setContentView(R.layout.activity_main)
//
//        findViewById<View>(R.id.testBtn).setOnClickListener {
//            val itemsLayout = findViewById<LinearLayout>(R.id.itemsLayout)
//
//            val view = CheckBox(this).apply {
//                text = "Checkbox #${itemsLayout.childCount}"
//            }
//
//            itemsLayout.addView(
//                view, LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//                )
//            )
//        }
//
//        findViewById<View>(R.id.messengerBtn).setOnClickListener {
//            startActivity(Intent(this, MessengerActivity::class.java))
//        }
//
//        val coloredSquareView: View = findViewById(R.id.coloredSquareView)
//
//        val initialSize = dpToPx(100)
//        val finalSize = dpToPx(150)
//
//        val animator = ValueAnimator.ofInt(initialSize, finalSize).apply {
//            duration = 1000 // 1 second
//            repeatMode = ValueAnimator.REVERSE
//            repeatCount = ValueAnimator.INFINITE
//
//            addUpdateListener { animation ->
//                val animatedValue = animation.animatedValue as Int
//                val layoutParams = coloredSquareView.layoutParams
//                layoutParams.width = animatedValue
//                layoutParams.height = animatedValue
//                coloredSquareView.layoutParams = layoutParams
//            }
//        }
//
//        animator.start()


//        runnable = object : Runnable {
//            override fun run() {
//                val randomColor = colors.random()
//                findViewById<View>(R.id.main).setBackgroundColor(randomColor)
//                handler.postDelayed(this, 1000)
//            }
//        }
//
//        handler.post(runnable)

//        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
//        val configurationInfo = activityManager.deviceConfigurationInfo
//        Log.i(
//            "OLOLO",
//            "Version: ${configurationInfo.getGlEsVersion()}/${configurationInfo.reqGlEsVersion}"
//        )
//
//        glSurfaceView = GLSurfaceView(this)
//
//        // Set up OpenGL ES 2.0 context
//        glSurfaceView.setEGLContextClientVersion(2)
//
//        // Create NativeRenderer instance
//        nativeRenderer = NativeRenderer()
//
//        // Set the renderer for the GLSurfaceView
//        glSurfaceView.setRenderer(object : GLSurfaceView.Renderer {
//            override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
//                nativeRenderer.onSurfaceCreated()
//            }
//
//            override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
//                Log.i("OLOLO", "onSurfaceChanged: " + width + "x" + height)
//                nativeRenderer.onSurfaceChanged(width, height)
//            }
//
//            override fun onDrawFrame(gl: GL10?) {
//                nativeRenderer.onDrawFrame()
//            }
//        })

        // Set the GLSurfaceView as the content view
//        setContentView(glSurfaceView)

//        enableEdgeToEdge()
    }

//    override fun onPause() {
//        super.onPause()
//        glSurfaceView.onPause()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        glSurfaceView.onResume()
//    }

    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

}
