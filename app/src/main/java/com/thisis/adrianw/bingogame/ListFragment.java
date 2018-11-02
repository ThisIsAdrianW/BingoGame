package com.thisis.adrianw.bingogame;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thisis.adrianw.bingogame.Bingodata.IndexWord;
import com.thisis.adrianw.bingogame.Bingodata.Words;
import com.thisis.adrianw.bingogame.Helpers.BingoListAdapter;
import com.thisis.adrianw.bingogame.databinding.FragmentListBinding;

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
        return binding.getRoot();

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem listItemMenu = menu.findItem(R.id.chooseList);
        listItemMenu.setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private int generateRandomNumber() {
        final int min = 1;
        final int max = 100;
        final int random = new Random().nextInt((max - min) + 1) + min;
        return random;
    }

    public void generateRandomList(View view) {
        int numberOfIndex = generateRandomNumber();
        IndexWord indexWord = new IndexWord();
        String indexString = getResources().getString(R.string.randomList) + " " + String.valueOf(numberOfIndex);
        indexWord.setIndexforwords(indexString);
        model.insertBingoIndex(indexWord);
        for (int i = 0; i < 24; i++) {
            Words words = new Words();
            words.setIndexforword(indexString);
            int currentValue = generateRandomNumber();
            words.setWordForBingo(String.valueOf(currentValue));
            model.inserBingoWord(words);
        }
        toastMaker(getResources().getString(R.string.savedRandom));
    }

    private void toastMaker(String toastMessage) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) getActivity().findViewById(R.id.toast_root));
        ImageView imageView = layout.findViewById(R.id.image_fot_toast);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_save));
        TextView text = (TextView) layout.findViewById(R.id.text_for_toast);
        text.setText(String.valueOf(toastMessage));
        Toast toast = new Toast(getActivity().getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
