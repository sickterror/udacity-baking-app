package com.timelesssoftware.bakingapp.Ui.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timelesssoftware.bakingapp.R;
import com.timelesssoftware.bakingapp.Ui.Adapter.RecipeStepsAdapter;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeListModel;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeStepsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnRecipeStepSelectListener} interface
 * to handle interaction events.
 * Use the {@link RecipeSteps#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeSteps extends Fragment implements RecipeStepsAdapter.OnRecipeStepAdapterSelectListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String RECIPE_LIST_MODEL = "recipe_steps";

    // TODO: Rename and change types of parameters
    private String recipeSteps;
    private String mParam2;

    private OnRecipeStepSelectListener mListener;
    private RecyclerView mRecipeStepsRv;
    private RecipeListModel recipeListModel;
    private RecipeStepsAdapter mRecipeStepAdapter;
    private List<RecipeStepsModel> recipeListModelList;

    public RecipeSteps() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipeStepsModelList
     * @return A new instance of fragment RecipeStep.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeSteps newInstance(List<RecipeStepsModel> recipeStepsModelList) {
        RecipeSteps fragment = new RecipeSteps();
        Bundle args = new Bundle();
        args.putParcelableArrayList(RECIPE_LIST_MODEL, new ArrayList<Parcelable>(recipeStepsModelList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeListModelList = getArguments().getParcelableArrayList(RECIPE_LIST_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_step, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecipeStepsRv = view.findViewById(R.id.recipe_step_rv);
        mRecipeStepAdapter = new RecipeStepsAdapter(recipeListModelList, this);
        mRecipeStepsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecipeStepsRv.setAdapter(mRecipeStepAdapter);
        mRecipeStepAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeStepSelectListener) {
            mListener = (OnRecipeStepSelectListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
    }

    @Override
    public void onRecipeSelect(RecipeStepsModel recipeStepModel) {
        mListener.onFragmentInteraction(recipeStepModel);
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
    public interface OnRecipeStepSelectListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(RecipeStepsModel recipeStepsModel);
    }
}
