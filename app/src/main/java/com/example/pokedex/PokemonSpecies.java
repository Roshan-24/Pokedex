package com.example.pokedex;

import com.example.pokedex.fragments.APIResource;

import java.util.List;

public class PokemonSpecies {

    private int id;
    private String name;
    private APIResource evolution_chain;
    private List<PokemonSpeciesVariety> varieties;

    public PokemonSpecies(int id, String name, APIResource evolution_chain, List<PokemonSpeciesVariety> varieties) {
        this.id = id;
        this.name = name;
        this.evolution_chain = evolution_chain;
        this.varieties = varieties;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public APIResource getEvolution_chain() {
        return evolution_chain;
    }

    public List<PokemonSpeciesVariety> getVarieties() {
        return varieties;
    }
}
