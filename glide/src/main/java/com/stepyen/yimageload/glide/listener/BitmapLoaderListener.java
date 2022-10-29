package com.stepyen.yimageload.glide.listener;

import android.graphics.Bitmap;

/**
 * date：2022/10/29
 * author：stepyen
 * description：
 */
public interface BitmapLoaderListener {

    void onSuccess(Bitmap b);

    void onError();
}
