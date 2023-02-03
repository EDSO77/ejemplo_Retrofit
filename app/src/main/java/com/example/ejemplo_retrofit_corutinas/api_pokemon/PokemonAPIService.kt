package com.example.ejemplo_retrofit_corutinas.api_pokemon

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonAPIService {
    //Sin RxJava
    @GET
    suspend fun getPokemones(@Url url:String):Response<PokemonResponse>

    //Con RxJava
    @GET
    fun getPokemonesRx(@Url url:String):Observable<PokemonResponse>


}