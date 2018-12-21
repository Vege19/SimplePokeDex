package com.example.vege.pokeapiexample.api;

import com.example.vege.pokeapiexample.models.Pokemon;
import com.example.vege.pokeapiexample.models.PokemonRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApi {

    //get pokemon
    @GET("pokemon")
    Call<PokemonRequest> pokemonList(@Query("limit") int limit,
                                     @Query("offset") int offset);
}
