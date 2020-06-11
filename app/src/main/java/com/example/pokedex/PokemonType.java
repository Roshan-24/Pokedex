package com.example.pokedex;

public class PokemonType {

    private int slot;
    private NamedAPIResource type;

    public PokemonType(int slot, NamedAPIResource type) {
        this.slot = slot;
        this.type = type;
    }

    public int getSlot() {
        return slot;
    }

    public NamedAPIResource getType() {
        return type;
    }
}
