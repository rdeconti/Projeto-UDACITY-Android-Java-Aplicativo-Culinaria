package com.rosemeire.deconti.bakehouse.userInterface;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.adapter.IngredientsRecyclerViewAdapter;
import com.rosemeire.deconti.bakehouse.databinding.IngredientsListFragmentBinding;
import com.rosemeire.deconti.bakehouse.factory.ViewModelFactory;
import com.rosemeire.deconti.bakehouse.model.modelIngredient;
import com.rosemeire.deconti.bakehouse.viewModel.IngredientsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.INGREDIENTS_RECORD;

/* ************************************************************************************************/
/* *** Treat recipe ingredients
/* ************************************************************************************************/
public class IngredientsListFragment extends Fragment {

    private ArrayList<modelIngredient> mIngredients;
    private IngredientsRecyclerViewAdapter mAdapter;
    private IngredientsListFragmentBinding mBinding;
    private IngredientsViewModel mViewModel;

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        /* TODO RUBRIC POINT: Use third-party library: DATABINDING */

        mBinding = DataBindingUtil.inflate(inflater, R.layout.ingredients_list_fragment, container,
                false);

        ViewModelFactory factory = ViewModelFactory.getInstance(Objects.requireNonNull(getActivity()).getApplication());
        mViewModel = ViewModelProviders.of(this, factory).get(IngredientsViewModel.class);

        if (getContext() != null && mIngredients != null) {

            mBinding.include.recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getActivity()));

            mBinding.include.recyclerViewIngredients.addItemDecoration(
                    new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

            mAdapter = new IngredientsRecyclerViewAdapter(getActivity().getApplication());

            mBinding.include.recyclerViewIngredients.setAdapter(mAdapter);

            mViewModel.getIngredientsList().setValue(mIngredients);
        }

        mViewModel.getIngredientsList().observe(this, new Observer<List<modelIngredient>>() {
            @Override
            public void onChanged(@Nullable List<modelIngredient> ingredients) {
                if(ingredients != null) {
                    mAdapter.setIngredientList(ingredients);
                }
            }
        });

        return mBinding.getRoot();
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(INGREDIENTS_RECORD)) {
            mIngredients = getArguments().getParcelableArrayList(INGREDIENTS_RECORD);
        }
    }
}
