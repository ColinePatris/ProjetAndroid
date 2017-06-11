package epf.projetandroid.models;

import android.media.ExifInterface;

import java.io.IOException;

public class photo extends ExifInterface {
    String imgName;

    public photo(String filename) throws IOException {
        super(filename);
        imgName = getAttribute(TAG_IMAGE_DESCRIPTION);
    }

    public String getImgName() {
        return imgName;
    }
}
