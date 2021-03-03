package com.example.newsapi.app.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapi.app.data.repository.WebApi
import com.example.newsapi.app.model.CadastroRetorno
import com.example.newsapi.app.model.LoginRetorno
import com.example.newsapi.app.util.Global
import com.example.newsapi.app.util.Resposta
import com.google.gson.JsonObject
import java.lang.Exception

class CadastroUsuarioViewModel : ViewModel() {

    val carregando = MutableLiveData<Boolean>()
    val statusRetorno = MutableLiveData<Boolean>()
    val msgError = MutableLiveData<String>()

    fun cadastrarUsuario(nome: String, email: String, senha: String){

        carregando.value = true
        val json = transformarEmJson(nome, email, senha)

        WebApi.cadastrarUsuario(json, object : Resposta<CadastroRetorno>{
            override fun sucesso(obj: CadastroRetorno) {
                carregando.value = false
                resultadoCadastro(obj)
            }
        })
    }

    fun resultadoCadastro(cadastroRetorno: CadastroRetorno){

        if(!cadastroRetorno.token.isNullOrEmpty()){
            Global.token = cadastroRetorno.token
            statusRetorno.value = true
        }else{
            statusRetorno.value = false
            msgError.value = cadastroRetorno.message
        }
    }

    fun transformarEmJson(nome: String, email: String, senha: String): JsonObject {
        val json = JsonObject()
        json.addProperty("name",nome)
        json.addProperty("email",email)
        json.addProperty("password",senha)
        return json
    }


}