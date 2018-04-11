package assignments.android.zensar.myarchitecturecomponents.networking

import assignments.android.zensar.myarchitecturecomponents.utility.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by RK51670 on 05-04-2018.
 */
class RetrofitAdapter {
    companion object {
        fun create(): RetrofitService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client1 = OkHttpClient.Builder().addInterceptor(interceptor)
            val retorfitAdapter = Retrofit.Builder().baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client1.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retorfitAdapter.create(RetrofitService::class.java)
        }

    }
}