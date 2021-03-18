package ru.andreym.marveltask.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import ru.andreym.marveltask.R
import ru.andreym.marveltask.di.mainViewModel
import ru.andreym.marveltask.repository.network.response.Item
import ru.andreym.marveltask.ui.adapters.ItemAdapter
import ru.andreym.marveltask.utils.loadImage
import ru.andreym.marveltask.viewmodel.MainViewModel

class DetailActivity : AppCompatActivity() , KodeinAware {

    override val kodein by kodein()
    private val viewModel: MainViewModel by mainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        intent.extras?.let {
            val characterId = it.getString("character")?.toLong()
            val character = characterId?.let { it1 -> viewModel.fetchCharactersById(it1) }
            character?.let {
                loadImage(ivBackground,character)
                showCardView(character.comics.items.toMutableList(),recycler_view_comics,cvComics)
                showCardView(character.events.items.toMutableList(),recycler_view_events,cvEvents)
                showCardView(character.series.items.toMutableList(),recycler_view_series,cvSeries)
                showCardView(character.stories.items.toMutableList(),recycler_view_stories,cvStories)

            }

        }

    }

    private fun showCardView(list: MutableList<Item>, recyclerView: RecyclerView, cardView: CardView){
        if(list.isNotEmpty()){
            val adapter = ItemAdapter(list)
            val linearLayoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = adapter

        }else{
            cardView.visibility = View.GONE
        }
    }
}