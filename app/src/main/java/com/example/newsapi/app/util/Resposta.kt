package com.example.newsapi.app.util

interface Resposta <T> {

    fun sucesso(obj: T)

}