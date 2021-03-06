package com.example.newsapi.app.view

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.newsapi.R
import com.example.newsapi.app.model.NoticiaModel
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso


class BannerAdapter (var newsList: ArrayList<NoticiaModel>, var act: Activity): SliderViewAdapter<BannerAdapter.Holder>(){

    fun renewItems(newsList: ArrayList<NoticiaModel>) {
        this.newsList = newsList
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        this.newsList.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(item: NoticiaModel) {
        this.newsList.add(item)
        notifyDataSetChanged()
    }

    fun getItensFavoritos(): ArrayList<NoticiaModel>{
        return newsList.filter { n -> n.eFavorito == true  } as ArrayList<NoticiaModel>
    }

    fun getLista(): ArrayList<NoticiaModel>{
        return newsList as ArrayList<NoticiaModel>
    }

    override fun getCount(): Int {
        return newsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.carrossel_layout, null)
        return Holder(inflate)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val noticia = newsList[position]
        noticia.eCarrossel = true

        holder.txtTitulo.setText(noticia.title)
        holder.txtDescricao.setText(noticia.description)
        Picasso.get().load(noticia.image_url)
            .resize(500, 500)
            .centerCrop()
            .into(holder.imageView);

        holder.btnFavorito.setOnClickListener {

            if(noticia.eFavorito){

                noticia.eFavorito = false
                FeedActivity.listaFavoritos.removeAll { n -> n.title == noticia.title && n.description == noticia.description }
                holder.btnFavorito.setImageResource(android.R.drawable.btn_star_big_off)

            }else{

                noticia.eFavorito = true
                FeedActivity.listaFavoritos.add(noticia)
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

    class Holder(v: View) : SliderViewAdapter.ViewHolder(v) {

        val txtTitulo = v.findViewById<TextView>(R.id.txtTitulo)
        val txtDescricao = v.findViewById<TextView>(R.id.txtDescricao)
        val imageView = v.findViewById<ImageView>(R.id.imageView)
        val btnFavorito = v.findViewById<ImageButton>(R.id.btnFavorito)
        val layout = v.findViewById<ConstraintLayout>(R.id.layout)

    }

}



