package com.example.pekedexexamenmvvm.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pekedexexamenmvvm.databinding.ListPokemonBinding
import com.example.pekedexexamenmvvm.responseApi.Pokemon

// Manipula la respuesta del servicio
class ListPokemonAdapter() : RecyclerView.Adapter<ListPokemonAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listPokemon = ArrayList<Pokemon>()


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    fun setData(poke: List<Pokemon>){
        listPokemon.clear()
        listPokemon.addAll(poke)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(var binding: ListPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokes: Pokemon){
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(pokes) }
            binding.apply{
                Glide.with(itemView)
                    .load(pokes.getImageUrl())
                    .into(ivImage)
                tvName.text = pokes.name // se inyecta la respuestas
                tvId.text = pokes.getId().format("#%03d")
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
            ListPokemonBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    interface OnItemClickCallback {
        fun onItemClicked(pokes: Pokemon)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listPokemon[position])
    }

    override fun getItemCount(): Int = listPokemon.size

}