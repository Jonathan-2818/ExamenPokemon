package com.example.pekedexexamenmvvm.api



import com.example.pekedexexamenmvvm.responseApi.AboutModel
import com.example.pekedexexamenmvvm.responseApi.DetailModel
import com.example.pekedexexamenmvvm.responseApi.MainModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Aqui es donde se consume el 3 enpoint
interface ApiService {

    @GET("pokemon")
    fun getListPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<MainModel>

    @GET("pokemon/{pokemon_id}")
    fun getPokemon(
        @Path("pokemon_id") pokemon_id: String
    ): Call<DetailModel>

    @GET("pokemon-species/{pokemon_id}")
    fun getDetailPokemon(
        @Path("pokemon_id") pokemon_id: String
    ): Call<AboutModel>
}