package test;

import com.szqd.framework.model.Resolution;
import com.szqd.framework.util.ImageUtil;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by like on 4/27/15.
 */
public class M {
    public static void main(String[] args) throws Exception {
        File imageFile = new File("/Users/like/Desktop/800*600.jpg");
        Resolution resolution = ImageUtil.getImageDimension(new FileInputStream(imageFile));
        System.out.println(resolution.getWidth()+" x "+resolution.getHeight());
    }




}