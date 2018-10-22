package com.thisis.adrianw.bingogame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.thisis.adrianw.bingogame.Bingodata.IndexWord;
import com.thisis.adrianw.bingogame.Helpers.BingoListAdapter;
import com.thisis.adrianw.bingogame.databinding.ActivityMainBinding;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    GameViewModel gameViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        binding.setLifecycleOwner(MainActivity.this);
        gameViewModel = ViewModelProviders.of(MainActivity.this).get(GameViewModel.class);
        final BingoListAdapter bingoListAdapter = new BingoListAdapter(MainActivity.this, gameViewModel);
        binding.recycleView.setVisibility(View.VISIBLE);
        binding.recycleView.setAdapter(bingoListAdapter);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        gameViewModel.getLiveIndex().observe(MainActivity.this, new Observer<List<IndexWord>>() {
            @Override
            public void onChanged(List<IndexWord> indexWords) {
                bingoListAdapter.setIndexWords(indexWords);
                Log.v("MainActivity", "List changed ^_^");
            }
        });


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
