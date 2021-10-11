package com.example.pokedex;

import java.util.List;

public class ChainLink {

    private boolean is_baby;
    private NamedAPIResource species;
    private List<ChainLink> evolves_to;

    public ChainLink(boolean Is_baby, NamedAPIResource Species, List<ChainLink> Evolves_to) {
        this.is_baby = Is_baby;
        this.species = Species;
        this.evolves_to = Evolves_to;
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
