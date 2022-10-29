package com.stepyen.yimageload.glide.listener;

import android.graphics.Bitmap;

/**
 * date：2022/10/29
 * author：stepyen
 * description：
 */
public interface ImageDownLoadCallBack {

    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();
}

