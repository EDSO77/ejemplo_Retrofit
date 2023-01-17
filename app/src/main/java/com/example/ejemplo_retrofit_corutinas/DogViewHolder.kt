package com.example.ejemplo_retrofit_corutinas

import android.content.ClipData.Item
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ejemplo_retrofit_corutinas.databinding.ItemDogBinding
import com.squareup.picasso.Picasso
import retrofit2.Response

class DogViewHolder(view: View):RecyclerView.ViewHolder(view){
    private val binding = ItemDogBinding.bind(view)
    fun bind(image:String){
        Picasso.get().load(image).into(binding.ivDog)

    }
}