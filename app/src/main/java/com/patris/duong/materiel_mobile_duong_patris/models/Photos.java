package com.patris.duong.materiel_mobile_duong_patris.models;

import android.media.ExifInterface;

import com.patris.duong.materiel_mobile_duong_patris.R;

import java.io.IOException;

/**
 * Created by Coline on 04/06/2017.
 */

public class Photos extends ExifInterface {
    String imgName;

    public Photos(String filename) throws IOException {
        super(filename);
        imgName = getAttribute(TAG_IMAGE_DESCRIPTION);
    }

    public String getImgName(){
        return imgName;
    }
}
