package com.stepyen.yimageload.glide.bean;

/**
 * date：2022/10/29
 * author：stepyen
 * description：
 */
public class RoundedDirection {

    public boolean leftTop;
    public boolean rightTop;
    public boolean leftBottom;
    public boolean rightBottom;

    public RoundedDirection(boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom) {
        this.leftTop = leftTop;
        this.rightTop = rightTop;
        this.leftBottom = leftBottom;
        this.rightBottom = rightBottom;
    }
}
