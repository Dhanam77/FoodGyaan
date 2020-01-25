package com.example.foodgyan.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodgyan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackerFragment extends Fragment {

    private View mView;


    public TrackerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_tracker, container, false);



        return mView;
    }

}
