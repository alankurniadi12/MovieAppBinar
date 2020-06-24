package com.alankurniadi.movieappbinar.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.alankurniadi.movieappbinar.R
import com.alankurniadi.movieappbinar.modeldata.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.progress_bar
import kotlinx.android.synthetic.main.activity_main.*

class DetailActivity : AppCompatActivity() {
    //private var movie: Movie? = null
    companion object {
        const val EXTRA_DATA = "extra_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(tb_title_detail_movie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.getParcelableExtra(EXTRA_DATA) as Movie
        showLoading(true)
        tb_title_detail_movie.title = data.title
        tv_release_date_detail.text = data.release_date
        tv_rating_detail.text = data.vote_average.toString()
        tv_description_detail.text = data.overview
        Glide.with(this).load("https://image.tmdb.org/t/p/w400/"+data.backround_path).into(img_bg_detail_movie)
        Glide.with(this).load("https://image.tmdb.org/t/p/w154/"+data.poster_path).into(img_poster_detail_movie)
        showLoading(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun showLoading(state: Boolean) {
        if (state) progress_bar.visibility = View.VISIBLE else progress_bar.visibility = View.GONE
    }
}