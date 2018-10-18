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
        // Get the data item for this position
        IndexWord indexWord = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        AppCompatTextView displayIndex =  convertView.findViewById(R.id.textViewCompatItem);
        // Populate the data into the template view using the data object
        displayIndex.setText(indexWord.getIndexforwords());
        // Return the completed view to render on screen
        return convertView;
    }
}
