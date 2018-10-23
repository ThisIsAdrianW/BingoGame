package com.thisis.adrianw.bingogame;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thisis.adrianw.bingogame.Bingodata.IndexWord;
import com.thisis.adrianw.bingogame.Helpers.BingoListAdapter;
import com.thisis.adrianw.bingogame.databinding.FragmentListBinding;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    private GameViewModel model;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentListBinding binding = FragmentListBinding.inflate(inflater, container, false);
        model = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
        binding.setLifecycleOwner(this);
        final BingoListAdapter bingoListAdapter = new BingoListAdapter(getActivity(), model);
        binding.recycleView.setAdapter(bingoListAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        model.getLiveIndex().observe(getActivity(), new Observer<List<IndexWord>>() {
            @Override
            public void onChanged(List<IndexWord> indexWords) {
                bingoListAdapter.setIndexWords(indexWords);
                if (indexWords.isEmpty()) {
                    binding.emptyTextView.setVisibility(View.VISIBLE);
                    binding.recycleView.setVisibility(View.GONE);
                }
                else {
                    binding.emptyTextView.setVisibility(View.GONE);
                    binding.recycleView.setVisibility(View.VISIBLE);
                }
                Log.v("MainActivity", "List changed ^_^");
            }
        });
        return binding.getRoot();

    }

}
