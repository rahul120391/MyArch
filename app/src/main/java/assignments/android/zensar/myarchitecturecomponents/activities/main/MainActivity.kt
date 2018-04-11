package assignments.android.zensar.myarchitecturecomponents.activities.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.*
import android.view.View
import assignments.android.zensar.myarchitecturecomponents.R
import assignments.android.zensar.myarchitecturecomponents.adapters.MyNearbyPlacesAdapter
import assignments.android.zensar.myarchitecturecomponents.interactors.IMainView
import assignments.android.zensar.myarchitecturecomponents.lifecycleobservers.MainActivityObserver
import assignments.android.zensar.myarchitecturecomponents.utility.Constants
import assignments.android.zensar.myarchitecturecomponents.utility.Utils
import assignments.android.zensar.myarchitecturecomponents.viewmodels.MainViewModel
import butterknife.BindView
import butterknife.ButterKnife
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IMainView {


    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mObserver: MainActivityObserver

    lateinit var mViewModel: MainViewModel

    @BindView(R.id.rvView)
    lateinit var mRvView: RecyclerView

    @BindView(R.id.pbBar)
    lateinit var mPbBar: ContentLoadingProgressBar

    @BindView(R.id.toolBar)
    lateinit var mToolBar: Toolbar

    @Inject
    lateinit var context: Context

    lateinit var mAdapter: MyNearbyPlacesAdapter

    val mLocationObserver = Observer { it: Location? ->
        it?.let {
            if (it != null) {
                fetchData(it.latitude, it.longitude)
            }
        }
    }

    val mPlacesData = Observer { it: Any? ->
        hideLoading()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        initViews()
    }

    override fun initViews() {
        setSupportActionBar(mToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        mToolBar.setNavigationOnClickListener { onBackPressed() }
        mAdapter = MyNearbyPlacesAdapter()
        val mLayoutManager = LinearLayoutManager(this)
        mRvView.layoutManager = mLayoutManager
        mRvView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        mRvView.adapter = mAdapter
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel::class.java)
        lifecycle.addObserver(mObserver)
        mObserver.getLiveLocationData().observe(this, mLocationObserver)

    }

    override fun fetchData(latitude: Double, longitude: Double) {
        if (Utils.checkConnectivity(this)) {
            showLoading()
            val params = HashMap<String, String>()
            val location = latitude.toString() + "," + longitude
            params.put(Constants.LOCATION, location)
            params.put(Constants.KEY, getString(R.string.api_key))
            params.put(Constants.RADIUS, "1000")
            params.put(Constants.TYPE, "bank")
            mViewModel.getData(params).observe(this,mPlacesData)
        } else {
            Utils.showToast(this, getString(R.string.network_error))
        }
    }

    override fun hideLoading() {
        mPbBar.visibility = View.GONE
    }

    override fun showLoading() {
        mPbBar.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        if (mObserver.getLiveLocationData().hasActiveObservers()) {
            removeLocationObserver()
        }
        lifecycle.removeObserver(mObserver)
        super.onDestroy()
    }

    override fun removeLocationObserver() {
        mObserver.getLiveLocationData().removeObserver(mLocationObserver)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mObserver.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        mObserver.onActivityResult(requestCode, resultCode, data)
    }
}
