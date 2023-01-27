package com.example.ejemplo_retrofit_corutinas.api_pokemon

import com.google.gson.annotations.SerializedName

data class PokemonResponse (
    @SerializedName("count")val count : String,
    @SerializedName("next")val next : String,
    @SerializedName("previous")val previous : String?,
    @SerializedName("results")val pokemones : List<Pokemon>,
)