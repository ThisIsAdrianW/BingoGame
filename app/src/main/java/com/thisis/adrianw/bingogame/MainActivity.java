package com.thisis.adrianw.bingogame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.thisis.adrianw.bingogame.Bingodata.IndexWord;
import com.thisis.adrianw.bingogame.Helpers.BingoAdapter;
import com.thisis.adrianw.bingogame.databinding.ActivityMainBinding;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    GameViewModel gameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        List<IndexWord> myIndex = gameViewModel.returnIndexAsync();
        if (myIndex.isEmpty()) {
            binding.listView.setVisibility(View.GONE);
        }
        else {
            binding.listView.setVisibility(View.VISIBLE);
            BingoAdapter adapter = new BingoAdapter(this, myIndex);
//            ArrayAdapter<IndexWord> adapter = new ArrayAdapter<IndexWord>(this,
//                    android.R.layout.simple_list_item_1, android.R.id.text1, myIndex);
            binding.listView.setAdapter(adapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_words:
                addWords();
                return true;
            case R.id.chooseList:
                return true;
            case R.id.changeBoard:
                gotoThree();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void gotoThree() {
        // Create new fragment and transaction
        Fragment mapThree = new MapThree();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, mapThree);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void addWords() {
        // Create new fragment and transaction
        Fragment addWords = new AddWords();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, addWords);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
