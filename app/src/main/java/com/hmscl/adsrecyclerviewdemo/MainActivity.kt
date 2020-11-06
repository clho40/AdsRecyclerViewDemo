package com.hmscl.adsrecyclerviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.hmscl.adsrecyclerviewdemo.model.news.NewsAdapter
import com.hmscl.adsrecyclerviewdemo.model.news.NewsListModel
import com.hmscl.adsrecyclerviewdemo.model.news.NewsModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var newsCard_view: RecyclerView
    private lateinit var newsCard_viewAdapter: RecyclerView.Adapter<*>
    private lateinit var newsCard_viewManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        populateNews()
    }

    private fun getData() : List<NewsModel>{
        //parse sample json data
        var sample_json_data = this.assets.open("news.json").bufferedReader().use { it.readText() }
        return Gson().fromJson(sample_json_data, NewsListModel::class.java).data
    }

    private fun populateNews() {
        newsCard_viewManager = LinearLayoutManager(this)
        newsCard_viewAdapter = NewsAdapter(this, getData(),getAdPosition())
        newsCard_view = rv_news.apply {
            setHasFixedSize(true)
            layoutManager = newsCard_viewManager
            adapter = newsCard_viewAdapter
        }
    }

    private fun getAdPosition() : Array<Int> {
        // build your custom logic here to obtain ad position
        return intArrayOf(3,5,7).toTypedArray()
    }
}