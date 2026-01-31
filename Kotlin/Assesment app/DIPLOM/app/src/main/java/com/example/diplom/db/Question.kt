package com.example.diplom.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true) var questionId: Int,
    @ColumnInfo(name = "questionText") var questionText : String,
    @ColumnInfo(name = "allAnswers") var allAnswers: List<String>,
    @ColumnInfo(name = "trueAnswer") var trueAnswer: String,
    @ColumnInfo(name = "answerType") val answerType: Int
)
{
    constructor(questionText: String, allAnswers: List<String>, trueAnswer: String,
                answerType: Int): this(0, questionText, allAnswers,
        trueAnswer, answerType)
}
