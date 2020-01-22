package com.example.submition4;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.submition4.activity.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static com.example.submition4.R.*;
import static com.example.submition4.R.menu.menu_item;

public class MainActivity extends AppCompatActivity {

    NavController navController;

    SearchBundel searchBundel;

    SearchView searchView;

    public void setOnSearchBundleChange(SearchBundel searchBundel){
        this.searchBundel = searchBundel;
    }

    private static final String TAG = "ASSU";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        BottomNavigationView navView = findViewById(id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                id.navigation_movie, id.navigation_tv_show, id.navigation_favorite)
                .build();
        navController = Navigation.findNavController(this, id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menu_item,menu);
        MenuItem item = menu.findItem(id.search_view);
        ActionBar actionBar = getSupportActionBar();
        searchView = (SearchView) item.getActionView();
        searchView.setFocusable(false);
        searchView.setQueryHint(getResources().getString(string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString("EXTRA",query);
                searchBundel.onSearchBundelChange(bundle);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Bundle bundle = new Bundle();
                bundle.putString("EXTRA",newText);
                searchBundel.onSearchBundelChange(bundle);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public interface SearchBundel{
        void onSearchBundelChange(Bundle bundle);
    }
}
