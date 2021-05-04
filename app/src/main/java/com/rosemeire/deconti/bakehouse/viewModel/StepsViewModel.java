package com.rosemeire.deconti.bakehouse.viewModel;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.model.modelStep;
import com.rosemeire.deconti.bakehouse.utilities.UtilitiesGlide;

import java.util.List;
import java.util.Objects;

/* ************************************************************************************************/
/* *** View Mode: STEPS
/* ************************************************************************************************/
public class StepsViewModel extends AndroidViewModel {

    public interface StepsEventHandler {
        void nextPositionClick();
        void previousPositionClick();
    }

    private static final String APPLICATION_ID = "com.rosemeire.deconti.bakehouse";
    private static PlayerLifeCycle mPlayerLifeCycle;

    @Nullable
    private StepsEventHandler stepsEventHandler;

    private MutableLiveData<List<modelStep>> mStepList = new MutableLiveData<>();

    private MutableLiveData<modelStep> mStep = new MutableLiveData<>();

    private int mStepSelectedId;

    private MutableLiveData<modelStep> mCurrentStep = new MutableLiveData<>();

    public StepsViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<modelStep> getStep() {
        return mStep;
    }

    public MutableLiveData<List<modelStep>> getStepList() {
        return mStepList;
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public String getStepId(){
        if (getStep().getValue() != null) {
            return getStep().getValue().getId().toString();
        } else return "";
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public MutableLiveData<modelStep> getCurrentStep() {

        return mCurrentStep;

    }
    /* TODO RUBRIC POINT: Use Exoplayer to treat videos of recipes */
    /* ****************************************************************************************** **
    /* **** Prepare video to be exhibit to user
    /* ****************************************************************************************** */
    @BindingAdapter("bind:videoUrl")
    public static void loadVideo(PlayerView view, String videoURL) {
        if (videoURL == null || videoURL.isEmpty()) {
            return;
        }

        Context context = view.getContext();
        Uri videoUri = Uri.parse(videoURL);
        DefaultDataSourceFactory sourceFactory = new DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, APPLICATION_ID),
                new DefaultBandwidthMeter());

        SimpleExoPlayer exoPlayer = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector());
        exoPlayer.prepare(new ExtractorMediaSource.Factory(sourceFactory).createMediaSource(videoUri));
        exoPlayer.setPlayWhenReady(true);

        view.setDefaultArtwork(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_bakehouse_icon));
        view.setPlayer(exoPlayer);
        if (mPlayerLifeCycle != null) {
            mPlayerLifeCycle.onSetupPlayer(exoPlayer);
        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @BindingAdapter("bind:thumbnailUrl")
    public static void loadThumbnail(ImageView view, String imageUrl) {

        if (imageUrl != null && !imageUrl.isEmpty()) {

            UtilitiesGlide.loadImage(view.getContext(), imageUrl, view);

        } else {

            view.setImageResource(R.drawable.ic_bakehouse_icon);

        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public interface PlayerLifeCycle {
        void onSetupPlayer(SimpleExoPlayer exoPlayer);
    }

    /* ****************************************************************************************** **
    /* **** Check if there is thumbnail to the recipe step
    /* ****************************************************************************************** */
    public boolean isHasThumbnail() {

        if (getCurrentStep().getValue() != null) {
            return !isHasVideo() && getCurrentStep().getValue().getThumbnailURL() != null
                    && !getCurrentStep().getValue().getThumbnailURL().isEmpty();
        }

        return false;
    }

    /* ****************************************************************************************** **
    /* **** Check if there is a video link to the recipe step
    /* ****************************************************************************************** */
    public boolean isHasVideo() {

        if (getCurrentStep().getValue() != null) {
            return getCurrentStep().getValue().getVideoURL() != null &&
                    !getCurrentStep().getValue().getVideoURL().isEmpty();
        }

        return false;
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public void setPlayerLifeCycle(PlayerLifeCycle mPlayerLifeCycle) {

        StepsViewModel.mPlayerLifeCycle = mPlayerLifeCycle;
    }

    /* ****************************************************************************************** **
    /* **** Check if previous recipe step is enable
    /* ****************************************************************************************** */
    public boolean isPrevStepEnable() {

        return mCurrentStep.getValue() != null && mCurrentStep.getValue().getId() > 0;
    }

    /* ****************************************************************************************** **
    /* **** Check if next recipe step is enable
    /* ****************************************************************************************** */
    public boolean isNextStepEnable() {

        return (mCurrentStep.getValue() != null && mCurrentStep.getValue().getId() >= 0)
                && (mStepList.getValue() != null
                && (mCurrentStep.getValue().getId() < mStepList.getValue().size() - 1));
    }

    /* ****************************************************************************************** **
    /* **** Setup next step of recipe step
    /* ****************************************************************************************** */
    public void nextStep() {

        if (stepsEventHandler != null) {
            if (isNextStepEnable()) {
                modelStep currentStep = Objects.requireNonNull(mStepList.getValue()).get(
                        Objects.requireNonNull(mCurrentStep.getValue()).getId() + 1);
                mCurrentStep.setValue(currentStep);
            }
        }
    }

    /* ****************************************************************************************** **
    /* **** Setup previous step of recipe step
    /* ****************************************************************************************** */
    public void prevStep() {

        if (stepsEventHandler != null) {
            if (isPrevStepEnable()) {
                modelStep currentStep = mStepList.getValue().get(
                        mCurrentStep.getValue().getId() - 1);
                mCurrentStep.setValue(currentStep);
            }
        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public void setStepsEventHandler(@Nullable StepsEventHandler stepsEventHandler) {
        this.stepsEventHandler = stepsEventHandler;
    }
}
