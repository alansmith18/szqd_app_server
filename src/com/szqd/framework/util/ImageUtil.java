package com.szqd.framework.util;

import com.szqd.framework.model.Resolution;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by like on 10/28/15.
 */
public class ImageUtil {
    /**
     * 获取图片分辨率
     * @param imageInput
     * @return
     * @throws Exception
     */
    public static Resolution getImageDimension(InputStream imageInput)throws Exception
    {
        Resolution resolution = new Resolution();
        try(ImageInputStream in = ImageIO.createImageInputStream(imageInput)){
            final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput(in);
                    resolution.setWidth(reader.getWidth(0));
                    resolution.setHeight(reader.getHeight(0));
                } finally {
                    reader.dispose();
                }
            }
        }

        return resolution;
    }
}
