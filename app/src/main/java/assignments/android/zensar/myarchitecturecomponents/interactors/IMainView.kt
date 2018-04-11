package assignments.android.zensar.myarchitecturecomponents.interactors

/**
 * Created by RK51670 on 06-04-2018.
 */
interface IMainView {

    fun initViews()
    fun hideLoading()
    fun showLoading()
    fun fetchData(latitude:Double,longitude:Double)
    fun removeLocationObserver()
}