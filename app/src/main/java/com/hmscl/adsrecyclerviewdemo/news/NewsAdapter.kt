package com.hmscl.adsrecyclerviewdemo.model.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hmscl.adsrecyclerviewdemo.R
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.nativead.NativeAd
import com.huawei.hms.ads.nativead.NativeAdLoader
import com.huawei.hms.ads.nativead.NativeView

class NewsAdapter(var context: Context, var newsList: List<NewsModel>, var adPosition: Array<Int>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val SHOW_AD = 0
    private val SHOW_NEWS = 1
    inner class NewsCardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.news_title)
        var description: TextView = itemView.findViewById(R.id.news_shortBody)
        var pubDate: TextView = itemView.findViewById(R.id.news_time)
    }

    inner class AdCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ad_template: NativeView = itemView.findViewById(R.id.ad_template);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            SHOW_AD -> {
                val nativeView = LayoutInflater.from(parent.context).inflate(R.layout.view_nativeads_huawei, parent, false)
                viewHolder = AdCardViewHolder(nativeView)
            }
            SHOW_NEWS -> {
                val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.view_news_card, parent, false)
                viewHolder = NewsCardViewHolder(layoutInflater)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            SHOW_NEWS -> {
                val item = newsList[position]
                (holder as NewsCardViewHolder).title.text = item.title
                (holder as NewsCardViewHolder).description.text = item.description
                (holder as NewsCardViewHolder).pubDate.text = item.pubDate
            }
            SHOW_AD -> {
                val builder = NativeAdLoader.Builder(context, context.getString(R.string.huawei_test_nativeAd_id))
                builder.setNativeAdLoadedListener(object : NativeAd.NativeAdLoadedListener {
                    override fun onNativeAdLoaded(nativeAd: NativeAd?) {
                        val nativeView = (holder as AdCardViewHolder).ad_template
                        initNativeAdView(nativeAd, nativeView)
                    }
                })
                val nativeAdLoader = builder.build()
                nativeAdLoader.loadAd(AdParam.Builder().build())
            }
        }
    }

    override fun getItemCount() = newsList.size

    override fun getItemViewType(position: Int): Int {
        return if (adPosition.contains(position)) {
            SHOW_AD
        } else {
            SHOW_NEWS
        }
    }

    private fun initNativeAdView(nativeAd: NativeAd?, nativeView: NativeView) {
        nativeView.titleView = nativeView.findViewById(R.id.ad_title)
        (nativeView.titleView as TextView).text = nativeAd?.title
        nativeView.mediaView = nativeView.findViewById(R.id.ad_media)
        nativeView.mediaView.setMediaContent(nativeAd?.mediaContent)

        nativeView.setNativeAd(nativeAd)
    }
}