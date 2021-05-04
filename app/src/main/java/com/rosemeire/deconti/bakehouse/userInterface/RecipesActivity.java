package com.rosemeire.deconti.bakehouse.userInterface;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.databinding.RecipesActivityBinding;
import com.rosemeire.deconti.bakehouse.factory.ViewModelFactory;
import com.rosemeire.deconti.bakehouse.model.modelRecipe;
import com.rosemeire.deconti.bakehouse.service.task.InsertWidgetRecipeTask;
import com.rosemeire.deconti.bakehouse.utilities.UtilitiesActivity;
import com.rosemeire.deconti.bakehouse.viewModel.RecipesViewModel;

import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.RECIPE_NAME;
import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.RECIPE_RECORD;

/* ************************************************************************************************/
/* *** Treat recipes
/* ************************************************************************************************/
public class RecipesActivity extends UtilitiesActivity implements
        InsertWidgetRecipeTask.InsertRecipeCallBack, View.OnClickListener {

    private RecipesActivityBinding mBinding;
    private modelRecipe mRecipe;
    private RecipesViewModel viewModel;

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public static void startActivity(UtilitiesActivity activity, Bundle extras) {
        activity.startActivity(new Intent(activity, RecipesActivity.class).putExtras(extras));
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* TODO RUBRIC POINT: Use third-party library: DATABINDING */

        mBinding = DataBindingUtil.setContentView(this, R.layout.recipes_activity);

        setSupportActionBar(mBinding.toolbarRecipes);

        mBinding.toolbarRecipes.setSubtitle(R.string.label_recipe_ingredients_steps);
        mBinding.toolbarRecipes.setSubtitleTextColor(getResources().getColor(R.color.titleColor));

        setupViewModel();

        if(getIntent().getExtras() != null && getIntent().hasExtra(RECIPE_RECORD)) {

            mRecipe = getIntent().getParcelableExtra(RECIPE_RECORD);

            viewModel.getRecipe().setValue(mRecipe);

        } else {

            finish();

        }

        mBinding.floatingActionButtonRecipes.setOnClickListener(this);

        mBinding.tabLayoutRecipes.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mBinding.floatingActionButtonRecipes.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mBinding.floatingActionButtonRecipes.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    private void setupViewModel() {

        final ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(RecipesViewModel.class);

        viewModel.getRecipe().observe(this, new Observer<modelRecipe>() {
            @Override
            public void onChanged(@Nullable modelRecipe recipe) {

                if (recipe != null) {

                    mBinding.viewPagerRecipes.setAdapter(new RecipesViewAdapter(getSupportFragmentManager(),
                            RecipesActivity.this, recipe));

                    mBinding.tabLayoutRecipes.setupWithViewPager(mBinding.viewPagerRecipes);

                    setupActionBar();

                }
            }
        });
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    private void setupActionBar() {

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle(mRecipe.getName());
            RECIPE_NAME = mRecipe.getName();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    @Override
    public void onInsert(modelRecipe recipe) {

        if (recipe != null) {

            WidgetRecipes.update(this);

            Snackbar saved = Snackbar.make(mBinding.getRoot(), getString(R.string.msg_widget_success)
                    , Snackbar.LENGTH_SHORT);

            ((TextView) saved.getView().findViewById(android.support.design.R.id.snackbar_text))
                    .setTextColor(getResources().getColor(android.R.color.white));

            saved.show();
        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onClick(View v) {

        if(mRecipe != null) {

            new InsertWidgetRecipeTask(getApplicationContext(), this).execute(mRecipe);

        } else {

            Snackbar error = Snackbar.make(mBinding.getRoot(), getString(R.string.msg_widget_error)
            , Snackbar.LENGTH_SHORT);

            ((TextView) error.getView().findViewById(android.support.design.R.id.snackbar_text))
                .setTextColor(getResources().getColor(android.R.color.white));

            error.show();
        }
    }
}
