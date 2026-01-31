package com.example.diplom.db
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface DBDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>
    @Query("SELECT * FROM user WHERE userId IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>
    @Query("SELECT * FROM question WHERE questionId IN (:qIds)")
    fun loadQuestionsByIds(qIds: List<Int>): List<Question>
    @Query("SELECT * FROM user WHERE login = :l")
    fun loadUserByLogin(l : String): User?
    @Update
    fun updateTest(test: Test)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)
    @Query("SELECT * FROM Test")
    fun getAllTests(): List<Test>
    @Query("SELECT * FROM CompletedTest where userCompTest = :uId")
    fun getUserCompletedTests(uId : Int): List<CompletedTest>
    @Query("SELECT * FROM Test WHERE testId = :tId")
    fun getTest(tId: Int): Test
    @Query("SELECT * FROM Test WHERE test_name = :tName")
    fun getTestByLogin(tName: String): Test?@Transaction
    @Query("SELECT * FROM Test")
    fun getQuestionsWithTest(): List<QuestionsByTest>
    //@Insert
    //fun insertTestWithQuestions(tWithQ: QuestionsByTest)
    //@Update
    //fun updateTestWithQuestions(tWithQ: QuestionsByTest)
    @Insert
    fun insertAll(vararg users: User)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompletedTest(completedTest: CompletedTest)
    @Insert
    fun insertQuestion(questions : Question) : Long
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun  insertTest(test: Test)
    @Delete
    fun delete(user: User)
    @Delete
    fun deleteTest(test: Test)
}