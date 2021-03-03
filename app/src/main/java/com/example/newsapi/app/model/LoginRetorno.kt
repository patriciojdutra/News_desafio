package com.example.newsapi.app.model

class LoginRetorno{

    var token: String = ""
    var message: String = ""

    constructor() {}

    constructor(token: String, message: String) {
        this.token = token
        this.message = message
    }
}



