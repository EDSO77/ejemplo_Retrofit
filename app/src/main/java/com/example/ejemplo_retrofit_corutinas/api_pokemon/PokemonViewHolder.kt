package com.example.ejemplo_retrofit_corutinas.api_pokemon

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ejemplo_retrofit_corutinas.databinding.ItemPokemonBinding
import com.squareup.picasso.Picasso

class PokemonViewHolder(view: View): RecyclerView.ViewHolder(view){
   private val binding = ItemPokemonBinding.bind(view)
    fun render(pokemon: Pokemon){
        //*** El view holder sera igual o similar, pero lo que en un principio pense que era
        //***la "url" de la imagen, devuelve toda la informacion del pokemon incluida una imagen dentro

        // ***0 1 2 3 4 5 6 7 8 9 10 1 2 3 4 5 6 7 8 9 20 1 2 3 4 5 6 7 8 9 30 1 2 3 4 5
        // ***h t t p s : / / p o k  e a p i . c o / a p  i / v 2 / p o k e m  o n / 1 /
        val numImg = pokemon.url.subSequence(34,(pokemon.url.length-1))
        val img = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/shiny/${numImg}.png"

        binding.tvPokemonName.text = pokemon.name
        Picasso.get().load(img).into(binding.ivPokemon)
        //binding.ivPokemon.setOnClickListener{
        // Toast.makeText(context,"Has seleccionado a ${pokemon.name}",Toast.LENGTH_SHORT).show()}
        //no pude resolver cual seria el context para el toast, por eso lo dejo pendiente
    }

}