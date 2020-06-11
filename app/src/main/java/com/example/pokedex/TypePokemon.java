package com.example.pokedex;

public class TypePokemon {

    private int slot;
    private NamedAPIResource pokemon;

    public TypePokemon(int slot, NamedAPIResource pokemon) {
        this.slot = slot;
        this.pokemon = pokemon;
    }

    public int getSlot() {
        return slot;
    }

    public NamedAPIResource getPokemon() {
        return pokemon;
    }
}
