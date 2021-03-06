package com.example.newsapi.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapi.app.data.repository.WebApi
import com.example.newsapi.app.model.CadastroRetorno
import com.example.newsapi.app.model.LoginRetorno
import com.example.newsapi.app.util.Resposta
import com.example.newsapi.app.view.MainActivity
import com.google.gson.JsonObject

class LoginViewModel: ViewModel() {

    val carregando = MutableLiveData<Boolean>()
    val msgError = MutableLiveData<String>()
    val token = MutableLiveData<String>()

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
            token.value  = loginRetorno.token
        }else if(MainActivity.usuarioId.isNotEmpty()){
            cadastrarUsuario(MainActivity.usuarioId, MainActivity.usuarioId+"@hotmail.com", MainActivity.usuarioId)
        }else{
            msgError.value = loginRetorno.message
        }
    }

    fun cadastrarUsuario(nome: String, email: String, senha: String){

        carregando.value = true
        val json = transformarCadastroEmJson(nome, email, senha)

        WebApi.cadastrarUsuario(json, object : Resposta<CadastroRetorno>{
            override fun sucesso(obj: CadastroRetorno) {
                carregando.value = false
                resultadoCadastro(obj)
            }
        })
    }

    fun resultadoCadastro(cadastroRetorno: CadastroRetorno){

        if(!cadastroRetorno.token.isNullOrEmpty()){
            token.value = cadastroRetorno.token
        }else{
            msgError.value = cadastroRetorno.message
        }
    }

    fun transformarCadastroEmJson(nome: String, email: String, senha: String): JsonObject {
        val json = JsonObject()
        json.addProperty("name",nome)
        json.addProperty("email",email)
        json.addProperty("password",senha)
        return json
    }

    fun transformarEmJson(email: String, senha: String): JsonObject {
        val json = JsonObject()
        json.addProperty("email",email)
        json.addProperty("password",senha)
        return json
    }


}