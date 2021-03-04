package com.example.newsapi.app.data.repository

import android.app.Activity
import com.example.newsapi.app.data.service.Retrofit
import com.example.newsapi.app.model.CadastroRetorno
import com.example.newsapi.app.model.LoginRetorno
import com.example.newsapi.app.model.NoticiasRetorno
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
        fun buscarNoticias(resposta: Resposta<NoticiasRetorno>) {

            val callback = Retrofit().endpoint.buscarNoticias(Global.token)

            callback.enqueue(object : Callback<NoticiasRetorno> {

                override fun onResponse(call: Call<NoticiasRetorno>, response: Response<NoticiasRetorno>) {

                    if (response.isSuccessful && response.body() != null) {
                        resposta.sucesso(response.body()!!)
                    } else {
                        resposta.sucesso(NoticiasRetorno(response.errorBody()?.string().toString()))
                    }
                }

                override fun onFailure(result: Call<NoticiasRetorno>, t: Throwable) {
                    resposta.sucesso(NoticiasRetorno(t.message.toString()))
                }
            })
        }

        @JvmStatic
        fun buscarNoticiasDestaques(resposta: Resposta<NoticiasRetorno>) {

            val callback = Retrofit().endpoint.buscarNoticiasDestaques(Global.token)

            callback.enqueue(object : Callback<NoticiasRetorno> {

                override fun onResponse(call: Call<NoticiasRetorno>, response: Response<NoticiasRetorno>) {

                    if (response.isSuccessful && response.body() != null) {
                        resposta.sucesso(response.body()!!)
                    } else {
                        resposta.sucesso(NoticiasRetorno(response.errorBody()?.string().toString()))
                    }
                }

                override fun onFailure(result: Call<NoticiasRetorno>, t: Throwable) {
                    resposta.sucesso(NoticiasRetorno(t.message.toString()))
                }
            })
        }

        @JvmStatic
        fun buscarNoticiasPorData(data: String, resposta: Resposta<NoticiasRetorno>) {

            val callback = Retrofit().endpoint.buscarNoticiasPorData(Global.token, data)

            callback.enqueue(object : Callback<NoticiasRetorno> {

                override fun onResponse(call: Call<NoticiasRetorno>, response: Response<NoticiasRetorno>) {

                    if (response.isSuccessful && response.body() != null) {
                        resposta.sucesso(response.body()!!)
                    } else {
                        resposta.sucesso(NoticiasRetorno(response.errorBody()?.string().toString()))
                    }
                }

                override fun onFailure(result: Call<NoticiasRetorno>, t: Throwable) {
                    resposta.sucesso(NoticiasRetorno(t.message.toString()))
                }
            })
        }

    }
}