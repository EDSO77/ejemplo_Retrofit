package com.example.ejemplo_retrofit_corutinas.api_pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejemplo_retrofit_corutinas.databinding.PokemonActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonMainActivity : AppCompatActivity() {

    private val binding: PokemonActivityMainBinding by lazy {
        PokemonActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: PokemonAdapter
    var pokemones = mutableListOf<Pokemon>()
    var nextPage = ""
    var previousPage = ""
    var counter = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initReciclerView()
        quertyPokemon()


        binding.btNext.setOnClickListener {
            quertyPokemon(nextPage)
            counter++
            binding.tvPagina.text = "Pagina $counter"
        }

        binding.btPrevious.setOnClickListener {
            if (previousPage!="") {
                quertyPokemon(previousPage)
                counter--
                binding.tvPagina.text = "Pagina $counter"
            }else{
                Toast.makeText(this@PokemonMainActivity, "Has llegado a la primer pagina", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun initReciclerView() {
        adapter = PokemonAdapter(pokemones)
        binding.rvPokemones.layoutManager = LinearLayoutManager(this)
        binding.rvPokemones.adapter = adapter
    }

    private fun quertyPokemon(query:String="https://pokeapi.co/api/v2/pokemon/") {

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit(query).create(PokemonAPIService::class.java).getPokemones("")
            val info = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    //hacemos las igualaciones a las lista y eso
                    val images = info?.pokemones ?: emptyList()
                    pokemones.clear()
                    pokemones.addAll(images)
                    nextPage = info?.next ?: ""
                    previousPage = info?.previous ?: ""
                    adapter.notifyDataSetChanged()
                }

                Toast.makeText(this@PokemonMainActivity, "Ha ocurrido un error", Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }

    private fun getRetrofit(url:String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}