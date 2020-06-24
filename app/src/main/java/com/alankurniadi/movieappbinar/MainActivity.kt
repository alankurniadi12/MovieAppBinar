package com.alankurniadi.movieappbinar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alankurniadi.movieappbinar.adapter.MoviePlayingNowAdapter
import com.alankurniadi.movieappbinar.adapter.MoviePopulerAdapter
import com.alankurniadi.movieappbinar.modeldata.Movie
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var moviePopulerAdapter: MoviePopulerAdapter
    private lateinit var moviePlayingNowAdapter: MoviePlayingNowAdapter

    private val listPopuler = ArrayList<Movie>()
    private val listPlayingNow = ArrayList<Movie>()

    private val apiKey = "54f1a575ff34a72f82134bf90ea5ff4f"

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //first adapter
        moviePopulerAdapter = MoviePopulerAdapter()
        moviePopulerAdapter.notifyDataSetChanged()

        //second adapter
        moviePlayingNowAdapter = MoviePlayingNowAdapter()
        moviePlayingNowAdapter.notifyDataSetChanged()

        //third adapter


        //fourth adapter


        recyclerViewPopuler()
        recyclerViewPlayingNow()

        //get Data API
        getDataMoviePopuler()
        getDataMoviePlaynow()
    }

    private fun recyclerViewPopuler() {
        //rv_first_MultiSnap.setHasFixedSize(true)
        rv_first_MultiSnap.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_first_MultiSnap.adapter = moviePopulerAdapter

        moviePopulerAdapter.setOnItemClickCallback(object : MoviePopulerAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun recyclerViewPlayingNow() {
        //rv_second_MultiSnap.setHasFixedSize(true)
        rv_second_MultiSnap.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_second_MultiSnap.adapter = moviePlayingNowAdapter

        moviePlayingNowAdapter.setOnItemClickCallback(object : MoviePlayingNowAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getDataMoviePopuler() {
        val url = "https://api.themoviedb.org/3/movie/popular?api_key=$apiKey&language=en-US"
        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObjects = JSONObject(result)
                    val movieResults = responseObjects.getJSONArray("results")

                    showLoading(true)
                    for (i in 0 until movieResults.length()) {
                        val movieAPI = movieResults.getJSONObject(i)
                        val movie = Movie()
                        movie.apply {
                            id = movieAPI.getInt("id")
                            poster_path = movieAPI.getString("poster_path")
                            backround_path = movieAPI.getString("backdrop_path")
                            title = movieAPI.getString("title")
                            vote_average = movieAPI.getInt("vote_average")
                            overview = movieAPI.getString("overview")
                            release_date = movieAPI.getString("release_date")
                        }
                        listPopuler.add(movie)
                    }
                    moviePopulerAdapter.setData(listPopuler)
                    showLoading(false)
                } catch (e: Exception) {
                    Log.d(TAG, "Movie Populer"+e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d(TAG, "Movie Populer"+error?.message.toString())
            }
        })
    }

    private fun getDataMoviePlaynow() {
        val url = "https://api.themoviedb.org/3/movie/now_playing?api_key=$apiKey&language=en-US"
        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObjects = JSONObject(result)
                    val movieResults = responseObjects.getJSONArray("results")

                    showLoading2(true)
                    for (i in 0 until movieResults.length()) {
                        val movieAPI = movieResults.getJSONObject(i)
                        val movie = Movie()
                        movie.apply {
                            id = movieAPI.getInt("id")
                            poster_path = movieAPI.getString("poster_path")
                            backround_path = movieAPI.getString("backdrop_path")
                            title = movieAPI.getString("title")
                            vote_average = movieAPI.getInt("vote_average")
                            overview = movieAPI.getString("overview")
                            release_date = movieAPI.getString("release_date")
                        }
                        listPlayingNow.add(movie)
                    }
                    moviePlayingNowAdapter.setData(listPlayingNow)
                    showLoading2(false)
                } catch (e: Exception) {
                    Log.e(TAG, "Movie Playing"+e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d(TAG, "Movie Playing"+error?.message.toString())
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) progress_bar.visibility = View.VISIBLE else progress_bar.visibility = View.GONE
        //if (state) progress_bar2.visibility = View.VISIBLE else progress_bar2.visibility = View.GONE
    }
    private fun showLoading2(state: Boolean) {
        if (state) progress_bar2.visibility = View.VISIBLE else progress_bar2.visibility = View.GONE
    }
}