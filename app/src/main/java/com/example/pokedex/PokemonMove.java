package com.example.pokedex;

import java.util.List;

public class PokemonMove {

    private NamedAPIResource move;
    private List<PokemonMoveVersion> version_group_details;

    public PokemonMove(NamedAPIResource move, List<PokemonMoveVersion> version_group_details) {
        this.move = move;
        this.version_group_details = version_group_details;
    }

    public NamedAPIResource getMove() {
        return move;
    }

    public List<PokemonMoveVersion> getVersion_group_details() {
        return version_group_details;
    }
}
