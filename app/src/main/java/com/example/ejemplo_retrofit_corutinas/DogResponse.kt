package com.example.ejemplo_retrofit_corutinas

import com.google.gson.annotations.SerializedName
//Siempre se debe utilizar el @SerializedName para recibir servicios, y como parametro debe contener el nombre de los campos
//exacto que da la respuesta, mayus, min, etc. todos tal cual; se puede analizar el modelo json que nos envia la fuente
//en paginas como JSON EDITOR ONLINE
data class DogResponse (
    @SerializedName("status") var status:String,
    @SerializedName("message") var images:List<String>)