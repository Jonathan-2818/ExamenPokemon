package com.example.pekedexexamenmvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pekedexexamenmvvm.adapter.ListPokemonAdapter
import com.example.pekedexexamenmvvm.databinding.ActivityMainBinding
import com.example.pekedexexamenmvvm.responseApi.Pokemon
import com.example.pekedexexamenmvvm.viewmodel.MainActivityViewModel

// Es el puente de los servicios, de la navegacion, del flujo
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val listPokemonAdapter = ListPokemonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aqui se puedes poner el limite de pokemones que quieras
        val mainActivityViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainActivityViewModel::class.java]
        mainActivityViewModel.getListPokemon(151, 0)
        binding.rvList.adapter = listPokemonAdapter

        // Sirve para poner editar la vista si quieres mas de una imagen en cuadros
        val layoutManager = GridLayoutManager(this, 1)
        binding.rvList.layoutManager = layoutManager
        val itemDecorationHorizontal = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        binding.rvList.addItemDecoration(itemDecorationHorizontal)
        val itemDecorationVertical = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rvList.addItemDecoration(itemDecorationVertical)


        mainActivityViewModel.showPokemon.observe(this) { results -> setPokemon(results) }
        mainActivityViewModel.isLoading.observe(this){showLoading(it)}

    }

    // Se cacha el pokemon que se le dio click y llevarlo a la siguiente pagina y lleva (nombre, imagen y id)
    private fun setPokemon(results: List<Pokemon>) {
        listPokemonAdapter.setData(results)
        listPokemonAdapter.setOnItemClickCallback(object : ListPokemonAdapter.OnItemClickCallback {
            override fun onItemClicked(pokes: Pokemon) {
                val moveWithObjectIntent = Intent(this@MainActivity, DetailActivity::class.java)
                moveWithObjectIntent.putExtra(DetailActivity.NAME, pokes.name)
                moveWithObjectIntent.putExtra(DetailActivity.IMAGE, pokes.getImageUrl())
                moveWithObjectIntent.putExtra(DetailActivity.ID, pokes.getId())
                startActivity(moveWithObjectIntent)
            }
        })
    }

    // Es el Loader espera el servicio
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
