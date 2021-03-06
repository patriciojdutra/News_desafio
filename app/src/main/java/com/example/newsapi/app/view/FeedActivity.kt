package com.example.newsapi.app.view

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapi.R
import com.example.newsapi.app.model.NoticiaModel
import com.example.newsapi.app.util.Alerta
import com.example.newsapi.app.util.PreferencesUtil
import com.example.newsapi.app.viewmodel.FeedViewModel
import com.patricio.dutra.desafiojeitto.utils.Constants
import com.smarteist.autoimageslider.SliderAnimations
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_feed.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.fixedRateTimer

class FeedActivity : AppCompatActivity() {

    private lateinit var adapter: RecyclerAdapter
    private lateinit var adapterBanner: BannerAdapter
    private lateinit var iconeFavorito: MenuItem
    private var filtroFavorito = false
    private var carregarNoticiasAutomatico = true
    private val viewModel: FeedViewModel by viewModels()
    val act = this

    companion object {

        var listaFavoritos = ArrayList<NoticiaModel>()

        @JvmStatic
        fun clearListNews(lista: ArrayList<NoticiaModel>, listaFavoritos: ArrayList<NoticiaModel>): ArrayList<NoticiaModel>{
            for(noticia in listaFavoritos){
                lista.removeAll { n -> n.title == noticia.title && n.description == noticia.description }
            }
            lista.addAll(0, listaFavoritos)
            return lista
        }

        @JvmStatic
        fun addNews(news: NoticiaModel){
            listaFavoritos.add(0, news)
        }

        @JvmStatic
        fun removeNews(news: NoticiaModel){
            listaFavoritos.removeAll { n -> n.title == news.title && n.description == news.description }
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        observes()
        filtroPesquisaPorTitulo()
        viewModel.buscarNoticiasDestaques(PreferencesUtil.getString(this,"token",""))
        buscarNoticiaPorTempo()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_feed, menu)
        iconeFavorito = menu.findItem(R.id.action_favorito)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_data){
            filtrarPorData()
        }else if(item.itemId == R.id.action_favorito){
            filtrarFavoritas()
        }else if(item.itemId == R.id.action_pesquisar){
            if(cardViewFeed.isVisible)
                cardViewFeed.visibility = View.GONE
            else
                cardViewFeed.visibility = View.VISIBLE
        }

        return true
    }

    fun observes(){

        viewModel.carregando.observe(this, Observer {
            if(it)
                loading.visibility = View.VISIBLE
            else
                loading.visibility = View.GONE
        })

        viewModel.listaNoticiasDestaques.observe(this, Observer {
           carregarNoticiasDestaques(it)
        })

        viewModel.listaNoticias.observe(this, Observer {
            carregarNoticias(it)
        })

        viewModel.msgError.observe(this, Observer {
            Alerta.aviso(it,this)
        })

    }

    fun carregarNoticiasDestaques(noticiasDestaques: ArrayList<NoticiaModel>){

        val listaFavoritosDestaque = listaFavoritos.filter { n -> n.eCarrossel == true } as ArrayList<NoticiaModel>
        var lista = clearListNews(noticiasDestaques, listaFavoritosDestaque)

        if(!lista.isNullOrEmpty()) {
            adapterBanner = BannerAdapter(lista, this)
            carrossel.setSliderAdapter(adapterBanner)
            carrossel.startAutoCycle()
            carrossel.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        }else{
            adapterBanner = BannerAdapter(ArrayList<NoticiaModel>(), this)
            carrossel.setSliderAdapter(adapterBanner)
        }
    }

    fun carregarNoticias(noticiasDestaques: ArrayList<NoticiaModel>){

        var lista = clearListNews(noticiasDestaques, listaFavoritos)
        lista = lista.filter { n -> n.eCarrossel == false } as ArrayList<NoticiaModel>

        recyclerView.layoutManager = LinearLayoutManager ( this )
        adapter = RecyclerAdapter(lista, this)
        recyclerView.adapter = adapter

    }

    fun filtrarFavoritas(){

        filtroFavorito = !filtroFavorito
        carregarNoticiasAutomatico = !filtroFavorito

        if(filtroFavorito){
            iconeFavorito.setIcon(R.drawable.ic_baseline_star_rate_24)
            var listaDestaqueFavoritas: ArrayList<NoticiaModel> = viewModel.listaNoticiasDestaques.value?.filter {
                noticia -> noticia.eFavorito == true  } as ArrayList<NoticiaModel>

            if(!listaDestaqueFavoritas.isNullOrEmpty())
                carregarNoticiasDestaques(listaDestaqueFavoritas)
            else
                carregarNoticiasDestaques(ArrayList<NoticiaModel>())

            var listaNoticias = adapter.getItensFavoritos()
            carregarNoticias(listaNoticias)
        }else{
            iconeFavorito.setIcon(R.drawable.ic_baseline_star_outline_24)
            carregarNoticiasDestaques(viewModel.listaNoticiasDestaques.value!!)
            carregarNoticias(viewModel.listaNoticias.value!!)
        }
    }

    fun filtrarPorData(){
       datePickerDialog()
    }

    fun filtroPesquisaPorTitulo(){

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {

                if(newText.isEmpty()) {
                    carregarNoticias(viewModel.listaNoticias.value as ArrayList<NoticiaModel>)
                    carregarNoticiasDestaques(viewModel.listaNoticiasDestaques.value as ArrayList<NoticiaModel>)
                    return false
                }

                filtroFavorito = false

                var listaNoticias = ArrayList<NoticiaModel>()
                listaNoticias.addAll(viewModel.listaNoticias.value!!)
                for (item in viewModel.listaNoticias.value!!){
                    if(!item.title?.contains(newText, true)!!){
                        listaNoticias.remove(item)
                    }
                }
                carregarNoticias(listaNoticias)

                var listaNoticiasDestaque = ArrayList<NoticiaModel>()
                listaNoticiasDestaque.addAll(viewModel.listaNoticiasDestaques.value!!)
                for (item in viewModel.listaNoticiasDestaques.value!!){
                    if(!item.title?.contains(newText,true)!!){
                        listaNoticiasDestaque.remove(item)
                    }
                }
                carregarNoticiasDestaques(listaNoticiasDestaque)

                return false
            }
        })

        //searchView.isIconified

    }

    fun datePickerDialog(){

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            val sMes = if(monthOfYear < 10) "0${monthOfYear+1}" else "${monthOfYear+1}"
            viewModel.buscarNoticiasPorData("$year-$dayOfMonth-$sMes", PreferencesUtil.getString(this,"token",""))

        }, year, month, day)
        dpd.show()

    }

    fun buscarNoticiaPorTempo(){

        fixedRateTimer(name = "timer",
                initialDelay = 1000, period = Constants.TIMENEWS, daemon = true) {
            runOnUiThread(){
                if(searchView.query.isEmpty() && carregarNoticiasAutomatico) {
                    viewModel.buscarNoticias(PreferencesUtil.getString(act, "token", ""))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 1){

            try {

                if(filtroFavorito)
                    filtrarFavoritas()
                else {
                    var listaNoticiasDestaque = viewModel.listaNoticiasDestaques.value as ArrayList<NoticiaModel>
                    carregarNoticiasDestaques(listaNoticiasDestaque)

                    var listaNoticias = viewModel.listaNoticias.value as ArrayList<NoticiaModel>
                    carregarNoticias(listaNoticias)
                }

            }catch (e:Exception){}
        }
    }
}