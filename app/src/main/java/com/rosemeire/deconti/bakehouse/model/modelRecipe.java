package com.rosemeire.deconti.bakehouse.model;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/* TODO RUBRIC POINT: RECOMMENDATION FROM LAST PROJECT REVIEW: Use Parcelable */

@Entity
public class modelRecipe implements Parcelable{

@SerializedName("id")
@Expose
@PrimaryKey
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("ingredients")
@Expose
@Ignore
private ArrayList<modelIngredient> ingredients = null;
@SerializedName("steps")
@Expose
@Ignore
private ArrayList<modelStep> steps = null;
@SerializedName("servings")
@Expose
private Integer servings;
@SerializedName("image")
@Expose
private String image;

    public modelRecipe(){}

    protected modelRecipe(Parcel in) {

        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }

        name = in.readString();

        ingredients = in.createTypedArrayList(modelIngredient.CREATOR);

        steps = in.createTypedArrayList(modelStep.CREATOR);

        if (in.readByte() == 0) {
            servings = null;
        } else {
            servings = in.readInt();
        }

        image = in.readString();
    }

    public static final Creator<modelRecipe> CREATOR = new Creator<modelRecipe>() {
        @Override
        public modelRecipe createFromParcel(Parcel in) {
            return new modelRecipe(in);
        }

        @Override
        public modelRecipe[] newArray(int size) {
            return new modelRecipe[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
    this.id = id;
    }

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public ArrayList<modelIngredient> getIngredients() {
    return ingredients;
    }

    public void setIngredients(ArrayList<modelIngredient> ingredients) {
    this.ingredients = ingredients;
    }

    public ArrayList<modelStep> getSteps() {
    return steps;
    }

    public void setSteps(ArrayList<modelStep> steps) {
    this.steps = steps;
    }

    public Integer getServings() {
    return servings;
    }

    public void setServings(Integer servings) {
    this.servings = servings;
    }

    public String getImage() {
    return image;
    }

    public void setImage(String image) {
    this.image = image;
    }

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
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        if (servings == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(servings);
        }
        dest.writeString(image);
    }
}