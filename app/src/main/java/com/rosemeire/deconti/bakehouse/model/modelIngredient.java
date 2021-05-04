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

import java.util.Objects;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/* TODO RUBRIC POINT: RECOMMENDATION FROM LAST PROJECT REVIEW: Use Parcelable */

@Entity(foreignKeys = @ForeignKey(
        entity = modelRecipe.class,
        parentColumns = "id",
        childColumns = "recipeId",
        onDelete = CASCADE))

public class modelIngredient implements Parcelable{

@PrimaryKey(autoGenerate = true)
private int id;
private int recipeId;
@SerializedName("quantity")
@Expose
private Double quantity;
@SerializedName("measure")
@Expose
private String measure;
@SerializedName("ingredient")
@Expose
private String ingredient;

    public modelIngredient(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {

        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    // Treat quantity
    protected modelIngredient(Parcel in) {

        id = in.readInt();

        recipeId = in.readInt();

        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readDouble();
        }

        measure = in.readString();

        String mMeasure;

        switch (Objects.requireNonNull(measure)) {
            case "TSP":
                mMeasure = "(tea spoon)";
                break;
            case "CUP":
                mMeasure = "(cup)";
                break;
            case "TBLSP":
                mMeasure = "(table spoon)";
                break;
            case "OZ" :
                mMeasure = "(ounce)";
                break;
            case "UNIT":
                mMeasure = "(unit)";
                break;
            default:
                mMeasure = "(free measure)";
                break;
        }

        measure = mMeasure;

        ingredient = in.readString();
    }

    public static final Creator<modelIngredient> CREATOR = new Creator<modelIngredient>() {
        @Override
        public modelIngredient createFromParcel(Parcel in) {
            return new modelIngredient(in);
        }

        @Override
        public modelIngredient[] newArray(int size) {
            return new modelIngredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);

        dest.writeInt(recipeId);

        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(quantity);
        }

        dest.writeString(measure);

        dest.writeString(ingredient);
    }
}