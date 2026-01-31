package com.example.diplom.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompletedTest(
    @PrimaryKey(autoGenerate = true) val tId: Int,
    @ColumnInfo(name = "compTest") val completedTestId: Int,
    @ColumnInfo(name = "userCompTest") val userCompletedTestId: Int,
    @ColumnInfo(name = "result") val result: String,
    )
{
    constructor(compTest: Int, userCompTest : Int, result: String):
            this(0, compTest, userCompTest, result)
}