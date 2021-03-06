package com.thisis.adrianw.bingogame;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.thisis.adrianw.bingogame.Model.Bingo;
import com.thisis.adrianw.bingogame.databinding.FragmentMapThreeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MapThree extends Fragment implements View.OnLongClickListener {
    private GameViewModel model;
//    private List<Words> wordsList;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String indexFromBundle;
    private List<String> tempList = new ArrayList<String>();

    public MapThree() {
        // Required empty public constructor
    }

    public static MapThree newInstance(String param1, String param2) {
        MapThree fragment = new MapThree();
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
        if (bundle != null) {
            indexFromBundle = bundle.getString("titleArgument", " ");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentMapThreeBinding binding = FragmentMapThreeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        model = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
        model.cancelToast();
        binding.setTestString(model.testString);
        binding.setViewModel(model);
        model.setCurrentBoardModel(9);
        binding.setActivity(MapThree.this);
        if (indexFromBundle != null && !indexFromBundle.trim().isEmpty()) {
            if (!indexFromBundle.equals(model.getMutableIndex())) {
                model.getStringMutableLiveData().setValue(indexFromBundle);
                model.updateWordList();
                model.prepareBingoNumbers();
            }
        }
        if (model.getStringMutableLiveData().getValue() != null) {
            model.getStringMutableLiveData().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String responseString) {
                    tempList = model.returnStringList();
                    binding.setList(tempList);
                }
            });
        }
        model.getBingoScore().observe(this, new Observer<Bingo>() {
            @Override
            public void onChanged(Bingo bingo) {
                if (bingo.equals(Bingo.Bingo)) {
                    model.showToast(getActivity(), String.valueOf(bingo), getResources().getDrawable(R.drawable.ic_action_achievement));
                    model.setBingoScore(Bingo.notBingo);
                }
            }
        });
        binding.linearFAB3.setVisibility(model.getVisibilityFor3());
        model.prepareBingoNumbers();
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
        MenuItem clearItem = menu.findItem(R.id.clearBoard);
        MenuItem changeItem = menu.findItem(R.id.changeBoard);
        MenuItem randomValue = menu.findItem(R.id.menuBingoGenerator);
        randomValue.setVisible(true);
        clearItem.setVisible(true);
        if (tempList.size() >= 24) {
            changeItem.setVisible(true);
            changeItem.setTitle(R.string.set5x5);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        Context context = view.getContext();

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        String textToDisplay;
        if (view instanceof TextView) {
            textToDisplay = (String) ((TextView) view).getText();
        } else {
            textToDisplay = context.getResources().getString(R.string.unknownError);
        }
        builder.setTitle(context.getResources().getString(R.string.textToDisplay))
                .setMessage(textToDisplay)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
        return false;
    }

    public void generateRandomWord(View view) {
        String newWord = model.prepareRandomWord();
        final int random = new Random().nextInt((5));
        Drawable[] drawables = {getResources().getDrawable(R.drawable.ic_action_dice1), getResources().getDrawable(R.drawable.ic_action_dice2), getResources().getDrawable(R.drawable.ic_action_dice3),
                getResources().getDrawable(R.drawable.ic_action_dice4), getResources().getDrawable(R.drawable.ic_action_dice5), getResources().getDrawable(R.drawable.ic_action_dice6)};
        model.showToast(getActivity(), newWord, drawables[random]);
    }

}
