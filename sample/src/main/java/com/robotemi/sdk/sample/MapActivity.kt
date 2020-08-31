package com.robotemi.sdk.sample

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.robotemi.sdk.Robot
import kotlinx.android.synthetic.main.activity_map.*
import kotlin.concurrent.thread
import kotlin.math.roundToInt


class MapActivity : AppCompatActivity() {

    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        Robot.getInstance().hideTopBar()
        textViewMapJson.movementMethod = ScrollingMovementMethod()
        ibBack.setOnClickListener { finish() }

        thread {
            val mapDataModel = Robot.getInstance().getMapData() ?: return@thread
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
                imageViewMap.setImageBitmap(bitmap)
            }
        }
    }

    override fun onDestroy() {
        bitmap?.recycle()
        super.onDestroy()
    }
}