package com.mostovoi.trackingclient

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap
    private lateinit var marker : Marker
    private var lat: Double = 0.0
    private var lon: Double = 0.0

    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)


        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        floatingActionButton.setOnClickListener {
            when (ACTIVATED) {
                0 -> {
                    val intent = Intent(this@MainActivity, AddUserActivity::class.java)
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(this@MainActivity, AddTracingObjectActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    Toast.makeText(this, "Not Allowed code", Toast.LENGTH_SHORT).show()
                }
            }


        }
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION

            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        setUpLocationListener()
                        Toast.makeText(this@MainActivity, "Permission granted", Toast.LENGTH_SHORT)
                            .show()

                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    Toast.makeText(this@MainActivity, "Permission Not granted", Toast.LENGTH_SHORT)
                        .show()
                }
            }).onSameThread()
            .check()
    }

    @SuppressLint("MissingPermission")
    private fun setUpLocationListener() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // for getting the current location update after every 2 seconds with high accuracy
        val locationRequest = LocationRequest.create().apply(){
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    for (location in locationResult.locations) {
                        lat = location.latitude
                        lon = location.longitude
                    }
//                    Toast.makeText(this@MainActivity, "$lat , $lon", Toast.LENGTH_SHORT)
//                        .show()

                    ServerCommunications.getRequest ("https://google.com", {
                        Toast.makeText(this@MainActivity,it ,Toast.LENGTH_LONG).show()
                    })
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat,lon)))
                    marker.position = LatLng(lat,lon)
                    // Few more things we can do here:
                    // For example: Update the location of user on server
                }
            },
            Looper.myLooper()!!
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        marker = mMap.addMarker(
            MarkerOptions()
                .position(LatLng(lat,lon))
                .title("Marker")
        )!!
    }


    companion object {
        //Mock for registration
        // 1 - activated 2 - not activated
        const val ACTIVATED = 0
    }
}