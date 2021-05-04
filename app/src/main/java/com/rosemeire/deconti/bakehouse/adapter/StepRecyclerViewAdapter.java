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
import com.rosemeire.deconti.bakehouse.databinding.StepsListItemBinding;
import com.rosemeire.deconti.bakehouse.model.modelStep;
import com.rosemeire.deconti.bakehouse.viewModel.StepsViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

/* TODO RUBRIC POINT: Use RecyclerView to treat steps */

/* ************************************************************************************************/
/* **** Adapter to fill up step view
/* ************************************************************************************************/
public class StepRecyclerViewAdapter
        extends RecyclerView.Adapter<StepRecyclerViewAdapter.ViewHolder> {

    private final WeakReference<StepRecyclerViewAdapter.EventHandler> mEventHandler;
    private List<modelStep> mStepsList;
    private Application mContext;

    public StepRecyclerViewAdapter(Application context, EventHandler eventHandler) {
        mContext = context;
        mEventHandler = new WeakReference<>(eventHandler);
    }

    /* ****************************************************************************************** **
    /* **** Notify changes
    /* ****************************************************************************************** */
    public void setStepsList(List<modelStep> mStepsList) {

        this.mStepsList = mStepsList;
        notifyDataSetChanged();

    }

    /* ****************************************************************************************** **
    /* **** Treat click on item
    /* ****************************************************************************************** */
    public interface EventHandler{
        void onItemClick(modelStep step);
    }

    /* ****************************************************************************************** **
    /* **** This gets called when each new ViewHolder is created. This happens when the RecyclerView
    /* **** is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling
    /* ****************************************************************************************** */
    @NonNull
    @Override
    public StepRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StepsListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.steps_list_item, parent, false);
        binding.setEventHandler(mEventHandler);
        return new ViewHolder(binding);
    }

    /* ****************************************************************************************** **
    /* **** OnBindViewHolder is called by the RecyclerView to display the data at the specified position
    /* ****************************************************************************************** */
    @Override
    public void onBindViewHolder(@NonNull StepRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.binding(mStepsList.get(position));
    }

    /* ****************************************************************************************** **
    /* **** This method simply returns the size of items to display. It is used behind the scenes
    /* **** to help layout our Views and for animations
    /* ****************************************************************************************** */
    @Override
    public int getItemCount() {

        return mStepsList != null ? mStepsList.size() : 0;

    }

    /* ****************************************************************************************** **
    /* **** A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
    /* **** a cache of the child views for a forecast item. It's also a convenient place to set an
    /* **** OnClickListener, since it has access to the adapter and the views
    /* ****************************************************************************************** */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private StepsListItemBinding mBinding;
        private StepsViewModel mViewModel;

        public ViewHolder(StepsListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mViewModel = new StepsViewModel(mContext);
            mBinding.setViewModel(mViewModel);
        }

        public void binding(modelStep step){
            mViewModel.getStep().setValue(step);
        }
    }
}
