package com.thisis.adrianw.bingogame;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.thisis.adrianw.bingogame.Bingodata.IndexWord;
import com.thisis.adrianw.bingogame.Bingodata.Words;
import com.thisis.adrianw.bingogame.Helpers.BingoListAdapter;
import com.thisis.adrianw.bingogame.databinding.FragmentListBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentListBinding binding = FragmentListBinding.inflate(inflater, container, false);
        model = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setActivity(ListFragment.this);
        final BingoListAdapter bingoListAdapter = new BingoListAdapter(getContext(), getActivity(), model);
        binding.recycleView.setAdapter(bingoListAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        model.getLiveIndex().observe(getActivity(), new Observer<List<IndexWord>>() {
            @Override
            public void onChanged(List<IndexWord> indexWords) {
                bingoListAdapter.setIndexWords(indexWords);
                if (indexWords.isEmpty()) {
                    binding.emptyTextView.setVisibility(View.VISIBLE);
                    binding.recycleView.setVisibility(View.GONE);
                } else {
                    binding.emptyTextView.setVisibility(View.GONE);
                    binding.recycleView.setVisibility(View.VISIBLE);
                }
            }
        });
        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return binding.getRoot();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem listItemMenu = menu.findItem(R.id.chooseList);
        MenuItem randomValue = menu.findItem(R.id.menuBingoGenerator);
        randomValue.setVisible(false);
        listItemMenu.setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private int generateRandomNumber() {
        final int min = 1;
        final int max = 75;
        final int random = new Random().nextInt((max - min) + 1) + min;
        return random;
    }

    public void generateRandomList(View view) {
        int numberOfIndex = generateRandomNumber();
        IndexWord indexWord = new IndexWord();
        ArrayList<Integer> randomList = new ArrayList<Integer>();
        String indexString = getResources().getString(R.string.randomList) + " " + String.valueOf(numberOfIndex);
        indexWord.setIndexforwords(indexString);
        model.insertBingoIndex(indexWord);
        while (randomList.size() < 50) {
            int newValue = generateRandomNumber();
            if (!randomList.contains(newValue)) {
                randomList.add(newValue);
            }

        }
        for (int i = 0; i < randomList.size(); i++) {
            Words words = new Words();
            words.setIndexforword(indexString);
            words.setWordForBingo(String.valueOf(randomList.get(i)));
            model.inserBingoWord(words);
        }
        model.showToast(getActivity(), getResources().getString(R.string.savedRandom), null);
    }

}
