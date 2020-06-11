package com.example.pokedex;

import java.util.List;

public class Pokedex {

    private int id;
    private String name;
    private List<PokemonEntry> pokemon_entries;

    public Pokedex(int id, String name, List<PokemonEntry> pokemon_entries) {
        this.id = id;
        this.name = name;
        this.pokemon_entries = pokemon_entries;
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
