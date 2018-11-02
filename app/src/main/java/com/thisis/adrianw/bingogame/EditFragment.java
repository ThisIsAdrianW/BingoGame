package com.thisis.adrianw.bingogame;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thisis.adrianw.bingogame.Bingodata.Words;
import com.thisis.adrianw.bingogame.Helpers.EditListAdapter;
import com.thisis.adrianw.bingogame.databinding.FragmentEditBinding;

import java.util.List;


public class EditFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public GameViewModel model;
    private String indexForEdit;
    private EditListAdapter editListAdapter;
    List<Words> currentList;

    private String mParam1;
    private String mParam2;


    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (getArguments() != null) {
            indexForEdit = bundle.getString("titleArgument", " ");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentEditBinding binding = FragmentEditBinding.inflate(inflater, container, false);
        model = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setActivity(EditFragment.this);
        binding.setViewModel(model);
        editListAdapter = new EditListAdapter(getContext(), getActivity(), model);
        binding.editRecycleView.setAdapter(editListAdapter);
        binding.editRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (indexForEdit != null && !indexForEdit.trim().isEmpty()) {
            currentList = model.returnAllWordsStandard(indexForEdit);
            editListAdapter.setWords(currentList);

        }
        return binding.getRoot();
    }

    public void addNewItem(View view) {
        if (currentList != null && indexForEdit != null) {
            Words words = new Words();
            words.setIndexforword(indexForEdit);
            words.setWordForBingo("0. You should change this");
            currentList.add(words);
            model.inserBingoWord(words);
            currentList = model.returnAllWordsStandard(indexForEdit);
            editListAdapter.setWords(currentList);
            editListAdapter.notifyDataSetChanged();
        }


    }
}
