package com.mostovoi.trackingclient

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)

        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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