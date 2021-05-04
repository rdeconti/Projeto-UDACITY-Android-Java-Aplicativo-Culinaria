package com.rosemeire.deconti.bakehouse.userInterface;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.databinding.StepsDetailFragmentBinding;
import com.rosemeire.deconti.bakehouse.model.modelStep;
import com.rosemeire.deconti.bakehouse.viewModel.StepsViewModel;

import java.lang.ref.WeakReference;

import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.PLAYER_STATE;
import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.STEP_SELECTED;

/* ************************************************************************************************/
/* *** Treat steps of recipe fragment screen
/* ************************************************************************************************/
public class StepsDetailFragment extends Fragment implements StepsViewModel.PlayerLifeCycle,
        StepsViewModel.StepsEventHandler {

    private StepsDetailFragmentBinding mBinding;
    private StepsViewModel mViewModel;

    private ExoPlayer mExoPlayer;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private Long mPlayerPosition;

    public StepsDetailFragment(){}

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        /* TODO RUBRIC POINT: Use third-party library: DATABINDING */

        mBinding = DataBindingUtil.inflate(inflater, R.layout.steps_detail_fragment, container,
                false);

        if(getActivity() != null)
            mViewModel = StepsDetailActivity.obtainViewModel(getActivity());

        return mBinding.getRoot();
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            if (getArguments().containsKey(STEP_SELECTED)) {

                modelStep mCurrentStep = getArguments().getParcelable(STEP_SELECTED);

                if (mViewModel != null){
                    mViewModel.getCurrentStep().setValue(mCurrentStep);
                }

            }
        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() != null && mViewModel == null)
            mViewModel = StepsDetailActivity.obtainViewModel(getActivity());

        mViewModel.setPlayerLifeCycle(this);
        mViewModel.setStepsEventHandler(this);

        mBinding.setViewModel(mViewModel);
        mBinding.setEventHandler(new WeakReference<StepsViewModel.StepsEventHandler>(this));

        if (getArguments() != null) {
            if (getArguments().containsKey(STEP_SELECTED)) {

                modelStep mCurrentStep = getArguments().getParcelable(STEP_SELECTED);
                mViewModel.getCurrentStep().setValue(mCurrentStep);

            }
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(STEP_SELECTED)) {

                modelStep mCurrentStep = savedInstanceState.getParcelable(STEP_SELECTED);
                mViewModel.getCurrentStep().setValue(mCurrentStep);

            }

            if (savedInstanceState.containsKey(PLAYER_STATE)) {
                mPlayerPosition = savedInstanceState.getLong(PLAYER_STATE);
            }
        }

        mViewModel.getCurrentStep().observe(this, new Observer<modelStep>() {
            @Override
            public void onChanged(@Nullable modelStep step) {

                if (step != null) {

                    mBinding.setViewModel(mViewModel);
                    mBinding.setEventHandler(new WeakReference<StepsViewModel.StepsEventHandler>(
                            StepsDetailFragment.this));

                    setupToolbar();
                    releasePlayer();

                }

            }
        });

        setupToolbar();
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    private void setupToolbar() {

        if (mViewModel.getCurrentStep().getValue() != null) {

            AppCompatActivity activity = (AppCompatActivity) this.getActivity();

            if (activity != null) {

                activity.setTitle(mViewModel.getCurrentStep().getValue().getShortDescription());

            }
        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(STEP_SELECTED, mViewModel.getCurrentStep().getValue());

        if (mExoPlayer != null) {
            outState.putLong(PLAYER_STATE, mExoPlayer.getCurrentPosition());
        }
    }

    /* TODO RUBRIC POINT: Application starts and ends video features at correct moment */
    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onDetach() {
        super.onDetach();

        releasePlayer();

    }

    /* TODO RUBRIC POINT: Application starts and ends video features at correct moment */
    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onSetupPlayer(SimpleExoPlayer exoPlayer) {

        if (isAdded()) {

            mExoPlayer = exoPlayer;

            mExoPlayer.addListener(new MyPlayerListener());

            if (mMediaSession == null) {
                initializeMediaSession();
            }

            if (mPlayerPosition != null) {
                mExoPlayer.seekTo(mPlayerPosition);
                mPlayerPosition = null;
            }
        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void nextPositionClick() {

        mViewModel.nextStep();

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void previousPositionClick() {

        mViewModel.prevStep();

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    private class MyPlayerListener implements Player.EventListener {

        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest, int reason) { }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) { }

        @Override
        public void onLoadingChanged(boolean isLoading) { }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

            if (mExoPlayer != null) {

                if ((playbackState == Player.STATE_READY) && playWhenReady) {
                    mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                            mExoPlayer.getCurrentPosition(), 1f);

                } else if ((playbackState == Player.STATE_READY)) {

                    mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                            mExoPlayer.getCurrentPosition(), 1f);

                }

                mMediaSession.setPlaybackState(mStateBuilder.build());
            }
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) { }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) { }

        @Override
        public void onPlayerError(ExoPlaybackException error) { }

        @Override
        public void onPositionDiscontinuity(int reason) {  }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) { }

        @Override
        public void onSeekProcessed() { }
    }

    /* TODO RUBRIC POINT: Application starts and ends video features at correct moment */
    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    private void initializeMediaSession() {

        if (getContext() != null) {

            mMediaSession = new MediaSessionCompat(getContext(), this.getClass().getSimpleName());

            mMediaSession.setFlags(
                    MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                            MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

            mMediaSession.setMediaButtonReceiver(null);

            mStateBuilder = new PlaybackStateCompat.Builder()
                    .setActions(
                            PlaybackStateCompat.ACTION_PLAY |
                                    PlaybackStateCompat.ACTION_PAUSE |
                                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                    PlaybackStateCompat.ACTION_PLAY_PAUSE);

            mMediaSession.setPlaybackState(mStateBuilder.build());
            mMediaSession.setCallback(new MySessionCallback());
            mMediaSession.setActive(true);
        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            if (mExoPlayer != null) {
                mExoPlayer.setPlayWhenReady(true);
            }
        }

        @Override
        public void onPause() {
            if (mExoPlayer != null) {
                mExoPlayer.setPlayWhenReady(false);
            }
        }

        @Override
        public void onSkipToPrevious() {
            if (mExoPlayer != null) {
                mExoPlayer.seekTo(0);
            }
        }
    }

    /* TODO RUBRIC POINT: application starts and ends video features at correct moment */
    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    private void releasePlayer() {

        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }

        mExoPlayer = null;
    }
}
