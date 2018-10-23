package com.thisis.adrianw.bingogame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.thisis.adrianw.bingogame.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    GameViewModel gameViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        binding.setLifecycleOwner(MainActivity.this);
        gameViewModel = ViewModelProviders.of(MainActivity.this).get(GameViewModel.class);
        if (savedInstanceState == null) {
            listFragment();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.clearBoard);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_words:
                addWords();
                return true;
            case R.id.chooseList:
                listFragment();
                return true;
            case R.id.clearBoard:
                gameViewModel.cleanBingoBoard();
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

    public void listFragment() {
        Fragment listFragment = new ListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, listFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        listFragment();
    }
}
