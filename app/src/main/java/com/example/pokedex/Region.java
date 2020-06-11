package com.example.pokedex;

import java.util.List;

public class Region {

    private int id;
    private String name;
    private List<NamedAPIResource> pokedexes;

    public Region(int id, List<NamedAPIResource> pokedexes, String name) {
        this.id = id;
        this.pokedexes = pokedexes;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public List<NamedAPIResource> getPokedexes() {
        return pokedexes;
    }

    public String getName() {
        return name;
    }
}
