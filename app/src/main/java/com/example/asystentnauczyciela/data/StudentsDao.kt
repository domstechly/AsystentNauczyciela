package com.example.asystentnauczyciela.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.asystentnauczyciela.model.Participants
import com.example.asystentnauczyciela.model.Students

@Dao
interface StudentsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStudents(students: Students)

    @Update
    suspend fun updateStudents(students: Students)

    @Delete
    suspend fun deleteStudents(students: Students)

    @Query("DELETE FROM students_table")
    suspend fun deleteAllStudents()

    @Query("SELECT * FROM students_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Students>>

    @Query("SELECT * FROM students_table WHERE id=:id_wybrane ORDER BY id ASC")
    fun readSelectData(id_wybrane: Int): LiveData<List<Students>>
}