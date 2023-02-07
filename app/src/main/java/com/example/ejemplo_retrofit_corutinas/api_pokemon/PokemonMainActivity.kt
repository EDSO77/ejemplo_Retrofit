package com.example.ejemplo_retrofit_corutinas.api_pokemon

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejemplo_retrofit_corutinas.databinding.PokemonActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PokemonMainActivity : AppCompatActivity() {

    private val binding: PokemonActivityMainBinding by lazy {
        PokemonActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: PokemonAdapter
    var pokemones = mutableListOf<Pokemon>() // se convertira a observador
    var nextPage = ""
    var previousPage = ""
    var pagina = 1 // se convertira a observador
    var compositeDisposable = CompositeDisposable() //*** Se crea una instancia del CompositeDisposable, para agregar las relaciones Observador Observable

    private lateinit var pokemonViewModel : PokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //calculationViewModel = ViewModelProvider(this).get(CalculationViewModel::class.java)
        //bindActionViewModel()

        //calculationViewModel.fulloperationLiveData.observe(this, Observer{
        //  binding.tvCalScreen.text= it
        //})
        //calculationViewModel.resultLiveData.observe(this, Observer{
        //  binding.tvCalResult.text= it
        //})
        initReciclerView()

        pokemonViewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)
        //siguen los live data
        pokemonViewModel.pokemonesLiveData.observe(this, Observer {
            pokemones = it
            adapter.notifyDataSetChanged()
        })
        pokemonViewModel.paginaLiveData.observe(this, Observer {
            binding.tvPagina.text = "Pagina $it"
        })

        pokemonViewModel.quertyPokemonRx()






        binding.btNext.setOnClickListener {

        }

        binding.btPrevious.setOnClickListener {
            if (pagina > 1) {
                quertyPokemonRx(previousPage)
                pagina--
                upToDateCurrentPage()
            } else {
                Toast.makeText(
                    this@PokemonMainActivity,
                    "Has llegado a la primer pagina",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }



    private fun initReciclerView() {
        adapter = PokemonAdapter(pokemones)
        binding.rvPokemones.layoutManager = LinearLayoutManager(this)
        binding.rvPokemones.adapter = adapter

    }






}