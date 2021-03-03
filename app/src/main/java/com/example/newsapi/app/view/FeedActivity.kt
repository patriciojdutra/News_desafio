package com.example.newsapi.app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapi.R
import com.example.newsapi.app.data.repository.WebApi
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)


        btnBuscarNoticias.setOnClickListener {
            buscarNoticias()
        }
    }

    fun buscarNoticias(){
        WebApi.buscarNoticias(this)
    }

}