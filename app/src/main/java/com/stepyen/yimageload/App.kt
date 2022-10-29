package com.stepyen.yimageload

import android.app.Application
import com.stepyen.yimageload.glide.ImageLoaderManager

/**
 * date：2022/10/28
 * author：stepyen
 * description：
 *
 */
class App:Application() {

    override fun onCreate() {
        super.onCreate()
        //图片加载库初始化和禁用兜底图逻辑
        ImageLoaderManager.getInstance().init()
        ImageLoaderManager.getInstance().setShowDefaultPic(false)
    }
}