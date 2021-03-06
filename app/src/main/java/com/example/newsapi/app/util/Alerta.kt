package com.example.newsapi.app.util

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.newsapi.R
import com.example.newsapi.app.view.FeedActivity
import com.example.newsapi.app.view.MainActivity
import com.facebook.*
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException

import java.lang.Exception

class Alerta {

    companion object {

        lateinit var dialog: Dialog

        @JvmStatic
        fun aviso(msg: String, act: Activity) {

            val builder = AlertDialog.Builder(act)
            builder.setTitle(R.string.aviso)
            builder.setMessage(msg)
            builder.setPositiveButton(R.string.continuar, DialogInterface.OnClickListener { dialog, which ->  dialog.dismiss()})
            builder.setCancelable(true)
            dialog = builder.create()
            dialog.show()

        }

        @JvmStatic
        fun avisoFacebookEstaConectado(act: Activity) {

            var view = View.inflate(act, R.layout.dialog_aviso_conectado_facebook,null)
            view.findViewById<Button>(R.id.btnContinuar).setOnClickListener {
                act.startActivity(Intent(act,FeedActivity::class.java))
                act.finish()
            }

            FacebookSdk.sdkInitialize(getApplicationContext());
            var callbackManager = CallbackManager.Factory.create();
            view.findViewById<LoginButton>(R.id.btnFacebook).registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {}
                override fun onCancel() {

                }
                override fun onError(exception: FacebookException) {
                    Alerta.aviso(exception.message.toString(), act)
                }
            })
            view.findViewById<LoginButton>(R.id.btnFacebook).setOnClickListener { dialog.dismiss() }

            val builder = AlertDialog.Builder(act)
            builder.setTitle(R.string.aviso)
            builder.setMessage(R.string.continuar_conectado_facebook)
            builder.setView(view)
            builder.setCancelable(false)
            dialog = builder.create()
            dialog.show()

        }
    }
}