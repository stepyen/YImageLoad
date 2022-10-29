package com.stepyen.yimageload.glide.svg;

import com.bumptech.glide.load.Options;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;
import java.io.InputStream;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

/**
 * date：2022/10/29
 * author：stepyen
 * description：
 */
public class SvgDecoder implements ResourceDecoder<InputStream, SVG> {

    @Override
    public boolean handles(InputStream source, Options options) {
        // TODO: Can we tell?
        return true;
    }

    public Resource<SVG> decode(
            InputStream source, int width, int height, Options options)
            throws IOException {
        try {
            SVG svg = SVG.getFromInputStream(source);
            if (width != SIZE_ORIGINAL) {
                svg.setDocumentWidth(width);
            }
            if (height != SIZE_ORIGINAL) {
                svg.setDocumentHeight(height);
            }
            return new SimpleResource<>(svg);
        } catch (SVGParseException ex) {
            throw new IOException("Cannot load SVG from stream", ex);
        }
    }
}
