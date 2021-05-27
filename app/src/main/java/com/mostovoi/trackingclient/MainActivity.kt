package com.mostovoi.trackingclient

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mFusedLocationClient : FusedLocationProviderClient

    override fun onStart() {
        super.onStart()
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION
//                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
//                        DO something when permissions granted
                        Toast.makeText(this@MainActivity,"Permission granted",Toast.LENGTH_SHORT).show()

                    }
                }
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    Toast.makeText(this@MainActivity,"Permission Not granted",Toast.LENGTH_SHORT).show()
                }
            }).onSameThread()
            .check()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)

        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // TODO (Step 1: Adding an click event to Fab button and calling the AddHappyPlaceActivity.)
        // START
        // Setting an click event for Fab Button and calling the AddHappyPlaceActivity.
        floatingActionButton.setOnClickListener {
            when (ACTIVATED) {
                0 -> {
                    val intent = Intent(this@MainActivity, AddUserActivity::class.java)
                    startActivity(intent)}
                1 -> {
                    val intent = Intent(this@MainActivity, AddTracingObjectActivity::class.java)
                    startActivity(intent)
                }
                else -> {Toast.makeText(this,"Not Allowed code",Toast.LENGTH_SHORT).show()}
            }


        }

    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    companion object{
        //Mock for registration
        // 1 - activated 2 - not activated
        const val ACTIVATED = 0
    }
}