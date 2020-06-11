package com.example.pokedex;

import java.util.List;

public class ChainLink {

    private boolean is_baby;
    private NamedAPIResource species;
    private List<ChainLink> evolves_to;

    public ChainLink(boolean is_baby, NamedAPIResource species, List<ChainLink> evolves_to) {
        this.is_baby = is_baby;
        this.species = species;
        this.evolves_to = evolves_to;
    }

    public boolean isIs_baby() {
        return is_baby;
    }

    public NamedAPIResource getSpecies() {
        return species;
    }

    public List<ChainLink> getEvolves_to() {
        return evolves_to;
    }
}
