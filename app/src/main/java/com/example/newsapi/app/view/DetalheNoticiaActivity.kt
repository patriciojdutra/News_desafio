package com.example.newsapi.app.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapi.R
import com.example.newsapi.app.model.DetalheNoticiaModel
import com.example.newsapi.app.util.Alerta
import kotlinx.android.synthetic.main.activity_detalhe_noticia.*
import java.lang.Exception

class DetalheNoticiaActivity : AppCompatActivity() {

    private var detalhe = DetalheNoticiaModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_noticia)

        try {

            detalhe = intent.getSerializableExtra("detalhe") as DetalheNoticiaModel
            webView.loadUrl(detalhe.url!!)

        }catch (e:Exception){
            Alerta.aviso(e.message.toString(), this)
        }


        btnFavorito2.setOnClickListener {
            if(detalhe.eFavorito){
                detalhe.eFavorito = false
                btnFavorito2.setImageResource(android.R.drawable.btn_star_big_off)
            }else{
                detalhe.eFavorito = true
                btnFavorito2.setImageResource(android.R.drawable.btn_star_big_on)
            }
        }


        if(!detalhe.eFavorito){
            btnFavorito2.setImageResource(android.R.drawable.btn_star_big_off)
        }else{
            btnFavorito2.setImageResource(android.R.drawable.btn_star_big_on)
        }

    }

    override fun onBackPressed() {
        var it = intent
        it.putExtra("detalhe", detalhe)
        setResult(Activity.RESULT_OK, it)
        finish()
    }
}