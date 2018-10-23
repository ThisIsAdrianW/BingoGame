package com.thisis.adrianw.bingogame;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.thisis.adrianw.bingogame.Bingodata.Words;
import com.thisis.adrianw.bingogame.Model.BingoBoard;
import com.thisis.adrianw.bingogame.databinding.FragmentMapThreeBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapThree extends Fragment implements View.OnLongClickListener {
    private GameViewModel model;
    private List<Words> wordsList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String indexFromBundle;
    private List<String> tempList = new ArrayList<String>();


    public MapThree() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapThree.
     */
    // TODO: Rename and change types and number of parameters
    public static MapThree newInstance(String param1, String param2) {
        MapThree fragment = new MapThree();
        String indexTitleFromClick;
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
        if (bundle!=null) {
            indexFromBundle = bundle.getString("titleArgument"," ");

        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final FragmentMapThreeBinding binding = FragmentMapThreeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        //set variables in Binding
        model = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
        binding.setTestString(model.testString);
        binding.setViewModel(model);
        binding.setActivity(MapThree.this);
        BingoBoard bingoBoard = new BingoBoard(5);
        if (indexFromBundle!=null && !indexFromBundle.trim().isEmpty()) {
            if (!indexFromBundle.equals(model.getMutableIndex())) {
                model.getStringMutableLiveData().setValue(indexFromBundle);
                model.updateWordList();
                Log.v("MapThree", "changed index for searching for ... " + indexFromBundle);
            }
        }
        if (model.getStringMutableLiveData().getValue()!=null) {
            model.getStringMutableLiveData().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String responseString) {
                    Log.v("MapThree", "Now response is ...." + responseString);
                    tempList = model.returnStringList();
                    binding.setList(tempList);
                }
            });
            Log.v("MapThree", "Null value in string");
        }

        return binding.getRoot();
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
            textToDisplay = "Unknown error";
        }
        Log.v("UiHellpler", "text is : " + textToDisplay);
        builder.setTitle("Text in this field is:")
                .setMessage(textToDisplay)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialog.cancel();
                    }
                })
                .show();
        return false;
    }

}
