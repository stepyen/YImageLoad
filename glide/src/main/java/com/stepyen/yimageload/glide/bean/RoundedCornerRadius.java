package com.stepyen.yimageload.glide.bean;

/**
 * date：2022/10/29
 * author：stepyen
 * description：
 */
public class RoundedCornerRadius {
    public float leftTop;
    public float rightTop;
    public float leftBottom;
    public float rightBottom;

    public RoundedCornerRadius(float leftTop, float rightTop, float leftBottom, float rightBottom) {
        this.leftTop = leftTop;
        this.rightTop = rightTop;
        this.leftBottom = leftBottom;
        this.rightBottom = rightBottom;
    }
}
