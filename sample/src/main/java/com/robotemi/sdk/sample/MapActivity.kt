package com.robotemi.sdk.sample

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.robotemi.sdk.Robot
import kotlinx.android.synthetic.main.activity_map.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.roundToInt


class MapActivity : AppCompatActivity() {

    private var bitmap: Bitmap? = null

    private val singleThreadExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        Robot.getInstance().hideTopBar()
        textViewMapJson.movementMethod = ScrollingMovementMethod()
        ibBack.setOnClickListener { finish() }
        imageViewMap.setOnClickListener { refreshMap() }
        refreshMap()
    }

    private fun refreshMap() {
        progressBar.visibility = View.VISIBLE
        singleThreadExecutor.execute {
            val mapDataModel = Robot.getInstance().getMapData() ?: return@execute
            val mapImage = mapDataModel.mapImage
            Log.i("Map", mapImage.toString())
            bitmap = Bitmap.createBitmap(
                mapImage.data.map { Color.argb((it * 2.55).roundToInt(), 0, 0, 0) }
                    .toIntArray(),
                mapImage.cols,
                mapImage.rows,
                Bitmap.Config.ARGB_8888
            )
            runOnUiThread {
                progressBar.visibility = View.GONE
                imageViewMap.setImageBitmap(bitmap)
            }
        }
    }

    override fun onDestroy() {
        bitmap?.recycle()
        singleThreadExecutor.shutdownNow()
        super.onDestroy()
    }
}