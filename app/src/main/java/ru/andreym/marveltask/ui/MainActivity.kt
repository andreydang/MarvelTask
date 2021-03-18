package ru.andreym.marveltask.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.RealmChangeListener
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import ru.andreym.marveltask.R
import ru.andreym.marveltask.di.mainViewModel
import ru.andreym.marveltask.repository.database.entity.CharacterModel
import ru.andreym.marveltask.ui.adapters.CharacterAdapter
import ru.andreym.marveltask.utils.EndlessScrollRecycelListener
import ru.andreym.marveltask.viewmodel.MainViewModel
import timber.log.Timber
import ru.andreym.marveltask.utils.Result


class MainActivity : AppCompatActivity(), KodeinAware, OnClickCharacter {

    override val kodein by kodein()
    private val viewModel: MainViewModel by mainViewModel()
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var endlessScroll: EndlessScrollRecycelListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listFromDb = viewModel.charactersRealmFromDb?.map { it.toCharacter() }?: mutableListOf()
        if (listFromDb.isNullOrEmpty())
            viewModel.fetchCharacters(0, 6)

        subscribeOnChangedNetwork()
        subscribeOnChangeDb()

        characterAdapter = CharacterAdapter(listFromDb.toMutableList(), this)
        endlessScroll = object : EndlessScrollRecycelListener() {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                Timber.d("onLoadMore $page $totalItemsCount")
                viewModel.fetchCharacters(totalItemsCount, 5)
            }
        }

        val linearLayoutManager = LinearLayoutManager(this)
        recycler_view_characters.layoutManager = linearLayoutManager
        recycler_view_characters.adapter = characterAdapter
        recycler_view_characters.setOnScrollListener(endlessScroll)

    }

    private fun subscribeOnChangeDb() {
        val listModel = viewModel.charactersRealmFromDb
        listModel?.addChangeListener(RealmChangeListener<RealmResults<CharacterModel>> {
            val charactersDb = it.map { it.toCharacter() }
            characterAdapter.setItems(charactersDb)
        })
    }

    private fun subscribeOnChangedNetwork() {
        viewModel.characters.observe(this, { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {

                }
                Result.Status.ERROR -> {
                    Timber.d("${result.message} ${result.error}")
                    endlessScroll.loading = false
                }
                Result.Status.LOADING -> {
                    Timber.d("loading")
                }
            }

        })
    }

    override fun onClick(characterId: String) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("character", characterId)
        startActivity(intent)
    }

}