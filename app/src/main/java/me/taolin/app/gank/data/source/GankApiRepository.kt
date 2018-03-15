package me.taolin.app.gank.data.source

import io.reactivex.Observable
import me.taolin.app.gank.data.GankData
import me.taolin.app.gank.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/26
 * @description
 */
class GankApiRepository @Inject constructor() {

    private val gankApiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GankApiService::class.java)

    fun getCategoryData(category: String, pageNum: Int, pageCount: Int): Observable<GankData> {
        return gankApiService.getCategoryData(category, pageNum, pageCount)
    }
}