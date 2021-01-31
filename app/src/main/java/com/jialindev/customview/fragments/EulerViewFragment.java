package com.jialindev.customview.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jialindev.customview.R;

public class EulerViewFragment extends Fragment {
    public EulerViewFragment() {
        // Required empty public constructor
    }
    public static EulerViewFragment newInstance(String param1, String param2) {
        EulerViewFragment fragment = new EulerViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("hello euler");
        return inflater.inflate(R.layout.fragment_euler_view, container, false);
    }
}