package com.example.vege.pokeapiexample.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vege.pokeapiexample.R;
import com.example.vege.pokeapiexample.models.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private ArrayList<Pokemon> dataset;
    private Context context;
    private String spriteURL = "https://pokeres.bastionbot.org/images/pokemon/";

    public PokemonAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pokemon, viewGroup, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder pokemonViewHolder, int i) {
        Pokemon p = dataset.get(i);

        pokemonViewHolder.pkmName.setText(p.getName());
        pokemonViewHolder.pkmNumber.setText("#" + String.valueOf(p.getNumber()));

        //getting image with picasso
        Picasso.get()
                .load(spriteURL + p.getNumber() + ".png")
                .resize(500, 500)
                .centerCrop()
                .into(pokemonViewHolder.pkmSprite);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        private CardView pkmItem;
        private ImageView pkmSprite;
        private TextView pkmName;
        private TextView pkmNumber;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            pkmItem = itemView.findViewById(R.id.pkmItem);
            pkmName = itemView.findViewById(R.id.pkmName);
            pkmSprite = itemView.findViewById(R.id.pkmSprite);
            pkmNumber = itemView.findViewById(R.id.pkmNumber);
        }
    }

    public void addPokemonList(ArrayList<Pokemon> pokemonList) {
        dataset.addAll(pokemonList);
        notifyDataSetChanged();
    }
}
