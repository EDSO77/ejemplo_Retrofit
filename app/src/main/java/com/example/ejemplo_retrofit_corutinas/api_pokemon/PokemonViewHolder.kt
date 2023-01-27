package com.example.ejemplo_retrofit_corutinas.api_pokemon

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ejemplo_retrofit_corutinas.databinding.ItemPokemonBinding
import com.squareup.picasso.Picasso

class PokemonViewHolder(view: View): RecyclerView.ViewHolder(view){
   private val binding = ItemPokemonBinding.bind(view)
    fun render(pokemon: Pokemon){
        binding.tvPokemonName.text = pokemon.name



        Picasso.get().load(pokemon.url).into(binding.ivPokemon)
        //binding.ivPokemon.setOnClickListener{
        // Toast.makeText(context,"Has seleccionado a ${pokemon.name}",Toast.LENGTH_SHORT).show()}
        //no pude resolver cual seria el context para el toast, por eso lo dejo pendiente
    }

}