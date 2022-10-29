package com.stepyen.yimageload.glide.webp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifdecoder.GifHeader;
import com.facebook.animated.webp.WebPFrame;
import com.facebook.animated.webp.WebPImage;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableFrameInfo;

import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * date：2022/10/29
 * author：stepyen
 * description：
 */
public class WebpDecoder implements GifDecoder {

    private WebPImage mWebPImage;
    private BitmapProvider mProvider;
    private int mFramePointer;
    private int[] mFrameDurations;
    private int downsampledWidth;
    private int downsampledHeight;
    private boolean[] mKeyFrame;
    private int mSampleSize;
    // 缓存上一帧，用于非关键帧
    private Bitmap mCacheBmp;


    public WebpDecoder(BitmapProvider provider, WebPImage webPImage, int sampleSize) {
        mProvider = provider;
        mWebPImage = webPImage;
        mFrameDurations = webPImage.getFrameDurations();
        mKeyFrame = new boolean[mFrameDurations.length];
        downsampledWidth = webPImage.getWidth() / sampleSize;
        downsampledHeight = webPImage.getHeight() / sampleSize;
        mSampleSize = sampleSize;
    }

    @Override
    public int getWidth() {
        return mWebPImage.getWidth();
    }

    @Override
    public int getHeight() {
        return mWebPImage.getHeight();
    }

    @Override
    public ByteBuffer getData() {
        return null;
    }

    @Override
    public int getStatus() {
        return GifDecoder.STATUS_OK;
    }

    @Override
    public void advance() {
        mFramePointer = (mFramePointer + 1) % mWebPImage.getFrameCount();
    }

    @Override
    public int getDelay(int n) {
        int delay = -1;
        if ((n >= 0) && (n < mFrameDurations.length)) {
            delay = mFrameDurations[n];
        }
        return delay;
    }

    @Override
    public int getNextDelay() {
        if (mFrameDurations.length == 0 || mFramePointer < 0) {
            return 0;
        }

        return getDelay(mFramePointer);
    }

    @Override
    public int getFrameCount() {
        return mWebPImage.getFrameCount();
    }

    @Override
    public int getCurrentFrameIndex() {
        return mFramePointer;
    }

    @Override
    public void resetFrameIndex() {
        mFramePointer = -1;
    }

    @Override
    public int getLoopCount() {
        return mWebPImage.getLoopCount();
    }

    @Override
    public int getNetscapeLoopCount() {
        return mWebPImage.getLoopCount();
    }

    @Override
    public int getTotalIterationCount() {
        if (mWebPImage.getLoopCount() == 0) {
            return TOTAL_ITERATION_COUNT_FOREVER;
        }
        return mWebPImage.getFrameCount() + 1;
    }

    @Override
    public int getByteSize() {
        return mWebPImage.getSizeInBytes();
    }

    @Override
    public Bitmap getNextFrame() {
        Bitmap result = mProvider.obtain(downsampledWidth, downsampledHeight, Bitmap.Config.ARGB_8888);
        result.eraseColor(Color.TRANSPARENT);
        int currentIndex = getCurrentFrameIndex();

        WebPFrame currentFrame = mWebPImage.getFrame(currentIndex);

        int frameW = currentFrame.getWidth() / mSampleSize;
        int frameH = currentFrame.getHeight() / mSampleSize;
        int offX = currentFrame.getXOffset() / mSampleSize;
        int offY = currentFrame.getYOffset() / mSampleSize;
        Bitmap frameBmp = mProvider.obtain(frameW, frameH, Bitmap.Config.ARGB_8888);
        frameBmp.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(result);
        if(!isKeyFrame(currentIndex) && mCacheBmp != null) {
            //与上一帧混合（不需要清空上一帧）
            canvas.drawBitmap(mCacheBmp, 0, 0, null);
        }

        currentFrame.renderFrame(frameW, frameH, frameBmp);
        canvas.drawBitmap(frameBmp, offX, offY, null);

        mProvider.release(frameBmp);
        mCacheBmp = result;


        currentFrame.dispose();
        return result;
    }

    private boolean isKeyFrame(int index) {
        if (index == 0) {
            return true;
        }

        AnimatedDrawableFrameInfo curFrameInfo = mWebPImage.getFrameInfo(index);
        AnimatedDrawableFrameInfo prevFrameInfo = mWebPImage.getFrameInfo(index - 1);
        if (curFrameInfo.blendOperation == AnimatedDrawableFrameInfo.BlendOperation.NO_BLEND) {
            return true;
        } else {
            return prevFrameInfo.disposalMethod == AnimatedDrawableFrameInfo.DisposalMethod.DISPOSE_TO_BACKGROUND;
        }
    }

    private boolean isFullFrame(AnimatedDrawableFrameInfo info) {
        return info.yOffset == 0 && info.xOffset == 0
                && mWebPImage.getHeight() == info.height
                && mWebPImage.getWidth() == info.width;
    }

    @Override
    public int read(InputStream inputStream, int i) {
        return GifDecoder.STATUS_OK;
    }

    @Override
    public void clear() {
        mWebPImage.dispose();
        mWebPImage = null;
    }

    @Override
    public void setData(GifHeader gifHeader, byte[] bytes) {

    }

    @Override
    public void setData(GifHeader gifHeader, ByteBuffer byteBuffer) {

    }

    @Override
    public void setData(GifHeader gifHeader, ByteBuffer byteBuffer, int i) {

    }

    @Override
    public int read(byte[] bytes) {
        return GifDecoder.STATUS_OK;
    }

    @Override
    public void setDefaultBitmapConfig(Bitmap.Config format) {
    }
}

