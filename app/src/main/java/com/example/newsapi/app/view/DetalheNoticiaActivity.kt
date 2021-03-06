package com.example.newsapi.app.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import com.example.newsapi.R
import com.example.newsapi.app.model.DetalheNoticiaModel
import com.example.newsapi.app.model.NoticiaModel
import com.example.newsapi.app.util.Alerta
import kotlinx.android.synthetic.main.activity_detalhe_noticia.*
import kotlinx.android.synthetic.main.activity_feed.*
import java.lang.Exception

class DetalheNoticiaActivity : AppCompatActivity() {

    private var noticia = NoticiaModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_noticia)

        try {

            noticia = intent.getSerializableExtra("noticia") as NoticiaModel
            webView.loadUrl(noticia.url!!)

        }catch (e:Exception){
            Alerta.aviso(e.message.toString(), this)
        }


        btnFavorito2.setOnClickListener {

            if(noticia.eFavorito){

                noticia.eFavorito = false
                FeedActivity.removeNews(noticia)
                btnFavorito2.setImageResource(android.R.drawable.btn_star_big_off)

            }else{
                noticia.eFavorito = true
                FeedActivity.addNews(noticia)
                btnFavorito2.setImageResource(android.R.drawable.btn_star_big_on)
            }
        }


        if(!noticia.eFavorito){
            btnFavorito2.setImageResource(android.R.drawable.btn_star_big_off)
        }else{
            btnFavorito2.setImageResource(android.R.drawable.btn_star_big_on)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detalhe, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_compartilhar){
            compartilharNoticia()
        }

        return true
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun compartilharNoticia(){

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, noticia.url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }
}