package com.thisis.adrianw.bingogame.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.thisis.adrianw.bingogame.Bingodata.IndexWord;
import com.thisis.adrianw.bingogame.R;

import java.util.List;

import androidx.appcompat.widget.AppCompatTextView;

public class BingoAdapter extends ArrayAdapter<IndexWord> {

    public BingoAdapter(Context context, List<IndexWord> indexWords) {
        super(context, 0, indexWords);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IndexWord indexWord = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        AppCompatTextView displayIndex =  convertView.findViewById(R.id.textViewCompatItem);
        displayIndex.setText(indexWord.getIndexforwords());
        return convertView;
    }

}
