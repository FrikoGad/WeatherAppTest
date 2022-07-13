package com.example.weatherapp.data.provider

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.weatherapp.data.db.entity.current.Location
import com.example.weatherapp.internal.LocationPermissionNotGrantedException
import com.example.weatherapp.internal.asDeferred
import com.example.weatherapp.utils.CUSTOM_LOCATION
import com.example.weatherapp.utils.USE_DEVICE_LOCATION
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred
import kotlin.math.abs


class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context), LocationProvider {

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: Location): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        } catch (e: LocationPermissionNotGrantedException) {
            false
        }
        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPreferredLocationString(): String {
        if (isUsingDeviceLocation()) {
            try {
                val deviceLocation = getLastDeviceLocation().await()
                    ?: return "${getCustomLocationName()}"
                return "${deviceLocation.latitude},${deviceLocation.longitude}"
            } catch (e: LocationPermissionNotGrantedException) {
                return "${getCustomLocationName()}"
            }
        }
        else
            return "${getCustomLocationName()}"
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: Location): Boolean {
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await()
            ?: return false

        val comparisonThreshold = 0.03
        return abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThreshold &&
                abs(deviceLocation.longitude - lastWeatherLocation.lon) >comparisonThreshold
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: Location): Boolean {
        val customLocationName = getCustomLocationName()
        return customLocationName != lastWeatherLocation.name
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }

    private fun getLastDeviceLocation(): Deferred<android.location.Location?> {
        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}