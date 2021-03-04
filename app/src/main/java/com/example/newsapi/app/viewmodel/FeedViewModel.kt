package com.example.newsapi.app.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapi.app.data.repository.WebApi
import com.example.newsapi.app.model.LoginRetorno
import com.example.newsapi.app.model.NoticiaModel
import com.example.newsapi.app.model.NoticiasRetorno
import com.example.newsapi.app.util.Global
import com.example.newsapi.app.util.Resposta
import com.google.gson.JsonObject
import java.lang.Exception

class FeedViewModel : ViewModel() {

    val carregando = MutableLiveData<Boolean>()
    val msgError = MutableLiveData<String>()
    var listaNoticiasDestaques = MutableLiveData<ArrayList<NoticiaModel>>()
    var listaNoticias = MutableLiveData<ArrayList<NoticiaModel>>()

    fun buscarNoticiasDestaques(){

        carregando.value = true

        WebApi.buscarNoticiasDestaques(object : Resposta<NoticiasRetorno>{
            override fun sucesso(obj: NoticiasRetorno) {
                carregando.value = false
                resultadoNoticiasDestaques(obj)
            }
        })
    }

    fun resultadoNoticiasDestaques(retorno: NoticiasRetorno){

        if(!retorno.data.isNullOrEmpty())
            listaNoticiasDestaques.value = retorno.data
        else
            msgError.value = retorno.msgError

    }

    fun buscarNoticias(){

        carregando.value = true

        WebApi.buscarNoticias(object : Resposta<NoticiasRetorno>{
            override fun sucesso(obj: NoticiasRetorno) {
                carregando.value = false
                resultadoNoticias(obj)
            }
        })
    }

    fun resultadoNoticias(retorno: NoticiasRetorno){

        if(!retorno.data.isNullOrEmpty())
            listaNoticias.value = retorno.data
        else
            msgError.value = retorno.msgError
    }

    fun buscarNoticiasPorData(data: String){

        carregando.value = true

        WebApi.buscarNoticiasPorData(data, object : Resposta<NoticiasRetorno>{
            override fun sucesso(obj: NoticiasRetorno) {
                carregando.value = false
                resultadoNoticias(obj)
            }
        })
    }

}