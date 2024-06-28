#include <jni.h>
#include <string>
#include <iostream>
#include <android/log.h>
#include "Renderer.h"

#define LOG_TAG "ActinisDEMO"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

static Renderer renderer;

__attribute__((constructor))
void onLoad() {
    LOGI("Loaded library");
}

extern "C" JNIEXPORT void
JNICALL Java_io_actinis_opengl_sample_NativeRenderer_onSurfaceCreated(
        JNIEnv *env,
        jobject obj
) {
    renderer.initialize();
}

extern "C" JNIEXPORT void
JNICALL Java_io_actinis_opengl_sample_NativeRenderer_onDrawFrame(
        JNIEnv *env,
        jobject obj
) {
    float angle = (float) clock() / CLOCKS_PER_SEC * 3;
    renderer.render(angle);
}

extern "C" JNIEXPORT void
JNICALL Java_io_actinis_opengl_sample_NativeRenderer_onSurfaceChanged(
        JNIEnv *env,
        jobject obj,
        jint width,
        jint height
) {
    LOGI("onSurfaceChanged: %dx%d", width, height);
    glViewport(0, 0, width, height);
}
