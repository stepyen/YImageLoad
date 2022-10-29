package com.stepyen.yimageload.glide.listener;

/**
 * date：2022/10/29
 * author：stepyen
 * description：
 */
public interface ImageLoopStatusCallBack {

    /**
     *
     * @param loopFinish  是否完成循环，loopEnable为true时有效
     * @param loopCount  循环次数
     * @param frameCount  总帧数
     * @param currentFrameIndex  当前显示帧数
     */
    void onLoopCallback(boolean loopFinish,int loopCount,int frameCount,int currentFrameIndex);
}

