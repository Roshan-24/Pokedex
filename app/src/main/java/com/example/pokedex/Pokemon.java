package com.example.pokedex;

import java.util.List;

public class Pokemon {

    private int id;
    private String name;
    private int base_experience;
    private int height;
    private boolean is_default;
    private int order;
    private int weight;
    private List<PokemonAbility> abilities;
    private List<NamedAPIResource> forms;
    private List<VersionGameIndex> game_indices;
    private List<PokemonHeldItem> held_items;
    private String location_area_encounters;
    private List<PokemonMove> moves;
    private PokemonSprites sprites;
    private NamedAPIResource species;
    private List<PokemonStat> stats;
    private List<PokemonType> types;

    public Pokemon(int id, String name, int base_experience, int height, boolean is_default, int order, int weight, List<PokemonAbility> abilities, List<NamedAPIResource> forms, List<VersionGameIndex> game_indices, List<PokemonHeldItem> held_items, String location_area_encounters, List<PokemonMove> moves, PokemonSprites sprites, NamedAPIResource species, List<PokemonStat> stats, List<PokemonType> types) {
        this.id = id;
        this.name = name;
        this.base_experience = base_experience;
        this.height = height;
        this.is_default = is_default;
        this.order = order;
        this.weight = weight;
        this.abilities = abilities;
        this.forms = forms;
        this.game_indices = game_indices;
        this.held_items = held_items;
        this.location_area_encounters = location_area_encounters;
        this.moves = moves;
        this.sprites = sprites;
        this.species = species;
        this.stats = stats;
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBase_experience() {
        return base_experience;
    }

    public int getHeight() {
        return height;
    }

    public boolean isIs_default() {
        return is_default;
    }

    public int getOrder() {
        return order;
    }

    public int getWeight() {
        return weight;
    }

    public List<PokemonAbility> getAbilities() {
        return abilities;
    }

    public List<NamedAPIResource> getForms() {
        return forms;
    }

    public List<VersionGameIndex> getGame_indices() {
        return game_indices;
    }

    public List<PokemonHeldItem> getHeld_items() {
        return held_items;
    }

    public String getLocation_area_encounters() {
        return location_area_encounters;
    }

    public List<PokemonMove> getMoves() {
        return moves;
    }

    public PokemonSprites getSprites() {
        return sprites;
    }

    public NamedAPIResource getSpecies() {
        return species;
    }

    public List<PokemonStat> getStats() {
        return stats;
    }

    public List<PokemonType> getTypes() {
        return types;
    }
}
