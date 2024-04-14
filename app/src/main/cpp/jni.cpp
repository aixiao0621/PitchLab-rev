#include "stdio.h"
#include "stdlib.h"
#include "jni.h"
#include <android/log.h>

#include <GLES/gl.h>
#include <GLES/glext.h>

#include <math.h>
#include <stdbool.h>

#include "kiss_fft.h"

#define  LOG_TAG    "libgl2jni"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

static void printGLString(const char *name, GLenum s) {
    const char *v = (const char *) glGetString(s);
    LOGI("GL %s = %s\n", name, v);
}

static void checkGlError(const char *op) {
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


    glShadeModel(GL_SMOOTH);                        // 启用阴影平滑
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                    // 黑色背景
    glClearDepthf(1.0f);                            // 设置深度缓存
    glEnable(GL_DEPTH_TEST);                        // 启用深度测试
    glDepthFunc(GL_LEQUAL);                            // 所作深度测试的类型
    glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);            // 告诉系统对透视进行修正

    return true;
}

const GLfloat gVertices[] = {
        0.0f, 1.0f, 0.0f,
        -1.0f, -1.0f, 1.0f,
        1.0f, -1.0f, 1.0f,

        0.0f, 1.0f, 0.0f,
        1.0f, -1.0f, 1.0f,
        1.0f, -1.0f, -1.0f,

        0.0f, 1.0f, 0.0f,
        1.0f, -1.0f, -1.0f,
        -1.0f, -1.0f, -1.0f,

        0.0f, 1.0f, 0.0f,
        -1.0f, -1.0f, -1.0f,
        -1.0f, -1.0f, 1.0f
};

const GLfloat gColors[] = {
        1.0f, 0.0f, 0.0f, 1.0f,
        0.0f, 1.0f, 0.0f, 1.0f,
        0.0f, 0.0f, 1.0f, 1.0f,

        1.0f, 0.0f, 0.0f, 1.0f,
        0.0f, 0.0f, 1.0f, 1.0f,
        0.0f, 1.0f, 0.0f, 1.0f,

        1.0f, 0.0f, 0.0f, 1.0f,
        0.0f, 1.0f, 0.0f, 1.0f,
        0.0f, 0.0f, 1.0f, 1.0f,

        1.0f, 0.0f, 0.0f, 1.0f,
        0.0f, 0.0f, 1.0f, 1.0f,
        0.0f, 1.0f, 0.0f, 1.0f
};

static GLfloat rtri;  //三角形的旋转变量
static GLfloat rquad;  //四边形的旋转变量

const GLfloat PI = 3.1415f;

static void _gluPerspective(GLfloat fovy, GLfloat aspect, GLfloat zNear, GLfloat zFar) {
    GLfloat top = zNear * ((GLfloat) tan(fovy * PI / 360.0));
    GLfloat bottom = -top;
    GLfloat left = bottom * aspect;
    GLfloat right = top * aspect;
    glFrustumf(left, right, bottom, top, zNear, zFar);
}

void resize(int width, int height) {
    if (height == 0)                                // 防止被零除
    {
        height = 1;                            // 将Height设为1
    }

    glViewport(0, 0, width, height);                    // 重置当前的视口
    glMatrixMode(GL_PROJECTION);                        // 选择投影矩阵
    glLoadIdentity();                            // 重置投影矩阵

    GLfloat ratio = (GLfloat) width / (GLfloat) height;
    // 设置视口的大小
    _gluPerspective(45.0f, (GLfloat) width / (GLfloat) height, 0.1f, 100.0f);
    //    glOrthof(-2.0f, 2.0f, -2.0f, 2.0f, -2.0f, 2.0f);

    glMatrixMode(GL_MODELVIEW);                        // 选择模型观察矩阵
    glLoadIdentity();                            // 重置模型观察矩阵
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

    glDisableClientState(GL_VERTEX_ARRAY);
    glDisableClientState(GL_COLOR_ARRAY);

    glFlush();

}


// 定义全局变量来存储频率和分贝值，及其大小
float *gFrequencies = NULL;
float *gDBs = NULL;
int gResultsLength = 0;
float gMaxFrequency;
// 定义复数结构体，供FFT使用
typedef struct {
    float r; // 实部
    float i; // 虚部
} complex_float;

void calculate_frequency_and_db(kiss_fft_cpx *spectrum, int window_size, int sample_rate) {
    if (gFrequencies != NULL) {
        free(gFrequencies);
    }
    if (gDBs != NULL) {
        free(gDBs);
    }

    gResultsLength = window_size / 2; // FFT输出的有效部分
    gFrequencies = (float *) malloc(sizeof(float) * gResultsLength);
    gDBs = (float *) malloc(sizeof(float) * gResultsLength);
    if (gFrequencies == NULL || gDBs == NULL) {
        // 处理分配失败的情况
        free(gFrequencies);
        free(gDBs);
        return;
    }

    int maxIndex = 0; // 记录最大分贝值的索引
    float maxDB = -FLT_MAX; // 初始化为最小值

    for (int i = 0; i < gResultsLength; i++) {
        gFrequencies[i] = i * sample_rate / (float) window_size;

        float power = spectrum[i].r * spectrum[i].r + spectrum[i].i * spectrum[i].i;
        if (power <= 0.0f) {
            power = 1e-10f; // 防止对数为负无穷大
        }

        gDBs[i] = 10.0 * log10(power);

        // 检查这个频率的音量是否是最大的
        if (gDBs[i] > maxDB) {
            maxIndex = i; // 更新最大分贝值的索引
            maxDB = gDBs[i]; // 更新最大分贝值
        }
    }

    // 找到最大分贝值对应的频率
    gMaxFrequency = gFrequencies[maxIndex];

    // 打印最大音量的频率和分贝值
    LOGE("Max Frequency: %f Hz, Max dB: %f dB", gMaxFrequency, maxDB);
}

// 汉宁窗函数
void apply_hanning_window(complex_float *input, int window_size) {
    for (int i = 0; i < window_size; ++i) {
        float multiplier = 0.5 * (1.0 - cos(2.0 * M_PI * i / (window_size - 1)));
        input[i].r *= multiplier;
        // FFT算法中虚部均为0，因此可以省略
        input[i].i *= multiplier;
    }
}

// 过滤谐波
void low_pass_filter(kiss_fft_cpx* spectrum, int len, int sample_rate, float cutoff_frequency) {
    int cutoff_bin = (int)(cutoff_frequency / (sample_rate / 2.0f) * len);
    for (int i = cutoff_bin; i < len; ++i) {
        spectrum[i].r = 0;
        spectrum[i].i = 0;
    }
}

#define LARGE_BUFFER_SIZE (4096 * 4)
static float large_buffer[LARGE_BUFFER_SIZE];  // 大的浮点缓冲区
static int large_buffer_index = 0;             // 缓冲区的当前索引
// 记录样本到大缓冲区函数
void record_samples_to_large_buffer(jshort *samples, int num_samples) {
    for (int i = 0; i < num_samples && large_buffer_index < LARGE_BUFFER_SIZE; ++i) {
        // 将短整型样本转换为浮点数，并存储到大缓冲区中
        large_buffer[large_buffer_index++] = (float) samples[i];
    }
}
extern "C" {
JNIEXPORT void JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_processStreamedSamples(
        JNIEnv *env,
        jclass clazz,
        jshortArray s_arr,
        jint bf_size,   // 3584
        jint sample_rate
) {
    // 获取Java数组的数据
    jshort *audio_samples = (*env).GetShortArrayElements(s_arr, NULL);
    if (audio_samples == NULL) {
        // 内存分配失败时的处理
        return; // JNI异常已抛出
    }

    // 将接收到的样本添加到大缓冲区
    record_samples_to_large_buffer(audio_samples, bf_size);

    // 检查大缓冲区是否已经收集了足够的样本来进行FFT
    if (large_buffer_index >= LARGE_BUFFER_SIZE) {
        // 分配并初始化FFT的输入数组
        kiss_fft_cpx *fin = (kiss_fft_cpx *) calloc(LARGE_BUFFER_SIZE, sizeof(kiss_fft_cpx));
        if (!fin) {
            // 释放Java数组引用并返回
            (*env).ReleaseShortArrayElements(s_arr, audio_samples, 0);
            return;
        }

        // 复制大缓冲区中的样本到FFT输入数组
        for (int i = 0; i < LARGE_BUFFER_SIZE; ++i) {
            fin[i].r = large_buffer[i];
            // FFT输入的虚部保持为0（calloc已经初始化）
        }

        // 分配FFT的输出数组
        kiss_fft_cpx *fout = (kiss_fft_cpx *) malloc(sizeof(kiss_fft_cpx) * LARGE_BUFFER_SIZE);
        if (!fout) {
            // 释放资源并返回
            free(fin);
            (*env).ReleaseShortArrayElements(s_arr, audio_samples, 0);
            return;
        }

        // 分配FFT配置
        kiss_fft_cfg cfg = kiss_fft_alloc(4096*4, 0, NULL, NULL);
        if (!cfg) {
            // 释放资源并返回
            free(fin);
            free(fout);
            (*env).ReleaseShortArrayElements(s_arr, audio_samples, 0);
            return;
        }

        // 应用汉宁窗
        apply_hanning_window((complex_float *) fin, LARGE_BUFFER_SIZE);

        // 执行FFT
        kiss_fft(cfg, fin, fout);

        // 根据新的FFT大小设置gResultsLength
        gResultsLength = LARGE_BUFFER_SIZE / 2;
        float cutoff_frequency = 1000.0f; // 设定一个合适的截止频率以去除谐波
        low_pass_filter(fout, gResultsLength, sample_rate, cutoff_frequency);

        // 计算频率和分贝值
        calculate_frequency_and_db(fout, LARGE_BUFFER_SIZE, sample_rate);

        // 清理并释放资源
        kiss_fft_free(cfg);
        free(fin);
        free(fout);

        // 重置大缓冲区索引
        large_buffer_index = 0;
    }

    // 释放Java数组引用
    (*env).ReleaseShortArrayElements(s_arr, audio_samples, 0);
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
                                                                jfloat jf2, jint jint1,
                                                                jint jint2) {
    static char *global_var_string = NULL; // 用于存储字符串
    static int global_var_int = 0; // 用于存储整数
    // 接受Java中传递过来的字符串并打印
    const char *str = (*env).GetStringUTFChars(jstr, 0);
    if (str != NULL) {
        printf("String received: %s\n", str);
        (*env).ReleaseStringUTFChars(jstr, str);
    }

    // 接受Java中传递过来的整型数组并打印每个元素值
    jint *i_arr = (*env).GetIntArrayElements(jintArray, NULL);
    if (i_arr != NULL) {
        jsize length = (*env).GetArrayLength(jintArray);
        printf("Integer array received:\n");
        for (int i = 0; i < length; ++i) {
            printf("%d ", i_arr[i]);
        }
        printf("\n");
        (*env).ReleaseIntArrayElements(jintArray, i_arr, 0);
    }

    // 接受Java中传递过来的浮点数组并打印每个元素值
    jfloat *f_arr = (*env).GetFloatArrayElements(jfloatArray, NULL);
    if (f_arr != NULL) {
        jsize length = (*env).GetArrayLength(jfloatArray);
        printf("Float array received:\n");
        for (int i = 0; i < length; ++i) {
            printf("%f ", f_arr[i]);
        }
        printf("\n");
        (*env).ReleaseFloatArrayElements(jfloatArray, f_arr, 0);
    }

    // 接受java中传递过来的浮点数并打印
    printf("Floats received: f1 = %f, f2 = %f\n", jf1, jf2);
    LOGE("Floats received: f1 = %f, f2 = %f", jf1, jf2);

    // 接受java中传递过来的整数并打印
    printf("Integers received: i1 = %d, i2 = %d\n", jint1, jint2);
    LOGE("Integers received: i1 = %d, i2 = %d", jint1, jint2);

    // TODO: 根据你的应用场景来具体实现设置分析参数的过程
}

JNIEXPORT jstring JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_getNoteNameTitle(JNIEnv *env, jclass clazz, jint i) {
    // 返回一个音符名
    char *noteName = "A"; // TODO: 你应该根据传入的i值返回相应的音符名
    return (*env).NewStringUTF(noteName);
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
    char *viewNames[] = {"View 1", "View 2", "View 3", "View 4", "View 5"};
    if (i >= 0 && i < 5) {
        return (*env).NewStringUTF(viewNames[i]);
    } else {
        // 输入值不正确时返回错误
        char *errorMsg = "Invalid index";
        return (*env).NewStringUTF(errorMsg);
    }
}

JNIEXPORT void JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_initialiseNative(JNIEnv *env, jclass clazz, jstring str) {
    // 初始化Native部分，假设接受一个初始化字符串
    const char *initString = (*env).GetStringUTFChars(str, 0);
    printf("Initialising with string: %s\n", initString);
    (*env).ReleaseStringUTFChars(str, initString);
    init();
}

JNIEXPORT void JNICALL
Java_com_symbolic_pitchlab_PitchLabNative_appPaused(JNIEnv *env, jclass clazz) {
    printf("appPaused\n");
}
}