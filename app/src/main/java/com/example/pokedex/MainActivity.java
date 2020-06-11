package com.example.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pokedex.databases.PokemonDB;
import com.example.pokedex.databases.PokemonDatabaseAbs;
import com.example.pokedex.fragments.FavoritesFragment;
import com.example.pokedex.fragments.PokemonListFragment;
import com.example.pokedex.fragments.RegionListFragment;
import com.example.pokedex.fragments.TypesListFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private TextView title;
    private TextView tip;

    public static PokemonListFragment pokemonListFragment;
    private TypesListFragment typesListFragment;
    private RegionListFragment regionListFragment;
    private FavoritesFragment favoritesFragment;

    private int whichFragment;

    public static PokemonDatabaseAbs pokemonDatabaseAbs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemonDatabaseAbs = Room.databaseBuilder(getApplicationContext(), PokemonDatabaseAbs.class, "user").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pokemonListFragment = new PokemonListFragment();
        typesListFragment = new TypesListFragment();
        regionListFragment = new RegionListFragment();
        favoritesFragment = new FavoritesFragment();

        title = findViewById(R.id.tvFragmentTitle);
        tip = findViewById(R.id.tvFavTip);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pokemonListFragment).commit();
            //whichFragment = 1;
            navigationView.setCheckedItem(R.id.nav_pokemons);
            tip.setVisibility(View.VISIBLE);
            title.setText("Pokemons");
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_icon, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(whichFragment == 1) {
                    pokemonListFragment.adapter.getFilter().filter(newText);
                }
                else if(whichFragment == 2) {
                    typesListFragment.adapter.getFilter().filter(newText);
                }
                else if(whichFragment == 3) {
                    regionListFragment.adapter.getFilter().filter(newText);
                }
                else if(whichFragment == 4) {
                    favoritesFragment.adapter.getFilter().filter(newText);
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_pokemons:
                whichFragment = 1;
                tip.setVisibility(View.VISIBLE);
                title.setText("Pokemons");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pokemonListFragment).commit();
                break;
            case R.id.nav_types:
                whichFragment = 2;
                tip.setVisibility(View.INVISIBLE);
                title.setText("Types");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, typesListFragment).commit();
                break;
            case R.id.nav_regions:
                whichFragment = 3;
                tip.setVisibility(View.INVISIBLE);
                title.setText("Regions");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, regionListFragment).commit();
                break;
            case R.id.nav_favorites:
                whichFragment = 4;
                tip.setVisibility(View.INVISIBLE);
                title.setText("Favorite pokemons");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, favoritesFragment).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    public void showToast() {
        Toast.makeText(this, "Pokemon added to favorites!", Toast.LENGTH_SHORT).show();
    }
}
