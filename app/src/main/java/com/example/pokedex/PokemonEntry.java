package com.example.pokedex;

import java.util.List;

public class PokemonEntry {

    private int entry_number;
    private NamedAPIResource pokemon_species;

    public PokemonEntry(int entry_number, NamedAPIResource pokemon_species) {
        this.entry_number = entry_number;
        this.pokemon_species = pokemon_species;
    }

    public int getEntry_number() {
        return entry_number;
    }

    public NamedAPIResource getPokemon_species() {
        return pokemon_species;
    }
}
