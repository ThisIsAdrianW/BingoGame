package com.thisis.adrianw.bingogame;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.thisis.adrianw.bingogame.Bingodata.IndexWord;
import com.thisis.adrianw.bingogame.Bingodata.Words;
import com.thisis.adrianw.bingogame.databinding.FragmentAddWordsBinding;
import com.thisis.adrianw.bingogame.databinding.FragmentMapThreeBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddWords#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddWords extends Fragment {
    private GameViewModel model;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int MIN_BOARD_SIZE = 9;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<IndexWord> indexWordsList;


    public AddWords() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddWords.
     */
    // TODO: Rename and change types and number of parameters
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
        LinearLayout linearLayout = binding.linearAddWords;
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_add_words, container, false);
    }

    public void addNewEditText(View view) {
        LinearLayout linearLayout = view.getRootView().findViewById(R.id.linearAddWords);
        int x = linearLayout.getChildCount();
        int nextId = R.id.wordToAdd+x;
        final EditText editText= new EditText(getContext());
        final LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(params);
        editText.setId(nextId);
        linearLayout.addView(editText);
        Log.v("AddWords", "New id is" + nextId + " and now count is " + linearLayout.getChildCount());
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
                    listOfWords.add(i, textFromEditText);
                    Log.v("AddWords", "SaveItemsForGame now saved word"  + textFromEditText + "and count is " + listOfWords.size());
                }
            }
        }
        String keyWord = editTitle.getText().toString().trim();
        if (keyWord.isEmpty()) {
            Toast toast = Toast.makeText(view.getRootView().getContext(), R.string.Toast_error_title, Toast.LENGTH_SHORT); toast.show();
        }
        else if (linearLayout.getChildCount() < MIN_BOARD_SIZE || listOfWords.size() < MIN_BOARD_SIZE-1) {
            Toast toast = Toast.makeText(view.getRootView().getContext(), R.string.Toast_error_count, Toast.LENGTH_SHORT); toast.show();
        }
        else {
            IndexWord indexWord = new IndexWord();
            indexWord.setIndexforwords(keyWord);
            model.insertBingoIndex(indexWord);
            for (int j = 0; j < listOfWords.size(); j++) {
                Words words = new Words();
                words.setIndexforword(keyWord);
                words.setWordForBingo(listOfWords.get(j));
                model.inserBingoWord(words);
                Toast toast = Toast.makeText(view.getRootView().getContext(), R.string.Toast_saved, Toast.LENGTH_SHORT); toast.show();
                Log.v("AddWords", "Now we should save a lot of words");
            }
        }
    }

}
