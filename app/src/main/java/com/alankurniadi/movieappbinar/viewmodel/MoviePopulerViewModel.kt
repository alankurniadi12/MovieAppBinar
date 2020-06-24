package com.alankurniadi.movieappbinar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alankurniadi.movieappbinar.MainActivity
import com.alankurniadi.movieappbinar.modeldata.Movie
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MoviePopulerViewModel: ViewModel() {

    val listItems = ArrayList<Movie>()
    private val apiKey = "54f1a575ff34a72f82134bf90ea5ff4f"

    val listPopuler = MutableLiveData<ArrayList<Movie>>()

    fun setDataMoviePopuler(){
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
                        listItems.add(movie)
                    }
                    listPopuler.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("TAG", "Movie Populer"+e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("TAG", "Movie Populer"+error?.message.toString())
            }
        })
    }

    fun getPopulerMovie(): LiveData<ArrayList<Movie>> {
        return listPopuler
    }
}