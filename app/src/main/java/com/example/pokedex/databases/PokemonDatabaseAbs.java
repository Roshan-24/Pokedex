package com.example.pokedex.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities =  {PokemonDB.class}, version = 2, exportSchema = false)
public abstract class PokemonDatabaseAbs extends RoomDatabase {

    public abstract PokemonDao pokemonDao();
}
