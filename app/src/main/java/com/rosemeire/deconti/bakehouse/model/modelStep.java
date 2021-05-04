package com.rosemeire.deconti.bakehouse.model;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/* TODO RUBRIC POINT: RECOMMENDATION FROM LAST PROJECT REVIEW: Use Parcelable */

@Entity(foreignKeys = @ForeignKey(
        entity = modelRecipe.class,
        parentColumns = "id",
        childColumns = "recipeId",
        onDelete = CASCADE))

public class modelStep implements Parcelable{

@SerializedName("id")
@Expose
@PrimaryKey
private Integer id;
private int recipeId;
@SerializedName("shortDescription")
@Expose
private String shortDescription;
@SerializedName("description")
@Expose
private String description;
@SerializedName("videoURL")
@Expose
private String videoURL;
@SerializedName("thumbnailURL")
@Expose
private String thumbnailURL;
    public modelStep(){}

    protected modelStep(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        recipeId = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<modelStep> CREATOR = new Creator<modelStep>() {
        @Override
        public modelStep createFromParcel(Parcel in) {
            return new modelStep(in);
        }

        @Override
        public modelStep[] newArray(int size) {
            return new modelStep[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeInt(recipeId);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}