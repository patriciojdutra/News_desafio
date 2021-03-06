package com.example.newsapi.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapi.app.data.repository.WebApi
import com.example.newsapi.app.model.NoticiaModel
import com.example.newsapi.app.model.NoticiasRetorno
import com.example.newsapi.app.util.Resposta
import com.patricio.dutra.desafiojeitto.utils.Constants

open class FeedViewModel : ViewModel() {

    val carregando = MutableLiveData<Boolean>()
    val msgError = MutableLiveData<String>()
    var listaNoticiasDestaques = MutableLiveData<ArrayList<NoticiaModel>>()
    var listaNoticias = MutableLiveData<ArrayList<NoticiaModel>>()

    fun buscarNoticiasDestaques(token: String){
        WebApi.buscarNoticiasDestaques(token, object : Resposta<NoticiasRetorno>{
            override fun sucesso(retorno: NoticiasRetorno) {
                if(!retorno.data.isNullOrEmpty())
                    listaNoticiasDestaques.value = retorno.data
                else
                    msgError.value = retorno.msgError
            }
        })
    }

    fun buscarNoticias(token: String){

        carregando.value = true

        WebApi.buscarNoticias(token, object : Resposta<NoticiasRetorno>{
            override fun sucesso(obj: NoticiasRetorno) {
                carregando.value = false
                resultadoNoticias(obj)
            }
        })
    }

    fun resultadoNoticias(retorno: NoticiasRetorno){
        if(!retorno.data.isNullOrEmpty()) {
            if (retorno.pagination.total_pages > Constants.current_page)
                Constants.current_page = Constants.current_page + 1
            else
                Constants.current_page = 1
            listaNoticias.value = retorno.data
        } else
            msgError.value = retorno.msgError
    }

    fun buscarNoticiasPorData(data: String, token: String){
        carregando.value = true
        WebApi.buscarNoticiasPorData(data, token, object : Resposta<NoticiasRetorno>{
            override fun sucesso(obj: NoticiasRetorno) {
                carregando.value = false
                resultadoNoticias(obj)
            }
        })
    }
}