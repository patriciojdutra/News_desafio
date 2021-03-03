package com.example.newsapi.app.util

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.newsapi.R
import kotlinx.android.synthetic.main.activity_main.*

import java.lang.Exception

class Alerta {

    companion object {

        lateinit var dialog: Dialog

        @JvmStatic
        fun aviso(msg: String, act: Activity) {

            dialog = Dialog(act)

            val builder = AlertDialog.Builder(act)
            builder.setTitle(R.string.aviso)
            builder.setMessage(msg)
            builder.setPositiveButton(R.string.continuar, DialogInterface.OnClickListener { dialog, which ->  dialog.dismiss()})
            builder.setCancelable(true)
            dialog = builder.create()
            dialog.show()

        }
    }
}