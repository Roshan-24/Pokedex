package com.example.pokedex;

public class PokemonSpeciesVariety {

    private boolean is_default;
    private NamedAPIResource pokemon;

    public PokemonSpeciesVariety(boolean is_default, NamedAPIResource pokemon) {
        this.is_default = is_default;
        this.pokemon = pokemon;
    }

    public boolean isIs_default() {
        return is_default;
    }

    public NamedAPIResource getPokemon() {
        return pokemon;
    }
}
