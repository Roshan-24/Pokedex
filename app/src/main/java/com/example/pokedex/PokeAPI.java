package com.example.pokedex;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

//list of apis used
public interface PokeAPI {

    @GET
    Call<NamedAPIResourceList> getPokemonList(@Url String url);

    @GET
    Call<NamedAPIResourceList> getTypes(@Url String url);

    @GET
    Call<PokemonSpecies> getSpecies(@Url String url);

    @GET
    Call<EvolutionChain> getEvoChain (@Url String url);

    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Path("id") int id);

    @GET
    Call<Pokemon> getPokemonbyUrl(@Url String url);

    @GET
    Call<Type> getPokemonbyTypes(@Url String url);

    @GET
    Call<Region> getRegionData(@Url String url);

    @GET
    Call<Pokedex> getPokedex(@Url String url);
}
