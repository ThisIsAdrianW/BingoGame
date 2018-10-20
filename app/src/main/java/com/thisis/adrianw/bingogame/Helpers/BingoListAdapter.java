package com.thisis.adrianw.bingogame.Helpers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.thisis.adrianw.bingogame.Bingodata.IndexWord;
import com.thisis.adrianw.bingogame.MapThree;
import com.thisis.adrianw.bingogame.R;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class BingoListAdapter extends RecyclerView.Adapter<BingoListAdapter.BingoViewHolder> {

    class BingoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final AppCompatTextView titleItemView;
        private final ImageView deleteButton;
        private BingoViewHolder(View itemView) {
            super(itemView);
            titleItemView = itemView.findViewById(R.id.textViewCompatItem);
            deleteButton = itemView.findViewById(R.id.deleteIcon);
            titleItemView.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.v("onClickFromAdapter", String.valueOf(view.getId()));
            if (view instanceof TextView) {
                String title = ((TextView) view).getText().toString().trim();
                Log.v("BingoViewHolder", "Loool, working" + title);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment defaultFragment3 = new MapThree();
                Bundle argumentsBundle = new Bundle();
                argumentsBundle.putString("titleArgument", title);
                defaultFragment3.setArguments(argumentsBundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, defaultFragment3).addToBackStack(null).commit();
            }
            else if (view instanceof ImageView) {
                Log.v("BingoListAdapter", "This is imageview lolz");
            }
        }
    }

    private final LayoutInflater layoutInflater;
    private List<IndexWord> titles;

    public BingoListAdapter(Context context) { layoutInflater = LayoutInflater.from(context); }

    @Override
    public BingoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new BingoViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(BingoViewHolder holder, int position) {
        if (titles != null) {
            IndexWord current = titles.get(position);
            holder.titleItemView.setText(String.valueOf(current.getIndexforwords()));
        } else {
            // Covers the case of data not being ready yet.
            holder.titleItemView.setText("No Word");
        }
    }
    public void setIndexWords(List<IndexWord> words){
        titles = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (titles != null)
            return titles.size();
        else return 0;
    }
}
