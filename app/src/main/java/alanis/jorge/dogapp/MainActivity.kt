package alanis.jorge.dogapp

import alanis.jorge.dogapp.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Al manifest le agregamos instruccion para obtener permisos de internet y las dependencias en el gradle
//Hay que hacer una clase para captar los datos y el response, este caso DogsResponse.kt
class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    //Declaramos la variable del view binding

    lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<String>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)// se agrego aqui -----------------------------
        setContentView(R.layout.activity_main)

        //Mandamos llama la funcion para el recycler view osea la de inicializar
        initRecyclerView()


    }
    //iniciamos la funcion para el recycler view
    private fun initRecyclerView(){
        adapter = DogAdapter(dogImages)
        binding.rvDogs.adapter = adapter
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        // mando llamar al seearch
        binding.searchBreed.setOnQueryTextListener(this)
        binding.rvDogs.adapter = adapter
    }

    private fun getRetrofit (): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Hacemos la llamada a la busqueda de la api

    private fun searchByName(querie: String){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val call = getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")// el $querie es la variable que se le pasa del usuario es decir la raza
                val puppies = call.body()
                runOnUiThread{
                    if(call.isSuccessful){
                        val images = puppies?.images ?: emptyList()
                        dogImages.clear()
                        dogImages.addAll(images)
                        adapter.notifyDataSetChanged()
                    }
                    else {
                        showErrorDialog()
                    }
                    hideKeyboard()

                }
            }catch (e: Exception){


            }
        }
    }

    private fun  showErrorDialog(){
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()

    }

    //el search view tiene dos funciones que tengo que implementar de afuerzas aqui

    override fun onQueryTextChange(query: String?): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByName(query.toLowerCase())
        }
        return true
    }

    //Agregamos la funcion para ocultar el teclado
    private fun hideKeyboard(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }





}