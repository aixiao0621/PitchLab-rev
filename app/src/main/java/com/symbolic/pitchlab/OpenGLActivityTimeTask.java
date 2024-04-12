package com.symbolic.pitchlab;

import java.util.TimerTask;

/**
 * 一个继承自TimerTask的类，用于在指定的时间间隔后执行特定操作。
 * 这个类是openGLActivity类的内部辅助类，提供了对openGLActivity中方法的定时调用功能。
 */
/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.symbolic.pitchlab.ck */
/* loaded from: classes.dex */
public class OpenGLActivityTimeTask extends TimerTask {

    /* 引用到C0963cj类的实例，用于在run方法中调用其方法。 */
    final /* synthetic */ openGLActivity f3112a;

    /**
     * C0964ck类的构造函数。
     *
     * @param openGLActivity 一个C0963cj类的实例，定时任务将调用此实例的方法。
     */
    /* JADX INFO: Access modifiers changed from: package-private */
    public OpenGLActivityTimeTask(openGLActivity openGLActivity) {
        this.f3112a = openGLActivity;
    }

    /**
     * 覆盖了TimerTask的run方法，这是定时任务执行时调用的方法。
     * 当定时器触发时，此方法会调用C0963cj类实例中的m18b方法。
     */
    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        this.f3112a.m18b(); // 调用C0963cj类实例的m18b方法。
    }
}
