package com.example.ejemplo_retrofit_corutinas.api_pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejemplo_retrofit_corutinas.R

class PokemonAdapter(val pokemones: List<Pokemon>): RecyclerView.Adapter<PokemonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.item_pokemon,parent,false))
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item=pokemones[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = pokemones.size
}