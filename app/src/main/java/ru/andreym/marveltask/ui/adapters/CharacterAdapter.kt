package ru.andreym.marveltask.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.andreym.marveltask.R
import ru.andreym.marveltask.databinding.CharacterItemBinding
import ru.andreym.marveltask.repository.network.response.Character
import ru.andreym.marveltask.ui.OnClickCharacter

class CharacterAdapter(private val characters: MutableList<Character>?,val onClickCharacter: OnClickCharacter) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.character_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = characters?.size ?: 0

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters?.get(position)
        character?.let { holder.bind(it)}

    }


    fun setItems( newCharacters: List<Character>) {
        characters?.clear()
        characters?.addAll(newCharacters)
        notifyDataSetChanged()
    }


    inner class CharacterViewHolder(val characterItemBinding: CharacterItemBinding) : RecyclerView.ViewHolder(characterItemBinding.root) {

        fun bind(character: Character) {

            characterItemBinding.character = character
            characterItemBinding.llCharacter.setOnClickListener {
                onClickCharacter.onClick(character.id.toString())
            }
            characterItemBinding.name.text = character.name
            if(character.description.trim().isEmpty())
                characterItemBinding.desc.visibility = View.GONE
            characterItemBinding.desc.text = character.description
            characterItemBinding.executePendingBindings()

        }



    }


}