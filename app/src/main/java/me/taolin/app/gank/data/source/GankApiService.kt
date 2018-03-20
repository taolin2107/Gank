package me.taolin.app.gank.data.source

import io.reactivex.Observable
import me.taolin.app.gank.data.GankData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/26
 * @description
 */
interface GankApiService {

    @GET("api/data/{category}/{pageCount}/{pageIndex}")
    fun getCategoryData(@Path("category") category: String,
                        @Path("pageCount") pageCount: Int,
                        @Path("pageIndex") pageIndex: Int): Observable<GankData>
}