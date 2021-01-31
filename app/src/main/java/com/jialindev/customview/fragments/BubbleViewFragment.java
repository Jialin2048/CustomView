package com.jialindev.customview.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jialindev.customview.R;

public class BubbleViewFragment extends Fragment {

    public BubbleViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("hello bubble");
        return inflater.inflate(R.layout.fragment_bubble_view, container, false);
    }
}