package com.alankurniadi.movieappbinar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alankurniadi.movieappbinar.adapter.MoviePlayingNowAdapter
import com.alankurniadi.movieappbinar.adapter.MoviePopulerAdapter
import com.alankurniadi.movieappbinar.adapter.MovieTopRateAdapter
import com.alankurniadi.movieappbinar.adapter.MovieUpcomingAdapter
import com.alankurniadi.movieappbinar.detail.DetailActivity
import com.alankurniadi.movieappbinar.modeldata.Movie
import com.alankurniadi.movieappbinar.viewmodel.MoviePopulerViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var moviePopulerAdapter: MoviePopulerAdapter
    private lateinit var moviePlayingNowAdapter: MoviePlayingNowAdapter
    private lateinit var movieUpcomingAdapter: MovieUpcomingAdapter
    private lateinit var movieTopRateAdapter: MovieTopRateAdapter

    private lateinit var moviePopulerViewModel: MoviePopulerViewModel

    private val listPopuler = ArrayList<Movie>()
    private val listPlayingNow = ArrayList<Movie>()
    private val listUpcoming = ArrayList<Movie>()
    private val listTopRate = ArrayList<Movie>()

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
        movieUpcomingAdapter = MovieUpcomingAdapter()
        movieUpcomingAdapter.notifyDataSetChanged()

        //fourth adapter
        movieTopRateAdapter = MovieTopRateAdapter()
        movieTopRateAdapter.notifyDataSetChanged()


        recyclerViewPopuler()
        recyclerViewPlayingNow()
        recyclerViewUpcoming()
        recyclerViewTopRate()

        //get API

        //getDataMoviePopuler()
        moviePopulerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MoviePopulerViewModel::class.java)
        moviePopulerViewModel.setDataMoviePopuler()
        moviePopulerViewModel.getPopulerMovie().observe(this, Observer { dataPopuler ->
            if (dataPopuler != null) {
                showLoading(true)
                moviePopulerAdapter.setData(dataPopuler)
                showLoading(false)
            }
        })
        getDataMoviePlaynow()
        getDataMovieUpcoming()
        getDataMovieTopRate()
    }

    private fun recyclerViewPopuler() {
        rv_first_MultiSnap.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_first_MultiSnap.adapter = moviePopulerAdapter

        moviePopulerAdapter.setOnItemClickCallback(object : MoviePopulerAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, data)
                startActivity(intent)
                Toast.makeText(this@MainActivity, "${data.title}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun recyclerViewPlayingNow() {
        rv_second_MultiSnap.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_second_MultiSnap.adapter = moviePlayingNowAdapter

        moviePlayingNowAdapter.setOnItemClickCallback(object : MoviePlayingNowAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun recyclerViewUpcoming() {
        rv_third_MultiSnap.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_third_MultiSnap.adapter = movieUpcomingAdapter

        movieUpcomingAdapter.setOnItemClickCallback(object : MovieUpcomingAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun recyclerViewTopRate() {
        rv_fourth_MultiSnap.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_fourth_MultiSnap.adapter = movieTopRateAdapter

        movieTopRateAdapter.setOnItemClickCallback(object : MovieTopRateAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                TODO("Not yet implemented")
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

    private fun getDataMovieUpcoming() {
        val url = "https://api.themoviedb.org/3/movie/upcoming?api_key=$apiKey&language=en-US"
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

                    showLoading3(true)
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
                        listUpcoming.add(movie)
                    }
                    movieUpcomingAdapter.setData(listUpcoming)
                    showLoading3(false)
                } catch (e: Exception) {
                    Log.e(TAG, "Movie Upcoming"+e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d(TAG, "Movie Upcoming"+error?.message.toString())
            }
        })
    }

    private fun getDataMovieTopRate() {
        val url = "https://api.themoviedb.org/3/movie/top_rated?api_key=$apiKey&language=en-US"
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

                    showLoading4(true)
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
                        listTopRate.add(movie)
                    }
                    movieTopRateAdapter.setData(listTopRate)
                    showLoading4(false)
                }catch (e: Exception) {
                    Log.d(TAG, "Movie Top Rate"+e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d(TAG, "Movie Top Rate"+error?.message.toString())
            }
        })
    }

    //loading
    fun showLoading(state: Boolean) {
        if (state) progress_bar.visibility = View.VISIBLE else progress_bar.visibility = View.GONE
    }
    private fun showLoading2(state: Boolean) {
        if (state) progress_bar2.visibility = View.VISIBLE else progress_bar2.visibility = View.GONE
    }
    private fun showLoading3(state: Boolean) {
        if (state) progress_bar3.visibility = View.VISIBLE else progress_bar3.visibility = View.GONE
    }
    private fun showLoading4(state: Boolean) {
        if (state) progress_bar4.visibility = View.VISIBLE else progress_bar4.visibility = View.GONE
    }
}