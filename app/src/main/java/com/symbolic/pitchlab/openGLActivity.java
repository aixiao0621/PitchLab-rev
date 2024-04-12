package com.symbolic.pitchlab;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.TimerTask;
/**
 * C0963cj 类继承自 GLSurfaceView，用于创建和管理一个 OpenGL 的绘图表面。
 * 创建并管理 `GLSurfaceView`
 */
public class openGLActivity extends GLSurfaceView {

    /* renamed from: a */
    private TimerTask f3101a;

    /* renamed from: b */
    private boolean f3102b;

    /* renamed from: c */
    private Activity f3103c;

    /* renamed from: d */
    private View f3104d;


    /***
     * 屏幕高度
     */
    int f3105e;

    /***
     * 屏幕宽度
     */
    int f3106f;

    /* renamed from: g */
    int f3107g;

    /* renamed from: h */
    private GestureDetector f3108h;

    /* renamed from: i */
    private openGLSurface f3109i;

    /* renamed from: j */
    private View.OnClickListener f3110j;

    /* renamed from: k */
    private View.OnClickListener f3111k;
    /**
     * 构造函数，初始化 GLSurfaceView 及其相关属性。
     *
     * @param context 上下文对象，通常为 Activity。
     */
    public openGLActivity(Context context) {
        super(context);
        this.f3101a = new OpenGLActivityTimeTask(this);
        this.f3102b = false;
        this.f3103c = null;
        this.f3104d = null;
        this.f3105e = 0;
        this.f3106f = 0;
        this.f3107g = 0;
        this.f3108h = null;
        this.f3109i = null;
        this.f3110j = null;
        this.f3111k = null;
        m21a(context);
    }

    /**
     * 调整视图滚动量。
     *
     * @param i 滚动增量。
     */
    public void m22a(int i) {
        this.f3107g += i;
    }

    /**
     * 设置 GLSurfaceView 的渲染器并进行其他初始化。
     *
     * @param context 上下文对象。
     */
    private void m21a(Context context) {
        if (isInEditMode()) {
            return;
        }
        this.f3109i = new openGLSurface(this);
        setRenderer(this.f3109i);
//        this.f3108h = new GestureDetector(context, new GestureDetector$OnGestureListenerC0965cl(this));
        m18b();
    }

    /**
     * 调整视图滚动值，并安排下一次滚动更新。
     */
    public void m18b() {
        if (!this.f3102b && this.f3107g != 0) {
            int i = this.f3107g / 10;
            int min = Math.min(this.f3105e / 32, Math.abs(this.f3107g));
            if (Math.abs(i) < min) {
                i = Integer.signum(this.f3107g) * min;
            }
            this.f3107g -= i;
        }
        postDelayed(this.f3101a, 10L);
    }

    /**
     * 根据传入的值调整视图显示。
     *
     * @param i 用于调整视图的值。
     */
    public void m17b(int i) {
        int numViews;
        if (i == 0 || (numViews = PitchLabNative.getNumViews()) <= 0) {
            return;
        }
        int m44q = settings.m44q();
        if (i > 0) {
            m44q--;
            this.f3107g -= this.f3105e;
            while (m44q < 0) {
                m44q += numViews;
            }
        } else if (i < 0) {
            m44q = (m44q + 1) % numViews;
            this.f3107g += this.f3105e;
        }
        settings.m56h(m44q);
    }


    /**
     * 检查是否满足特定条件。
     *
     * @return 条件满足返回 true，否则返回 false。
     */
    public static boolean m14c() {
        return settings.m39v() != 0;
    }

    /**
     * 处理触摸事件，支持手势识别。
     *
     * @param motionEvent 触摸事件对象。
     * @return 总是返回 true，表示事件已处理。
     */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = this.f3102b;
        switch (motionEvent.getAction()) {
            case 0:
                this.f3102b = true;
                break;
            case 1:
                this.f3102b = false;
                break;
        }
        if (!this.f3108h.onTouchEvent(motionEvent) && z && !this.f3102b && Math.abs(this.f3107g) > this.f3105e / 2) {
            m17b(this.f3107g);
        }
        return true;
    }

    /**
     * 设置关联的 Activity。
     *
     * @param activity 关联的 Activity 对象。
     */
    public void setActivity(Activity activity) {
        this.f3103c = activity;
//        this.f3104d = this.f3103c.getWindow().findViewById(16908290);
    }
    /**
     * 设置长按监听器。
     *
     * @param onClickListener 长按事件的监听器对象。
     */
    public void setOnLongPressListener(View.OnClickListener onClickListener) {
        this.f3111k = onClickListener;
    }

    /**
     * 设置单击监听器。
     *
     * @param onClickListener 单击事件的监听器对象。
     */
    public void setOnSingleTapListener(View.OnClickListener onClickListener) {
        this.f3110j = onClickListener;
    }
}