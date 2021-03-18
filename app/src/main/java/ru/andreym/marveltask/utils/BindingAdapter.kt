package ru.andreym.marveltask.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import ru.andreym.marveltask.repository.network.response.Character

@BindingAdapter("app:character")
fun loadImage(view: ImageView?, character: Character) {
    val url = "${character.thumbnail.path}.${character.thumbnail.extension}".replace("http:", "https:")
    Picasso.get().load(url).into(view)

}