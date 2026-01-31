package com.example.diplom.db
import androidx.room.*

@Entity(indices = [Index(value = ["login"],
    unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int,
    @ColumnInfo(name = "login") var login: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "first_name") var firstName: String?,
    @ColumnInfo(name = "last_name") var lastName: String?,
    @ColumnInfo(name = "isTeacher") val isTeacher: Boolean

)
{
    constructor(login: String, password: String, firstName: String,
                lastName: String, isTeacher: Boolean, ): this(0, login, password,
                firstName, lastName, isTeacher)

}
