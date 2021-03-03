package com.example.newsapi.app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.newsapi.R
import com.example.newsapi.app.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer
import com.example.newsapi.app.util.Alerta

class MainActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCadastrarOnClick()
        btnLogarOnClick()
        observes()

    }

    fun observes(){

        viewModel.carregando.observe(this, Observer {
            if(it)
                loading.visibility = View.VISIBLE
            else
                loading.visibility = View.GONE
        })

        viewModel.statusRetorno.observe(this, Observer {
            if(it) {
                startActivity(Intent(this, FeedActivity::class.java))
                finish()
            }
        })

        viewModel.msgError.observe(this, Observer {
            Alerta.aviso(it,this)
        })

    }

    fun btnCadastrarOnClick(){
        btnCadastrarUsuario.setOnClickListener {
            startActivity(Intent(this,CadastroDeUsuarioActivity::class.java))
        }
    }

    fun btnLogarOnClick(){
        btnEntrar.setOnClickListener {
            fazerLogin()
        }
    }

    fun fazerLogin(){
        if(loginEstaValido()){
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()
            viewModel.fazerLogin(email, senha)
        }
    }

    fun loginEstaValido(): Boolean{

        if(edtEmail.text.isEmpty())
            return false
        else if(edtSenha.text.isEmpty())
            return false
        else
            return true

    }
}