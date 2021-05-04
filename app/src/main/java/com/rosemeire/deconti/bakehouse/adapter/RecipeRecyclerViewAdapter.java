package com.rosemeire.deconti.bakehouse.adapter;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.app.Application;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.databinding.RecipesListItemBinding;
import com.rosemeire.deconti.bakehouse.model.modelRecipe;
import com.rosemeire.deconti.bakehouse.viewModel.RecipesViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

/* TODO RUBRIC POINT: Use RecyclerView to treat recipes */

/* ************************************************************************************************/
/* **** Adapter to fill up recipe view
/* ************************************************************************************************/
public class RecipeRecyclerViewAdapter
        extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private List<modelRecipe> recipeArrayList;
    private Application mContext;
    private final WeakReference<EventHandler> mEventHandler;

    /* ****************************************************************************************** **
    /* **** Treat on item click
    /* ****************************************************************************************** */
    public interface EventHandler {
        void onItemClick(modelRecipe recipe);
    }

    /* ****************************************************************************************** **
    /* **** Initialization
    /* ****************************************************************************************** */
    public RecipeRecyclerViewAdapter(Application context, EventHandler eventHandler){

        mContext = context;
        mEventHandler = new WeakReference<>(eventHandler);

    }

    /* ****************************************************************************************** **
    /* **** Notify changes
    /* ****************************************************************************************** */
    public void setRecipes(List<modelRecipe> recipes) {

        this.recipeArrayList = recipes;
        notifyDataSetChanged();

    }

    /* ****************************************************************************************** **
    /* **** This gets called when each new ViewHolder is created. This happens when the RecyclerView
    /* **** is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling
    /* ****************************************************************************************** */
    @NonNull
    @Override
    public RecipeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecipesListItemBinding mRecipeBinding = DataBindingUtil.inflate(

            LayoutInflater.from(viewGroup.getContext()),

            R.layout.recipes_list_item, viewGroup, false);

            mRecipeBinding.setEventHandler(mEventHandler);

            return new ViewHolder(mRecipeBinding);
    }

    /* ****************************************************************************************** **
    /* **** OnBindViewHolder is called by the RecyclerView to display the data at the specified position
    /* ****************************************************************************************** */
    @Override
    public void onBindViewHolder(@NonNull RecipeRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        viewHolder.binding(recipeArrayList.get(position));
    }

    /* ****************************************************************************************** **
    /* **** This method simply returns the size of items to display. It is used behind the scenes
    /* **** to help layout our Views and for animations
    /* ****************************************************************************************** */
    @Override
    public int getItemCount() {

        return recipeArrayList != null ? recipeArrayList.size() : 0;

    }

    /* ****************************************************************************************** **
    /* **** A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
    /* **** a cache of the child views for a forecast item. It's also a convenient place to set an
    /* **** OnClickListener, since it has access to the adapter and the views
    /* ****************************************************************************************** */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private RecipesListItemBinding recipeBinding;
        private RecipesViewModel viewModel;

        public ViewHolder(@NonNull RecipesListItemBinding itemRecipeBinding) {
            super(itemRecipeBinding.getRoot());
            recipeBinding = itemRecipeBinding;
        }

        public void binding(modelRecipe recipe){
            viewModel = new RecipesViewModel(mContext);
            viewModel.getRecipe().setValue(recipe);
            recipeBinding.setViewModel(viewModel);
            //// TODO MAYBE CAN BE DELETED
            //// recipeBinding.imageViewRecipesItemPhoto.setImageResource(viewModel.setRoundIcon(recipe.getName()));
            setImageResource(recipe.getName(), recipeBinding.imageViewRecipesItemPhoto);
        }

        private void setImageResource (String recipeValue, ImageView imageView) {
            switch (recipeValue) {
                case "Nutella Pie":
                    imageView.setImageResource(R.mipmap.ic_bakehouse1);
                    break;
                case "Brownies":
                    imageView.setImageResource(R.mipmap.ic_bakehouse3);
                    break;
                case "Yellow Cake":
                    imageView.setImageResource(R.mipmap.ic_bakehouse5);
                    break;
                case "Cheesecake":
                    imageView.setImageResource(R.mipmap.ic_bakehouse9);
                    break;
                default :
                    imageView.setImageResource(R.mipmap.ic_bakehouse2);
                    break;
            }
        }
    }
}
