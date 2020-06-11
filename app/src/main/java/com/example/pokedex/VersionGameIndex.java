package com.example.pokedex;

public class VersionGameIndex {

    private int game_index;
    private NamedAPIResource version;

    public VersionGameIndex(int game_index, NamedAPIResource version) {
        this.game_index = game_index;
        this.version = version;
    }

    public int getGame_index() {
        return game_index;
    }

    public NamedAPIResource getVersion() {
        return version;
    }
}
