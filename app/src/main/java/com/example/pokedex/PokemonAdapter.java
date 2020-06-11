package com.example.pokedex;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.databases.PokemonDB;
import com.example.pokedex.fragments.PokemonListFragment;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> implements Filterable {
    private List<NamedAPIResource> mPokemonList;
    private List<NamedAPIResource> pokemonListFull;
    private OnItemListener mListener;

    private PokeAPI pokeAPI;
    private String pokemonUrl;
    private PokemonDB pokemonDB;

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;

        public PokemonViewHolder(@NonNull View itemView, OnItemListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        String url = mPokemonList.get(position).getUrl();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, url, v);
                        }
                    }
                }
            });
        }
    }

    public PokemonAdapter(List<NamedAPIResource> pokemonList) {
        mPokemonList = pokemonList;
        pokemonListFull = new ArrayList<>(mPokemonList);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pokeAPI = retrofit.create(PokeAPI.class);
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        PokemonViewHolder pvh = new PokemonViewHolder(v, mListener);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        NamedAPIResource currentPokemon = mPokemonList.get(position);

        String url = currentPokemon.getUrl();

        if(url.length() < 10 || url.substring(0, 33).equals("https://pokeapi.co/api/v2/pokemon")) {
            String id;
            if(url.length() < 10) {
                id = url;
            }
            else {
                if(url.substring(0,34).equals("https://pokeapi.co/api/v2/pokemon-")) {
                    id = url.substring(42, url.length() - 1);
                }
                else {
                    id = url.substring(34, url.length() - 1);
                }
            }
            Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png").into(holder.imageView);
        }

        holder.textView1.setText(currentPokemon.getName());
        holder.textView2.setText("#" + (position+1));
    }

    @Override
    public int getItemCount() {
        return mPokemonList.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, String url, View view);
    }

    public void setOnItemListener(OnItemListener listener) {
        mListener = listener;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<NamedAPIResource> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(pokemonListFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(NamedAPIResource item : pokemonListFull) {
                    if(item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mPokemonList.clear();
            mPokemonList.addAll((List) results.values);

            notifyDataSetChanged();
        }
    };

     public ItemTouchHelper.SimpleCallback itchMain = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            pokemonUrl = mPokemonList.get(viewHolder.getAdapterPosition()).getUrl();

            Snackbar.make(MainActivity.pokemonListFragment.recyclerView, mPokemonList.get(viewHolder.getAdapterPosition()).getName() + " added to favorites", Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PokemonDB pokemonDB1 = new PokemonDB();
                            pokemonDB1.setId(pokemonDB.getId());

                            MainActivity.pokemonDatabaseAbs.pokemonDao().deletePokemon(pokemonDB1);
                        }
                    }).show();

            getPokemon();
        }
    };

    public ItemTouchHelper.SimpleCallback itchTypeFilter = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            pokemonUrl = mPokemonList.get(viewHolder.getAdapterPosition()).getUrl();

            Snackbar.make(FilteredPokemonList.recyclerView, mPokemonList.get(viewHolder.getAdapterPosition()).getName() + " added to favorites", Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PokemonDB pokemonDB1 = new PokemonDB();
                            pokemonDB1.setId(pokemonDB.getId());

                            MainActivity.pokemonDatabaseAbs.pokemonDao().deletePokemon(pokemonDB1);
                        }
                    }).show();

            getPokemon();
        }
    };

    public ItemTouchHelper.SimpleCallback itchRegionFilter = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            pokemonUrl = mPokemonList.get(viewHolder.getAdapterPosition()).getUrl();

            Snackbar.make(FilteredPokemonbyRegion.recyclerView, mPokemonList.get(viewHolder.getAdapterPosition()).getName() + " added to favorites", Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PokemonDB pokemonDB1 = new PokemonDB();
                            pokemonDB1.setId(pokemonDB.getId());

                            MainActivity.pokemonDatabaseAbs.pokemonDao().deletePokemon(pokemonDB1);
                        }
                    }).show();

            getPokemon();
        }
    };

    public ItemTouchHelper.SimpleCallback ithcDelete = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            PokemonDB pokemonDB = new PokemonDB();
            pokemonDB.setName(mPokemonList.get(viewHolder.getAdapterPosition()).getName());


            //pokemonDB.setId(6);

            MainActivity.pokemonDatabaseAbs.pokemonDao().deletebyName(pokemonDB.getName());
            mPokemonList.remove(viewHolder.getAdapterPosition());

            notifyDataSetChanged();
        }
    };

    private void getPokemon() {
        Call<Pokemon> call = pokeAPI.getPokemonbyUrl(pokemonUrl);

        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if(!response.isSuccessful()) {
                    return;
                }

                Pokemon pokemon = response.body();
                pokemonDB = new PokemonDB();

                pokemonDB.setImgurl(pokemon.getSprites().getFront_default());

                pokemonDB.setHp(pokemon.getStats().get(5).getBase_stat());
                pokemonDB.setAttack(pokemon.getStats().get(4).getBase_stat());
                pokemonDB.setDefense(pokemon.getStats().get(3).getBase_stat());
                pokemonDB.setSpAtt(pokemon.getStats().get(2).getBase_stat());
                pokemonDB.setSpDef(pokemon.getStats().get(1).getBase_stat());
                pokemonDB.setSpeed(pokemon.getStats().get(0).getBase_stat());

                String abilitiess = "";
                for(int i = 0 ; i < pokemon.getAbilities().size() ; i++) {
                    abilitiess += pokemon.getAbilities().get(i).getAbility().getName();
                    abilitiess += ", ";
                }
                abilitiess = abilitiess.substring(0, abilitiess.length() -2);

                pokemonDB.setAbilities(abilitiess);

                String types = "";
                for(int i = 0; i < pokemon.getTypes().size() ; i++) {
                    types += pokemon.getTypes().get(i).getType().getName();
                    types += "/";
                }
                types = (types == null || types.length() == 0) ? null : (types.substring(0, types.length() - 1));

                pokemonDB.setTypes(types);
                pokemonDB.setName(pokemon.getName());
                pokemonDB.setId(pokemon.getId());

                MainActivity.pokemonDatabaseAbs.pokemonDao().addPokemon(pokemonDB);



                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

            }
        });
    }
}
