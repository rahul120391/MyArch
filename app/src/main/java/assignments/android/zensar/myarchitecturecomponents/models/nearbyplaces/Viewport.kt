package assignments.android.zensar.myarchitecturecomponents.models.nearbyplaces

import assignments.android.zensar.myarchitecturecomponents.models.nearbyplaces.Northeast
import assignments.android.zensar.myarchitecturecomponents.models.nearbyplaces.Southwest
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Viewport {

    @SerializedName("northeast")
    @Expose
    var northeast: Northeast? = null
    @SerializedName("southwest")
    @Expose
    var southwest: Southwest? = null

}