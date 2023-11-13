package alanis.jorge.dogapp

import com.google.gson.annotations.SerializedName

// le ponemos que es una data class
data class DogsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val images: List<String>// Cambio la respuestya que viene en mensaje a imagenes
)

//Hay que hacer adaptador, por que en la interfaz grafica se hizo un recycler view, este caso llamado DogAdapter.kt

