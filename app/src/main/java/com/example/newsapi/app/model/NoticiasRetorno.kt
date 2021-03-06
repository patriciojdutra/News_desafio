package com.example.newsapi.app.model

class NoticiasRetorno{

    var data = ArrayList<NoticiaModel>()
    var pagination = PageModel()

    var msgError = ""

    constructor() {}

    constructor(msgError: String) {
        this.msgError = msgError
    }
}



