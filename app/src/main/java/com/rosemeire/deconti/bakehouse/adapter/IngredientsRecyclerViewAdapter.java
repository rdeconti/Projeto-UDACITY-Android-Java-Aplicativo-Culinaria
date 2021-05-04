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

import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.databinding.IngredientsListItemBinding;
import com.rosemeire.deconti.bakehouse.model.modelIngredient;
import com.rosemeire.deconti.bakehouse.viewModel.IngredientsViewModel;

import java.util.List;

/* TODO RUBRIC POINT: Use RecyclerView to treat ingredients */

/* ************************************************************************************************/
/* **** Adapter to fill up ingredient view
/* ************************************************************************************************/
public class IngredientsRecyclerViewAdapter
        extends RecyclerView.Adapter
        <IngredientsRecyclerViewAdapter.ViewHolder> {

    private List<modelIngredient> mIngredientList;
    private Application mContext;

    /* ****************************************************************************************** **
    /* **** Initialize context
    /* ****************************************************************************************** */
    public IngredientsRecyclerViewAdapter(Application context){

        mContext = context;

    }

    /* ****************************************************************************************** **
    /* **** Notify changes
    /* ****************************************************************************************** */
    public void setIngredientList(List<modelIngredient> ingredientList) {
        this.mIngredientList = ingredientList;

        notifyDataSetChanged();

    }

    /* ****************************************************************************************** **
    /* **** This gets called when each new ViewHolder is created. This happens when the RecyclerView
    /* **** is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling
    /* ****************************************************************************************** */
    @NonNull
    @Override
    public IngredientsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        IngredientsListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), R.layout.ingredients_list_item, parent, false);

        return new ViewHolder(binding);
    }

    /* ****************************************************************************************** **
    /* **** OnBindViewHolder is called by the RecyclerView to display the data at the specified position
    /* ****************************************************************************************** */
    @Override
    public void onBindViewHolder(@NonNull IngredientsRecyclerViewAdapter.
            ViewHolder holder, int position) {

        holder.binding(mIngredientList.get(position));

    }

    /* ****************************************************************************************** **
    /* **** This method simply returns the size of items to display. It is used behind the scenes
    /* **** to help layout our Views and for animations
    /* ****************************************************************************************** */
    @Override
    public int getItemCount() {

        return mIngredientList != null ? mIngredientList.size() : 0;

    }

    /* ****************************************************************************************** **
    /* **** A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
    /* **** a cache of the child views for a forecast item. It's also a convenient place to set an
    /* **** OnClickListener, since it has access to the adapter and the views
    /* ****************************************************************************************** */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private IngredientsListItemBinding mBinding;
        private IngredientsViewModel mViewModel;

        public ViewHolder(IngredientsListItemBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
            mViewModel = new IngredientsViewModel(mContext);
        }

        public void binding(modelIngredient ingredient) {

            mViewModel.getIngredients().setValue(ingredient);
            mBinding.setViewModel(mViewModel);

        }
    }
}
