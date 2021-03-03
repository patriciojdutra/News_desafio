package com.example.newsapi.app.data.repository

import android.app.Activity
import com.example.newsapi.app.data.service.Retrofit
import com.example.newsapi.app.model.CadastroRetorno
import com.example.newsapi.app.model.LoginRetorno
import com.example.newsapi.app.util.Alerta
import com.example.newsapi.app.util.Global
import com.example.newsapi.app.util.Resposta
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebApi {

    companion object {


        @JvmStatic
        fun logar(json: JsonObject, resposta: Resposta<LoginRetorno>) {

            val callback = Retrofit().endpoint.login(json)

            callback.enqueue(object : Callback<LoginRetorno> {

                override fun onResponse(call: Call<LoginRetorno>, response: Response<LoginRetorno>) {

                    if (response.isSuccessful && response.body() != null) {
                        resposta.sucesso(response.body()!!)
                    } else {
                        resposta.sucesso(LoginRetorno("", response.errorBody()?.string().toString()))
                    }

                }


                override fun onFailure(result: Call<LoginRetorno>, t: Throwable) {
                    resposta.sucesso(LoginRetorno("",t.message.toString()))
                }
            })
        }

        @JvmStatic
        fun cadastrarUsuario(json: JsonObject, resposta: Resposta<CadastroRetorno>) {

            val callback = Retrofit().endpoint.cadastrarUsuario(json)

            callback.enqueue(object : Callback<CadastroRetorno> {

                override fun onResponse(call: Call<CadastroRetorno>, response: Response<CadastroRetorno>) {

                    if (response.isSuccessful && response.body() != null) {
                        resposta.sucesso(response.body()!!)
                    } else {
                        resposta.sucesso(CadastroRetorno("", response.errorBody()?.string().toString()))
                    }
                }

                override fun onFailure(result: Call<CadastroRetorno>, t: Throwable) {
                    resposta.sucesso(CadastroRetorno("",t.message.toString()))
                }
            })
        }

        @JvmStatic
        fun buscarNoticias(act : Activity) {

            val callback = Retrofit().endpoint.buscarNoticias(Global.token)

            callback.enqueue(object : Callback<JsonObject> {

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                    if (response.isSuccessful && response.body() != null) {

                        var json = response.body()
                        var t = json.toString()

                        Alerta.aviso(t,act)

                    } else {
                        Alerta.aviso(response.errorBody()?.string().toString(),act)
                        //resposta.sucesso(CadastroRetorno("", response.errorBody()?.string().toString()))
                    }
                }

                override fun onFailure(result: Call<JsonObject>, t: Throwable) {
                    Alerta.aviso(t.message.toString(),act)
                    //resposta.sucesso(CadastroRetorno("",t.message.toString()))
                }
            })
        }

    }
}