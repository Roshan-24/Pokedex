package com.example.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonPage extends AppCompatActivity {

    public static int pokemonid = 1;
    public static String pokemonUrl = "https://pokeapi.co/api/v2/pokemon/1/";
    public static int isRegion;

    private TextView dexNumber;
    private TextView pokemonName;
    private TextView typing;
    private TextView abilities;
    private TextView evoChain;
    private TextView evoChainTitle;
    private TextView hp;
    private TextView atk;
    private TextView def;
    private TextView spa;
    private TextView spd;
    private TextView spe;
    private ImageView pokeImage;

    private ProgressBar progressBar;

    private ImageView i3f1p;
    private ImageView i3f2p;
    private ImageView i3f3p;
    private ImageView i2f1p;
    private ImageView i2f2p;
    private TextView t3fl1;
    private TextView t3fl2;
    private TextView t2fl;

    private PokeAPI pokeAPI;

    private Pokemon pokemon;
    private PokemonSpecies pokemonSpecies;

    private String idFromURL;
    private String idfu2;
    private String idfu3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_page);

        dexNumber = findViewById(R.id.tvDexNumber);
        pokemonName = findViewById(R.id.tvPokeName);
        typing = findViewById(R.id.tvTyping);
        pokeImage = findViewById(R.id.ivPokemon);
        abilities = findViewById(R.id.tvAbilities);
        evoChain = findViewById(R.id.tvEvoChain);
        evoChainTitle = findViewById(R.id.tvECTitle);
        hp = findViewById(R.id.tvHP);
        atk = findViewById(R.id.tvAtk);
        def = findViewById(R.id.tvDef);
        spa = findViewById(R.id.tvSpA);
        spd = findViewById(R.id.tvSpD);
        spe = findViewById(R.id.tvSpe);

        i2f1p = findViewById(R.id.iv2F1P);
        i2f2p = findViewById(R.id.iv2F2P);
        i3f1p = findViewById(R.id.iv3F1P);
        i3f2p = findViewById(R.id.iv3F2P);
        i3f3p = findViewById(R.id.iv3F3P);
        t3fl1 = findViewById(R.id.tv3Flink1);
        t3fl2 = findViewById(R.id.tv3Flink2);
        t2fl = findViewById(R.id.tv2Flink2);

        dexNumber.setVisibility(View.INVISIBLE);
        pokemonName.setVisibility(View.INVISIBLE);
        typing.setVisibility(View.INVISIBLE);
        pokeImage.setVisibility(View.INVISIBLE);
        abilities.setVisibility(View.INVISIBLE);
        evoChain.setVisibility(View.INVISIBLE);
        evoChainTitle.setVisibility(View.INVISIBLE);
        hp.setVisibility(View.INVISIBLE);
        atk.setVisibility(View.INVISIBLE);
        def.setVisibility(View.INVISIBLE);
        spa.setVisibility(View.INVISIBLE);
        spd.setVisibility(View.INVISIBLE);
        spe.setVisibility(View.INVISIBLE);

        progressBar = findViewById(R.id.progress_pokepage);
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pokeAPI = retrofit.create(PokeAPI.class);

        if(isRegion == 1)
            getSpeciesbeforePokemon();
        else
            getPokemon();
    }

    private void getPokemon() {
        Call<Pokemon> call = pokeAPI.getPokemonbyUrl(pokemonUrl);

        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {

                if(!response.isSuccessful()) {
                    //textView.setText("Code: " + response.code());
                    Toast.makeText(PokemonPage.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                pokemon = response.body();

                dexNumber.setText("#" + pokemon.getId());
                pokemonName.setText(pokemon.getName());
                String types = "";
                for(int i = 0; i < pokemon.getTypes().size() ; i++) {
                    types += pokemon.getTypes().get(i).getType().getName();
                    types += "/";
                }
                types = (types == null || types.length() == 0) ? null : (types.substring(0, types.length() - 1));
                typing.setText(types);
                Picasso.get().load(pokemon.getSprites().getFront_default()).into(pokeImage);

                String abilitiess = "";
                for(int i = 0 ; i < pokemon.getAbilities().size() ; i++) {
                    abilitiess += pokemon.getAbilities().get(i).getAbility().getName();
                    abilitiess += ", ";
                }
                abilitiess = abilitiess.substring(0, abilitiess.length() -2);
                abilities.setText(abilitiess);

                hp.setText("HP: " + pokemon.getStats().get(5).getBase_stat());
                atk.setText("Attack: " + pokemon.getStats().get(4).getBase_stat());
                def.setText("Defense: " + pokemon.getStats().get(3).getBase_stat());
                spa.setText("Special Attack: " + pokemon.getStats().get(2).getBase_stat());
                spd.setText("Special Defense: " + pokemon.getStats().get(1).getBase_stat());
                spe.setText("Speed: " + pokemon.getStats().get(0).getBase_stat());

                getSpecies();
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

            }
        });
    }

    private void getSpecies() {
        Call<PokemonSpecies> call = pokeAPI.getSpecies(pokemon.getSpecies().getUrl());

        call.enqueue(new Callback<PokemonSpecies>() {
            @Override
            public void onResponse(Call<PokemonSpecies> call, Response<PokemonSpecies> response) {
                if (!response.isSuccessful()) {
                    //textView.setText("Code: " + response.code());
                    Toast.makeText(PokemonPage.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                pokemonSpecies = response.body();

                getEvoChain();
            }

            @Override
            public void onFailure(Call<PokemonSpecies> call, Throwable t) {

            }
        });
    }

    private void getEvoChain() {
        Call<EvolutionChain> call = pokeAPI.getEvoChain(pokemonSpecies.getEvolution_chain().getUrl());

        call.enqueue(new Callback<EvolutionChain>() {
            @Override
            public void onResponse(Call<EvolutionChain> call, Response<EvolutionChain> response) {
                if(!response.isSuccessful()) {
                    //textView.setText("Code: " + response.code());
                    Toast.makeText(PokemonPage.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                EvolutionChain evolutionChain = response.body();

                int flag = 1;

                idFromURL = evolutionChain.getChain().getSpecies().getUrl();
                idFromURL = idFromURL.substring(42, idFromURL.length() - 1);

                String content = "";
                content += evolutionChain.getChain().getSpecies().getName();
                content += " -> ";
                if(evolutionChain.getChain().getEvolves_to().size() != 0) {
                    content += evolutionChain.getChain().getEvolves_to().get(0).getSpecies().getName();
                    content += " -> ";

                    idfu2 = evolutionChain.getChain().getEvolves_to().get(0).getSpecies().getUrl();
                    idfu2 = idfu2.substring(42, idfu2.length() - 1);

                    Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + idFromURL + ".png").into(i2f1p);
                    Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + idfu2 + ".png").into(i2f2p);

                    t2fl.setVisibility(View.VISIBLE);
                    i2f1p.setVisibility(View.VISIBLE);
                    i2f2p.setVisibility(View.VISIBLE);

                    i2f1p.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PokemonPage.this, PokemonPage.class);
                            PokemonPage.pokemonUrl = "https://pokeapi.co/api/v2/pokemon/" + idFromURL;
                            isRegion = 0;
                            startActivity(intent);
                        }
                    });

                    i2f2p.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PokemonPage.this, PokemonPage.class);
                            PokemonPage.pokemonUrl = "https://pokeapi.co/api/v2/pokemon/" + idfu2;
                            isRegion = 0;
                            startActivity(intent);
                        }
                    });

                    if (evolutionChain.getChain().getEvolves_to().get(0).getEvolves_to().size() != 0) {
                        content += evolutionChain.getChain().getEvolves_to().get(0).getEvolves_to().get(0).getSpecies().getName();
                        content += " -> ";

                        idfu3 = evolutionChain.getChain().getEvolves_to().get(0).getEvolves_to().get(0).getSpecies().getUrl();
                        idfu3 = idfu3.substring(42, idfu3.length() - 1);

                        t2fl.setVisibility(View.INVISIBLE);
                        i2f1p.setVisibility(View.INVISIBLE);
                        i2f2p.setVisibility(View.INVISIBLE);

                        Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + idFromURL + ".png").into(i3f1p);
                        Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + idfu2 + ".png").into(i3f2p);
                        Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + idfu3 + ".png").into(i3f3p);

                        i3f1p.setVisibility(View.VISIBLE);
                        i3f2p.setVisibility(View.VISIBLE);
                        i3f3p.setVisibility(View.VISIBLE);
                        t3fl1.setVisibility(View.VISIBLE);
                        t3fl2.setVisibility(View.VISIBLE);

                        i3f1p.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(PokemonPage.this, PokemonPage.class);
                                PokemonPage.pokemonUrl = "https://pokeapi.co/api/v2/pokemon/" + idFromURL;
                                isRegion = 0;
                                startActivity(intent);
                            }
                        });

                        i3f2p.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(PokemonPage.this, PokemonPage.class);
                                PokemonPage.pokemonUrl = "https://pokeapi.co/api/v2/pokemon/" + idfu2;
                                isRegion = 0;
                                startActivity(intent);
                            }
                        });

                        i3f3p.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(PokemonPage.this, PokemonPage.class);
                                PokemonPage.pokemonUrl = "https://pokeapi.co/api/v2/pokemon/" + idfu3;
                                isRegion = 0;
                                startActivity(intent);
                            }
                        });
                    }
                }
                content = (content == null || content.length() == 0) ? null : (content.substring(0, content.length() - 4));

                evoChain.setText(content);

                dexNumber.setVisibility(View.VISIBLE);
                pokemonName.setVisibility(View.VISIBLE);
                typing.setVisibility(View.VISIBLE);
                pokeImage.setVisibility(View.VISIBLE);
                abilities.setVisibility(View.VISIBLE);
                //evoChain.setVisibility(View.VISIBLE);
                evoChainTitle.setVisibility(View.VISIBLE);
                hp.setVisibility(View.VISIBLE);
                atk.setVisibility(View.VISIBLE);
                def.setVisibility(View.VISIBLE);
                spa.setVisibility(View.VISIBLE);
                spd.setVisibility(View.VISIBLE);
                spe.setVisibility(View.VISIBLE);

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<EvolutionChain> call, Throwable t) {

            }
        });
    }

    private void getSpeciesbeforePokemon() {
        Call<PokemonSpecies> call = pokeAPI.getSpecies(pokemonUrl);

        call.enqueue(new Callback<PokemonSpecies>() {
            @Override
            public void onResponse(Call<PokemonSpecies> call, Response<PokemonSpecies> response) {
                if(!response.isSuccessful()) {
                    //textView.setText("Code: " + response.code());
                    Toast.makeText(PokemonPage.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                PokemonSpecies pokemonSpecies = response.body();
                pokemonUrl = pokemonSpecies.getVarieties().get(0).getPokemon().getUrl();

                getPokemon();
            }

            @Override
            public void onFailure(Call<PokemonSpecies> call, Throwable t) {

            }
        });
    }

}
