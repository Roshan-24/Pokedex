package com.example.pokedex.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.FilteredPokemonList;
import com.example.pokedex.NamedAPIResource;
import com.example.pokedex.NamedAPIResourceList;
import com.example.pokedex.PokeAPI;
import com.example.pokedex.PokemonAdapter;
import com.example.pokedex.PokemonPage;
import com.example.pokedex.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TypesListFragment extends Fragment {

    private PokeAPI pokeAPI;

    private List<NamedAPIResource> types;
    private NamedAPIResourceList type;

    private RecyclerView recyclerView;
    public PokemonAdapter adapter;
    private LinearLayoutManager layoutManager;

    private ProgressBar progressBar;

    public TypesListFragment() {

    }

    public static TypesListFragment newInstance() {
        TypesListFragment fragment = new TypesListFragment();
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
        View v =  inflater.inflate(R.layout.fragment_types_list, container, false);

        recyclerView = v.findViewById(R.id.recyclerViewFragT);
        progressBar = v.findViewById(R.id.progressBar4);

        progressBar.setVisibility(View.VISIBLE);

        types = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pokeAPI = retrofit.create(PokeAPI.class);

        getTypes();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        return v;
    }

    private void getTypes() {
        Call<NamedAPIResourceList> call = pokeAPI.getTypes("https://pokeapi.co/api/v2/type/");

        call.enqueue(new Callback<NamedAPIResourceList>() {
            @Override
            public void onResponse(Call<NamedAPIResourceList> call, Response<NamedAPIResourceList> response) {

                if(!response.isSuccessful()) {
                    //textView.setText("Code: " + response.code());
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                type = response.body();
                types = type.getResults();
                adapter = new PokemonAdapter(types);
                recyclerView.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);
                //adapter.notifyDataSetChanged();

                adapter.setOnItemListener(new PokemonAdapter.OnItemListener() {
                    @Override
                    public void onItemClick(int position, String url, View v) {
                        Intent intent = new Intent(getContext(), FilteredPokemonList.class);
                        FilteredPokemonList.url = url;
                        FilteredPokemonList.isRegion = 0;
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
}
