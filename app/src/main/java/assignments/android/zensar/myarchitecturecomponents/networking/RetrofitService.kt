package assignments.android.zensar.myarchitecturecomponents.networking

import assignments.android.zensar.myarchitecturecomponents.models.nearbyplaces.MyPlaces
import assignments.android.zensar.myarchitecturecomponents.utility.Constants
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.*

/**
 * Created by RK51670 on 04-04-2018.
 */
public interface RetrofitService {

     @GET(Constants.PLACE_SEARCH_URL)
     fun getPlaces(@QueryMap params:Map<String,String>):Flowable<MyPlaces>
}