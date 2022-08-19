package com.example.pekedexexamenmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pekedexexamenmvvm.api.ApiConfig
import com.example.pekedexexamenmvvm.responseApi.MainModel
import com.example.pekedexexamenmvvm.responseApi.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Aqui es donde se guardan los resultados del servicio
class MainActivityViewModel : ViewModel() {

    private val _showPokemon = MutableLiveData<List<Pokemon>>() // se guarda el resultado del consumo (lista)
    val showPokemon : LiveData<List<Pokemon>> = _showPokemon // Es una variable de tiempo real

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getListPokemon (limit: Int, offset: Int){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListPokemon(limit, offset)
        client.enqueue(object: Callback<MainModel> {
            override fun onResponse(
                call: Call<MainModel>,
                response: Response<MainModel>
            ){
                _isLoading.value = false
                Log.d("tes", response.toString())
                if (response.isSuccessful){
                    _showPokemon.value = response.body()?.results // Se da el resultado del tipo de respuesta
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MainModel>, t: Throwable) { // Pinta en consola el error
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "MainActivityViewModel"
    }


}