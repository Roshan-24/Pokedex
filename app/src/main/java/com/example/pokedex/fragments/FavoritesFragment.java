package com.example.pokedex.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.MainActivity;
import com.example.pokedex.NamedAPIResource;
import com.example.pokedex.PokemonAdapter;
import com.example.pokedex.PokemonPage;
import com.example.pokedex.R;
import com.example.pokedex.SavedPokemonPage;
import com.example.pokedex.databases.PokemonDB;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    public PokemonAdapter adapter;
    private LinearLayoutManager layoutManager;

    private List<NamedAPIResource> pokemons;
    private List<PokemonDB> pokemonDBs;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = v.findViewById(R.id.recyclerViewFav);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        pokemons = new ArrayList<>();

        pokemonDBs = MainActivity.pokemonDatabaseAbs.pokemonDao().getPokemonfromDB();
        String pokeName = "";
        String pokeUrl = "https://pokeapi.co/api/v2/pokemon/";
        NamedAPIResource pokemon;

        for( PokemonDB poke : pokemonDBs) {
            pokeName = poke.getName();

            //pokemon = new NamedAPIResource(pokeName, pokeUrl+pokeName);
            pokemon = new NamedAPIResource(pokeName, Integer.toString(poke.getId()));
            pokemons.add(pokemon);
        }

        adapter = new PokemonAdapter(pokemons);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemListener(new PokemonAdapter.OnItemListener() {
            @Override
            public void onItemClick(int position, String url, View v) {
                Intent intent = new Intent(getContext(), SavedPokemonPage.class);
                SavedPokemonPage.ID = Integer.parseInt(url);
                TextView textView = v.findViewById(R.id.textView1);
                ImageView imageView = v.findViewById(R.id.imageView);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) getContext(), imageView, ViewCompat.getTransitionName(imageView));
                startActivity(intent, options.toBundle());
            }
        });


        new ItemTouchHelper(adapter.ithcDelete).attachToRecyclerView(recyclerView);

        return v;
    }
}
