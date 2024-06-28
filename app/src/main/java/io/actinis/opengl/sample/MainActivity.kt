package io.actinis.opengl.sample

import android.app.ActivityManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class MainActivity : AppCompatActivity() {

    private lateinit var glSurfaceView: GLSurfaceView
    private lateinit var nativeRenderer: NativeRenderer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        Log.i(
            "OLOLO",
            "Version: ${configurationInfo.getGlEsVersion()}/${configurationInfo.reqGlEsVersion}"
        )

        glSurfaceView = GLSurfaceView(this)

        // Set up OpenGL ES 2.0 context
        glSurfaceView.setEGLContextClientVersion(2)

        // Create NativeRenderer instance
        nativeRenderer = NativeRenderer()

        // Set the renderer for the GLSurfaceView
        glSurfaceView.setRenderer(object : GLSurfaceView.Renderer {
            override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
                nativeRenderer.onSurfaceCreated()
            }

            override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
                Log.i("OLOLO", "onSurfaceChanged: " + width + "x" + height)
                nativeRenderer.onSurfaceChanged(width, height)
            }

            override fun onDrawFrame(gl: GL10?) {
                nativeRenderer.onDrawFrame()
            }
        })

        // Set the GLSurfaceView as the content view
        setContentView(glSurfaceView)

        enableEdgeToEdge()
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView.onResume()
    }

}
