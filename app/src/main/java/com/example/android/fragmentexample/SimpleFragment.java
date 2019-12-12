package com.example.android.fragmentexample;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josycom.fragmentexample.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleFragment extends Fragment {

    private static final int YES = 0;
    private static final int NO = 1;
    public String mRadioButtonChoice = "None";
    OnFragmentInteractionListener mListener;
    private static final String CHOICE = "choice";


    public SimpleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_simple, container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);
        final RatingBar ratingBar = rootView.findViewById(R.id.rating_bar);

        if (getArguments().containsKey(CHOICE)){
            // A choice was made, get the choice
            mRadioButtonChoice = getArguments().getString(CHOICE);
            // Check the radio button choice
            if (!mRadioButtonChoice.equals("None")){
                radioGroup.getCheckedRadioButtonId();
            }
        }

        // Set the radioGroup onCheckedChanged listener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);
                TextView textView = rootView.findViewById(R.id.rate_article_tv);
                switch (index){
                    case YES:
                        textView.setText(R.string.yes_message);
                        mRadioButtonChoice = getString(R.string.yes);
                        mListener.onRadioButtonChoice(mRadioButtonChoice);
                        break;
                    case NO:
                        textView.setText(R.string.no_message);
                        mRadioButtonChoice = getString(R.string.no);
                        mListener.onRadioButtonChoice(mRadioButtonChoice);
                        break;
                    default:
                        mRadioButtonChoice = "None";
                        mListener.onRadioButtonChoice(mRadioButtonChoice);
                        break;
                }
            }
        });

        // Set the rating bar onCheckedChanged listener
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String myRating = getString(R.string.my_rating) + " " + rating;
                Toast.makeText(getContext(), myRating, Toast.LENGTH_SHORT).show();
            }
        });
        // Return the View for the fragment's UI
        return rootView;
    }

    public static SimpleFragment newInstance(String choice){
        SimpleFragment fragment = new SimpleFragment();
        Bundle args = new Bundle();
        args.putString(CHOICE, choice);
        fragment.setArguments(args);
        return fragment;
    }

    interface OnFragmentInteractionListener {
        void onRadioButtonChoice(String choice);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString() + getResources().getString(R.string.exception_message));
        }
    }
}
