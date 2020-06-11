package com.example.pokedex;

public class PokemonStat {

    private NamedAPIResource stat;
    private int effort;
    private int base_stat;

    public PokemonStat(NamedAPIResource stat, int effort, int base_stat) {
        this.stat = stat;
        this.effort = effort;
        this.base_stat = base_stat;
    }

    public NamedAPIResource getStat() {
        return stat;
    }

    public int getEffort() {
        return effort;
    }

    public int getBase_stat() {
        return base_stat;
    }
}
