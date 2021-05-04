package com.rosemeire.deconti.bakehouse.userInterface;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.databinding.StepsDetailActivityBinding;
import com.rosemeire.deconti.bakehouse.factory.ViewModelFactory;
import com.rosemeire.deconti.bakehouse.model.modelStep;
import com.rosemeire.deconti.bakehouse.utilities.UtilitiesActivity;
import com.rosemeire.deconti.bakehouse.viewModel.StepsViewModel;

import java.util.ArrayList;

import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.RECIPE_NAME;

/* ************************************************************************************************/
/* *** Treat step details of recipe
/* ***
/* ************************************************************************************************/
public class StepsDetailActivity extends UtilitiesActivity {

    public static final String STEPS_RECORD =  StepsDetailActivity.class.getName().concat(".STEP_RECORD");
    public static final String STEP_SELECTED =  StepsDetailActivity.class.getName().concat(".STEP_SELECTED");
    private static final String FRAGMENT_PAGE = StepsDetailActivity.class.getName();

    private StepsDetailFragment mStepsDetailFragment;

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public static void startActivity(UtilitiesActivity activity, Bundle extras) {
        activity.startActivity(new Intent(activity, StepsDetailActivity.class).putExtras(extras));
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* TODO RUBRIC POINT: Use third-party library: DATABINDING */

        StepsDetailActivityBinding mBinding = DataBindingUtil.setContentView(
                this, R.layout.steps_detail_activity);

        setSupportActionBar(mBinding.toolbarStepDetail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mBinding.toolbarStepDetail.setSubtitle(RECIPE_NAME);
        mBinding.toolbarStepDetail.setSubtitleTextColor(getResources().getColor(R.color.titleColor));

        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        final StepsViewModel mViewModel = ViewModelProviders.of(this, factory).get(StepsViewModel.class);

        if (getIntent().getExtras() != null
                && getIntent().hasExtra(STEPS_RECORD)
                && getIntent().hasExtra(STEP_SELECTED)) {

            if (savedInstanceState != null && savedInstanceState.containsKey(FRAGMENT_PAGE)) {
                mStepsDetailFragment = (StepsDetailFragment) getSupportFragmentManager()
                        .getFragment(savedInstanceState, FRAGMENT_PAGE);
            }

            ArrayList<modelStep> steps = getIntent().getParcelableArrayListExtra(STEPS_RECORD);
            mViewModel.getStepList().setValue(steps);

            mViewModel.getCurrentStep().setValue((modelStep)getIntent().getParcelableExtra(STEP_SELECTED));

            setupFragment();

            mBinding.setLifecycleOwner(this);

        } else {

            finish();

        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    private void setupFragment(){

        if (mStepsDetailFragment == null) {
            mStepsDetailFragment = new StepsDetailFragment();
        }

        if (!mStepsDetailFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameLayout_stepDetail, mStepsDetailFragment)
                    .commit();
        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, FRAGMENT_PAGE, mStepsDetailFragment);
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public static StepsViewModel obtainViewModel(FragmentActivity activity) {

        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(StepsViewModel.class);

    }
}
