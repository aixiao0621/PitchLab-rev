package com.symbolic.pitchlab;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * C0966cm 类实现了 GLSurfaceView.Renderer 接口，用于渲染 OpenGL ES 的内容。
 * openGLSurface 用于具体的渲染
 */
public class openGLSurface implements GLSurfaceView.Renderer {

    /***
     *     渲染时调用->f3114a->openGLSurface->openGLActivity
     *     引用到外部的 C0963cj 对象
     */
    final /* synthetic */ openGLActivity f3114a;

    /**
     * 构造函数
     *
     * @param openGLActivity 一个 C0963cj 类型的对象，通常为 GLSurfaceView 的持有者。
     */
    /* renamed from: com.symbolic.pitchlab.cm */
    public openGLSurface(openGLActivity openGLActivity) {
        this.f3114a = openGLActivity;
    }

    /**
     * 当 GLSurfaceView 需要绘制一帧时调用此方法。
     *
     * @param gl10 GL10 接口，提供 OpenGL ES 1.0 的功能。
     */
    @Override
    public void onDrawFrame(GL10 gl10) {
        // 处理多视图渲染逻辑
        int i;
        int numViews = PitchLabNative.getNumViews();
        if (numViews > 0) {
            settings.m56h(settings.m44q() % numViews);
        }
        // 更新渲染设置
        settings.m54i(settings.m43r() | (1 << settings.m44q()));

        // 计算当前应绘制的视图数量
        int m43r = settings.m43r();
        int i2 = 0;
        int i3 = 0;
        while (i2 < numViews) {
            int i4 = ((1 << i2) & m43r) == 0 ? i3 + 1 : i3;
            i2++;
            i3 = i4;
        }

        // 调用 native 方法进行帧渲染

        // 获取 temperament 编号
        int m39v = settings.m39v();
        // 当前视图模式
        int m44q = settings.m44q();
        // int i
        // 音高容忍度 m48m()
        // 音名显示风格 m47n()
        // (i3 << 24) | 0  -> 1 or 0

        i = this.f3114a.f3107g;
        PitchLabNative.drawFrame(m39v, m44q, i, settings.m48m(), settings.m47n(), (i3 << 24) | settings.m38w());
    }

    /**
     * 当 GLSurfaceView 的表面大小发生变化时调用此方法。
     *
     * @param gl10 GL10 接口。
     * @param i 新的宽度。
     * @param i2 新的高度。
     */
    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        // 通知 native 层表面大小发生变化
        PitchLabNative.surfaceChanged(i, i2);
        // 更新视图大小
        this.f3114a.f3105e = i;
        this.f3114a.f3106f = i2;
    }

    /**
     * 当 GLSurfaceView 的表面被创建时调用此方法。
     *
     * @param gl10 GL10 接口。
     * @param eGLConfig EGL 配置。
     */
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        // 通知 native 层表面已创建
        PitchLabNative.surfaceCreated();
    }
}
