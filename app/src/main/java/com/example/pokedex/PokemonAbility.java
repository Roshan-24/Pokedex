package com.example.pokedex;

public class PokemonAbility {

    private boolean is_hidden;
    private int slot;
    private NamedAPIResource ability;

    public PokemonAbility(boolean is_hidden, int slot, NamedAPIResource ability) {
        this.is_hidden = is_hidden;
        this.slot = slot;
        this.ability = ability;
    }

    public boolean isIs_hidden() {
        return is_hidden;
    }

    public int getSlot() {
        return slot;
    }

    public NamedAPIResource getAbility() {
        return ability;
    }
}
