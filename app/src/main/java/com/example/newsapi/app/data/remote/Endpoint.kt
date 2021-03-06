package com.example.newsapi.app.data.remote

import com.example.newsapi.app.model.CadastroRetorno
import com.example.newsapi.app.model.LoginRetorno
import com.example.newsapi.app.model.NoticiasRetorno
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface Endpoint {

    @POST("v1/client/auth/signin")
    fun login(@Body jsonObject: JsonObject) : Call<LoginRetorno>

    @POST("v1/client/auth/signup")
    fun cadastrarUsuario(@Body jsonObject: JsonObject) : Call<CadastroRetorno>

    @Headers( "Content-Type: application/json;charset=UTF-8")
    @GET("v1/client/news")
    fun buscarNoticias(@Header("Authorization") token: String, @Query( "current_page" ) pagina :  Int ) : Call<NoticiasRetorno>

    @Headers( "Content-Type: application/json;charset=UTF-8")
    @GET("v1/client/news/highlights")
    fun buscarNoticiasDestaques(@Header("Authorization") token: String) : Call<NoticiasRetorno>

    @Headers( "Content-Type: application/json;charset=UTF-8")
    @GET("v1/client/news")
    fun buscarNoticiasPorData(@Header("Authorization") token: String, @Query( "published_at" ) data :  String ) : Call<NoticiasRetorno>

}