package com.thisis.adrianw.bingogame;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.thisis.adrianw.bingogame.Bingodata.Words;
import com.thisis.adrianw.bingogame.Helpers.BingoListAdapter;
import com.thisis.adrianw.bingogame.Helpers.EditListAdapter;
import com.thisis.adrianw.bingogame.databinding.FragmentEditBinding;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public GameViewModel model;
    private String indexForEdit;
    private EditListAdapter editListAdapter;
    List<Words> currentList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        if (indexForEdit!=null && !indexForEdit.trim().isEmpty()) {
            currentList = model.returnAllWordsStandard(indexForEdit);
            editListAdapter.setWords(currentList);

        }
        return binding.getRoot();
    }

    public void addNewItem(View view) {
        if (currentList!=null && indexForEdit!=null) {
            Words words = new Words();
            words.setIndexforword(indexForEdit);
            words.setWordForBingo("0. You should to change this");
            model.inserBingoWord(words);
            currentList = model.returnAllWordsStandard(indexForEdit);
            editListAdapter.setWords(currentList);
            editListAdapter.notifyDataSetChanged();
        }


    }
}
