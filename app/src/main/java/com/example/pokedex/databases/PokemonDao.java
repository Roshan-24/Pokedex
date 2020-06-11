package com.example.pokedex.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PokemonDao {

    @Insert
    public void addPokemon(PokemonDB pokemon);

    @Query("select * from PokemonDB")
    public List<PokemonDB> getPokemonfromDB();

    @Delete
    public void deletePokemon(PokemonDB pokemonDB);

    @Query("delete from PokemonDB where name = :pokeName")
    public void deletebyName(String pokeName);

    @Query("select * from PokemonDB where id = :id")
    public PokemonDB getPokemonbyID(int id);
}
