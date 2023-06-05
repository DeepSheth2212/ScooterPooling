package com.example.scooterpooling

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class RiderPickupActivity : AppCompatActivity() {
    private lateinit var mapView : MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rider_pickup)

        val ctx = applicationContext
        Configuration.getInstance().load(ctx,PreferenceManager.getDefaultSharedPreferences(ctx))

        mapView = findViewById(R.id.map_view)
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        // Set initial map view configuration
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(12.0)
        mapView.controller.setCenter(GeoPoint(23.033863, 72.585022)) // Set initial map center

        // Add a marker to the map
        val marker = Marker(mapView)
        marker.position = GeoPoint(23.033863, 72.585022) // Set marker position
        marker.title = "Ahmedabad" // Set marker title (optional)
        marker.snippet = "The city of dreams" // Set marker snippet (optional)
        mapView.overlays.add(marker) // Add the marker to the map overlays

        mapView.invalidate()
    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}