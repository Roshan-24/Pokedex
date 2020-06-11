package com.example.pokedex;

public class PokemonHeldItemVersion {

    private NamedAPIResource version;
    private int rarity;

    public PokemonHeldItemVersion(NamedAPIResource version, int rarity) {
        this.version = version;
        this.rarity = rarity;
    }

    public NamedAPIResource getVersion() {
        return version;
    }

    public int getRarity() {
        return rarity;
    }
}
