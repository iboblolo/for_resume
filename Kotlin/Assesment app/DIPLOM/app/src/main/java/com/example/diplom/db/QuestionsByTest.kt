package com.example.diplom.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class QuestionsByTest(
    @Embedded val test: Test,
    @Relation(
        parentColumn = "testId",
        entityColumn = "questionId",
        associateBy = Junction(TestQuestionCrossRef::class))
    val questions: List<Question>
)