package com.thisis.adrianw.bingogame;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thisis.adrianw.bingogame.Model.BingoBoard;
import com.thisis.adrianw.bingogame.databinding.FragmentMapThreeBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapThree extends Fragment {
    private GameViewModel model;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    void methodTest(View view) {
        Log.v("FragmentMethod", "This id is " + view.getId());
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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
        // Inflate the layout for this fragment
        FragmentMapThreeBinding binding = FragmentMapThreeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        //set variables in Binding
        model = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
        binding.setTestString(model.testString);
        binding.setViewModel(model);
        binding.setActivity(MapThree.this);
        BingoBoard bingoBoard = new BingoBoard(5);
        return binding.getRoot();

        //        return inflater.inflate(R.layout.fragment_map_three, container, false);
    }
    public void testMethod (View view, int x, int y) {
        int a = view.getId();
        if (view instanceof TextView) {
            String b = ((TextView) view).getText().toString().trim();
            Log.v("testMethod", "text is ... " +b);
        }
        model.markBingo(x, y);
        Log.v("MapThreeFragment", "And your id for view is ....... " + a);
    }
}
