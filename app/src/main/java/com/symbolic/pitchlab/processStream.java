package com.symbolic.pitchlab;

/**
 * 一个实现InterfaceC0940bn接口的类，用于处理音频样本。
 */
public class processStream implements InterfaceC0940bn {

    // 指向ActivityVisualiser的引用，用于在处理音频样本时与UI交互
    final /* synthetic */ ActivityVisualiser f3039a;

    /**
     * C0937bk类的构造函数。
     *
     * @param activityVisualiser 一个ActivityVisualiser对象的引用，用于在音频处理过程中与UI交互。
     */
    /* JADX INFO: Access modifiers changed from: package-private */
    public processStream(ActivityVisualiser activityVisualiser) {
        this.f3039a = activityVisualiser;
    }

    /**
     * 处理流式音频样本的函数。
     *
     * @param sArr 一个short数组，包含待处理的音频样本。
     * @param i 开始处理的样本索引。
     * @param i2 要处理的样本数量。
     */
    @Override // com.symbolic.pitchlab.InterfaceC0940bn
    /* renamed from: a */
    public void mo119a(short[] sArr, int i, int i2) {
        // 调用Native代码处理音频样本
        PitchLabNative.processStreamedSamples(sArr, i, i2);
    }
}
