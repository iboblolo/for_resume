package com.example.diplom.db

import androidx.room.TypeConverter

object AnsConverter {
    @TypeConverter
    fun toString(ansList: List<String>): String
    {
        val stringList = mutableListOf<String>()

        ansList.forEach {
            stringList.add(it)
        }
        return stringList.joinToString("<;>")
    }

    @TypeConverter
    fun toAnsList(str: String): List<String>
    {
        return str.split("<;>")
    }
}
