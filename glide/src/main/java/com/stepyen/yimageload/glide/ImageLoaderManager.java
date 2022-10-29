package com.stepyen.yimageload.glide;



import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.stepyen.yimageload.glide.listener.BitmapLoaderListener;
import com.stepyen.yimageload.glide.listener.ImageLoaderListener;
import com.stepyen.yimageload.glide.listener.ImageLoopStatusCallBack;
import com.stepyen.yimageload.glide.strategy.GlideImageLoader;
import com.stepyen.yimageload.glide.strategy.IImageLoader;
import com.stepyen.yimageload.glide.strategy.ImageLoadConfig;

/**
 * date：2022/10/29
 * author：stepyen
 * description：
 */
public class ImageLoaderManager implements IImageLoader {

    private IImageLoader mImageLoader;

    private static class SingletonHolder {
        private static ImageLoaderManager instance = new ImageLoaderManager();
    }

    private ImageLoaderManager() {
    }

    public static ImageLoaderManager getInstance() {
        return SingletonHolder.instance;
    }

    public void setImageLoader(IImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    // 在application的oncreate中初始化
    public void init() {
        //        mImageLoader = new FrescoImageLoader();
        //        mImageLoader.init(context);
        mImageLoader = new GlideImageLoader();
    }

    @Override
    public void loadGif(ImageView view, String gifUrl) {
        if (mImageLoader != null) {
            mImageLoader.loadGif(view, gifUrl, null);
        }
    }

    @Override
    public void loadGif(ImageView view, String gifUrl, ImageLoadConfig config) {
        if (mImageLoader != null) {
            mImageLoader.loadGif(view, gifUrl, config, null);
        }
    }

    @Override
    public void loadGif(ImageView view, String gifUrl, ImageLoadConfig config, ImageLoaderListener listener) {
        if (mImageLoader != null) {
            mImageLoader.loadGif(view, gifUrl, config, listener);
        }
    }

    @Override
    public void loadImage(ImageView view, String imgUrl) {
        if (mImageLoader != null) {
            mImageLoader.loadImage(view, imgUrl, null);
        }
    }

    @Override
    public void loadImage(ImageView view, String imgUrl, ImageLoadConfig config) {
        if (mImageLoader != null) {
            mImageLoader.loadImage(view, imgUrl, config, null);
        }
    }

    @Override
    public void loadImage(ImageView view, int resourceId, ImageLoadConfig config) {
        if (mImageLoader != null) {
            mImageLoader.loadImage(view, resourceId, config, null);
        }
    }

    @Override
    public void loadImage(ImageView view, String imgUrl, ImageLoadConfig config, ImageLoaderListener listener) {
        if (mImageLoader != null) {
            mImageLoader.loadImage(view, imgUrl, config, listener);
        }
    }

    @Override
    public void loadImage(ImageView view, int resourceId, ImageLoadConfig config, ImageLoaderListener listener) {
        if (mImageLoader != null) {
            mImageLoader.loadImage(view, resourceId, config, listener);
        }
    }

    @Override
    public void loadBitmap(Context context, String url, BitmapLoaderListener listener) {
        if (mImageLoader != null) {
            mImageLoader.loadBitmap(context, url, listener);
        }
    }

    @Override
    public void loadSVG(ImageView imageView, String url) {
        if(mImageLoader != null) {
            mImageLoader.loadSVG(imageView, url);
        }
    }

    @Override
    public void loadSVG(ImageView imageView, String url, ImageLoadConfig config) {
        if(mImageLoader != null) {
            mImageLoader.loadSVG(imageView, url,config);
        }
    }

    @Override
    public void loadSVG(ImageView imageView, String url, ImageLoadConfig config, ImageLoaderListener listener) {
        if(mImageLoader != null) {
            mImageLoader.loadSVG(imageView, url,config,listener);
        }
    }

    @Override
    public void loadSVG(ImageView imageView, int resourceId) {
        if(mImageLoader != null) {
            mImageLoader.loadSVG(imageView, resourceId);
        }
    }

    @Override
    public void loadSVG(ImageView imageView, int resourceId, ImageLoadConfig config) {
        if(mImageLoader != null) {
            mImageLoader.loadSVG(imageView, resourceId,config);
        }
    }

    @Override
    public void loadSVG(ImageView imageView, int resourceId, ImageLoadConfig config, ImageLoaderListener listener) {
        if(mImageLoader != null) {
            mImageLoader.loadSVG(imageView, resourceId,config,listener);
        }
    }

    @Override
    public void loadWebp(ImageView imageView, String url) {
        if(mImageLoader != null) {
            mImageLoader.loadWebp(imageView, url);
        }
    }

    @Override
    public void loadWebp(ImageView imageView, String url, int maxLoopCount, ImageLoopStatusCallBack loopStatusCallBack) {
        if(mImageLoader != null) {
            mImageLoader.loadWebp(imageView, url, maxLoopCount, loopStatusCallBack);
        }
    }

    @Override
    public void loadWebp(ImageView imageView, String url, ImageLoadConfig config) {
        if(mImageLoader != null) {
            mImageLoader.loadWebp(imageView, url, config);
        }
    }

    @Override
    public void loadWebp(ImageView imageView, String url, ImageLoadConfig config, int maxLoopCount, ImageLoopStatusCallBack loopStatusCallBack) {
        if(mImageLoader != null) {
            mImageLoader.loadWebp(imageView, url, config, maxLoopCount, loopStatusCallBack);
        }
    }

    @Override
    public void loadWebp(ImageView imageView, String url, ImageLoadConfig config, ImageLoaderListener listener) {
        if(mImageLoader != null) {
            mImageLoader.loadWebp(imageView, url, config, listener);
        }
    }

    @Override
    public void loadWebp(ImageView imageView, String url, ImageLoadConfig config, ImageLoaderListener listener, int maxLoopCount, ImageLoopStatusCallBack loopStatusCallBack) {
        if(mImageLoader != null) {
            mImageLoader.loadWebp(imageView, url, config, listener, maxLoopCount, loopStatusCallBack);
        }
    }

    @Override
    public void loadWebp(ImageView imageView, int resourceId) {
        if(mImageLoader != null) {
            mImageLoader.loadWebp(imageView, resourceId);
        }
    }

    @Override
    public void loadWebp(ImageView imageView, int resourceId, int maxLoopCount, ImageLoopStatusCallBack loopStatusCallBack) {
        if(mImageLoader != null) {
            mImageLoader.loadWebp(imageView, resourceId, maxLoopCount, loopStatusCallBack);
        }
    }

    @Override
    public void loadWebp(ImageView imageView, int resourceId, ImageLoadConfig config) {
        if(mImageLoader != null) {
            mImageLoader.loadWebp(imageView, resourceId, config);
        }
    }

    @Override
    public void loadWebp(ImageView imageView, int resourceId, ImageLoadConfig config, int maxLoopCount, ImageLoopStatusCallBack loopStatusCallBack) {
        if(mImageLoader != null) {
            mImageLoader.loadWebp(imageView, resourceId, config, maxLoopCount, loopStatusCallBack);
        }
    }

    @Override
    public void loadWebp(ImageView imageView, int resourceId, ImageLoadConfig config, ImageLoaderListener listener) {
        if(mImageLoader != null) {
            mImageLoader.loadWebp(imageView, resourceId, config, listener);
        }
    }

    @Override
    public void loadWebp(ImageView imageView, int resourceId, ImageLoadConfig config, ImageLoaderListener listener, int maxLoopCount, ImageLoopStatusCallBack loopStatusCallBack) {
        if(mImageLoader != null) {
            mImageLoader.loadWebp(imageView, resourceId, config, listener, maxLoopCount, loopStatusCallBack);
        }
    }

    @Override
    public void cancelAllTasks(Context context) {
        if (mImageLoader != null) {
            mImageLoader.cancelAllTasks(context);
        }
    }

    @Override
    public void resumeAllTasks(Context context) {
        if (mImageLoader != null) {
            mImageLoader.resumeAllTasks(context);
        }
    }

    @Override
    public void clearDiskCache(Context context) {
        if (mImageLoader != null) {
            mImageLoader.clearDiskCache(context);
        }
    }

    @Override
    public void clearAllCache(Context context) {
        if (mImageLoader != null) {
            mImageLoader.clearAllCache(context);
        }
    }

    @Override
    public long getDiskCacheSize(Context context) {
        if (mImageLoader != null) {
            return mImageLoader.getDiskCacheSize(context);
        }
        return 0L;
    }

    @Override
    public void clearTarget(View view) {
        if (mImageLoader != null) {
            mImageLoader.clearTarget(view);
        }
    }

    @Override
    public void onTrimMemory(Context context, int level) {
        if (mImageLoader != null) {
            mImageLoader.onTrimMemory(context, level);
        }
    }

    @Override
    public void onLowMemory(Context context) {
        if (mImageLoader != null) {
            mImageLoader.onLowMemory(context);
        }
    }

    @Override
    public void setShowDefaultPic(boolean showDefaultPic) {
        if (mImageLoader != null) {
            mImageLoader.setShowDefaultPic(showDefaultPic);
        }
    }
}