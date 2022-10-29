package com.stepyen.yimageload.glide;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.util.Util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * date：2022/10/29
 * author：stepyen
 * description：
 */

public class GlideRoundedTransform implements Transformation<Bitmap> {

    private static final String ID = "com.sinyee.babybus.core.image.GlideRoundedTransform";
    private static final byte[] ID_BYTES = ID.getBytes(Charset.forName("UTF-8"));

    private BitmapPool mBitmapPool;

    private float radius;
    private float radiusLeftTop, radiusRightTop, radiusLeftBottom, radiusRightBottom;
    private boolean isLeftTop, isRightTop, isLeftBottom, isRightBottom;
    private boolean isCornerRadiusSame;

    /**
     * 需要设置圆角的部分
     *
     * @param leftTop     左上角
     * @param rightTop    右上角
     * @param leftBottom  左下角
     * @param rightBottom 右下角
     */
    public void setNeedCorner(boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom) {
        isLeftTop = leftTop;
        isRightTop = rightTop;
        isLeftBottom = leftBottom;
        isRightBottom = rightBottom;
    }

    /**
     * @param context 上下文
     * @param radius  圆角幅度
     */
    public GlideRoundedTransform(Context context, float radius) {
        this.mBitmapPool = Glide.get(context).getBitmapPool();
        this.radius = radius;
        isCornerRadiusSame = true;
    }

    /**
     * @param context 上下文
     * @param leftTop 左上圆角
     * @param rightTop 右上圆角
     * @param leftBottom 坐下圆角
     * @param rightBottom 右下圆角
     */
    public GlideRoundedTransform(Context context, float leftTop, float rightTop, float leftBottom, float rightBottom) {
        this.mBitmapPool = Glide.get(context).getBitmapPool();
        radiusLeftTop = leftTop;
        radiusRightTop = rightTop;
        radiusLeftBottom = leftBottom;
        radiusRightBottom = rightBottom;
        isLeftTop = true;
        isRightTop = true;
        isLeftBottom = true;
        isRightBottom = true;
        isCornerRadiusSame = false;
    }

    @Override
    public Resource<Bitmap> transform(Context context, Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        int finalWidth, finalHeight;
        //输出目标的宽高或高宽比例
        float scale;
        if (outWidth > outHeight) {
            //如果 输出宽度 > 输出高度 求高宽比
            scale = (float) outHeight / (float) outWidth;
            finalWidth = source.getWidth();
            //固定原图宽度,求最终高度
            finalHeight = (int) ((float) source.getWidth() * scale);
            if (finalHeight > source.getHeight()) {
                //如果 求出的最终高度 > 原图高度 求宽高比
                scale = (float) outWidth / (float) outHeight;
                finalHeight = source.getHeight();
                //固定原图高度,求最终宽度
                finalWidth = (int) ((float) source.getHeight() * scale);
            }
        } else if (outWidth < outHeight) {
            //如果 输出宽度 < 输出高度 求宽高比
            scale = (float) outWidth / (float) outHeight;
            finalHeight = source.getHeight();
            //固定原图高度,求最终宽度
            finalWidth = (int) ((float) source.getHeight() * scale);
            if (finalWidth > source.getWidth()) {
                //如果 求出的最终宽度 > 原图宽度 求高宽比
                scale = (float) outHeight / (float) outWidth;
                finalWidth = source.getWidth();
                finalHeight = (int) ((float) source.getWidth() * scale);
            }
        } else {
            //如果 输出宽度=输出高度
            finalHeight = source.getHeight();
            finalWidth = finalHeight;
        }

        //修正圆角
        this.radius *= (float) finalHeight / (float) outHeight;
        Bitmap outBitmap = this.mBitmapPool.get(finalWidth, finalHeight, Bitmap.Config.ARGB_8888);
        if (outBitmap == null) {
            outBitmap = Bitmap.createBitmap(finalWidth, finalHeight, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        //关联画笔绘制的原图bitmap
        BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //计算中心位置,进行偏移
        int width = (source.getWidth() - finalWidth) / 2;
        int height = (source.getHeight() - finalHeight) / 2;
        if (width != 0 || height != 0) {
            Matrix matrix = new Matrix();
            matrix.setTranslate((float) (-width), (float) (-height));
            shader.setLocalMatrix(matrix);
        }

        paint.setShader(shader);
        paint.setAntiAlias(true);

        if (isCornerRadiusSame) {
            drawRadiusSame(canvas, paint);
        } else {
            drawRadiusDifferent(canvas, paint);
        }

        return BitmapResource.obtain(outBitmap, this.mBitmapPool);
    }

    private void drawRadiusSame(Canvas canvas, Paint paint) {
        RectF rectF = new RectF(0.0F, 0.0F, (float) canvas.getWidth(), (float) canvas.getHeight());
        //先绘制圆角矩形
        canvas.drawRoundRect(rectF, this.radius, this.radius, paint);

        //左上角圆角
        if (!isLeftTop) {
            canvas.drawRect(0, 0, radius, radius, paint);
        }
        //右上角圆角
        if (!isRightTop) {
            canvas.drawRect(canvas.getWidth() - radius, 0, canvas.getWidth(), radius, paint);
        }
        //左下角圆角
        if (!isLeftBottom) {
            canvas.drawRect(0, canvas.getHeight() - radius, radius, canvas.getHeight(), paint);
        }
        //右下角圆角
        if (!isRightBottom) {
            canvas.drawRect(canvas.getWidth() - radius, canvas.getHeight() - radius, canvas.getWidth(), canvas.getHeight(), paint);
        }
    }

    private void drawRadiusDifferent(Canvas canvas, Paint paint) {
        RectF rectF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        // 左上
        if (isLeftTop) {
            float r = radiusLeftTop;
            canvas.save();
            canvas.clipRect(0, 0, canvas.getWidth() / 2, canvas.getHeight() / 2);
            canvas.drawRoundRect(rectF, r, r, paint);
            canvas.restore();
        }

        // 右上
        if (isRightTop) {
            float r = radiusRightTop;
            canvas.save();
            canvas.clipRect(canvas.getWidth() / 2, 0, canvas.getWidth(), canvas.getHeight() / 2);
            canvas.drawRoundRect(rectF, r, r, paint);
            canvas.restore();
        }

        // 右下
        if (isRightBottom) {
            float r = radiusRightBottom;
            canvas.save();
            canvas.clipRect(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight());
            canvas.drawRoundRect(rectF, r, r, paint);
            canvas.restore();
        }

        // 左下
        if (isLeftBottom) {
            float r = radiusLeftBottom;
            canvas.save();
            canvas.clipRect(0, canvas.getHeight() / 2, canvas.getWidth() / 2, canvas.getHeight());
            canvas.drawRoundRect(rectF, r, r, paint);
            canvas.restore();
        }
    }

    // 下面三个方法需要实现，不然会出现刷新闪烁
    @Override
    public boolean equals(Object o) {
        if (o instanceof GlideRoundedTransform) {
            GlideRoundedTransform other = (GlideRoundedTransform) o;
            return radius == other.radius;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Util.hashCode(ID.hashCode(), Util.hashCode(radius));
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);

        byte[] radiusData = ByteBuffer.allocate(4).putFloat(radius).array();
        messageDigest.update(radiusData);
    }
}