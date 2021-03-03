package com.example.newsapi.app.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapi.app.data.repository.WebApi
import com.example.newsapi.app.model.LoginRetorno
import com.example.newsapi.app.util.Global
import com.example.newsapi.app.util.Resposta
import com.google.gson.JsonObject
import java.lang.Exception

class LoginViewModel : ViewModel() {

    val carregando = MutableLiveData<Boolean>()
    val statusRetorno = MutableLiveData<Boolean>()
    val msgError = MutableLiveData<String>()


    fun fazerLogin(email: String, senha: String){

        carregando.value = true
        val json = transformarEmJson(email, senha)

        WebApi.logar(json, object : Resposta<LoginRetorno>{
            override fun sucesso(obj: LoginRetorno) {
                carregando.value = false
                resultadoLogin(obj)
            }
        })
    }

    fun resultadoLogin(loginRetorno: LoginRetorno){

        if(!loginRetorno.token.isNullOrEmpty()){
            Global.token = loginRetorno.token
            statusRetorno.value = true
        }else{
            statusRetorno.value = false
            msgError.value = loginRetorno.message
        }
    }

    fun transformarEmJson(email: String, senha: String): JsonObject {
        val json = JsonObject()
        json.addProperty("email",email)
        json.addProperty("password",senha)
        return json
    }


}