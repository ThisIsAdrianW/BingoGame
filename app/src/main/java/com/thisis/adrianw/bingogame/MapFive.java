package com.thisis.adrianw.bingogame;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.thisis.adrianw.bingogame.databinding.FragmentMapFiveBinding;
import com.thisis.adrianw.bingogame.databinding.FragmentMapThreeBinding;


public class MapFive extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private GameViewModel model;


    public MapFive() {
        // Required empty public constructor
    }

    public static MapFive newInstance(String param1, String param2) {
        MapFive fragment = new MapFive();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final FragmentMapFiveBinding binding = FragmentMapFiveBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        model = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
        binding.setViewModel(model);
        model.setCurrentBoardModel(24);
        binding.setActivity(MapFive.this);
        binding.setList(model.returnStringList());
        return binding.getRoot();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem clearItem= menu.findItem(R.id.clearBoard);
        MenuItem changeItem = menu.findItem(R.id.changeBoard);
        clearItem.setVisible(true);
        changeItem.setTitle(R.string.set3x3);
        changeItem.setVisible(true);
    }

}
