package com.rosemeire.deconti.bakehouse.userInterface;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.adapter.StepRecyclerViewAdapter;
import com.rosemeire.deconti.bakehouse.databinding.StepsListFragmentBinding;
import com.rosemeire.deconti.bakehouse.factory.ViewModelFactory;
import com.rosemeire.deconti.bakehouse.model.modelStep;
import com.rosemeire.deconti.bakehouse.utilities.UtilitiesActivity;
import com.rosemeire.deconti.bakehouse.viewModel.StepsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.STEP_SELECTED;

/* ************************************************************************************************/
/* *** Treat steps of recipe fragment screen
/* ************************************************************************************************/
public class StepsListFragment extends Fragment implements StepRecyclerViewAdapter.EventHandler {

    public static final String STEPS_RECORD =
            StepsListFragment.class.getName().concat(".STEPS_RECORD");

    private ArrayList<modelStep> mSteps;
    private StepRecyclerViewAdapter mAdapter;
    private StepsListFragmentBinding mBinding;
    private boolean isRunningOnTabletView;

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        /* TODO RUBRIC POINT: Use third-party library: DATABINDING */

        mBinding = DataBindingUtil.inflate(inflater, R.layout.steps_list_fragment, container,
                false);

        ViewModelFactory factory = ViewModelFactory.getInstance(Objects.requireNonNull(getActivity()).getApplication());
        StepsViewModel mViewModel = ViewModelProviders.of(this, factory).get(StepsViewModel.class);

        isRunningOnTabletView = mBinding.getRoot().findViewById(R.id.frameLayout_stepDetail) != null;

        if (mSteps != null && getContext() != null) {
            mBinding.include.stepList.setLayoutManager(new LinearLayoutManager(getActivity()));
            mBinding.include.stepList.addItemDecoration(
                    new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            mAdapter = new StepRecyclerViewAdapter(getActivity().getApplication(), this);
            mBinding.include.stepList.setAdapter(mAdapter);
            mViewModel.getStepList().setValue(mSteps);

            if (isRunningOnTabletView) {
                startStepDetailActivity(mSteps.get(0));
            }


        }

        mViewModel.getStepList().observe(this, new Observer<List<modelStep>>() {
            @Override
            public void onChanged(@Nullable List<modelStep> steps) {
                if(steps != null) {
                    mAdapter.setStepsList(steps);
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

        if (getArguments() != null && getArguments().containsKey(STEPS_RECORD)) {
            mSteps = getArguments().getParcelableArrayList(STEPS_RECORD);
        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onItemClick(modelStep step) {

        startStepDetailActivity(step);

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    private void startStepDetailActivity(modelStep step){

        if (isRunningOnTabletView) {

            Bundle arguments = new Bundle();

            arguments.putParcelable(STEP_SELECTED, step);

            StepsDetailFragment fragment =  new StepsDetailFragment();

            fragment.setArguments(arguments);

            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout_stepDetail, fragment)
                        .commit();
            }

        } else {

            Bundle extras = new Bundle();
            extras.putParcelableArrayList(StepsDetailActivity.STEPS_RECORD, mSteps);
            extras.putParcelable(StepsDetailActivity.STEP_SELECTED, step);
            StepsDetailActivity.startActivity((UtilitiesActivity) Objects.requireNonNull(getActivity()), extras);
        }
    }
}
