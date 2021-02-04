package com.jialindev.customview.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jialindev.customview.R;
import com.jialindev.customview.databinding.FragmentFindMeViewBinding;

public class FindMeViewFragment extends Fragment {
    private FragmentFindMeViewBinding binding;

    public FindMeViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFindMeViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}