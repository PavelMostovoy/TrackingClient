package com.mostovoi.trackingclient

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)

        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)

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
        // END
    }
    companion object{
        //Mock for registration
        // 1 - activated 2 - not activated
        const val ACTIVATED = 0
    }
}