package com.example.submition4.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_table")
public class ContentModel implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int type;
    private String title;
    private String release;
    private String description;
    private String photo;
    private String backdropPhoto;
    private String rating;
    private boolean isFavorite;

    public ContentModel(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        title = in.readString();
        release = in.readString();
        description = in.readString();
        photo = in.readString();
        backdropPhoto = in.readString();
        rating = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public ContentModel() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeString(release);
        dest.writeString(description);
        dest.writeString(photo);
        dest.writeString(backdropPhoto);
        dest.writeString(rating);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
