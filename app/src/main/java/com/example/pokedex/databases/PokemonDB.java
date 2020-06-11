package com.example.pokedex.databases;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PokemonDB {

    @PrimaryKey
    private int id;
    private String imgurl;
    private String name;
    private String types;
    private String abilities;
    private String evoChain;
    private int hp;
    private int attack;
    private int defense;
    private int spAtt;
    private int spDef;
    private int speed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public String getEvoChain() {
        return evoChain;
    }

    public void setEvoChain(String evoChain) {
        this.evoChain = evoChain;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpAtt() {
        return spAtt;
    }

    public void setSpAtt(int spAtt) {
        this.spAtt = spAtt;
    }

    public int getSpDef() {
        return spDef;
    }

    public void setSpDef(int spDef) {
        this.spDef = spDef;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
