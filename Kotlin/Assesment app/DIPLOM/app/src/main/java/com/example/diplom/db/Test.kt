package com.example.diplom.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["test_name"],
    unique = true)])
data class Test(
    @PrimaryKey(autoGenerate = true) val testId: Int,
    @ColumnInfo(name = "test_name") var testName: String,
    @ColumnInfo(name = "questions_ids") var questions: String,
)
{
    constructor(testName: String, qList : String): this(0, testName, qList)
}