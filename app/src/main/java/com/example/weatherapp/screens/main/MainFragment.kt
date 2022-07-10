package com.example.weatherapp.screens.main

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.adapters.ViewPagerAdapter
import com.example.weatherapp.base.ScopedFragment
import com.example.weatherapp.data.network.WeatherNetworkDataSource
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.screens.days.DaysFragment
import com.example.weatherapp.screens.hours.HoursFragment
import com.example.weatherapp.utils.FEELS_LIKE
import com.example.weatherapp.utils.GRAD
import com.example.weatherapp.utils.isPermissionGranted
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MainFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: MainFragmentViewModelFactory by instance()

    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var weather: WeatherNetworkDataSource

    private val fragmentList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val tabList = listOf(
        "Hours",
        "Days"
    )

    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainFragmentViewModel::class.java)
        bindUI()
        init()
    }

    private fun init() = with(binding) {
        val adapter = ViewPagerAdapter(activity as FragmentActivity, fragmentList)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()

        currentWeather.observe(this@MainFragment, Observer {
            if (it == null) return@Observer
            val tStr = it.temperature.toInt().toString()
            val flStr = it.feelsLikeTemperature.toInt().toString()
            tv_current_temp.text = "${tStr}${GRAD}"
            tv_condition.text = it.conditionText
            tv_feels_like.text = "${FEELS_LIKE}${flStr}${GRAD}"

            group_loading.visibility = View.GONE
        })
    }

    private fun updateLocation(location: String) {
        tv_location.text = location
    }
}