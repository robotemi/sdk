package com.robotemi.sdk.sample

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.async
import org.ros.temiros.Pose
import timber.log.Timber

class RosMapActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMapRosBinding

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
        Log.d("RosMapActivity", "onCreate called, initializing map view...")
        lifecycleScope.async {
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

//                    updateMapCanvasColor("FF384C61")
                    updateMapCanvasColor("FF0000FF")

                    updateGroundAndWallColor(
//                        hexGround = "FFACD3F2",
                        hexGround = "FFFF0000",
//                        hexWall = "FF000000"
                        hexWall = "FFFFFF00"
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
                                    val locationData = LocationData(
                                        name = layer.layerId,
                                        pose = pose,
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
        }

        robot.addOnCurrentPositionChangedListener(object : OnCurrentPositionChangedListener {
            override fun onCurrentPositionChanged(position: Position) {
                binding.rosMapView.renderRobot(robotPose = Pose(position.x, position.y, position.yaw))
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

}
