package com.symbolic.pitchlab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类是用来存储和管理PitchLab应用中的配置和状态信息的。
 * 它包含了应用版本、音频设置、用户界面选项等的管理方法。
 */
public class settings {

    // 存储不同类型的音高检测算法
    private static final List f3071a = new ArrayList();

    // 存储自定义乐器列表
    private static final List f3072b = new ArrayList();

    // 当前视图模式
    private static int f3073c = 0;

    // 音频采样率
    private static int f3074d = 44100;

    // 分析速率
    private static int f3075e = 0;

    // A4音高的参考频率
    private static float f3076f = 440.0f;

    // 当前乐器名称
    private static String f3077g = "";

    // 存储自定义音高范围
    private static List f3078h = new ArrayList();

    // 用户选择的乐器索引
    private static int f3079i = -1;

    // 保存用户上次使用的音高分析算法
    private static String f3080j = "";

    // 存储自定义音高范围数量
    private static List f3081k = new ArrayList();

    // 用户自定义音高范围索引
    private static int f3082l = -1;

    // 音高容忍度
    private static int f3083m = 5;

    // 音名显示风格
    private static int f3084n = 0;

    // 调音阈值
    private static float f3085o = 5.0f;

    // 屏幕旋转设置
    private static int f3086p = 0;

    // 使用类型掩码
    private static int f3087q = 0;

    // 音频设置版本
    private static String f3088r = "";

    // 音频分析版本
    private static String f3089s = "";

    // 是否显示过欢迎界面
    private static boolean f3090t = false;

    // 用户选择的 temperament 编号
    private static int f3091u = 1;

    // 上次应用使用的音高分析算法编号
    private static int f3092v = 0;

    /* renamed from: a */
    public static synchronized String m83a() {
        synchronized (settings.class) {
        }
        return "1_0_22";
    }

    /* renamed from: a */
    public static synchronized void m82a(float f) {
        synchronized (settings.class) {
            f3076f = f;
        }
    }

    /* renamed from: a */
    public static synchronized void m81a(int i) {
        synchronized (settings.class) {
            f3074d = i;
        }
    }

    /* renamed from: a */
    public static synchronized void m80a(Context context) {
        synchronized (settings.class) {
            SharedPreferences m68c = m68c(context);
            f3073c = m68c.getInt("viewMode", 0);
            f3074d = m68c.getInt("sampleRate", 44100);
            f3075e = m68c.getInt("analysisRate", 0);
            f3076f = m68c.getFloat("A4ReferenceHz", 440.0f);
            f3083m = m68c.getInt("texTypeSpectrogram", 5);
            f3084n = m68c.getInt("noteNameStyle", 0);
            f3085o = m68c.getFloat("inTuneThreshold", 5.0f);
            f3086p = m68c.getInt("screenOrientation", 0);
            f3087q = m68c.getInt("usageMask", 0);
            f3088r = m68c.getString("audioSetupVersion", "");
            f3089s = m68c.getString("audioAnalysisVersion", "");
            f3090t = m68c.getBoolean("welcomeShown_1_0_22", false);
            f3080j = m68c.getString("temperamentName", "");
            f3081k = Tuna.m33a(m68c, "customTemperamentCount", "customTemperamentInstance");
            f3082l = m68c.getInt("customTemperamentFocus", -1);
            f3077g = m68c.getString("instrumentName", "");
            f3078h = kindsOfTuna.m92a(m68c, "customInstrumentCount", "customInstrumentInstance");
            f3079i = m68c.getInt("customInstrumentFocus", -1);
        }
    }

    /* renamed from: a */
    public static synchronized void m79a(String str) {
        synchronized (settings.class) {
            f3077g = str;
        }
    }

    /* renamed from: a */
    public static synchronized void m78a(List list) {
        synchronized (settings.class) {
            f3078h = new ArrayList(list);
        }
    }

    /* renamed from: a */
    public static synchronized void m77a(boolean z) {
        synchronized (settings.class) {
            f3090t = z;
        }
    }

    /* renamed from: b */
    public static synchronized String m76b() {
        String str;
        synchronized (settings.class) {
            str = String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + "/PitchLab";
        }
        return str;
    }

    /* renamed from: b */
    public static synchronized void m75b(float f) {
        synchronized (settings.class) {
            f3085o = f;
        }
    }

    /* renamed from: b */
    public static synchronized void m74b(int i) {
        synchronized (settings.class) {
            f3075e = i;
        }
    }

    /* renamed from: b */
    public static synchronized void m73b(Context context) {
        synchronized (settings.class) {
            SharedPreferences.Editor edit = m68c(context).edit();
            edit.clear();
            edit.putInt("viewMode", f3073c);
            edit.putInt("sampleRate", f3074d);
            edit.putInt("analysisRate", f3075e);
            edit.putFloat("A4ReferenceHz", f3076f);
            edit.putString("instrumentName", f3077g);
            edit.putString("temperamentName", f3080j);
            edit.putInt("texTypeSpectrogram", f3083m);
            edit.putInt("noteNameStyle", f3084n);
            edit.putFloat("inTuneThreshold", f3085o);
            edit.putInt("screenOrientation", f3086p);
            edit.putInt("usageMask", f3087q);
            edit.putString("audioSetupVersion", f3088r);
            edit.putString("audioAnalysisVersion", f3089s);
            kindsOfTuna.m93a(edit, "customInstrumentCount", "customInstrumentInstance", f3078h);
            edit.putInt("customInstrumentFocus", f3079i);
            Tuna.m34a(edit, "customTemperamentCount", "customTemperamentInstance", f3081k);
            edit.putInt("customTemperamentFocus", f3082l);
            edit.putBoolean("welcomeShown_1_0_22", f3090t);
            edit.commit();
        }
    }

    /* renamed from: b */
    public static synchronized void m72b(String str) {
        synchronized (settings.class) {
            f3080j = str;
        }
    }

    /* renamed from: b */
    public static synchronized void m71b(List list) {
        synchronized (settings.class) {
            f3081k = new ArrayList(list);
        }
    }

    /* renamed from: c */
    public static synchronized int m70c() {
        int i;
        synchronized (settings.class) {
            i = f3074d;
        }
        return i;
    }

    /* renamed from: c */
    private static SharedPreferences m68c(Context context) {
        return context.getSharedPreferences("com.symbolic.pitchlab.settings", 0);
    }

    /* renamed from: c */
    public static synchronized void m69c(int i) {
        synchronized (settings.class) {
            f3079i = i;
        }
    }

    /* renamed from: c */
    public static synchronized void m67c(String str) {
        synchronized (settings.class) {
            f3088r = str;
        }
    }

    /* renamed from: d */
    public static synchronized int m66d() {
        int i;
        synchronized (settings.class) {
            i = f3075e;
        }
        return i;
    }

    /* renamed from: d */
    public static synchronized void m65d(int i) {
        synchronized (settings.class) {
            f3082l = i;
        }
    }

    /* renamed from: d */
    public static synchronized void m64d(String str) {
        synchronized (settings.class) {
            f3089s = str;
        }
    }

    /* renamed from: e */
    public static synchronized float m63e() {
        float f;
        synchronized (settings.class) {
            f = f3076f;
        }
        return f;
    }

    /* renamed from: e */
    public static synchronized void m62e(int i) {
        synchronized (settings.class) {
            f3083m = i;
        }
    }

    /* renamed from: f */
    public static synchronized float m61f() {
        synchronized (settings.class) {
        }
        return 440.0f;
    }

    /* renamed from: f */
    public static synchronized void m60f(int i) {
        synchronized (settings.class) {
            f3084n = i;
        }
    }

    /* renamed from: g */
    public static synchronized String m59g() {
        String str;
        synchronized (settings.class) {
            str = f3077g;
        }
        return str;
    }

    /* renamed from: g */
    public static synchronized void m58g(int i) {
        synchronized (settings.class) {
            f3086p = i;
        }
    }

    /* renamed from: h */
    public static synchronized List m57h() {
        ArrayList arrayList;
        synchronized (settings.class) {
            arrayList = new ArrayList(f3078h);
        }
        return arrayList;
    }

    /***
     *
     *
     * @param i 要设置的视图模式
     */
    public static synchronized void m56h(int i) {
        synchronized (settings.class) {
            f3073c = i;
        }
    }

    /* renamed from: i */
    public static synchronized int m55i() {
        int i;
        synchronized (settings.class) {
            i = f3079i;
        }
        return i;
    }

    /* renamed from: i */
    public static synchronized void m54i(int i) {
        synchronized (settings.class) {
            f3087q = i;
        }
    }

    /* renamed from: j */
    public static synchronized String m53j() {
        String str;
        synchronized (settings.class) {
            str = f3080j;
        }
        return str;
    }

    /* renamed from: j */
    public static synchronized void m52j(int i) {
        synchronized (settings.class) {
            f3091u = i;
        }
    }

    /* renamed from: k */
    public static synchronized List m51k() {
        ArrayList arrayList;
        synchronized (settings.class) {
            arrayList = new ArrayList(f3081k);
        }
        return arrayList;
    }

    /* renamed from: k */
    public static synchronized void m50k(int i) {
        synchronized (settings.class) {
            f3092v = i;
        }
    }

    /* renamed from: l */
    public static synchronized int m49l() {
        int i;
        synchronized (settings.class) {
            i = f3082l;
        }
        return i;
    }

    /* renamed from: m */
    public static synchronized int m48m() {
        int i;
        synchronized (settings.class) {
            i = f3083m;
        }
        return i;
    }

    /* renamed from: n */
    public static synchronized int m47n() {
        int i;
        synchronized (settings.class) {
            i = f3084n;
        }
        return i;
    }

    /* renamed from: o */
    public static synchronized float m46o() {
        float f;
        synchronized (settings.class) {
            f = f3085o;
        }
        return f;
    }

    /* renamed from: p */
    public static synchronized int m45p() {
        int i;
        synchronized (settings.class) {
            i = f3086p;
        }
        return i;
    }

    /***
     *
     * @return 当前选择的视图模式
     */
    public static synchronized int m44q() {
        int i;
        synchronized (settings.class) {
            i = f3073c;
        }
        return i;
    }

    /* renamed from: r */
    public static synchronized int m43r() {
        int i;
        synchronized (settings.class) {
            i = f3087q;
        }
        return i;
    }

    /* renamed from: s */
    public static synchronized String m42s() {
        String str;
        synchronized (settings.class) {
            str = f3088r;
        }
        return str;
    }

    /* renamed from: t */
    public static synchronized String m41t() {
        String str;
        synchronized (settings.class) {
            str = f3089s;
        }
        return str;
    }

    /* renamed from: u */
    public static synchronized boolean m40u() {
        boolean z;
        synchronized (settings.class) {
            z = f3090t;
        }
        return z;
    }

    /* renamed from: v */
    public static synchronized int m39v() {
        int i;
        synchronized (settings.class) {
            i = f3091u;
        }
        return i;
    }

    /* renamed from: w */
    public static synchronized int m38w() {
        int i;
        synchronized (settings.class) {
            i = f3092v;
        }
        return i;
    }

    /* renamed from: x */
    public static synchronized void m37x() {
        synchronized (settings.class) {
            f3073c = 0;
            f3074d = 44100;
            f3075e = 0;
            f3076f = 440.0f;
            f3077g = "";
            f3079i = -1;
            f3080j = "";
            f3082l = -1;
            f3083m = 5;
            f3090t = false;
            f3084n = 0;
            f3085o = 5.0f;
            f3086p = 0;
            f3087q = 0;
            f3088r = "";
            f3089s = "";
        }
    }
}