package assignments.android.zensar.myarchitecturecomponents.networking

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import assignments.android.zensar.myarchitecturecomponents.interactors.IRepoView
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by RK51670 on 05-04-2018.
 */
class Repository @Inject constructor(retrofitService: RetrofitService) : IRepoView {

    val mRetrofitService = retrofitService
    override fun fetchNearbyPlaces(params: Map<String, String>): LiveData<Any> {
        val flowable = mRetrofitService.getPlaces(params)
        var data = MutableLiveData<Any>()
        flowable.observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
                .subscribe { t: Any ->
                        data.postValue(t)
                }


        return data
    }
}