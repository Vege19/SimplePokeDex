package com.example.vege.pokeapiexample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.vege.pokeapiexample.adapter.PokemonAdapter;
import com.example.vege.pokeapiexample.api.PokeApi;
import com.example.vege.pokeapiexample.models.Pokemon;
import com.example.vege.pokeapiexample.models.PokemonRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PokemonAdapter mAdapter;
    private String URL = "http://pokeapi.co/api/v2/";
    PokeApi api;
    private Retrofit retrofit;
    private int offset;
    private boolean readyToLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recyclerview setup
        mRecyclerView = findViewById(R.id.pkmRecyclerView);
        mAdapter = new PokemonAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(layoutManager);

        //to load more of 20 pokemon
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (readyToLoad) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Toast.makeText(MainActivity.this, "This is the end.", Toast.LENGTH_SHORT).show();

                            readyToLoad = false;
                            offset += 20;
                            getData(offset);
                        }
                    }
                }
            }
        });

        //retrofit setup
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        readyToLoad = true;
        offset = 0;

        //get data
        getData(offset);
    }

    private void getData(int offset) {
        PokeApi service = retrofit.create(PokeApi.class);
        Call<PokemonRequest> pokemonRequestCall = service.pokemonList(20, offset);

        pokemonRequestCall.enqueue(new Callback<PokemonRequest>() {
            @Override
            public void onResponse(Call<PokemonRequest> call, Response<PokemonRequest> response) {
                readyToLoad  = true;
                if (response.isSuccessful()) {

                    PokemonRequest pokemonRequest = response.body();
                    ArrayList<Pokemon> pokemonList = pokemonRequest.getResults();

                    mAdapter.addPokemonList(pokemonList);

                } else {

                    Toast.makeText(MainActivity.this, "Error al cargar...", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<PokemonRequest> call, Throwable t) {
                readyToLoad = true;
                Toast.makeText(MainActivity.this, "Error al cargar...", Toast.LENGTH_SHORT).show();
            }
        });
    }


}