package com.example.diplom.db
import androidx.room.*

data class UserWithCompletedTests (
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "testId",
    )
    val tests: List<Test>
)