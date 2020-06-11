package com.example.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilteredPokemonList extends AppCompatActivity {

    private PokeAPI pokeAPI;

    public static RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private LinearLayoutManager layoutManager;

    private TextView textView;

    public static String url;
    public static String region;
    public static int isRegion = 0;

    private List<TypePokemon> pokemons;
    private List<NamedAPIResource> pokemonList;

    private Region regiondata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtered_pokemon_list);

        recyclerView = findViewById(R.id.recyclerViewFiltered);
        textView = findViewById(R.id.tvE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pokeAPI = retrofit.create(PokeAPI.class);

        /*if(isRegion == 0)
            getPokemonbyTypes();
        else if(isRegion == 1)
            getPokemonbyRegion();

         */
        getPokemonbyTypes();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        pokemons = new ArrayList<>();
        pokemonList = new ArrayList<>();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_icon, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        //SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    private void getPokemonbyTypes() {
        Call<Type> call = pokeAPI.getPokemonbyTypes(url);

        call.enqueue(new Callback<Type>() {
            @Override
            public void onResponse(Call<Type> call, Response<Type> response) {
                if(!response.isSuccessful()) {
                    //textView.setText("Code: " + response.code());
                    Toast.makeText(FilteredPokemonList.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Type type = response.body();
                pokemons = type.getPokemon();

                for(int i = 0 ; i < type.getPokemon().size() ; i++) {
                    pokemonList.add(pokemons.get(i).getPokemon());
                }
                adapter = new PokemonAdapter(pokemonList);
                recyclerView.setAdapter(adapter);

                new ItemTouchHelper(adapter.itchTypeFilter).attachToRecyclerView(recyclerView);

                adapter.setOnItemListener(new PokemonAdapter.OnItemListener() {
                    @Override
                    public void onItemClick(int position, String url, View v) {
                        Intent intent = new Intent(FilteredPokemonList.this, PokemonPage.class);
                        PokemonPage.pokemonid = position+1 ;
                        PokemonPage.pokemonUrl = url;
                        PokemonPage.isRegion = 0;
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<Type> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    /*
    private void getPokemonbyRegion() {
        Call<Region> call = pokeAPI.getRegionData(region);

        call.enqueue(new Callback<Region>() {
            @Override
            public void onResponse(Call<Region> call, Response<Region> response) {
                if(!response.isSuccessful()) {
                    //textView.setText("Code: " + response.code());
                    Toast.makeText(FilteredPokemonList.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                regiondata = response.body();
                getPokemonbyPokedex();


            }

            @Override
            public void onFailure(Call<Region> call, Throwable t) {

            }
        });
    }

    private void getPokemonbyPokedex() {
        Call<Pokedex> call = pokeAPI.getPokedex(regiondata.getPokedexes().get(0).getUrl());

        call.enqueue(new Callback<Pokedex>() {
            @Override
            public void onResponse(Call<Pokedex> call, Response<Pokedex> response) {
                if(!response.isSuccessful()) {
                    //textView.setText("Code: " + response.code());
                    Toast.makeText(FilteredPokemonList.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Pokedex pokedex = response.body();
                List<PokemonEntry> pokemonEntry = pokedex.getPokemon_entries();

                for(int i = 0 ; i < pokemonEntry.size() ; i++) {
                    pokemonList.add(pokemonEntry.get(i).getPokemon_species());
                }

                adapter = new PokemonAdapter(pokemonList);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemListener(new PokemonAdapter.OnItemListener() {
                    @Override
                    public void onItemClick(int position, String url) {
                        Intent intent = new Intent(FilteredPokemonList.this, PokemonPage.class);
                        PokemonPage.pokemonid = position+1 ;
                        PokemonPage.pokemonUrl = url;
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<Pokedex> call, Throwable t) {

            }
        });
    }


     */
}
