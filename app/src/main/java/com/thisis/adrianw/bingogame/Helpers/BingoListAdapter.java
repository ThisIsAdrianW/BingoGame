package com.thisis.adrianw.bingogame.Helpers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thisis.adrianw.bingogame.Bingodata.IndexWord;
import com.thisis.adrianw.bingogame.GameViewModel;
import com.thisis.adrianw.bingogame.MapThree;
import com.thisis.adrianw.bingogame.R;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class BingoListAdapter extends RecyclerView.Adapter<BingoListAdapter.BingoViewHolder> {
    FragmentActivity fragmentActivity;
    private final Context context;

    class BingoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final AppCompatTextView titleItemView;
        private final ImageView deleteButton;
        private final ImageView editButton;
        private final LinearLayout linearLayout;
        private final int blackText;
        private final int whiteText;
        private final Drawable binIcon;


        private BingoViewHolder(View itemView) {
            super(itemView);
            titleItemView = itemView.findViewById(R.id.textViewCompatItem);
            deleteButton = itemView.findViewById(R.id.deleteIcon);
            linearLayout = itemView.findViewById(R.id.linearItemLayout);
            editButton = itemView.findViewById(R.id.editIcon);
            blackText = ContextCompat.getColor(context, R.color.textBlack);
            whiteText = ContextCompat.getColor(context, R.color.textWhite);
            binIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete);
            titleItemView.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            editButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.v("onClickFromAdapter", String.valueOf(view.getId()));
            String title = titleItemView.getText().toString().trim();
            if (view instanceof TextView) {
                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                Fragment defaultFragment3 = new MapThree();
                Bundle argumentsBundle = new Bundle();
                argumentsBundle.putString("titleArgument", title);
                defaultFragment3.setArguments(argumentsBundle);
                fragmentManager.beginTransaction().replace(R.id.frameLayout, defaultFragment3).addToBackStack(null).commit();
            } else if (view instanceof ImageView) {
                if (view.getId() == R.id.deleteIcon) {
                    IndexWord indexWord = new IndexWord();
                    indexWord.setIndexforwords(title);
                    gameViewModel.deleteIndex(indexWord);
                }
                else if (view.getId() == R.id.editIcon) {
                    Log.v("BingoListAdapter", "Clicked on Edit, wow");
                }
            }
        }
    }

    private final LayoutInflater layoutInflater;
    private List<IndexWord> titles;
    private final GameViewModel gameViewModel;

    public BingoListAdapter(Context context, FragmentActivity fragmentActivity, GameViewModel viewModel) {
        layoutInflater = LayoutInflater.from(context);
        gameViewModel = viewModel;
        this.context = context;
        this.fragmentActivity = fragmentActivity;

    }

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
            if (position % 2 == 0) {
                holder.linearLayout.setBackgroundResource(R.color.bingoDarkBlue);
                holder.titleItemView.setTextColor(holder.whiteText);
            } else {
                holder.linearLayout.setBackgroundResource(R.color.bingoLightBlue);
                holder.titleItemView.setTextColor(holder.blackText);
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.titleItemView.setText("No Word");
        }
    }

    public void setIndexWords(List<IndexWord> words) {
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
