package com.example.ejemplo_retrofit_corutinas.api_pokemon

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejemplo_retrofit_corutinas.databinding.PokemonActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections

class PokemonMainActivity : AppCompatActivity() {

    private val binding: PokemonActivityMainBinding by lazy {
        PokemonActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: PokemonAdapter
    var pokemones = mutableListOf<Pokemon>()
    var nextPage = ""
    var previousPage = ""
    var pagina = 1
    //*** Se crea una instancia del CompositeDisposable, para agregar las relaciones Observador Observable
    var compositeDisposable = CompositeDisposable()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initReciclerView()
        upToDateCurrentPage()
      //quertyPokemon()
      quertyPokemonRx()
       //quertyPokemonRxLambda()

        binding.btNext.setOnClickListener {
            quertyPokemonRx(nextPage)
            pagina++
            upToDateCurrentPage()
        }

        binding.btPrevious.setOnClickListener {
            if (pagina>1) {
                quertyPokemonRx(previousPage)
                pagina--
                upToDateCurrentPage()
            }else{
                Toast.makeText(this@PokemonMainActivity, "Has llegado a la primer pagina", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun upToDateCurrentPage() {
        binding.tvPagina.text = "Pagina $pagina"
    }

    private fun initReciclerView() {
        adapter = PokemonAdapter(pokemones)
        binding.rvPokemones.layoutManager = LinearLayoutManager(this)
        binding.rvPokemones.adapter = adapter

    }

    private fun orderByNameList(pokemonResponse: PokemonResponse): List<Pokemon> {
        return pokemonResponse.pokemones.sortedBy { it.name }
    }

    private fun quertyPokemonRxMap(query:String="https://pokeapi.co/api/v2/pokemon/"){

        compositeDisposable.add(
            getRetrofit(query)
                .create(PokemonAPIService::class.java)
                .getPokemonesRx("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.map {

               // }
                //.map (this::orderByNameList)
                //.map { pokemonResponse ->
                 //   pokemonResponse.pokemones.sortedBy { it.name }
                //(}
                .subscribe(object : Consumer<PokemonResponse> {
                    override fun accept(t: PokemonResponse?) {
                        Log.d("TAG2", "on accepted ${Thread.currentThread().getName()}")
                        if (t!=null) {
                            Log.d("TAG2", "on if ${Thread.currentThread().getName()}")
                            val images = t.pokemones ?: emptyList()
                            pokemones.clear()
                            pokemones.addAll(images)
                            nextPage = t.next ?: ""
                            previousPage = t.previous ?: ""
                            adapter.notifyDataSetChanged() //*** Preguntar a ivan a que se refiere la sugerencia
                            Toast.makeText(this@PokemonMainActivity, "Actualizado con Rx", Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(this@PokemonMainActivity, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                        }
                    }
                })


                //.map { orderByNameList() }
                /*.map {object : io.reactivex.functions.Function<PokemonResponse,PokemonResponse>{
                        override fun apply(t: PokemonResponse): PokemonResponse {
                                Collections.sort(t.pokemones, object : Comparator<Pokemon> {
                                    override fun compare(o1: Pokemon?, o2: Pokemon?): Int {
                                        if (o1 != null && o2 != null) {
                                            return o1.name.compareTo(o2.name)
                                        }else{return 0}
                                    }
                                })
                            return t
                        }

                    }}*/


        )

    }


    private fun quertyPokemonRxLambda(query:String="https://pokeapi.co/api/v2/pokemon/"){

        compositeDisposable.add(
            getRetrofit(query)
                .create(PokemonAPIService::class.java)
                .getPokemonesRx("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t ->
                    if (t != null) {
                        val images = t.pokemones ?: emptyList()
                        pokemones.clear()
                        pokemones.addAll(images)
                        nextPage = t.next ?: ""
                        previousPage = t.previous ?: ""
                        adapter.notifyDataSetChanged() //*** Preguntar a ivan a que se refiere la sugerencia
                        Toast.makeText(this@PokemonMainActivity, "Actualizado con Rx Lambda", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@PokemonMainActivity, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                    }
                }

        )

    }

    private fun quertyPokemonRx(query:String="https://pokeapi.co/api/v2/pokemon/"){

        compositeDisposable.add(
            getRetrofit(query)
                .create(PokemonAPIService::class.java)
                .getPokemonesRx("")
                    //*** debo investigar como ver un Log en el observer para este caso
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<PokemonResponse> {
                    override fun accept(t: PokemonResponse?) {
                        Log.d("TAG2", "on accepted ${Thread.currentThread().getName()}")
                        if (t!=null) {
                            Log.d("TAG2", "on if ${Thread.currentThread().getName()}")
                            val images = t.pokemones ?: emptyList()
                            pokemones.clear()
                            pokemones.addAll(images)
                            nextPage = t.next ?: ""
                            previousPage = t.previous ?: ""
                            adapter.notifyDataSetChanged() //*** Preguntar a ivan a que se refiere la sugerencia
                            Toast.makeText(this@PokemonMainActivity, "Actualizado con Rx", Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(this@PokemonMainActivity, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                        }
                    }
                })

        )

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
                }else {
                    Toast.makeText(this@PokemonMainActivity, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()}

            }
        }
    }

    private fun getRetrofit(url:String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


}