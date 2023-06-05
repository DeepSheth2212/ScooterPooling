package com.example.scooterpooling

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.bonuspack.location.GeocoderNominatim
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


class MapSearchActivity : AppCompatActivity() {

    private lateinit var mapView : MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_search)

        val intent = intent
        val from = intent.getStringExtra("FROM_LOCATION").toString()
        val to = intent.getStringExtra("TO_LOCATION").toString()

        //val ctx = applicationContext
        //Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        mapView = findViewById(R.id.map_view)
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(12.0)

        if(from!=null && to != null &&!from.isEmpty() && !to.isEmpty()){
            lateinit var startPoint: GeoPoint
            lateinit var endPoint : GeoPoint

            CoroutineScope(Dispatchers.Main).launch {
                val geocoder = GeocoderNominatim("UserAgent")
                withContext(Dispatchers.IO){
                    val from_address = geocoder.getFromLocationName(from,1)
                    val to_address = geocoder.getFromLocationName(to,1)

                    if(from_address!=null && to_address!=null && !from_address.isEmpty() && !to_address.isEmpty()){
                        startPoint = GeoPoint(from_address[0].latitude, from_address[0].longitude)
                        endPoint = GeoPoint(to_address[0].latitude, to_address[0].longitude)
                    }
                    else{
                        Toast.makeText(applicationContext,"There might be some error there",Toast.LENGTH_SHORT).show()
                    }
                }
                val roadManager = OSRMRoadManager(this@MapSearchActivity,"UserAgent")
                val waypoints = ArrayList<GeoPoint>()
                waypoints.add(startPoint)
                waypoints.add(endPoint)
                val road = roadManager.getRoad(waypoints)
                val roadOverlay = RoadManager.buildRoadOverlay(road)
                mapView.overlays.add(roadOverlay)
                mapView.invalidate()
            }
        }
        mapView.invalidate()
    }


}
