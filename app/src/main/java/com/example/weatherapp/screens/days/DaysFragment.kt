package com.example.weatherapp.screens.days

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.adapters.WeatherAdapter
import com.example.weatherapp.data.network.response.WeatherDaysResponse
import com.example.weatherapp.databinding.FragmentDaysBinding
import com.example.weatherapp.screens.main.MainFragmentViewModel
import com.example.weatherapp.screens.main.MainFragmentViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class DaysFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: MainFragmentViewModelFactory by instance()

    lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentDaysBinding
    private val adapter by lazy { WeatherAdapter() }
    private lateinit var viewModel: DaysFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DaysFragmentViewModel::class.java)
        init()
    }

    private fun init() {
        recyclerView = binding.rvDays
        recyclerView.adapter = adapter
        viewModel.getDaysWeatherRetrofit()
        viewModel.daysWeather.observe(this, { list ->
            adapter.setList(list.body()!!.forecast.forecastday)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }
}