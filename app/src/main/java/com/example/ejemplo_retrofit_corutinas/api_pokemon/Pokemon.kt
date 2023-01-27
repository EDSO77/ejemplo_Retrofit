package com.example.ejemplo_retrofit_corutinas.api_pokemon

import com.google.gson.annotations.SerializedName

data class Pokemon (
    @SerializedName("name")val name: String,
    @SerializedName("url")val url: String
        )