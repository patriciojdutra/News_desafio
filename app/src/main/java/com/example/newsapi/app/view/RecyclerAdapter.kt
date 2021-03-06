package com.example.newsapi.app.view

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.R
import com.example.newsapi.app.model.DetalheNoticiaModel
import com.example.newsapi.app.model.NoticiaModel
import com.squareup.picasso.Picasso


class RecyclerAdapter (private val newsList: List<NoticiaModel>, var act: Activity): RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_news,parent,false) as View
        return Holder(v)
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.txtQuant.setText((position+1).toString())
        holder.txtHeadNews.setText(newsList[position].title)
        holder.txtBodyNews.setText(newsList[position].description)
        Picasso.get().load(newsList[position].image_url)
            .resize(150, 150)
            .centerCrop()
            .into(holder.imageView);

        holder.btnFavorito.setOnClickListener {
            if(newsList[position].eFavorito){

                newsList[position].eFavorito = false
                FeedActivity.listaFavoritos.removeAll { n -> n.title == newsList[position].title && n.description == newsList[position].description }
                holder.btnFavorito.setImageResource(android.R.drawable.btn_star_big_off)

            }else{

                newsList[position].eFavorito = true
                FeedActivity.listaFavoritos.add(newsList[position])
                holder.btnFavorito.setImageResource(android.R.drawable.btn_star_big_on)

            }
        }

        if(newsList[position].eFavorito)
            holder.btnFavorito.setImageResource(android.R.drawable.btn_star_big_on)
        else
            holder.btnFavorito.setImageResource(android.R.drawable.btn_star_big_off)


        holder.layout.setOnClickListener {

            var it = Intent(act, DetalheNoticiaActivity::class.java)
            it.putExtra("noticia", newsList[position])
            act.startActivityForResult(it,1)

        }

    }

    fun getItensFavoritos(): ArrayList<NoticiaModel>{
        return newsList.filter { n -> n.eFavorito == true  } as ArrayList<NoticiaModel>
    }

    fun getLista(): ArrayList<NoticiaModel>{
        return newsList as ArrayList<NoticiaModel>
    }

    class Holder(v: View) : RecyclerView.ViewHolder(v) {

        val txtBodyNews = v.findViewById<TextView>(R.id.txtBodyNews)
        val txtHeadNews = v.findViewById<TextView>(R.id.txtHeadNews)
        val imageView = v.findViewById<ImageView>(R.id.imageView3)
        val txtQuant = v.findViewById<TextView>(R.id.txtQuant)
        val layout = v.findViewById<ConstraintLayout>(R.id.layout)
        val btnFavorito = v.findViewById<ImageView>(R.id.btnFavorito)
    }

}



