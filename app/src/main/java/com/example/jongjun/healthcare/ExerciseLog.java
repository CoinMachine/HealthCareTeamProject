package com.example.jongjun.healthcare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by jongjun on 2015-06-04.
 */

//운동 일지 클래스
public class ExerciseLog implements Serializable {
    private static final long serialVersionUID = -5228835234164263905L;
    String weight, day, memo;
    SerialBitmap image;

    ExerciseLog(){
    }

    ExerciseLog(String weight, String day, String memo, SerialBitmap image){
        this.weight=weight;
        this.day=day;
        this.memo=memo;
        this.image=image;
    }
}
