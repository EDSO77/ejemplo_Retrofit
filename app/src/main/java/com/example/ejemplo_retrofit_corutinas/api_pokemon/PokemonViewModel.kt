package com.example.ejemplo_retrofit_corutinas.api_pokemon

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PokemonViewModel: ViewModel(){


    private lateinit var adapter: PokemonAdapter

    /*val fulloperationLiveData = MutableLiveData<String>()
    var fulloperation: String = ""*/

    var pokemonesLiveData = MutableLiveData<MutableList<Pokemon>>()  //necesita ser pasado como LiveData
    var nextPage = ""
    var previousPage = ""
    var paginaLiveData = MutableLiveData<Int>()//necesita ser pasado como LiveData
    var compositeDisposable = CompositeDisposable() //*** Se crea una instancia del CompositeDisposable, para agregar las relaciones Observador Observable

    fun quertyPokemonRx(query: String = "https://pokeapi.co/api/v2/pokemon/") {

        compositeDisposable.add(
            getRetrofit(query)
                .create(PokemonAPIService::class.java)
                .getPokemonesRx("")
                //*** debo investigar como ver un Log en el observer para este caso
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<PokemonResponse> {
                    override fun accept(t: PokemonResponse?) {
                        Log.d("TAG2", "on accepted ${Thread.currentThread().name}")
                        if (t != null) {
                            Log.d("TAG2", "on if ${Thread.currentThread().name}")
                            val images = t.pokemones
                            pokemones.clear()
                            pokemones.addAll(images)
                            nextPage = t.next
                            previousPage = t.previous ?: ""
                            adapter.notifyDataSetChanged() //*** Preguntar a ivan a que se refiere la sugerencia
                            Toast.makeText(
                                this@PokemonMainActivity,
                                "Actualizado con Rx",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            Toast.makeText(
                                this@PokemonMainActivity,
                                "Ha ocurrido un error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })

        )

    }


    private fun getRetrofit(url: String): Retrofit {
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

    fun clickNext(){
        quertyPokemonRx(nextPage)
        paginaLiveData=
    }
    fun clickNext(){
        quertyPokemonRx(nextPage)
        pagina++
        upToDateCurrentPage()
    }

}