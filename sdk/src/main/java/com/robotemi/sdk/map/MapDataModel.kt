package com.robotemi.sdk.map

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class MapDataModel(
    @SerializedName("Map_Image")
    var mapImage: MapImage,
    var mapId: String = "",
    var mapInfo: MapInfo = MapInfo(),
    var virtualWalls: MutableList<Layer> = mutableListOf(),
    var greenPaths: MutableList<Layer> = mutableListOf(),
    var locations: MutableList<Layer> = mutableListOf(),
    var mapName: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(MapImage::class.java.classLoader)!!,
        parcel.readString() ?: "",
        parcel.readParcelable(MapInfo::class.java.classLoader)!!,
        parcel.createTypedArrayList(Layer) ?: mutableListOf(),
        parcel.createTypedArrayList(Layer) ?: mutableListOf(),
        parcel.createTypedArrayList(Layer) ?: mutableListOf()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(mapImage, flags)
        parcel.writeString(mapId)
        parcel.writeParcelable(mapInfo, flags)
        parcel.writeTypedList(virtualWalls)
        parcel.writeTypedList(greenPaths)
        parcel.writeTypedList(locations)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<MapDataModel> {
        override fun createFromParcel(parcel: Parcel): MapDataModel {
            return MapDataModel(parcel)
        }

        override fun newArray(size: Int): Array<MapDataModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class MapImage(
    @SerializedName("type_id")
    val typeId: String,
    val rows: Int,
    val cols: Int,
    val dt: String,
    val data: List<Int>
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readInt(),
        source.readInt(),
        source.readString()!!,
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(typeId)
        writeInt(rows)
        writeInt(cols)
        writeString(dt)
        writeList(data)
    }

    companion object CREATOR : Parcelable.Creator<MapImage> {
        override fun createFromParcel(parcel: Parcel): MapImage {
            return MapImage(parcel)
        }

        override fun newArray(size: Int): Array<MapImage?> {
            return arrayOfNulls(size)
        }
    }
}

data class MapInfo(
    @SerializedName("height") val height: Int = 0,
    @SerializedName("width") val width: Int = 0,
    @SerializedName("origin_x") val originX: Float = 0f,
    @SerializedName("origin_y") val originY: Float = 0f,
    @SerializedName("resolution") val resolution: Float = 0f
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(height)
        parcel.writeInt(width)
        parcel.writeFloat(originX)
        parcel.writeFloat(originY)
        parcel.writeFloat(resolution)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MapInfo> {
        override fun createFromParcel(parcel: Parcel): MapInfo {
            return MapInfo(parcel)
        }

        override fun newArray(size: Int): Array<MapInfo?> {
            return arrayOfNulls(size)
        }
    }
}

data class Layer(
    @SerializedName("layer_creation_universal_time") val layerCreationUTC: Int,
    @SerializedName("layer_category") val layerCategory: Int,
    @SerializedName("layer_id") val layerId: String = "",
    @SerializedName("layer_thickness") val layerThickness: Float,
    @SerializedName("layer_status") val layerStatus: Int,
    @SerializedName("layer_poses") val layerPoses: List<LayerPose>?,
    @SerializedName("layer_direction") val layerDirection: Int = 0, // added in sprint 132 for one-way virtual wall, value can be -1, 0, 1.
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readInt(),
        parcel.createTypedArrayList(LayerPose)
    )

    override fun toString(): String {
        val category = when (layerCategory) {
            GREEN_PATH -> "greenPath"
            VIRTUAL_WALL -> "virtualWall"
            LOCATION -> "location"
            else -> "unknown"
        }

        val status = when (layerStatus) {
            STATUS_UPDATE -> "UPDATE"
            STATUS_ADD_POSE -> "ADD_POSE"
            STATUS_DELETE -> "DELETE"
            STATUS_CURRENT -> "CURRENT"
            else -> "unknown"
        }

        return """
            
            { "layerCategory": $category, "layerId": $layerId, "layerCreationUTC": $layerCreationUTC, "layerStatus": $status, "layerThickness": $layerThickness, "layerPoses": $layerPoses }
        """.trimIndent()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(layerCreationUTC)
        parcel.writeInt(layerCategory)
        parcel.writeString(layerId)
        parcel.writeFloat(layerThickness)
        parcel.writeInt(layerStatus)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Layer> {
        override fun createFromParcel(parcel: Parcel): Layer {
            return Layer(parcel)
        }

        override fun newArray(size: Int): Array<Layer?> {
            return arrayOfNulls(size)
        }
    }
}

const val GREEN_PATH = 0
const val VIRTUAL_WALL = 3
const val LOCATION = 4

const val STATUS_CURRENT = 0
const val STATUS_UPDATE = 1
const val STATUS_ADD_POSE = 2
const val STATUS_DELETE = 3

const val MAP_ID = "map_id"
const val MAP_INFO = "map_info"
const val MAP_ELEMENTS = "map_elements"
const val MAP_NAME = "map_name"


@Keep
data class LayerPose(
    @SerializedName("x") val x: Float,
    @SerializedName("y") val y: Float,
    @SerializedName("theta") val theta: Float
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(x)
        parcel.writeFloat(y)
        parcel.writeFloat(theta)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<LayerPose> {
        override fun createFromParcel(parcel: Parcel): LayerPose {
            return LayerPose(parcel)
        }

        override fun newArray(size: Int): Array<LayerPose?> {
            return arrayOfNulls(size)
        }
    }
}