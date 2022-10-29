package com.stepyen.yimageload.glide.strategy;

import com.stepyen.yimageload.glide.R;
import com.stepyen.yimageload.glide.bean.RoundedCornerRadius;
import com.stepyen.yimageload.glide.bean.RoundedDirection;

/**
 * date：2022/10/29
 * author：stepyen
 * description：
 */

public class ImageLoadConfig {

    public static final int CENTER_CROP = 0;
    public static final int FIT_CENTER = 1;

    private Integer placeHolderResId; //默认占位资源
    private Integer errorResId; //错误时显示的资源

    private boolean crossFade; //是否淡入淡出动画
    private int crossDuration; //淡入淡出动画持续的时间
    private OverrideSize size; //图片最终显示在ImageView上的宽高度像素
    private int CropType = CENTER_CROP; //裁剪类型,默认为中部裁剪

    private boolean asGif; //true,强制显示的是gif动画,如果url不是gif的资源,那么会回调error()
    private boolean asBitmap;//true,强制显示为常规图片,如果是gif资源,则加载第一帧作为图片
    private boolean asDrawable;//true,强制显示为常规图片,如果是gif资源,则加载第一帧作为图片
    private boolean asSvg;//true,强制显示为svg图片
    private boolean asWebp;//true,强制显示为webp图片

    private boolean skipMemoryCache;//true,跳过内存缓存,默认false
    private DiskCache diskCache; //硬盘缓存,默认为all类型
    private LoadPriority priority;

    private float thumbnail;//设置缩略图的缩放比例0.0f-1.0f,如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示
    private String thumbnailUrl;//设置缩略图的url,如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示

    private boolean cropCircle;//圆形裁剪
    private boolean roundedCorners;//圆角处理
    private int roundingRadius;//圆角弧度
    private RoundedDirection roundedDirection;//圆角方向
    private RoundedCornerRadius roundedCornerRadius;//四个圆角大小
    private boolean grayscale;//灰度处理
    private boolean blur;//高斯模糊处理
    private boolean rotate;//旋转图片
    private int rotateDegree;//默认旋转°
    private String tag; //唯一标识


    private static int defaultResId = -1;

    private static int getDefaultResId() {
        if (!GlideImageLoader.showDefaultPic) {
            defaultResId = -1;
        } else {
//            defaultResId = R.drawable.common_image_default;
        }
        return defaultResId;
    }

    /**
     * 硬盘缓存策略
     */
    public enum DiskCache {
        NONE,//跳过硬盘缓存
        SOURCE,//仅仅保存原始分辨率的图片
        RESULT,//仅仅缓存最终分辨率的图像(降低分辨率后的或者转换后的)
        ALL//缓存所有版本的图片,默认行为
    }

    /**
     * 加载优先级策略
     */
    public enum LoadPriority {
        IMMEDIATE,
        HIGH,
        NORMAL,
        LOW
    }

    private ImageLoadConfig(ImageLoadConfig.Builder builder) {
        this.placeHolderResId = builder.placeHolderResId;
        this.errorResId = builder.errorResId;
        this.crossFade = builder.crossFade;
        this.crossDuration = builder.crossDuration;
        this.size = builder.size;
        this.CropType = builder.CropType;
        this.asGif = builder.asGif;
        this.asBitmap = builder.asBitmap;
        this.asSvg = builder.asSvg;
        this.asWebp = builder.asWebp;
        this.skipMemoryCache = builder.skipMemoryCache;
        this.diskCache = builder.diskCacheStrategy;
        this.thumbnail = builder.thumbnail;
        this.thumbnailUrl = builder.thumbnailUrl;
        this.priority = builder.priority;
        this.blur = builder.blur;
        this.cropCircle = builder.cropCircle;
        this.roundedCorners = builder.roundedCorners;
        this.roundingRadius = builder.roundingRadius;
        this.roundedDirection = builder.roundedDirection;
        this.roundedCornerRadius = builder.roundedCornerRadius;
        this.grayscale = builder.grayscale;
        this.rotate = builder.rotate;
        this.rotateDegree = builder.rotateDegree;
        this.tag = builder.tag;
    }

    /**
     * Builder类
     */
    public static class Builder {
        private int placeHolderResId = getDefaultResId();
        private int errorResId = getDefaultResId();
        private boolean crossFade = false;
        private int crossDuration;
        private OverrideSize size;
        private int CropType = CENTER_CROP;
        private boolean asGif = false;
        private boolean asBitmap = true;
        private boolean asDrawable = false;
        private boolean asSvg = false;
        private boolean asWebp = false;
        private boolean skipMemoryCache;
        private DiskCache diskCacheStrategy = DiskCache.ALL;
        private LoadPriority priority = LoadPriority.HIGH;
        private float thumbnail;
        private String thumbnailUrl;
        private boolean cropCircle;
        private boolean roundedCorners;
        private int roundingRadius;
        private RoundedDirection roundedDirection;
        private RoundedCornerRadius roundedCornerRadius;
        private boolean grayscale;
        private boolean blur;
        private boolean rotate;
        private int rotateDegree = 90;
        private String tag;

        public Builder setPlaceHolderResId(Integer placeHolderResId) {
            this.placeHolderResId = placeHolderResId;
            return this;
        }

        public Builder setErrorResId(Integer errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public Builder setCrossFade(boolean crossFade) {
            this.crossFade = crossFade;
            return this;
        }

        public Builder setCrossDuration(int crossDuration) {
            this.crossDuration = crossDuration;
            return this;
        }

        public Builder setSize(OverrideSize size) {
            this.size = size;
            return this;
        }

        public Builder setCropType(int cropType) {
            CropType = cropType;
            return this;
        }

        public Builder setAsGif(boolean asGif) {
            this.asGif = asGif;
            return this;
        }

        public Builder setAsBitmap(boolean asBitmap) {
            this.asBitmap = asBitmap;
            return this;
        }

        public Builder setAsDrawable(boolean asDrawable) {
            this.asDrawable = asDrawable;
            return this;
        }

        public Builder setAsSvg(boolean asSvg) {
            this.asSvg = asSvg;
            return this;
        }

        public Builder setAsWebp(boolean asWebp) {
            this.asWebp = asWebp;
            return this;
        }

        public Builder setSkipMemoryCache(boolean skipMemoryCache) {
            this.skipMemoryCache = skipMemoryCache;
            return this;
        }

        public Builder setDiskCacheStrategy(DiskCache diskCacheStrategy) {
            this.diskCacheStrategy = diskCacheStrategy;
            return this;
        }

        public Builder setPriority(LoadPriority priority) {
            this.priority = priority;
            return this;
        }

        public Builder setThumbnail(float thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public Builder setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder setCropCircle(boolean cropCircle) {
            this.cropCircle = cropCircle;
            return this;
        }

        public Builder setRoundedCorners(boolean roundedCorners) {
            this.roundedCorners = roundedCorners;
            return this;
        }

        public Builder setRoundingRadius(int roundingRadius) {
            this.roundingRadius = roundingRadius;
            return this;
        }

        public Builder setRoundedDirection(RoundedDirection roundedDirection) {
            this.roundedDirection = roundedDirection;
            return this;
        }

        public Builder setRoundedCornerRadius(RoundedCornerRadius roundedCornerRadius) {
            this.roundedCornerRadius = roundedCornerRadius;
            return this;
        }

        public Builder setGrayscale(boolean grayscale) {
            this.grayscale = grayscale;
            return this;
        }

        public Builder setBlur(boolean blur) {
            this.blur = blur;
            return this;
        }

        public Builder setRotate(boolean rotate) {
            this.rotate = rotate;
            return this;
        }

        public Builder setRotateDegree(int rotateDegree) {
            this.rotateDegree = rotateDegree;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public ImageLoadConfig build() {
            return new ImageLoadConfig(this);
        }
    }

    public static Builder parseBuilder(ImageLoadConfig config) {
        if (null == config) {
            config = new ImageLoadConfig.Builder().build();
        }
        Builder builder = new Builder();
        builder.placeHolderResId = config.placeHolderResId;
        builder.errorResId = config.errorResId;
        builder.crossFade = config.crossFade;
        builder.crossDuration = config.crossDuration;
        builder.size = config.size;
        builder.CropType = config.CropType;
        builder.asGif = config.asGif;
        builder.asBitmap = config.asBitmap;
        builder.asDrawable = config.asDrawable;
        builder.asSvg = config.asSvg;
        builder.asWebp = config.asWebp;
        builder.skipMemoryCache = config.skipMemoryCache;
        builder.diskCacheStrategy = config.diskCache;
        builder.thumbnail = config.thumbnail;
        builder.thumbnailUrl = config.thumbnailUrl;
        builder.priority = config.priority;
        builder.cropCircle = config.cropCircle;
        builder.roundedCorners = config.roundedCorners;
        builder.roundingRadius = config.roundingRadius;
        builder.roundedDirection = config.roundedDirection;
        builder.roundedCornerRadius = config.roundedCornerRadius;
        builder.grayscale = config.grayscale;
        builder.blur = config.blur;
        builder.rotate = config.rotate;
        builder.rotateDegree = config.rotateDegree;
        builder.tag = config.tag;

        return builder;
    }

    public Integer getPlaceHolderResId() {
        return placeHolderResId;
    }

    public Integer getErrorResId() {
        return errorResId;
    }

    public boolean isCrossFade() {
        return crossFade;
    }

    public int getCrossDuration() {
        return crossDuration;
    }

    public OverrideSize getSize() {
        return size;
    }

    public int getCropType() {
        return CropType;
    }

    public boolean isAsGif() {
        return asGif;
    }

    public boolean isAsBitmap() {
        return asBitmap;
    }

    public boolean isAsDrawable() {
        return asDrawable;
    }

    public boolean isAsSvg() {
        return asSvg;
    }

    public boolean isAsWebp() {
        return asWebp;
    }

    public boolean isSkipMemoryCache() {
        return skipMemoryCache;
    }

    public DiskCache getDiskCache() {
        return diskCache;
    }

    public LoadPriority getPriority() {
        return priority;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public boolean isCropCircle() {
        return cropCircle;
    }

    public boolean isRoundedCorners() {
        return roundedCorners;
    }

    public int getRoundingRadius() {
        return roundingRadius;
    }

    public RoundedDirection getRoundedDirection() {
        return roundedDirection;
    }

    public RoundedCornerRadius getRoundedCornerRadius() {
        return roundedCornerRadius;
    }

    public boolean isGrayscale() {
        return grayscale;
    }

    public boolean isBlur() {
        return blur;
    }

    public boolean isRotate() {
        return rotate;
    }

    public int getRotateDegree() {
        return rotateDegree;
    }

    public String getTag() {
        return tag;
    }

    /**
     * 图片最终显示在ImageView上的宽高像素
     * Created by mChenys on 2016/4/29.
     */
    public static class OverrideSize {
        private final int width;
        private final int height;

        public OverrideSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}