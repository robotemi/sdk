package com.robotemi.sdk.sample

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.robotemi.ros.data.GridData
import com.robotemi.ros.data.LocationData
import com.robotemi.ros.data.PathData
import com.robotemi.ros.data.PathType
import com.robotemi.ros.renderLocations
import com.robotemi.ros.renderMap
import com.robotemi.ros.renderPaths
import com.robotemi.ros.renderRobot
import com.robotemi.ros.setDebug
import com.robotemi.ros.setLocationClickCallback
import com.robotemi.ros.setPathClickCallback
import com.robotemi.ros.setPoseClickCallback
import com.robotemi.ros.updateGroundAndWallColor
import com.robotemi.ros.updateMapBackgroundColor
import com.robotemi.ros.updateMapCanvasColor
import com.robotemi.sdk.Robot
import com.robotemi.sdk.map.GREEN_PATH
import com.robotemi.sdk.map.LOCATION
import com.robotemi.sdk.map.Layer
import com.robotemi.sdk.map.MAP_ERASER
import com.robotemi.sdk.map.MapDataModel
import com.robotemi.sdk.map.VIRTUAL_WALL
import com.robotemi.sdk.navigation.listener.OnCurrentPositionChangedListener
import com.robotemi.sdk.navigation.model.Position
import com.robotemi.sdk.sample.databinding.ActivityMapRosBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import org.ros.temiros.Pose
import timber.log.Timber

class RosMapActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMapRosBinding


    private var isCtrlPressed = false
    private val keyStates = mutableMapOf(
        KeyEvent.KEYCODE_W to false,
        KeyEvent.KEYCODE_S to false,
        KeyEvent.KEYCODE_A to false,
        KeyEvent.KEYCODE_D to false
    )
    private var isSending = false
    private val handler = Handler(Looper.getMainLooper())



    private val robot: Robot
        get() = Robot.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapRosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        binding.ibBack.setOnClickListener {
            finish()
        }

        binding.btnskidStartJoy.setOnClickListener {
            startSkidJoyControl()
        }

        binding.btnStopskidJoy.setOnClickListener {
            stopSkidJoyControl()
        }

        lifecycleScope.launch {
            while (isActive) {
                async(Dispatchers.IO) {
                    Log.d("RosMapActivity", "Fetching map data...")
                    val map = getMap()
                    val mapElements = getMapElements()
                    if (map != null) {
                        Log.d("RosMapActivity", "Map data retrieved successfully: ${map.mapInfo}")
                        Log.d("RosMapActivity", "Map data retrieved successfully: ${map.mapImage.dataBase64?.length}")
                        Log.d("RosMapActivity", "Map data retrieved successfully: ${map.mapImage.data?.size}")
                        val grid = GridData(
                            resolution = map.mapInfo.resolution,
                            height = map.mapInfo.height,
                            width = map.mapInfo.width,
                            data = map.mapImage.dataBase64 ?: "",
                            originX = map.mapInfo.originX.toDouble(),
                            originY = map.mapInfo.originY.toDouble(),
                        )
                        binding.rosMapView.apply {

                        updateMapCanvasColor("FF384C61")
//                            updateMapCanvasColor("FF0000FF")

                            updateGroundAndWallColor(
                            hexGround = "FFACD3F2",
//                                hexGround = "FFFF0000",
                            hexWall = "FF000000"
//                                hexWall = "FFFFFF00"
                            )

                            renderMap(
                                grid = grid,
                                this@async
                            ) {
                                Log.d("RosMapActivity", "Map rendered successfully")
                                setDebug(true)

                                val paths = mutableListOf<PathData>()
                                val locations = mutableListOf<LocationData>()
                                mapElements.forEach { layer ->
                                    when (layer.layerCategory) {
                                        GREEN_PATH,
                                        VIRTUAL_WALL -> {
                                            val pathType = when (layer.layerCategory) {
                                                GREEN_PATH -> PathType.GREEN_PATH
                                                else -> PathType.VIRTUAL_WALL
                                            }
                                            val pathData = PathData(
                                                id = layer.layerId,
                                                size = layer.layerPoses?.size ?: 0,
                                                pathType = pathType,
                                                poses = (layer.layerPoses ?: listOf()).map {
                                                    Pose(
                                                        x = it.x,
                                                        y = it.y,
                                                        z = it.theta
                                                    )
                                                },
                                                direction = layer.layerDirection
                                            )
                                            paths.add(pathData)
                                        }

                                        LOCATION -> {
                                            val pose = Pose(
                                                x = layer.layerPoses?.firstOrNull()?.x ?: 0.0f,
                                                y = layer.layerPoses?.firstOrNull()?.y ?: 0.0f,
                                                z = layer.layerPoses?.firstOrNull()?.theta ?: 0.0f,

                                                )
                                            val displayName = if (layer.layerId == "home base") {
//                                        "充電桩"
//                                        "充电桩"
                                                "ホームベース"
                                            } else {
                                                layer.layerId
                                            }
                                            val locationData = LocationData(
                                                name = layer.layerId,
                                                pose = pose,
                                                displayName = displayName,
                                            )
                                            locations.add(locationData)
                                        }

                                        MAP_ERASER -> {

                                        }
                                    }

                                }

                                setLocationClickCallback { locationData ->
                                    Log.d("RosMapActivity", "Location clicked: ${locationData.name}")
                                }

                                setPathClickCallback { pathData ->
                                    Log.d("RosMapActivity", "Path clicked: ${pathData.id}, size: ${pathData.size}, type: ${pathData.pathType}")
                                }

                                setPoseClickCallback { point, color ->
                                    Log.d("RosMapActivity", "Pose ${point.x}, ${point.y}, color $color")
                                }

                                renderPaths(paths.asSequence())
                                renderLocations(locations.asSequence())

                            }

//                    updateMapBackgroundColor("FF384C61")
//                    updateMapBackgroundColor("FF0000FF")
                        }


                    } else {
                        Log.w("RosMapActivity", "Map data is null")
                    }
                }.await()
                delay(1000)
            }
        }


        robot.addOnCurrentPositionChangedListener(object : OnCurrentPositionChangedListener {
            override fun onCurrentPositionChanged(position: Position) {
                binding.rosMapView.renderRobot(robotPose = Pose(position.x, position.y, position.yaw))
                Log.d("RosMapActivity", "X"+position.x+"Y"+position.y+"yaw"+position.yaw)
            }
        })
    }

    private fun getMap(): MapDataModel? {
        // Run the map retrieval on the Robot instance with coroutine
        return robot.getMapImage()
    }

    private fun getMapElements(): List<Layer> {
        return robot.getMapElements() ?: listOf()
    }

    private val sendRunnable = object : Runnable {
        override fun run() {
            val isW = keyStates[KeyEvent.KEYCODE_W] == true
            val isS = keyStates[KeyEvent.KEYCODE_S] == true
            val isA = keyStates[KeyEvent.KEYCODE_A] == true
            val isD = keyStates[KeyEvent.KEYCODE_D] == true

            var x = 0f
            var y = 0f

            when {
                isW && !isS -> x = 0.6f
                isS && !isW -> x = -0.6f
                else -> x = 0f
            }

            when {
                isA && !isD -> y = 1.0f
                isD && !isA -> y = -1.0f
                else -> y = 0f
            }

            if (x != 0f || y != 0f) {
                robot.skidJoy(x, y, !isCtrlPressed)
            } else {
                robot.stopMovement()
            }

            // 如果还有任意方向键按下，继续发送
            if (isW || isS || isA || isD) {
                handler.postDelayed(this, 100)
            } else {
                isSending = false
            }
        }
    }

    private fun startSkidJoyControl() {
        val rootLayout = binding.root

        rootLayout.isFocusable = true
        rootLayout.isFocusableInTouchMode = true
        rootLayout.requestFocus()

        val keyListener = View.OnKeyListener { _, keyCode, event ->
            val isKeyDown = event.action == KeyEvent.ACTION_DOWN

            if (listOf(KeyEvent.KEYCODE_Z, KeyEvent.KEYCODE_X, KeyEvent.KEYCODE_C).contains(keyCode) && isKeyDown) {
                robot.stopMovement()
                keyStates.keys.forEach { keyStates[it] = false }
                handler.removeCallbacks(sendRunnable)
                isSending = false
                return@OnKeyListener true
            }

            if (keyCode == KeyEvent.KEYCODE_CTRL_LEFT || keyCode == KeyEvent.KEYCODE_CTRL_RIGHT) {
                isCtrlPressed = isKeyDown
                return@OnKeyListener true
            }

            val isDirectionKey = listOf(KeyEvent.KEYCODE_W, KeyEvent.KEYCODE_S, KeyEvent.KEYCODE_A, KeyEvent.KEYCODE_D)
                .contains(keyCode)

            if (isDirectionKey) {
                keyStates[keyCode] = isKeyDown

                val anyKeyPressed = keyStates.values.any { it }

                if (anyKeyPressed && !isSending) {
                    isSending = true
                    handler.post(sendRunnable)
                }

                return@OnKeyListener true
            }

            false
        }

        rootLayout.setOnKeyListener(keyListener)

        Toast.makeText(this, "Skid Joy 控制已启用 (WSAD)", Toast.LENGTH_SHORT).show()
    }

    private fun stopSkidJoyControl() {
        handler.removeCallbacks(sendRunnable)
        robot.stopMovement()
        keyStates.keys.forEach { keyStates[it] = false }
        isCtrlPressed = false
        isSending = false
    }

    override fun onPause() {
        super.onPause()
        stopSkidJoyControl()
    }
}
