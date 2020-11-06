package com.hmscl.adsrecyclerviewdemo.model.news
data class NewsListModel(
    var data: List<NewsModel>
)
data class NewsModel(val title: String?, val description: String?, val link: String?, val pubDate: String?)