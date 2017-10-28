package com.timelesssoftware.bakingapp.Ui.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.timelesssoftware.bakingapp.R;
import com.timelesssoftware.bakingapp.Utils.Glide.GlideApp;
import com.timelesssoftware.bakingapp.Utils.Glide.MyAppGlideModule;
import com.timelesssoftware.bakingapp.Utils.Network.RecipeImageType;
import com.timelesssoftware.bakingapp.Utils.Network.RecipeMediaType;
import com.timelesssoftware.bakingapp.Utils.Network.RecipeVideoType;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeStepsModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnRecipeFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Recipe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Recipe extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String RECIPE_STEPS_MODEL = "recipe_steps_model";
    private static final String PLAYER_POSITION = "PLAYER_POSIITON";

    // TODO: Rename and change types of parameters
    private RecipeStepsModel recipeStepModel;
    private String mParam2;

    private OnRecipeFragmentInteractionListener mListener;
    private TextView mRecipeDesc;
    private SimpleExoPlayerView mSimpleExoPlayer;
    private SimpleExoPlayer player;
    private Bundle mSavedInstance;
    private ImageView mRecipeImageView;
    private RecipeMediaType mediaType;
    private long mMediaTimestamp = 0;

    public Recipe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Recipe.
     */
    // TODO: Rename and change types and number of parameters
    public static Recipe newInstance(RecipeStepsModel recipeStepsModel) {
        Recipe fragment = new Recipe();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_STEPS_MODEL, recipeStepsModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstance = savedInstanceState;
        if (getArguments() != null) {
            recipeStepModel = getArguments().getParcelable(RECIPE_STEPS_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecipeDesc = view.findViewById(R.id.recipe_description);
        mSimpleExoPlayer = view.findViewById(R.id.simpleExoPlayerView);
        mRecipeDesc.setText(recipeStepModel.description);
        mRecipeImageView = view.findViewById(R.id.recipe_iv);


        mediaType = getRecipeMediaType(recipeStepModel);
        if (mediaType instanceof RecipeImageType) {
            initImageView(mediaType.getUrl());
        } else {
            initExoplayer(mediaType.getUrl());
        }


        view.findViewById(R.id.go_to_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onNextStep();
            }
        });

        view.findViewById(R.id.go_to_prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onPrevStep();
            }
        });

        if (getResources().getBoolean(R.bool.isTablet)) {
            view.findViewById(R.id.go_to_next).setVisibility(View.GONE);
            view.findViewById(R.id.go_to_prev).setVisibility(View.GONE);
            view.findViewById(R.id.frameLayout2).setVisibility(View.GONE);
        }
    }


    public void initExoplayer(String url) {
        if (url == null || url.isEmpty())
            return;
        Handler mainHandler = new Handler();
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        player =
                ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getContext(), "com.timelesssoftware.bakingapp"), bandwidthMeter);
// Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
// This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(url),
                dataSourceFactory, extractorsFactory, null, null);
/* Prepare the player with the source. */
        player.prepare(videoSource);

        mSimpleExoPlayer.setPlayer(player);
        if (mSavedInstance != null) {
            player.seekTo(mSavedInstance.getLong(PLAYER_POSITION, 0));
            player.setPlayWhenReady(true);
        }
    }

    void initImageView(String imageUrl) {
        mSimpleExoPlayer.setVisibility(View.INVISIBLE);
        mRecipeImageView.setVisibility(View.VISIBLE);
        Glide.with(getContext())
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder))
                .into(mRecipeImageView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mediaType instanceof RecipeVideoType) {
            initExoplayer(mediaType.getUrl());
            //We go back to where the user left the video, if there is a player instance
            //If there is no mMediaTimestamp value we go back to the start of the video
            if (player != null) {
                player.seekTo(mMediaTimestamp);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            mMediaTimestamp = player.getCurrentPosition();
            player.stop();
            player.release();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeFragmentInteractionListener) {
            mListener = (OnRecipeFragmentInteractionListener) context;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //If there is an image instead of the video
        if (player != null) {
            outState.putLong(PLAYER_POSITION, player.getCurrentPosition());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRecipeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onNextStep();

        void onPrevStep();
    }

    private RecipeMediaType getRecipeMediaType(RecipeStepsModel recipeStepsModel) {
        RecipeVideoType recipeVideoType = new RecipeVideoType();
        RecipeImageType recipeImageType = new RecipeImageType();
        if (recipeStepsModel.videoURL.isEmpty()) {
            if (recipeStepsModel.thumbnailURL.contains(".mp4")) {
                recipeVideoType.setUrl(recipeStepsModel.thumbnailURL);
                return recipeVideoType;
            }
        } else {
            recipeVideoType.setUrl(recipeStepsModel.videoURL);
            return recipeVideoType;
        }

        recipeImageType.setUrl(recipeStepsModel.thumbnailURL);
        return recipeImageType;
    }
}
