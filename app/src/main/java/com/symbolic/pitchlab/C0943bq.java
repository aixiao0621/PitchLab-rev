package com.symbolic.pitchlab;

import android.media.AudioRecord;

/* renamed from: com.symbolic.pitchlab.bq */
/* loaded from: classes.dex */
public class C0943bq implements InterfaceC0941bo {

    /* renamed from: a */
    private short[] f3042a;

    /* renamed from: b */
    private AudioRecord f3043b;

    /* renamed from: c */
//    private C0944br f3044c;

    /* renamed from: d */
    private InterfaceC0940bn f3045d;

    public C0943bq(int i, InterfaceC0940bn interfaceC0940bn) {
        this.f3045d = interfaceC0940bn;
        int max = Math.max(i / 10, AudioRecord.getMinBufferSize(i, 16, 2));
        this.f3042a = new short[max];
//        this.f3043b = new AudioRecord(0, i, 16, 2, max);
        if (this.f3043b.getRecordingState() != 1) {
//            throw new Exception("AudioRecord failed to initialise");
        }
        try {
            this.f3043b.startRecording();
//            this.f3044c = new C0944br(this, null);
//            this.f3044c.start();
        } catch (Exception e) {
            this.f3043b.release();
            this.f3043b = null;
            throw e;
        }
    }

    @Override // com.symbolic.pitchlab.InterfaceC0941bo
    /* renamed from: a */
    public void mo110a() {
//        if (this.f3044c != null) {
//            this.f3044c.interrupt();
//            try {
//                this.f3044c.join();
//            } catch (Exception e) {
//            }
//            this.f3044c = null;
//        }
        if (this.f3043b != null) {
            this.f3043b.stop();
            this.f3043b.release();
            this.f3043b = null;
        }
        this.f3042a = null;
        this.f3045d = null;
    }
}