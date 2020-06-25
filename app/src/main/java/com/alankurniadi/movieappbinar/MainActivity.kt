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
import com.alankurniadi.movieappbinar.viewmodel.MoviePlayingNowViewModel
import com.alankurniadi.movieappbinar.viewmodel.MoviePopulerViewModel
import com.alankurniadi.movieappbinar.viewmodel.MovieTopRateViewModel
import com.alankurniadi.movieappbinar.viewmodel.MovieUpcomingViewModel
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
    private lateinit var moviePlayingNowViewModel: MoviePlayingNowViewModel
    private lateinit var movieUpcomingViewModel: MovieUpcomingViewModel
    private lateinit var movieTopRateViewModel: MovieTopRateViewModel

    private val listPopuler = ArrayList<Movie>()
    private val listPlayingNow = ArrayList<Movie>()
    private val listUpcoming = ArrayList<Movie>()
    private val listTopRate = ArrayList<Movie>()

    private val apiKey = "54f1a575ff34a72f82134bf90ea5ff4f"

    private val TAG = MainActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initsialize adapter
        moviePopulerAdapter = MoviePopulerAdapter()
        moviePopulerAdapter.notifyDataSetChanged()

        moviePlayingNowAdapter = MoviePlayingNowAdapter()
        moviePlayingNowAdapter.notifyDataSetChanged()

        movieUpcomingAdapter = MovieUpcomingAdapter()
        movieUpcomingAdapter.notifyDataSetChanged()

        movieTopRateAdapter = MovieTopRateAdapter()
        movieTopRateAdapter.notifyDataSetChanged()


        recyclerViewPopuler()
        recyclerViewPlayingNow()
        recyclerViewUpcoming()
        recyclerViewTopRate()

        //get API
        moviePopulerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MoviePopulerViewModel::class.java)
        moviePopulerViewModel.setDataMoviePopuler()
        moviePopulerViewModel.getPopulerMovie().observe(this, Observer { dataPopuler ->
            if (dataPopuler != null) {
                showLoading(true)
                moviePopulerAdapter.setData(dataPopuler)
                showLoading(false)
            }
        })

        moviePlayingNowViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MoviePlayingNowViewModel::class.java)
        moviePlayingNowViewModel.setDataMoviePlayingNow()
        moviePlayingNowViewModel.getPlayingNowMoview().observe(this, Observer { dataPlayingNow ->
            if (dataPlayingNow != null) {
                showLoading2(true)
                moviePlayingNowAdapter.setData(dataPlayingNow)
                showLoading2(false)
            }
        })

        movieUpcomingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MovieUpcomingViewModel::class.java)
        movieUpcomingViewModel.setDataMovieUpcoming()
        movieUpcomingViewModel.getUpcoming().observe(this, Observer { dataUpcoming ->
            if (dataUpcoming != null) {
                showLoading3(true)
                movieUpcomingAdapter.setData(dataUpcoming)
                showLoading3(false)
            }
        })

        movieTopRateViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MovieTopRateViewModel::class.java)
        movieTopRateViewModel.setDataMovieTopRate()
        movieTopRateViewModel.getTopRate().observe(this, Observer { dataTopRate ->
            if (dataTopRate != null) {
                showLoading4(true)
                movieTopRateAdapter.setData(dataTopRate)
                showLoading4(false)
            }
        })
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
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, data)
                startActivity(intent)
                Toast.makeText(this@MainActivity, "${data.title}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun recyclerViewUpcoming() {
        rv_third_MultiSnap.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_third_MultiSnap.adapter = movieUpcomingAdapter

        movieUpcomingAdapter.setOnItemClickCallback(object : MovieUpcomingAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, data)
                startActivity(intent)
                Toast.makeText(this@MainActivity, "${data.title}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun recyclerViewTopRate() {
        rv_fourth_MultiSnap.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_fourth_MultiSnap.adapter = movieTopRateAdapter

        movieTopRateAdapter.setOnItemClickCallback(object : MovieTopRateAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, data)
                startActivity(intent)
                Toast.makeText(this@MainActivity, "${data.title}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    /*private fun getDataMovieTopRate() {
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
    }*/

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