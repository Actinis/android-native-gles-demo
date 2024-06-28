package io.actinis.opengl.sample

class NativeRenderer {


    external fun onSurfaceCreated()
    external fun onDrawFrame()
    external fun onSurfaceChanged(width: Int, height: Int)

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}
