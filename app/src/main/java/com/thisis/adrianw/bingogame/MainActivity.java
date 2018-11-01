package com.thisis.adrianw.bingogame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.thisis.adrianw.bingogame.Model.Bingo;
import com.thisis.adrianw.bingogame.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    GameViewModel gameViewModel;
    Fragment fragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        final ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        binding.setLifecycleOwner(MainActivity.this);
        gameViewModel = ViewModelProviders.of(MainActivity.this).get(GameViewModel.class);
        if (savedInstanceState == null) {
            listFragment();
        }
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(fragmentManager.getBackStackEntryCount() == 0) finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem clearItem = menu.findItem(R.id.clearBoard);
        MenuItem changeItem = menu.findItem(R.id.changeBoard);
        clearItem.setVisible(false);
        changeItem.setVisible(false);
//        menu.removeItem(R.id.clearBoard);
//        menu.removeItem(R.id.changeBoard);
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
            case R.id.changeBoard:
                changeBoards();
                return true;
            case R.id.helpandAbout:
                helpAndAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void changeBoards() {
        // Create new fragment and transaction
        if (gameViewModel.getCurrentBoardModel() == 9) {
            fragment = new MapFive();
            gameViewModel.setBingoScore(Bingo.notBingo);
        } else {
            fragment = new MapThree();
            gameViewModel.setBingoScore(Bingo.notBingo);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addWords() {
        // Create new fragment and transaction
        Fragment addWords = new AddWords();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, addWords);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void listFragment() {
        Fragment listFragment = new ListFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, listFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void helpAndAbout() {
        Fragment helpAndAbout = new HelpAndAbout();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, helpAndAbout);
        transaction.addToBackStack(null);
        transaction.commit();
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        listFragment();
//    }
}
