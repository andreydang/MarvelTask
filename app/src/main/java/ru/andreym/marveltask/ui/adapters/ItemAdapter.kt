package ru.andreym.marveltask.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.andreym.marveltask.R
import ru.andreym.marveltask.databinding.ItemBinding
import ru.andreym.marveltask.repository.network.response.Item


class ItemAdapter(private val items: MutableList<Item>?) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items?.get(position)
        item?.let { holder.bind(it)}

    }


    inner class ItemViewHolder(val itemBinding: ItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Item) {

            itemBinding.tvName.text = item.name

            itemBinding.executePendingBindings()

        }



    }


}