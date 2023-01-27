package com.example.ejemplo_retrofit_corutinas.api_pokemon

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonAPIService {
    @GET
    suspend fun getPokemones(@Url url:String):Response<PokemonResponse>
}