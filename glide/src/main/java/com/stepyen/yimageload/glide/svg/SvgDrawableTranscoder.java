package com.stepyen.yimageload.glide.svg;

import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.caverock.androidsvg.SVG;

/**
 * date：2022/10/29
 * author：stepyen
 * description：
 */
public class SvgDrawableTranscoder implements ResourceTranscoder<SVG, PictureDrawable> {

    @Override
    public Resource<PictureDrawable> transcode(
            Resource<SVG> toTranscode, Options options) {
        SVG svg = toTranscode.get();
        Picture picture = svg.renderToPicture();
        PictureDrawable drawable = new PictureDrawable(picture);
        return new SimpleResource<>(drawable);
    }
}

