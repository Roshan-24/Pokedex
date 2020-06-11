package com.example.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex.databases.PokemonDB;
import com.squareup.picasso.Picasso;

public class SavedPokemonPage extends AppCompatActivity {

    private ImageView pokeimg;
    private TextView dexnumber;
    private TextView name;
    private TextView typing;
    private TextView abilities;
    private TextView hp;
    private TextView atk;
    private TextView def;
    private TextView spa;
    private TextView spd;
    private TextView spe;

    public static int ID;
    public static String POKEMONNAME;

    private String text = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_pokemon_page);

        pokeimg = findViewById(R.id.ivPokemon1);
        dexnumber = findViewById(R.id.tvDexNumber1);
        name = findViewById(R.id.tvPokeName1);
        typing = findViewById(R.id.tvTyping1);
        abilities = findViewById(R.id.tvAbilities1);
        hp = findViewById(R.id.tvHP1);
        atk = findViewById(R.id.tvAtk1);
        def = findViewById(R.id.tvDef1);
        spa = findViewById(R.id.tvSpA1);
        spd = findViewById(R.id.tvSpD1);
        spe = findViewById(R.id.tvSpe1);

        PokemonDB pokemonDB = MainActivity.pokemonDatabaseAbs.pokemonDao().getPokemonbyID(ID);

        Picasso.get().load(pokemonDB.getImgurl()).into(pokeimg);

        dexnumber.setText("#" + pokemonDB.getId());
        name.setText(pokemonDB.getName());
        typing.setText(pokemonDB.getTypes());
        abilities.setText(pokemonDB.getAbilities());

        hp.setText("HP: " + pokemonDB.getHp());
        atk.setText("Attack: " + pokemonDB.getAttack());
        def.setText("Defense: " + pokemonDB.getDefense());
        spa.setText("Special Attack: " + pokemonDB.getSpAtt());
        spd.setText("Special Defense: " + pokemonDB.getSpDef());
        spe.setText("Speed: " + pokemonDB.getSpeed());

        text += "Check out this cool pokemon! \n Its name is " + pokemonDB.getName() + " and it is " + pokemonDB.getTypes() + " type \n";
        text += "It can have " + pokemonDB.getAbilities() + " as its abilities! \n Its stats are:";
        text += "HP: " + pokemonDB.getHp() + "\n";
        text += "Attack: " + pokemonDB.getAttack() + "\n";
        text += "Defense: " + pokemonDB.getDefense() + "\n";
        text += "Special Attack: " + pokemonDB.getSpAtt() + "\n";
        text += "Special Defense: " + pokemonDB.getSpDef() + "\n";
        text += "Speed: " + pokemonDB.getSpeed() + "\n";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setType("text/plain");
            startActivity(intent);
        }
        else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
