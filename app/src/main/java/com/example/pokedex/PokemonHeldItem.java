package com.example.pokedex;

import java.util.List;

public class PokemonHeldItem {

    private NamedAPIResource item;
    private List<PokemonHeldItemVersion> version_details;

    public PokemonHeldItem(NamedAPIResource item, List<PokemonHeldItemVersion> version_details) {
        this.item = item;
        this.version_details = version_details;
    }

    public NamedAPIResource getItem() {
        return item;
    }

    public List<PokemonHeldItemVersion> getVersion_details() {
        return version_details;
    }
}
