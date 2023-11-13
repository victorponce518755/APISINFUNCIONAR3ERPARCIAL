package alanis.jorge.dogapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface APIService {
    @GET
    suspend fun getDogsByBreeds(@Url url: String): Response<DogsResponse>

}

//Despues nos vamos a modificar el main activity, este caso llamado MainActivity.kt