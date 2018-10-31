package com.thisis.adrianw.bingogame;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.thisis.adrianw.bingogame.Bingodata.IndexWord;
import com.thisis.adrianw.bingogame.Bingodata.Words;
import com.thisis.adrianw.bingogame.databinding.FragmentAddWordsBinding;

import java.util.ArrayList;
import java.util.List;


public class AddWords extends Fragment {
    private LinearLayout linearLayout;
    private ArrayList<String> wordsForStateSave = new ArrayList<String>();
    private GameViewModel model;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int MIN_BOARD_SIZE = 9;
    private String mParam1;
    private String mParam2;
    private List<IndexWord> indexWordsList;


    public AddWords() {
        // Required empty public constructor
    }

    public static AddWords newInstance(String param1, String param2) {
        AddWords fragment = new AddWords();
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
        FragmentAddWordsBinding binding = FragmentAddWordsBinding.inflate(inflater, container, false);
        model = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setActivity(AddWords.this);
        binding.setViewModel(model);
        linearLayout = binding.linearAddWords;
        if (savedInstanceState != null) {
            wordsForStateSave = savedInstanceState.getStringArrayList("savedWords");
            for (int i = 0; i < wordsForStateSave.size(); i++) {
                createEditText(container, wordsForStateSave.get(i));
            }
        }
        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        wordsForStateSave.clear();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View edit = linearLayout.getChildAt(i);
            if (edit instanceof EditText) {
                String textFromEditText = ((EditText) edit).getText().toString().trim();
                if (!textFromEditText.isEmpty() && edit.getId() != R.id.wordToAdd) {
                    wordsForStateSave.add(textFromEditText);
                }
            }
        }
        savedInstanceState.putStringArrayList("savedWords", wordsForStateSave);
    }


    public void addNewEditText(View view) {
        createEditText(view, " ");
    }

    public void saveItemsForGame(View view) {
        indexWordsList = model.returnIndexAsync();
        List<String> listOfWords = new ArrayList<String>();
        EditText editTitle = view.getRootView().findViewById(R.id.titleForList);
        LinearLayout linearLayout = view.getRootView().findViewById(R.id.linearAddWords);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View edit = linearLayout.getChildAt(i);
            if (edit instanceof EditText) {
                String textFromEditText = ((EditText) edit).getText().toString().trim();
                if (!textFromEditText.isEmpty()) {
                    listOfWords.add(textFromEditText);
                }
            }
        }
        String keyWord = editTitle.getText().toString().trim();
        if (keyWord.isEmpty()) {
            Toast toast = Toast.makeText(view.getRootView().getContext(), R.string.Toast_error_title, Toast.LENGTH_SHORT);
            toast.show();
        } else if (linearLayout.getChildCount() < MIN_BOARD_SIZE || listOfWords.size() < MIN_BOARD_SIZE) {
            Toast toast = Toast.makeText(view.getRootView().getContext(), R.string.Toast_error_count, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            IndexWord indexWord = new IndexWord();
            indexWord.setIndexforwords(keyWord);
            model.insertBingoIndex(indexWord);
            for (int j = 0; j < listOfWords.size(); j++) {
                Words words = new Words();
                words.setIndexforword(keyWord);
                words.setWordForBingo(listOfWords.get(j));
                model.inserBingoWord(words);
            }
            switchToList();
            Toast toast = Toast.makeText(view.getRootView().getContext(), R.string.Toast_saved, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void switchToList() {
        Fragment listFragment = new ListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, listFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    public void createEditText(View view, String currentText) {
        int x = linearLayout.getChildCount();
        final EditText editText = new EditText(getContext());
        editText.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setWidth(R.dimen.editTextH);
        if (!currentText.trim().isEmpty()) {
            editText.setText(currentText);
        }
        editText.setHint(R.string.add_words_for_game);
        if (x % 2 == 0) {
            editText.setBackgroundColor(getResources().getColor(R.color.bingoDarkBlue));
        } else {
            editText.setBackgroundColor(getResources().getColor(R.color.bingoLightBlue));
        }
        editText.requestFocus();
        linearLayout.addView(editText);
    }

}
