package com.example.diplom.db
import androidx.room.*

@Database(entities = [User::class, Test::class, Question::class,
                     CompletedTest::class, TestQuestionCrossRef::class], version = 1)
@TypeConverters(AnsConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dbDao(): DBDao
}