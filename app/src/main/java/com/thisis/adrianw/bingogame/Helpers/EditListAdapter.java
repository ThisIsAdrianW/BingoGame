package com.thisis.adrianw.bingogame.Helpers;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thisis.adrianw.bingogame.Bingodata.Words;
import com.thisis.adrianw.bingogame.GameViewModel;
import com.thisis.adrianw.bingogame.R;

import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class EditListAdapter extends RecyclerView.Adapter<EditListAdapter.EditViewHolder> {
    private final Context context;
    private final GameViewModel model;
    private final FragmentActivity fragmentActivity;
    private final LayoutInflater inflater;
    private List<Words> words; // Cached copy of words
    private final int MIN_LIST_SIZE = 9;

    class EditViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final EditText editText;
        private final ImageButton imageButtonDelete;
        private final ImageButton imageButtonSave;
        private final LinearLayout linearLayout;


        private EditViewHolder(View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.listEditText);
            imageButtonDelete = itemView.findViewById(R.id.listButtonDelete);
            imageButtonSave = itemView.findViewById(R.id.listButtonSave);
            linearLayout = itemView.findViewById(R.id.linearEditLayout);
            imageButtonSave.setOnClickListener(this);
            imageButtonDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }


    public EditListAdapter(Context context, FragmentActivity fragmentActivity, GameViewModel viewModel) {
        this.context = context;
        this.model = viewModel;
        this.fragmentActivity = fragmentActivity;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public EditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.edit_item, parent, false);
        return new EditViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EditViewHolder holder, int position) {
        if (words != null) {
            final Words current = words.get(position);
            if (position % 2 == 0) {
                holder.linearLayout.setBackgroundResource(R.color.primaryLightColor);
            } else {
                holder.linearLayout.setBackgroundResource(R.color.colorPrimary);
            }
            holder.editText.setText(current.getWordForBingo());
            holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int wordPlace = words.indexOf(current);
                    if (words.size() > MIN_LIST_SIZE) {
                        model.deleteWord(current);
                        words.remove(current);
                        notifyItemRemoved(wordPlace);
                        notifyItemRangeChanged(wordPlace, words.size());
                    } else {
                        model.showToast(fragmentActivity,String.valueOf(context.getResources().getString(R.string.Toast_error_count)), context.getResources().getDrawable(R.drawable.ic_report_problem) );
                    }
                }
            });
            holder.imageButtonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newBingoWord = holder.editText.getText().toString().trim();
                    if (newBingoWord == null || newBingoWord.isEmpty()) {
                        model.showToast(fragmentActivity, context.getResources().getString(R.string.Toast_error_empty), context.getResources().getDrawable(R.drawable.ic_report_problem));
                    } else {
                        current.setWordForBingo(newBingoWord);
                        model.updateWord(current);
                        notifyItemChanged(words.indexOf(current));
                        model.showToast(fragmentActivity, context.getResources().getString(R.string.Toast_saved_word), null);
                    }
                }
            });
        } else {
            holder.editText.setText("No Word");
        }
    }

    public void setWords(List<Words> wordsList) {
        this.words = wordsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (words != null)
            return words.size();
        else return 0;
    }
}
