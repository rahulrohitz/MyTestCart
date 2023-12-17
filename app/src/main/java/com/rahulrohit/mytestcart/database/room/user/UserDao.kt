    package com.rahulrohit.mytestcart.database.room.user

    import androidx.room.Dao
    import androidx.room.Insert
    import androidx.room.OnConflictStrategy
    import androidx.room.Query

    @Dao
    interface UserDao {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(user: User)

        @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
        suspend fun getUser(email: String, password: String): User?

        @Query("SELECT * FROM user_table WHERE email = :email")
        suspend fun getUserByEmail(email: String): User?
    }
