package com.example.newsapi.app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.newsapi.R
import com.example.newsapi.app.util.Alerta
import com.example.newsapi.app.viewmodel.CadastroUsuarioViewModel
import com.example.newsapi.app.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_cadastro_de_usuario.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.edtEmail
import kotlinx.android.synthetic.main.activity_main.edtSenha
import kotlinx.android.synthetic.main.activity_main.loading

class CadastroDeUsuarioActivity : AppCompatActivity() {

    private val viewModel: CadastroUsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_de_usuario)

        btnCadastrarOnClick()
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
                finishAffinity()
            }
        })

        viewModel.msgError.observe(this, Observer {
            Alerta.aviso(it,this)
        })

    }

    fun btnCadastrarOnClick(){
        btnSalvarUsuario.setOnClickListener {
            salvarCadastro()
        }
    }

    fun salvarCadastro(){
        if(cadastroEstaValido()){
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()
            val nome = edtNome.text.toString()
            viewModel.cadastrarUsuario(nome, email, senha)
        }
    }

    fun cadastroEstaValido(): Boolean {

        if (edtEmail.text.isEmpty())
            return false
        else if (edtSenha.text.isEmpty())
            return false
        else if (edtNome.text.isEmpty())
            return false
        else if (edtConfiSenha.text.toString() != edtSenha.text.toString())
            return false
        else
            return true
    }

}