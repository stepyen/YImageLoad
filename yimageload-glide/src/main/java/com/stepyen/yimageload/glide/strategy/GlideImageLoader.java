package com.stepyen.yimageload.glide.strategy;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.stepyen.yimageload.glide.GlideRoundedTransform;
import com.stepyen.yimageload.glide.bean.RoundedCornerRadius;
import com.stepyen.yimageload.glide.bean.RoundedDirection;
import com.stepyen.yimageload.glide.listener.BitmapLoaderListener;
import com.stepyen.yimageload.glide.listener.ImageLoaderListener;
import com.stepyen.yimageload.glide.listener.ImageLoopStatusCallBack;
import com.stepyen.yimageload.glide.svg.SvgSoftwareLayerSetter;
import com.stepyen.yimageload.glide.util.DensityUtils;
import com.stepyen.yimageload.glide.webp.WebpDrawable;


import java.util.concurrent.Executors;

/**
 * date：2022/10/29
 * author：stepyen
 * description：图片加载器的glide实现
 */
public class GlideImageLoader implements IImageLoader {
    /**
     * 是否显示默认图
     */
    public static Boolean showDefaultPic = true;

    /**
     * 轮播次数  -1 无限轮播
     */
    private int maxLoopCount =-1;
    /**
     * 轮播信息回调
     */
    private ImageLoopStatusCallBack loopStatusCallBack;
    /**
     * 加载状态回调
     */
    private ImageLoaderListener loaderListener;

    @Override
    public void loadGif(ImageView view, String gifUrl) {
        loadGif(view, gifUrl, null);
    }

    @Override
    public void loadGif(ImageView view, String gifUrl, ImageLoadConfig config) {
        loadGif(view, gifUrl, config, null);
    }

    @Override
    public void loadGif(ImageView view, String gifUrl, ImageLoadConfig config, ImageLoaderListener listener) {
        if (config == null){
            config = new ImageLoadConfig.Builder().build();
        }
        loadImage(view, gifUrl, ImageLoadConfig.parseBuilder(config)
                .setAsBitmap(false).setAsDrawable(false).setAsWebp(false)
                .setAsSvg(false).setAsGif(true).build(), listener);
    }

    @Override
    public void loadImage(ImageView view, String imgUrl) {
        loadImage(view, imgUrl, null);
    }

    @Override
    public void loadImage(ImageView view, String imgUrl, ImageLoadConfig config) {
        loadImage(view, imgUrl, config, null);
    }

    @Override
    public void loadImage(ImageView view, int resourceId, ImageLoadConfig config) {
        loadImage(view, resourceId, config, null);
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadImage(ImageView view, String imgUrl, ImageLoadConfig config, ImageLoaderListener listener) {
        loadImage(view, imgUrl, config, listener,-1,null);
    }

    /**
     *
     * @param view
     * @param imgUrl
     * @param config
     * @param listener
     * @param maxLoopCount  WebpDrawable#LOOP_FOREVER -1为无限循环
     * @param imageLoopStatusCallBack
     */
    private void loadImage(ImageView view, String imgUrl, ImageLoadConfig config, final ImageLoaderListener listener,final int maxLoopCount,final ImageLoopStatusCallBack imageLoopStatusCallBack) {
        if (null == imgUrl) {
            imgUrl = "";
        }
        if (view == null){
            //视图为空时什么都不做
            return;
        }
        if (null == config) {
            config = new ImageLoadConfig.Builder().build();
        }

        try {
            RequestOptions options = new RequestOptions();
            RequestBuilder builder = null;
            Context context = view.getContext();
            this.maxLoopCount = maxLoopCount;
            this.loopStatusCallBack = imageLoopStatusCallBack;
            this.loaderListener = listener;
            if (config.isAsGif()) {
                // gif类型
                builder = Glide.with(context).asGif().load(imgUrl);
            } else if (config.isAsBitmap()) {
                // bitmap 类型
                builder = Glide.with(context).asBitmap().load(imgUrl);
                options.dontAnimate();
            } else if (config.isAsDrawable()) {
                // bitmap 类型
                builder = Glide.with(context).asDrawable().load(imgUrl);
                options.dontAnimate();
            }else if (config.isAsSvg()) {
                // svg 类型
                builder = Glide.with(context).as(PictureDrawable.class).listener(new SvgSoftwareLayerSetter()).load(imgUrl);
            }else if (config.isAsWebp()) {
                // webp 类型
                builder = Glide.with(context).load(imgUrl);
                setListener(builder);
            }

            if (config.isCrossFade()) {
                // 渐入渐出动画
                if (config.isAsBitmap()){
                    builder = builder.transition(BitmapTransitionOptions.withCrossFade());
                }else{
                    builder = builder.transition(DrawableTransitionOptions.withCrossFade());
                }
            }
//            // 缓存设置
//            DiskCacheStrategy strategy = DiskCacheStrategy.ALL;
//            switch (config.getDiskCache()) {
//                case NONE:
//                    strategy = DiskCacheStrategy.NONE;
//                    break;
//                case SOURCE:
//                    strategy = DiskCacheStrategy.SOURCE;
//                    break;
//                case RESULT:
//                    strategy = DiskCacheStrategy.RESULT;
//                    break;
//                case ALL:
//                    strategy = DiskCacheStrategy.ALL;
//                    break;
//                default:
//                    break;
//            }
            Priority priority = Priority.HIGH;
            switch (config.getPriority()) {
                case IMMEDIATE:
                    priority = Priority.IMMEDIATE;
                    break;
                case HIGH:
                    priority = Priority.HIGH;
                    break;
                case NORMAL:
                    priority = Priority.NORMAL;
                    break;
                case LOW:
                    priority = Priority.LOW;
                    break;
                default:
                    break;
            }
            options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(config.isSkipMemoryCache())
                    .priority(priority);
//            if (null != config.getTag()) {
//                builder.signature(new StringSignature(config.getTag()));
//            } else {
//                builder.signature(new StringSignature(imgUrl));
//            }
//            if (config.isBlur()) {
//                options.transform(new BlurTransformation(context));
//            }
            //WEBP按原样输出不做调整
            if (!config.isAsWebp()) {
                if (!config.isRoundedCorners() && config.getCropType() == ImageLoadConfig.CENTER_CROP) {
                    options.centerCrop();
                } else {
                    options.fitCenter();
                }
            }
            if (config.isRoundedCorners()) {
                int roundingRadius = 0 != config.getRoundingRadius() ? config.getRoundingRadius() : 5;
                if (config.getCropType() == ImageLoadConfig.CENTER_CROP) {
                    RoundedDirection roundedDirection = config.getRoundedDirection();
                    RoundedCornerRadius roundedCornerRadius = config.getRoundedCornerRadius();
                    if (null != roundedDirection) {
                        GlideRoundedTransform transform;
                        if (roundedCornerRadius != null) {
                            transform = new GlideRoundedTransform(context, roundedCornerRadius.leftTop, roundedCornerRadius.rightTop, roundedCornerRadius.leftBottom, roundedCornerRadius.rightBottom);
                        } else {
                            transform = new GlideRoundedTransform(context, DensityUtils.dp2px(context,roundingRadius));
                        }
                        transform.setNeedCorner(roundedDirection.leftTop, roundedDirection.rightTop, roundedDirection.leftBottom, roundedDirection.rightBottom);
                        options.transforms(new CenterCrop(), transform);
                    } else {
                        if (roundedCornerRadius != null) {
                            GlideRoundedTransform transform = new GlideRoundedTransform(context, roundedCornerRadius.leftTop, roundedCornerRadius.rightTop, roundedCornerRadius.leftBottom, roundedCornerRadius.rightBottom);
                            options.transforms(new CenterCrop(), transform);
                        } else {
                            options.transforms(new CenterCrop(), new RoundedCorners(DensityUtils.dp2px(context, roundingRadius)));
                        }
                    }
                } else {
                    RoundedDirection roundedDirection = config.getRoundedDirection();
                    RoundedCornerRadius roundedCornerRadius = config.getRoundedCornerRadius();
                    if (null != roundedDirection) {
                        GlideRoundedTransform transform;
                        if (roundedCornerRadius != null) {
                            transform = new GlideRoundedTransform(context, roundedCornerRadius.leftTop, roundedCornerRadius.rightTop, roundedCornerRadius.leftBottom, roundedCornerRadius.rightBottom);
                        } else {
                            transform = new GlideRoundedTransform(context, DensityUtils.dp2px(context, roundingRadius));
                        }
                        transform.setNeedCorner(roundedDirection.leftTop, roundedDirection.rightTop, roundedDirection.leftBottom, roundedDirection.rightBottom);
                        options.transform(transform);
                    } else {
                        if (roundedCornerRadius != null) {
                            GlideRoundedTransform transform = new GlideRoundedTransform(context, roundedCornerRadius.leftTop, roundedCornerRadius.rightTop, roundedCornerRadius.leftBottom, roundedCornerRadius.rightBottom);
                            options.transform(transform);
                        } else {
                            options.transform(new RoundedCorners(DensityUtils.dp2px(context, roundingRadius)));
                        }
                    }
                }
            }
            if (config.getThumbnail() > 0.0f) {
                builder.thumbnail(config.getThumbnail());
            }
            if (config.getErrorResId() > 0) {
                options.error(config.getErrorResId());
            }
            if (config.getPlaceHolderResId() > 0) {
                options.placeholder(config.getPlaceHolderResId());
            }
            if (config.isCropCircle()) {
                options.circleCrop();
            }
            if (null != config.getSize()) {
                options.override(config.getSize().getWidth(), config.getSize().getHeight());
            }
            if (null != listener && !config.isAsWebp()) {
                setListener(builder);
            }
            if (null != config.getThumbnailUrl()) {
                RequestBuilder thumbnailRequest = Glide.with(context).asBitmap().load(config.getThumbnailUrl());
                builder.thumbnail(thumbnailRequest).apply(options).into(view);
            } else {
                builder.apply(options).into(view);
            }
        } catch (Exception e) {
            if (config != null && config.getErrorResId() > 0) {
                view.setImageResource(config.getErrorResId());
            }
        }
    }

    @Override
    public void loadImage(ImageView view, int resourceId, ImageLoadConfig config, ImageLoaderListener listener) {
        loadImage(view, resourceId, config, listener,-1,null);
    }
    private void loadImage(ImageView view, int resourceId, ImageLoadConfig config, ImageLoaderListener listener, int maxLoopCount, ImageLoopStatusCallBack imageLoopStatusCallBack) {
        if (0 == resourceId) {
            //imgUrl is null
        }
        if (view == null){
            //视图为空时什么都不做
            return;
        }
        if (null == config) {
            config = new ImageLoadConfig.Builder().build();
        }

        try {
            RequestOptions options = new RequestOptions();
            RequestBuilder builder = null;
            this.maxLoopCount = maxLoopCount;
            this.loopStatusCallBack = imageLoopStatusCallBack;
            this.loaderListener = listener;
            Context context = view.getContext();
            if (config.isAsGif()) {
                // gif类型
                builder = Glide.with(context).asGif().load(resourceId);
            } else if (config.isAsBitmap()) {
                // bitmap 类型
                builder = Glide.with(context).asBitmap().load(resourceId);
                options.dontAnimate();
            } else if (config.isAsDrawable()) {
                // bitmap 类型
                builder = Glide.with(context).asDrawable().load(resourceId);
                options.dontAnimate();
            }else if (config.isAsSvg()) {
                // svg 类型
                Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/"
                        + resourceId);
                builder = Glide.with(context).as(PictureDrawable.class).listener(new SvgSoftwareLayerSetter()).load(uri);
            }else if (config.isAsWebp()) {
                // webp 类型
                builder = Glide.with(context).load(resourceId);
                setListener(builder);
            }

            if (config.isCrossFade()) {
                // 渐入渐出动画
                if (config.isAsBitmap()){
                    builder = builder.transition(BitmapTransitionOptions.withCrossFade());
                }else{
                    builder = builder.transition(DrawableTransitionOptions.withCrossFade());
                }
            }
            //WEBP按原样输出不做调整
            if (!config.isAsWebp()) {
                if (config.getCropType() == ImageLoadConfig.CENTER_CROP) {
                    options.centerCrop();
                } else {
                    options.fitCenter();
                }
            }
//            // 缓存设置
//            DiskCacheStrategy strategy = DiskCacheStrategy.ALL;
//            switch (config.getDiskCache()) {
//                case NONE:
//                    strategy = DiskCacheStrategy.NONE;
//                    break;
//                case SOURCE:
//                    strategy = DiskCacheStrategy.SOURCE;
//                    break;
//                case RESULT:
//                    strategy = DiskCacheStrategy.RESULT;
//                    break;
//                case ALL:
//                    strategy = DiskCacheStrategy.ALL;
//                    break;
//                default:
//                    break;
//            }
            Priority priority = Priority.HIGH;
            switch (config.getPriority()) {
                case IMMEDIATE:
                    priority = Priority.IMMEDIATE;
                    break;
                case HIGH:
                    priority = Priority.HIGH;
                    break;
                case NORMAL:
                    priority = Priority.NORMAL;
                    break;
                case LOW:
                    priority = Priority.LOW;
                    break;
                default:
                    break;
            }
            options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(config.isSkipMemoryCache())
                    .priority(priority);
//            if (null != config.getTag()) {
//                builder.signature(new StringSignature(config.getTag()));
//            } else {
//                builder.signature(new StringSignature(imgUrl));
//            }

//            if (config.isBlur()) {
//                options.transform(new BlurTransformation(context));
//            }
            if (config.isRoundedCorners()) {
                options.transform(new RoundedCorners(DensityUtils.dp2px(context, 5)));
//                options.transform(new RoundedCornersTransformation(context, DensityUtils.dp2px(5), 0));
            }
            if (config.getThumbnail() > 0.0f) {
                builder.thumbnail(config.getThumbnail());
            }
            if (config.getErrorResId() > 0) {
                options.error(config.getErrorResId());
            }
            if (config.getPlaceHolderResId() > 0) {
                options.placeholder(config.getPlaceHolderResId());
            }
            if (config.isCropCircle()) {
                options.circleCrop();
            }

            if (null != config.getSize()) {
                options.override(config.getSize().getWidth(), config.getSize().getHeight());
            }
            if (null != listener  && !config.isAsWebp()) {
                setListener(builder);
            }
            if (null != config.getThumbnailUrl()) {
                RequestBuilder thumbnailRequest = Glide.with(context).asBitmap().load(config.getThumbnailUrl());
                builder.thumbnail(thumbnailRequest).apply(options).into(view);
            } else {
                builder.apply(options).into(view);
            }
        } catch (Exception e) {
            if (config != null && config.getErrorResId() > 0) {
                view.setImageResource(config.getErrorResId());
            }
        }
    }

    @SuppressLint("CheckResult")
    private void setListener(RequestBuilder request) {
        request.listener(new RequestListener() {
            @Override
            public boolean onLoadFailed(GlideException e, Object model, Target target, boolean isFirstResource) {
                loadImageFailed();
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                loadImageSuccess(resource);
                return false;
            }
        });
    }

    private void loadImageFailed(){
        if (loaderListener != null) {
            loaderListener.onError();
        }
    }

    private void loadImageSuccess(Object resource){
        if (resource != null && resource instanceof WebpDrawable){
            ((WebpDrawable)resource).setMaxLoopCount(maxLoopCount);
            ((WebpDrawable)resource).setLoopStatusCallBack(loopStatusCallBack);
        }
        if (loaderListener != null) {
            loaderListener.onSuccess();
        }
    }

    /**
     * 加载bitmap
     *
     * @param context
     * @param url
     * @param listener
     */
    @Override
    public void loadBitmap(Context context, String url, final BitmapLoaderListener listener) {
        if (TextUtils.isEmpty(url)) {
            if (listener != null) {
                listener.onError();
            }
        } else {
            Glide.with(context).asBitmap().
                    load(url).
                    into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            if (listener != null) {
                                listener.onSuccess(resource);
                            }
                        }

                        @Override
                        public void onLoadFailed(Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            if (listener != null) {
                                listener.onError();
                            }
                        }
                    });
        }
    }

    @Override
    public void loadSVG(ImageView imageView, String url) {
        loadSVG(imageView,url,null);
    }

    @Override
    public void loadSVG(ImageView imageView, String url, ImageLoadConfig config) {
        loadSVG(imageView,url,config,null);
    }

    @Override
    public void loadSVG(ImageView imageView, String url, ImageLoadConfig config, ImageLoaderListener listener) {
        if (config == null){
            config = new ImageLoadConfig.Builder().build();
        }
        loadImage(imageView, url,
                ImageLoadConfig.parseBuilder(config)
                        .setAsGif(false).setAsBitmap(false)
                        .setAsDrawable(false).setAsWebp(false).setAsSvg(true).build(), listener);
    }

    @Override
    public void loadSVG(ImageView imageView, int resourceId) {
        loadSVG(imageView,resourceId,null);
    }

    @Override
    public void loadSVG(ImageView imageView, int resourceId, ImageLoadConfig config) {
        loadSVG(imageView,resourceId,config,null);
    }

    @Override
    public void loadSVG(ImageView imageView, int resourceId, ImageLoadConfig config, ImageLoaderListener listener) {
        if (config == null){
            config = new ImageLoadConfig.Builder().build();
        }
        loadImage(imageView, resourceId,
                ImageLoadConfig.parseBuilder(config)
                        .setAsGif(false).setAsBitmap(false)
                        .setAsDrawable(false).setAsWebp(false).setAsSvg(true).build(), listener);
    }

    @Override
    public void loadWebp(ImageView imageView, String url) {
        loadWebp(imageView, url,null);
    }

    @Override
    public void loadWebp(ImageView imageView, String url, int maxLoopCount, ImageLoopStatusCallBack loopStatusCallBack) {
        loadWebp(imageView, url,null,maxLoopCount,loopStatusCallBack);
    }

    @Override
    public void loadWebp(ImageView imageView, String url, ImageLoadConfig config) {
        loadWebp(imageView, url,config,null);
    }

    @Override
    public void loadWebp(ImageView imageView, String url, ImageLoadConfig config, int maxLoopCount, ImageLoopStatusCallBack loopStatusCallBack) {
        loadWebp(imageView, url,config,null,maxLoopCount,loopStatusCallBack);
    }

    @Override
    public void loadWebp(ImageView imageView, String url, ImageLoadConfig config, ImageLoaderListener listener) {
        loadWebp(imageView, url, config, listener,-1,null);
    }

    @Override
    public void loadWebp(ImageView imageView, String url, ImageLoadConfig config, ImageLoaderListener listener, int maxLoopCount, ImageLoopStatusCallBack loopStatusCallBack) {
        if (config == null){
            config = new ImageLoadConfig.Builder().build();
        }
        loadImage(imageView, url,ImageLoadConfig.parseBuilder(config)
                .setAsGif(false).setAsBitmap(false)
                .setAsDrawable(false).setAsSvg(false).setAsWebp(true).build(), listener,maxLoopCount,loopStatusCallBack);
    }

    @Override
    public void loadWebp(ImageView imageView, int resourceId) {
        loadWebp(imageView,resourceId,null);
    }

    @Override
    public void loadWebp(ImageView imageView, int resourceId, int maxLoopCount, ImageLoopStatusCallBack loopStatusCallBack) {
        loadWebp(imageView,resourceId,null,maxLoopCount,loopStatusCallBack);
    }

    @Override
    public void loadWebp(ImageView imageView, int resourceId, ImageLoadConfig config) {
        loadWebp(imageView,resourceId,config,null);
    }

    @Override
    public void loadWebp(ImageView imageView, int resourceId, ImageLoadConfig config, int maxLoopCount, ImageLoopStatusCallBack loopStatusCallBack) {
        loadWebp(imageView,resourceId,config,null,maxLoopCount,loopStatusCallBack);
    }

    @Override
    public void loadWebp(ImageView imageView, int resourceId, ImageLoadConfig config, ImageLoaderListener listener) {
        loadWebp(imageView, resourceId, config, listener,-1,null);
    }

    @Override
    public void loadWebp(ImageView imageView, int resourceId, ImageLoadConfig config, ImageLoaderListener listener, int maxLoopCount, ImageLoopStatusCallBack loopStatusCallBack) {
        if (config == null){
            config = new ImageLoadConfig.Builder().build();
        }
        loadImage(imageView, resourceId,ImageLoadConfig.parseBuilder(config)
                .setAsGif(false).setAsBitmap(false)
                .setAsDrawable(false).setAsSvg(false).setAsWebp(true).build(), listener,maxLoopCount,loopStatusCallBack);
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     */
    @Override
    public void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    @Override
    public void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context
     */
    @Override
    public void clearDiskCache(final Context context) {
        try {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Glide.get(context).clearDiskCache();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除所有缓存
     *
     * @param context
     */
    @Override
    public void clearAllCache(Context context) {
        clearDiskCache(context);
        Glide.get(context).clearMemory();
    }

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     */
    @Override
    public synchronized long getDiskCacheSize(Context context) {
        /*File cacheDir = PathUtils.getDiskCacheDir(context, CacheConfig.IMG_DIR);

        if (cacheDir != null && cacheDir.exists()) {
            File[] files = cacheDir.listFiles();
            if (files != null) {
                File[] arr$ = files;
                int len$ = files.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    File imageCache = arr$[i$];
                    if (imageCache.isFile()) {
                        size += imageCache.length();
                    }
                }
            }
        }*/
        return 0L;
    }

    @Override
    public void clearTarget(View view) {
        Glide.with(view).clear(view);
    }

    @Override
    public void onTrimMemory(Context context, int level) {
//        Glide.with(context).onTrimMemory(level);
    }

    @Override
    public void onLowMemory(Context context) {
//        Glide.with(context).onLowMemory();
    }

    @Override
    public void setShowDefaultPic(boolean showDefaultPic) {
        this.showDefaultPic = showDefaultPic;
    }


}
