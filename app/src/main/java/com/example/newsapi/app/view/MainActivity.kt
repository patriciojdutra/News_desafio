package com.example.newsapi.app.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.newsapi.R
import com.example.newsapi.app.util.Alerta
import com.example.newsapi.app.util.PreferencesUtil
import com.example.newsapi.app.viewmodel.LoginViewModel
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.login.LoginResult
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_cadastro_de_usuario.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.loading
import org.json.JSONException


class MainActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    lateinit var callbackManager: CallbackManager
    val act = this

    companion object {
        var usuarioId = ""
        var usuarioName = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verificarConexaoComFacebook()
        btnCadastrarOnClick()
        btnLogarOnClick()
        observes()
        loginComFacebook()
        tabLayoutopcaoSelecionada()
        btnCadastrarOnClick()

    }

    fun tabLayoutopcaoSelecionada(){
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                layoutCadastro.visibility = if(tab?.position == 0) View.GONE else View.VISIBLE
                layoutLogin.visibility = if(tab?.position == 0) View.VISIBLE else View.GONE
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    fun verificarConexaoComFacebook(){

        try{
            val token = AccessToken.getCurrentAccessToken()
            if(token != null){
                Alerta.avisoFacebookEstaConectado(act)
            }
        }catch (e:Exception){ }

    }

    fun loginComFacebook(){

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        btnFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {

                    val request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken()) { `object`, response ->
                        try {
                            if (response.jsonObject != null)
                                usuarioId = response.jsonObject.getString("id")
                                usuarioName = response.jsonObject.getString("name")
                                viewModel.fazerLogin(usuarioId+"@hotmail.com", usuarioId)
                        } catch (e: JSONException) {
                            Alerta.aviso(e.message.toString(), act)
                        }
                    }

                    val parameters = Bundle()
                    parameters.putString("fields","id,name")
                    request.parameters = parameters
                    request.executeAsync()
            }

            override fun onCancel() {

            }

            override fun onError(exception: FacebookException) {
                Alerta.aviso(exception.message.toString(), act)
            }
        })

    }

    fun observes(){

        viewModel.carregando.observe(this, Observer {
            if(it)
                loading.visibility = View.VISIBLE
            else
                loading.visibility = View.GONE
        })

        viewModel.token.observe(this, Observer {
            if(it.isNotEmpty()) {
                PreferencesUtil.putString(this,"token", it)
                irParaOFeed()
            }
        })


        viewModel.msgError.observe(this, Observer {
            Alerta.aviso(it,this)
        })

    }

    fun irParaOFeed() {
        startActivity(Intent(this, FeedActivity::class.java))
        finish()
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

        if (edtEmail.text.isEmpty() || !edtEmail.text.toString().contains("@")) {
            Alerta.aviso(getString(R.string.email_invalid), this)
            return false
        }else if (edtSenha.text.isEmpty())
            return false
        else if (edtNome.text.isEmpty())
            return false
        else if (edtConfiSenha.text.toString() != edtSenha.text.toString()) {
            Alerta.aviso(getString(R.string.senha_nao_coincidem), this)
            return false
        }else
            return true
    }

    fun btnLogarOnClick(){
        btnEntrar.setOnClickListener {
            fazerLogin()
        }
    }

    fun fazerLogin(){
        if(loginEstaValido()){
            val email = edtEmailLogin.text.toString()
            val senha = edtSenhaLogin.text.toString()
            viewModel.fazerLogin(email, senha)
        }
    }

    fun loginEstaValido(): Boolean{

        if(edtEmailLogin.text.isEmpty()) {
            Alerta.aviso(getString(R.string.email_vazio), act)
            return false
        }else if(edtSenhaLogin.text.isEmpty()) {
            Alerta.aviso(getString(R.string.senha_vazia), act)
            return false
        }else
            return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


}