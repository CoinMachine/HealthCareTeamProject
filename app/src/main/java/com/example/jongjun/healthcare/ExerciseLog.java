package com.example.jongjun.healthcare;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

/**
 * Created by jongjun on 2015-06-04.
 */
/*
public class CustomCreator implements Parcelable.Creator<ExerciseLog>{
    public ExerciseLog createFromParcel(Parcel src){
        return new ExerciseLog(src);
    }

    public ExerciseLog[] newArray(int size){
        return new ExerciseLog[size];
    }
}*/

//오브젝트를 Parcelable클래스로 만들어 줌
public class ExerciseLog implements Parcelable{
    String weight,day,memo;

    //writeToParcel() 메소드에서 기록한 순서와 동일해야한다.
    ExerciseLog(Parcel src){
        weight=src.readString();
        day=src.readString();
        memo=src.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void readFromParcel(Parcel in){
        weight=in.readString();
        day=in.readString();
        memo=in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(weight);
        dest.writeString(day);
        dest.writeString(memo);
    }


    //Parcel에서 데이터를 un-marshal/de-serialize 하는 단계를 추가해야함
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public ExerciseLog createFromParcel(Parcel in){
            return new ExerciseLog(in);
        }
        public ExerciseLog[] newArray(int size){
            return new ExerciseLog[size];
        }
    };
}
