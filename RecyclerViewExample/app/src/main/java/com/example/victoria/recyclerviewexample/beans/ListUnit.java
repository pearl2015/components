package com.example.victoria.recyclerviewexample.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by victoria on 10/25/16.
 */

public class ListUnit implements Parcelable {
    private String topic;
    private String time;

    protected ListUnit(Parcel in) {
        topic = in.readString();
        time = in.readString();
    }

    public ListUnit(){}

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static Creator<ListUnit> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<ListUnit> CREATOR = new Creator<ListUnit>() {
        @Override
        public ListUnit createFromParcel(Parcel in) {
            return new ListUnit(in);
        }

        @Override
        public ListUnit[] newArray(int size) {
            return new ListUnit[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(topic);
        dest.writeString(time);
    }
}
