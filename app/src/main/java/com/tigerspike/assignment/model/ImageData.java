package com.tigerspike.assignment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lydiaamanna on 07/05/17.
 */

public class ImageData implements Parcelable{
    public String title;
    public String imgUrl;
    public String description;
    public String author;
    public String date_taken;
    public String tags;

    public ImageData(){

    }

    protected ImageData(Parcel in) {
        title = in.readString();
        imgUrl = in.readString();
        description = in.readString();
        author = in.readString();
        date_taken = in.readString();
        tags = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imgUrl);
        dest.writeString(description);
        dest.writeString(author);
        dest.writeString(date_taken);
        dest.writeString(tags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageData> CREATOR = new Creator<ImageData>() {
        @Override
        public ImageData createFromParcel(Parcel in) {
            return new ImageData(in);
        }

        @Override
        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(String date_taken) {
        this.date_taken = date_taken;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }




}
