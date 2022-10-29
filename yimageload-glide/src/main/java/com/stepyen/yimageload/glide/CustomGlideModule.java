package com.stepyen.yimageload.glide;

/**
 * date：2022/10/29
 * author：stepyen
 * description：
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.caverock.androidsvg.SVG;
import com.stepyen.yimageload.glide.svg.SvgDecoder;
import com.stepyen.yimageload.glide.svg.SvgDrawableTranscoder;
import com.stepyen.yimageload.glide.webp.WebpBytebufferDecoder;
import com.stepyen.yimageload.glide.webp.WebpDrawable;
import com.stepyen.yimageload.glide.webp.WebpResourceDecoder;

import java.io.InputStream;
import java.nio.ByteBuffer;

@GlideModule
public class CustomGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(
            Context context, Glide glide, Registry registry) {
        registry.prepend(InputStream.class, WebpDrawable.class, new WebpResourceDecoder(context,glide))
                .prepend(ByteBuffer.class, WebpDrawable.class, new WebpBytebufferDecoder(context,glide))
                .register(SVG.class, PictureDrawable.class, new SvgDrawableTranscoder())
                .append(InputStream.class, SVG.class, new SvgDecoder());


    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}

