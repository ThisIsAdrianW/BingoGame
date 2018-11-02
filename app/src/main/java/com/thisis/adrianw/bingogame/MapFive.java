package com.thisis.adrianw.bingogame;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thisis.adrianw.bingogame.Model.Bingo;
import com.thisis.adrianw.bingogame.databinding.FragmentMapFiveBinding;

import static android.app.Activity.RESULT_OK;


public class MapFive extends Fragment implements View.OnLongClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private GameViewModel model;
    private int REQUEST_FOR_IMAGE = 1;
    private ImageView imageView;


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

        final FragmentMapFiveBinding binding = FragmentMapFiveBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        model = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
        imageView = binding.imageV1;
        binding.setViewModel(model);
        model.setCurrentBoardModel(24);
        binding.setActivity(MapFive.this);
        if (model.getImageUri() != null) {
            imageView.setImageURI(model.getImageUri());
        }
        binding.setList(model.returnStringList());
        model.getBingoScore().observe(this, new Observer<Bingo>() {
            @Override
            public void onChanged(Bingo bingo) {
                if (bingo.equals(Bingo.Bingo)) {
                    toastMaker(String.valueOf(bingo));
                    model.setBingoScore(Bingo.notBingo);
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem clearItem = menu.findItem(R.id.clearBoard);
        MenuItem changeItem = menu.findItem(R.id.changeBoard);
        clearItem.setVisible(true);
        changeItem.setTitle(R.string.set3x3);
        changeItem.setVisible(true);
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
        builder.setTitle("Text in this field is:")
                .setMessage(textToDisplay)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
        return false;
    }

    public void selectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_FOR_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FOR_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            imageView.setImageURI(uri);
            model.setImageUri(uri);
        }
    }

    private void toastMaker(String toastMessage) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) getActivity().findViewById(R.id.toast_root));
        ImageView imageView = layout.findViewById(R.id.image_fot_toast);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_achievement));
        TextView text = layout.findViewById(R.id.text_for_toast);
        text.setTextSize(27);
        text.setText(String.valueOf(toastMessage));
        Toast toast = new Toast(getActivity().getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
