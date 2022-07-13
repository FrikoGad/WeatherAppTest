package com.example.weatherapp.screens

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority

class LifeCycleBoundLocationManager(
    lifecycleOwner: LifecycleOwner,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationCallback: LocationCallback
) : DefaultLifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private val locationRequest = LocationRequest.create().apply {
        interval = 5000
        fastestInterval = 5000
        // dev
        priority = Priority.PRIORITY_BALANCED_POWER_ACCURACY
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}