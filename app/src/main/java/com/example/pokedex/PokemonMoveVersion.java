package com.example.pokedex;

public class PokemonMoveVersion {

    private NamedAPIResource move_learn_method;
    private NamedAPIResource version_group;
    private int level_learned_at;

    public PokemonMoveVersion(NamedAPIResource move_learn_method, NamedAPIResource version_group, int level_learned_at) {
        this.move_learn_method = move_learn_method;
        this.version_group = version_group;
        this.level_learned_at = level_learned_at;
    }

    public NamedAPIResource getMove_learn_method() {
        return move_learn_method;
    }

    public NamedAPIResource getVersion_group() {
        return version_group;
    }

    public int getLevel_learned_at() {
        return level_learned_at;
    }
}
