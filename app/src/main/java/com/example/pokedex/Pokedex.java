package com.example.pokedex;

import java.util.List;

public class Pokedex {

    private int id;
    private String name;
    private List<PokemonEntry> pokemon_entries;

    public Pokedex(int Id, String Name, List<PokemonEntry> Pokemon_entries) {
        this.id = Id;
        this.name = Name;
        this.pokemon_entries = Pokemon_entries;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PokemonEntry> getPokemon_entries() {
        return pokemon_entries;
    }
}
