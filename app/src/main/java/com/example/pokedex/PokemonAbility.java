package com.example.pokedex;

//pokemon ability model
public class PokemonAbility {

    private boolean is_hidden;
    private int slot;
    private NamedAPIResource ability;

    public PokemonAbility(boolean is_hidden, int Slot, NamedAPIResource Ability) {
        this.is_hidden = is_hidden;
        this.slot = Slot;
        this.ability = Ability;
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
