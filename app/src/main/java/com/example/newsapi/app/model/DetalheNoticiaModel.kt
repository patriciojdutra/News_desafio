package com.example.newsapi.app.model

import java.io.Serializable

class DetalheNoticiaModel:Serializable{
    var highlight: Boolean = false
    var url: String = ""
    var posicao: Int = 0
    var eFavorito = false



    constructor(highlight: Boolean, url: String, posicao: Int, eFavorito: Boolean) {
        this.highlight = highlight
        this.url = url
        this.posicao = posicao
        this.eFavorito = eFavorito
    }

    constructor()
}



