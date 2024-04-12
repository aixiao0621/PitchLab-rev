package com.symbolic.pitchlab;

public class PitchLabNative {
    private static boolean initialized = false;

    public static synchronized void initialize() {
        if (!initialized) {
            System.loadLibrary("PitchLab");
//            initialiseNative(ActivityCrashlogs.getCrashLogDirectory());
            initialized = true;
        }
    }

    public static native synchronized void appPaused();

    public static native synchronized void drawFrame(int i, int i2, int i3, int i4, int i5, int i6);

    public static native synchronized String getNoteNameTitle(int i);

    public static native synchronized int getNumNoteNames();

    public static native synchronized int getNumViews();

    public static native synchronized String getViewName(int i);

    /***
     *
     * @param sArr 写入读取的数据的字节数组short[ ]
     * @param i read返回的数组数据的长度
     * @param i2 通过getSampleRate()获取音频记录的采样率
     */
    public static native synchronized void processStreamedSamples(short[] sArr, int i, int i2);

    public static native synchronized void setAnalysisParameters(String str, int[] iArr, float[] fArr, float f, float f2, int i, int i2);

    public static native synchronized void surfaceChanged(int i, int i2);

    public static native synchronized void surfaceCreated();

    private static native synchronized void initialiseNative(String str);
}
