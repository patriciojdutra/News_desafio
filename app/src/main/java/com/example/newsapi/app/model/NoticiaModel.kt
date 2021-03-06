package com.example.newsapi.app.model

import java.io.Serializable

class NoticiaModel: Serializable{
    var title: String = ""
    var description: String = ""
    var content: String = ""
    var author: String = ""
    var published_at: String = ""
    var highlight: Boolean = false
    var url: String = ""
    var image_url: String = ""
    var eFavorito = false
    var eCarrossel = false
}



