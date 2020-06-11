package com.example.pokedex.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pokedex.MainActivity;
import com.example.pokedex.NamedAPIResource;
import com.example.pokedex.NamedAPIResourceList;
import com.example.pokedex.PokeAPI;
import com.example.pokedex.PokemonAdapter;
import com.example.pokedex.PokemonPage;
import com.example.pokedex.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonListFragment extends Fragment {

    private PokeAPI pokeAPI;

    private List<NamedAPIResourceList> pokemons;
    private NamedAPIResourceList pokemon;

    public RecyclerView recyclerView;
    public PokemonAdapter adapter;
    private LinearLayoutManager layoutManager;

    private ProgressBar progressBar;
    private ProgressBar progressBar2;

    private List<NamedAPIResource> fullPokemonList = new ArrayList<>();

    private Boolean isScrolling = false;
    private int currentItems;
    private int totalItems;
    private int scrolledOutItems;

    private String nextUrl = "https://pokeapi.co/api/v2/pokemon/";

    public PokemonListFragment() {
        // Required empty public constructor
    }

    public static PokemonListFragment newInstance() {
        PokemonListFragment fragment = new PokemonListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        recyclerView = v.findViewById(R.id.recyclerViewFrag);
        progressBar = v.findViewById(R.id.progressBar1);
        progressBar2 = v.findViewById(R.id.progressBar2);

        progressBar2.setVisibility(View.VISIBLE);

        pokemons = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pokeAPI = retrofit.create(PokeAPI.class);

        getPokemons();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PokemonAdapter(fullPokemonList);
        recyclerView.setAdapter(adapter);



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrolledOutItems = layoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems+scrolledOutItems) == totalItems) {
                    isScrolling = false;
                    progressBar.setVisibility(View.VISIBLE);
                    fetchMoreData();
                }
            }
        });

        return v;
    }

    private void getPokemons() {
        Call<NamedAPIResourceList> call = pokeAPI.getPokemonList(nextUrl);

        call.enqueue(new Callback<NamedAPIResourceList>() {
            @Override
            public void onResponse(Call<NamedAPIResourceList> call, Response<NamedAPIResourceList> response) {

                if(!response.isSuccessful()) {
                    //textView.setText("Code: " + response.code());
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                pokemon = response.body();
                List<NamedAPIResource> pokemonList = pokemon.getResults();
                fullPokemonList.addAll(pokemonList);
                adapter.notifyDataSetChanged();

                progressBar2.setVisibility(View.GONE);

                progressBar.setVisibility(View.GONE);

                new ItemTouchHelper(adapter.itchMain).attachToRecyclerView(recyclerView);

                adapter.setOnItemListener(new PokemonAdapter.OnItemListener() {
                    @Override
                    public void onItemClick(int position, String url, View v) {
                        Intent intent = new Intent(getContext(), PokemonPage.class);
                        PokemonPage.pokemonid = position+1 ;
                        PokemonPage.pokemonUrl = url;
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(Call<NamedAPIResourceList> call, Throwable t) {
                //textView.setText(t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMoreData() {
        nextUrl = pokemon.getNext();
        getPokemons();
    }

}
