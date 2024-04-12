#include "stdio.h"
#include "stdlib.h"
#include "jni.h"





#include <jni.h>
#include <android/log.h>

#include <GLES/gl.h>
#include <GLES/glext.h>

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>

#define  LOG_TAG    "libgl2jni"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

static void printGLString(const char *name, GLenum s) {
    const char *v = (const char *) glGetString(s);
    LOGI("GL %s = %s\n", name, v);
}

static void checkGlError(const char* op) {
    for (GLint error = glGetError(); error; error
                                                    = glGetError()) {
        LOGI("after %s() glError (0x%x)\n", op, error);
    }
}

bool init() {
    printGLString("Version", GL_VERSION);
    printGLString("Vendor", GL_VENDOR);
    printGLString("Renderer", GL_RENDERER);
    printGLString("Extensions", GL_EXTENSIONS);


    glShadeModel(GL_SMOOTH);						// 启用阴影平滑
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);					// 黑色背景
    glClearDepthf(1.0f);							// 设置深度缓存
    glEnable(GL_DEPTH_TEST);						// 启用深度测试
    glDepthFunc(GL_LEQUAL);							// 所作深度测试的类型
    glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);			// 告诉系统对透视进行修正

    return true;
}

const GLfloat gVertices[] = {
        0.0f, 1.0f, 0.0f,
        -1.0f,-1.0f, 1.0f,
        1.0f,-1.0f, 1.0f,

        0.0f, 1.0f, 0.0f,
        1.0f,-1.0f, 1.0f,
        1.0f,-1.0f, -1.0f,

        0.0f, 1.0f, 0.0f,
        1.0f,-1.0f, -1.0f,
        -1.0f,-1.0f, -1.0f,

        0.0f, 1.0f, 0.0f,
        -1.0f,-1.0f,-1.0f,
        -1.0f,-1.0f, 1.0f
};

const GLfloat gColors[] = {
        1.0f,0.0f,0.0f, 1.0f,
        0.0f,1.0f,0.0f, 1.0f,
        0.0f,0.0f,1.0f, 1.0f,

        1.0f,0.0f,0.0f, 1.0f,
        0.0f,0.0f,1.0f, 1.0f,
        0.0f,1.0f,0.0f, 1.0f,

        1.0f,0.0f,0.0f, 1.0f,
        0.0f,1.0f,0.0f, 1.0f,
        0.0f,0.0f,1.0f, 1.0f,

        1.0f,0.0f,0.0f, 1.0f,
        0.0f,0.0f,1.0f, 1.0f,
        0.0f,1.0f,0.0f, 1.0f
};

static GLfloat rtri;  //三角形的旋转变量
static GLfloat rquad;  //四边形的旋转变量

const GLfloat PI = 3.1415f;

static void _gluPerspective(GLfloat fovy, GLfloat aspect, GLfloat zNear, GLfloat zFar)
{
    GLfloat top = zNear * ((GLfloat) tan(fovy * PI / 360.0));
    GLfloat bottom = -top;
    GLfloat left = bottom * aspect;
    GLfloat right = top * aspect;
    glFrustumf(left, right, bottom, top, zNear, zFar);
}

void resize(int width, int height)
{
    if (height==0)								// 防止被零除
    {
        height=1;							// 将Height设为1
    }

    glViewport(0, 0, width, height);					// 重置当前的视口
    glMatrixMode(GL_PROJECTION);						// 选择投影矩阵
    glLoadIdentity();							// 重置投影矩阵

    GLfloat ratio = (GLfloat)width/(GLfloat)height;
    // 设置视口的大小
    _gluPerspective(45.0f,(GLfloat)width/(GLfloat)height,0.1f,100.0f);
    //    glOrthof(-2.0f, 2.0f, -2.0f, 2.0f, -2.0f, 2.0f);

    glMatrixMode(GL_MODELVIEW);						// 选择模型观察矩阵
    glLoadIdentity();							// 重置模型观察矩阵
}

void renderFrame() {

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);    // 清除屏幕及深度缓存
    glLoadIdentity();                    // 重置模型观察矩阵
    glTranslatef(0.0f, 0.0f, -6.0f);                // 移入屏幕 6.0

    glRotatef(rtri, 0.0f, 1.0f, 0.0f);                // 绕Y轴旋转金字塔

    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_COLOR_ARRAY);
    glColorPointer(4, GL_FLOAT, 0, gColors);

    glVertexPointer(3, GL_FLOAT, 0, gVertices);
    glDrawArrays(GL_TRIANGLES, 0, 12);


    rtri += 0.2f;                        // 增加三角形的旋转变量
    //LOGI("xxxxx");

    glDisableClientState(GL_VERTEX_ARRAY);
    glDisableClientState(GL_COLOR_ARRAY);

    glFlush();

}





JNIEXPORT void JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_drawFrame(JNIEnv *env, jclass clazz, jint i, jint i2,
                                                    jint i3, jint i4, jint i5, jint i6) {
    renderFrame();
}

JNIEXPORT void JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_surfaceCreated(JNIEnv *env, jclass clazz) {
    // 处理界面创建的函数，这里仅打印一条信息
    printf("Surface created\n");

}

JNIEXPORT void JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_surfaceChanged(JNIEnv *env, jclass clazz, jint i,
                                                         jint i2) {
    resize(i, i2);
}

JNIEXPORT void JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_setAnalysisParameters(JNIEnv *env, jclass clazz,
                                                                jstring jstr, jintArray jintArray,
                                                                jfloatArray jfloatArray, jfloat jf1,
                                                                jfloat jf2, jint jint1, jint jint2) {
    static char *global_var_string = NULL; // 用于存储字符串
    static int global_var_int = 0; // 用于存储整数
    // 接受Java中传递过来的字符串并打印
    const char *str = (*env)->GetStringUTFChars(env, jstr, 0);
    if (str != NULL) {
        printf("String received: %s\n", str);
        (*env)->ReleaseStringUTFChars(env, jstr, str);
    }

    // 接受Java中传递过来的整型数组并打印每个元素值
    jint *i_arr = (*env)->GetIntArrayElements(env, jintArray, NULL);
    if (i_arr != NULL) {
        jsize length = (*env)->GetArrayLength(env, jintArray);
        printf("Integer array received:\n");
        for (int i = 0; i < length; ++i) {
            printf("%d ", i_arr[i]);
        }
        printf("\n");
        (*env)->ReleaseIntArrayElements(env, jintArray, i_arr, 0);
    }

    // 接受Java中传递过来的浮点数组并打印每个元素值
    jfloat *f_arr = (*env)->GetFloatArrayElements(env, jfloatArray, NULL);
    if (f_arr != NULL) {
        jsize length = (*env)->GetArrayLength(env, jfloatArray);
        printf("Float array received:\n");
        for (int i = 0; i < length; ++i) {
            printf("%f ", f_arr[i]);
        }
        printf("\n");
        (*env)->ReleaseFloatArrayElements(env, jfloatArray, f_arr, 0);
    }

    // 接受java中传递过来的浮点数并打印
    printf("Floats received: f1 = %f, f2 = %f\n", jf1, jf2);

    // 接受java中传递过来的整数并打印
    printf("Integers received: i1 = %d, i2 = %d\n", jint1, jint2);

    // TODO: 根据你的应用场景来具体实现设置分析参数的过程
}

JNIEXPORT jstring JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_getNoteNameTitle(JNIEnv *env, jclass clazz, jint i) {
    // 返回一个音符名
    char* noteName = "A"; // TODO: 你应该根据传入的i值返回相应的音符名
    return (*env)->NewStringUTF(env, noteName);
}

JNIEXPORT jint JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_getNumNoteNames(JNIEnv *env, jclass clazz) {
    // 返回音符名的数量，这里假设了有7个音符
    return 7;
}

JNIEXPORT jint JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_getNumViews(JNIEnv *env, jclass clazz) {
    // 返回视图的数量，这里假设有5个视图
    return 5;
}

JNIEXPORT jstring JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_getViewName(JNIEnv *env, jclass clazz, jint i) {
    // 返回视图名称，这里假设根据索引返回对应的名称
    char* viewNames[] = {"View 1", "View 2", "View 3", "View 4", "View 5"};
    if (i >= 0 && i < 5) {
        return (*env)->NewStringUTF(env, viewNames[i]);
    } else {
        // 输入值不正确时返回错误
        char* errorMsg = "Invalid index";
        return (*env)->NewStringUTF(env, errorMsg);
    }
}

JNIEXPORT void JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_processStreamedSamples(JNIEnv *env, jclass clazz,
                                                                 jshortArray s_arr, jint i,
                                                                 jint i2) {
    // 处理流式样本数据，这里为了示例只打印了一些参数
    jsize arrayLength = (*env)->GetArrayLength(env, s_arr);
    printf("Processing %d streamed samples, starting from %d with step %d\n", arrayLength, i, i2);
    // 实际处理过程中可能需要获取数组元素并操作它们
}

JNIEXPORT void JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_initialiseNative(JNIEnv *env, jclass clazz, jstring str) {
    // 初始化Native部分，假设接受一个初始化字符串
    const char *initString = (*env)->GetStringUTFChars(env, str, 0);
    printf("Initialising with string: %s\n", initString);
    (*env)->ReleaseStringUTFChars(env, str, initString);
    init();
}

JNIEXPORT void JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_appPaused(JNIEnv *env, jclass clazz) {
    printf("appPaused\n");
}
