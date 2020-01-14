package com.example.submition4.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ContentModel implements Parcelable {
    private int id;
    private String title;
    private String release;
    private String description;
    private String photo;
    private String backdropPhoto;
    private String rating;

    public ContentModel() {
    }

    protected ContentModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        release = in.readString();
        description = in.readString();
        photo = in.readString();
        backdropPhoto = in.readString();
        rating = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(release);
        dest.writeString(description);
        dest.writeString(photo);
        dest.writeString(backdropPhoto);
        dest.writeString(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContentModel> CREATOR = new Creator<ContentModel>() {
        @Override
        public ContentModel createFromParcel(Parcel in) {
            return new ContentModel(in);
        }

        @Override
        public ContentModel[] newArray(int size) {
            return new ContentModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBackdropPhoto() {
        return backdropPhoto;
    }

    public void setBackdropPhoto(String backdropPhoto) {
        this.backdropPhoto = backdropPhoto;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
