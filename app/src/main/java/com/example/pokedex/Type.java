package com.example.pokedex;

import java.util.List;

public class Type {

    private int id;
    private String name;
    private List<TypePokemon> pokemon;

    public Type(int id, String name, List<TypePokemon> pokemon) {
        this.id = id;
        this.name = name;
        this.pokemon = pokemon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<TypePokemon> getPokemon() {
        return pokemon;
    }
}
