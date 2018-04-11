package assignments.android.zensar.myarchitecturecomponents.models.nearbyplaces

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OpeningHours {

    @SerializedName("open_now")
    @Expose
    var isOpenNow: Boolean = false
    @SerializedName("weekday_text")
    @Expose
    var weekdayText: List<Any>? = null

}