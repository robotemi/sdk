package com.robotemi.sdk.map

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test


class MapImageTest {

    @Test
    fun serialize() {
        val mapImage = MapImage(
            "id",
            2,
            4,
            "dt",
            listOf(1, 2, 3, 4, 5, 6, 7),
            null
        )
        val ss = """{"type_id":"id","rows":2,"cols":4,"dt":"dt","data":[1,2,3,4,5,6,7]}"""
        println(Gson().toJson(mapImage))
        val mapImage2 = Gson().fromJson<MapImage>(ss, MapImage::class.java)
        println(mapImage2)
        assertEquals(mapImage, mapImage2)
    }

}