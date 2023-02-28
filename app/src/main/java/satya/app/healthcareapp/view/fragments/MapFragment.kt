package satya.app.healthcareapp.view.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import satya.app.healthcareapp.R
import satya.app.healthcareapp.databinding.FragmentMapBinding
import satya.app.healthcareapp.models.DoctorListModel

class MapFragment : Fragment(), OnMapReadyCallback {

    private val DEFAULT_ZOOM: Float = 15F
    private lateinit var binding: FragmentMapBinding
    private lateinit var selectedDoctor: DoctorListModel
    private lateinit var mGoogleMap: GoogleMap
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location
    private val args by navArgs<MapFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)

        initUI()
        return binding.root
    }

    private fun initUI() {
        // getting bundle data
        selectedDoctor = args.selectedDoctor


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        // Add a marker in doctor's location and move the Camera
        val latLng = LatLng(selectedDoctor.latitude, selectedDoctor.longitude)
        createMarker(latLng, DEFAULT_ZOOM, "${selectedDoctor.name}'s Location")

        // get the device current location
        getDeviceCurrentLocation()

    }

    private fun getDeviceCurrentLocation() {
        Log.e("TAG", "getDeviceCurrentLocation: ")
        mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        try {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.e("TAG", "getDeviceCurrentLocation: Permission Not Granted")
                return
            }

            mGoogleMap.isMyLocationEnabled = true

            val location = mFusedLocationProviderClient.lastLocation
            location.addOnCompleteListener {
                when {
                    it.isSuccessful -> {
                        Log.e("TAG", "getDeviceCurrentLocation: Location Found")
                        if (it.result != null) {
                            currentLocation = it.result
                            createMarker(
                                LatLng(
                                    currentLocation.latitude, currentLocation.longitude
                                ), DEFAULT_ZOOM, "Current Location"
                            )

                            drawPath(
                                LatLng(currentLocation.latitude, currentLocation.longitude),
                                LatLng(selectedDoctor.latitude, selectedDoctor.longitude)
                            )
                        }
                    }
                    else -> {
                        Log.e("TAG", "getDeviceCurrentLocation: Location is Null")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", "getDeviceCurrentLocation:${e.message}")
            e.printStackTrace()
        }
    }

    // method to create marker.
    private fun createMarker(latLng: LatLng, zoom: Float, title: String) {
        mGoogleMap.addMarker(
            MarkerOptions().position(latLng).title(title)
        )
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    // method to draw a path between two locations coordinates
    private fun drawPath(currentLocationLatLng: LatLng, destinationLocationLatLng: LatLng) {
        val polylineOptions = PolylineOptions()
        polylineOptions.add(currentLocationLatLng).add(destinationLocationLatLng)
        mGoogleMap.addPolyline(polylineOptions)
    }
}