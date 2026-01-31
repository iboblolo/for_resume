package com.example.diplom.db

import androidx.room.Entity

@Entity(primaryKeys = ["testId", "questionId"])
data class TestQuestionCrossRef (
    val testId : Int,
    val questionId: Int
)
