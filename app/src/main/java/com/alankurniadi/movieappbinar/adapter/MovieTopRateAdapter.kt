package com.alankurniadi.movieappbinar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.alankurniadi.movieappbinar.R
import com.alankurniadi.movieappbinar.modeldata.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_card_main.view.*

class MovieTopRateAdapter: RecyclerView.Adapter<MovieTopRateAdapter.MovieViewHolder>() {

    private  var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }

    private val mData = ArrayList<Movie>()

    fun setData(items: ArrayList<Movie>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_card_main, parent, false)
        return MovieViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w154/"+movie.poster_path)
                    .into(img_poster_main)
                tv_title_main.text = movie.title
                tv_rating_main.text = movie.vote_average.toString()

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(movie) }
            }
        }
    }
}