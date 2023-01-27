package com.example.ejemplo_retrofit_corutinas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejemplo_retrofit_corutinas.api_pokemon.PokemonMainActivity
import com.example.ejemplo_retrofit_corutinas.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: DogAdapter
    private var dogImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.svDogs.setOnQueryTextListener(this)
        initReciclerView()
    }

    private fun initReciclerView() {
        adapter = DogAdapter(dogImages)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = adapter
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun serachByBreed(querty:String) {
        //corutina, dispacher .lanzate... tode lo que este dentro se ejecutara en un hilo secundariop
        CoroutineScope(Dispatchers.IO).launch {
            //retrofit.crea,(una instancia de APPiservice), esta instancia llama a su propio metodo
            // y el metodo pasa como arguneto la solicitud buscada + el resto de direccion necesario
            // por lo tanto el call es un "Response<DogResponse>" que es lo que devuelve la interface
            val call = getRetrofit().create(DogAPIService::class.java).getDogsByBreeds("$querty/images")
            val puppies = call.body() // en el body, es donde se encuentran alojados los datos de la respuesta
            // runOnUiThread, nos regresa a la ejecucion sobre el hilo principal
            runOnUiThread {
                if (call.isSuccessful){
                    // aqui comprobamos que apesar de que la respuesta sea "exitosa" haya llegado la informacion completa
                    //y no tengamos(para ete caso), una lista en null.
                    val images=puppies?.images ?: emptyList()
                    dogImages.clear()
                    dogImages.addAll(images)
                    //por ultimo avisamos a nuestro adapter del reciclerView que han habido cambios, esta linea seria de alguna forma
                    //la resposable de que el servicio se pinte en pantalla, en este punto se le pasa la batuta al recicler view
                    adapter.notifyDataSetChanged()
            }else{
               showError()
            }
                hideKeyboard()
            }

        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
        //para implementar es necesario que el contenedor parent tenga un id, en este caso fue "viewRoot"
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            serachByBreed(query.toLowerCase()) //se pasa a tolowercase para asegurarnos que el texto sea recibido en la Url
        }
        return true

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intentPokemon = Intent(this,PokemonMainActivity::class.java)
        startActivity(intentPokemon)
        return super.onOptionsItemSelected(item)
    }

}

/*
0) se configuro el permiso de internet en el manifest, y se implemento el binding en el gradle las
dependencias correspondientes

1)Se Creo el diseño. en el layout, un recicler view y un searchView

2)Cuando recibamos la respuesta tendremos que reconocer su contenido atravez de un modelo de datos
(data class) por ende fue lo segundo que se hizo crear el DogResponse

3)Lo siguiente fue crear una interface, llamada APIService (Practicamente sera igual para cada llamada @GET
solo se asgina nombre a la funcion y se asigna el modelo de datos como tipo, para el Response)

4)Se crea una instancia del objeto Retrofit, dentro de la funcion "getRetrofit" este va a contener la URL base, el conversor de gson
a nuestro modelo de datos, y toda la configuracion para hacer las llamadas a internet

5)se creo metodo que sera llamado para buscar imagenes por raza "serachByBreed(querty:String)"
esto significa que se iniciara un servico de consulta.
es aqui donde utilizaremos una corrutina (osea un nuevo hilo)

6)La linea "adapter.notifyDataSetChanged()" actualiza el reciclerView, la unica comunicacion de datos directa
entre el reciclerVIew y retrofit, es atravez de la lista "dogImages" que fue declarada como una variale global

7)por ultimo se implemetna la interfase a travez de la clase searchview "SearchView.OnQueryTextListener"
y por ende obliga a implementar 2 metodos:

"onQueryTextSubmit(query: String?)" // el query lo trae por default y es el valor que se escribe
en el serchView dog (svDog) y es la funcion que se ejecutara cuando despues de escribir se de enter;
retrona un boolean, se deja como true

"onQueryTextChange" // se ejecutara cada que cambia el texto, retrona un boolean, se deja como true

8)por ultimo como extra, para que el teclado se minimice despues de dar buscar, se implementa una funcion
que es llamda al final dela funcion searchByBreed, esta es "hideKeyboard()",
e implementa codigo sin explicar, pues es ajeno al tema principal, pero funciona




 */