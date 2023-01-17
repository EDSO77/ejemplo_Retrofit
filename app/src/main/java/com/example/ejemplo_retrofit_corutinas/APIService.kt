package com.example.ejemplo_retrofit_corutinas

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    //se indica el tipo de llamada: @GET,
    @GET
    suspend fun getDogsByBreeds(@Url url:String):Response<DogResponse>
    //el modificador suspend debe estar presente en cualquier funcion
    // que vaya a ser ejecutada en un hilo secundario
}
/*
Al parecer el GET y Url son etiquetas especiales de retrofit, esto deja pensar, que esta sera una interface
que se debera aplicar paracticamente igual en cada caso, si la llamda es get creo que es editable por los unicos valores:
* nombre de funcion
* modelo de datos para el Response

a menos que la URL se pueda pasar como otro tipo de dato en vez de String

*/