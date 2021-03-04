package com.example.newsapi.app.view

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapi.R
import com.example.newsapi.app.model.DetalheNoticiaModel
import com.example.newsapi.app.model.NoticiaModel
import com.example.newsapi.app.util.Alerta
import com.example.newsapi.app.viewmodel.FeedViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_feed.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class FeedActivity : AppCompatActivity() {

    private lateinit var adapter: RecyclerAdapter
    private lateinit var iconeFavorito: MenuItem
    private var filtroFavorito = false
    private val viewModel: FeedViewModel by viewModels()
    val act = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        observes()
        viewModel.buscarNoticiasDestaques()
        viewModel.buscarNoticias()
        filtroPesquisaPorTitulo()

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

    fun carregarNoticiasDestaques(lista: ArrayList<NoticiaModel>){


        carrossel.apply {
            size = lista.size
            setCarouselViewListener { view, position ->

                val titulo = view.findViewById<TextView>(R.id.txtTitulo)
                titulo.setText(lista[position].title)

                val descricao = view.findViewById<TextView>(R.id.txtDescricao)
                descricao.setText(lista[position].description)

                val btnFavorito = view.findViewById<ImageButton>(R.id.btnFavorito)
                btnFavorito.setOnClickListener {
                    if(lista[position].eFavorito){
                        lista[position].eFavorito = false
                        viewModel.listaNoticiasDestaques.value?.get(position)?.eFavorito = false
                        btnFavorito.setImageResource(android.R.drawable.btn_star_big_off)
                    }else{
                        lista[position].eFavorito = true
                        viewModel.listaNoticiasDestaques.value?.get(position)?.eFavorito = true
                        btnFavorito.setImageResource(android.R.drawable.btn_star_big_on)
                    }
                }

                if(lista[position].eFavorito)
                    btnFavorito.setImageResource(android.R.drawable.btn_star_big_on)
                else
                    btnFavorito.setImageResource(android.R.drawable.btn_star_big_off)

                val imageView = view.findViewById<ImageView>(R.id.imageView)
                Picasso.get().load(lista[position].image_url)
                    .resize(1200, imageView.height)
                    .centerCrop()
                    .into(imageView);

                view.setOnClickListener {

                    var detalhe = DetalheNoticiaModel(true,lista[position].url,position,lista[position].eFavorito)

                    var it = Intent(act, DetalheNoticiaActivity::class.java)
                    it.putExtra("detalhe", detalhe)
                    startActivityForResult(it,1)
                }


            }
            show()
        }

    }

    fun carregarNoticias(lista: ArrayList<NoticiaModel>){

        recyclerView.layoutManager = LinearLayoutManager ( this )
        adapter = RecyclerAdapter(lista, this)
        recyclerView.adapter = adapter

    }

    fun filtrarFavoritas(){

        filtroFavorito = !filtroFavorito

        if(filtroFavorito){
            iconeFavorito.setIcon(R.drawable.ic_baseline_star_rate_24)
            var listaDestaqueFavoritas: ArrayList<NoticiaModel> = viewModel.listaNoticiasDestaques.value?.filter { noticia -> noticia.eFavorito == true  } as ArrayList<NoticiaModel>
            carregarNoticiasDestaques(listaDestaqueFavoritas)
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

                var listaNoticias = ArrayList<NoticiaModel>()
                listaNoticias.addAll(viewModel.listaNoticias.value!!)
                for (item in viewModel.listaNoticias.value!!){
                    if(!item.title?.contains(newText)!!){
                        listaNoticias.remove(item)
                    }
                }
                carregarNoticias(listaNoticias)

                var listaNoticiasDestaque = ArrayList<NoticiaModel>()
                listaNoticiasDestaque.addAll(viewModel.listaNoticiasDestaques.value!!)
                for (item in viewModel.listaNoticiasDestaques.value!!){
                    if(!item.title?.contains(newText)!!){
                        listaNoticiasDestaque.remove(item)
                    }
                }
                carregarNoticiasDestaques(listaNoticias)

                return false
            }
        })

    }

    fun datePickerDialog(){

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            val sMes = if(monthOfYear < 10) "0${monthOfYear+1}" else "${monthOfYear+1}"
            viewModel.buscarNoticiasPorData("$year-$dayOfMonth-$sMes")

        }, year, month, day)
        dpd.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 1){

            try {

                var detalhe = data?.getSerializableExtra("detalhe") as DetalheNoticiaModel

                if(detalhe.highlight){
                    var lista = viewModel.listaNoticias.value as ArrayList<NoticiaModel>
                    lista[detalhe.posicao].eFavorito = detalhe.eFavorito
                    carregarNoticiasDestaques(lista)
                }else{
                    var lista = adapter.getLista()
                    lista[detalhe.posicao].eFavorito = detalhe.eFavorito
                    adapter.notifyDataSetChanged()
                }

            }catch (e:Exception){}

        }
    }

}