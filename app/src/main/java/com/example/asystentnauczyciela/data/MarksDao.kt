package com.example.asystentnauczyciela.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.asystentnauczyciela.model.Marks

@Dao
interface MarksDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addParticipant(marks: Marks)

    @Delete
    suspend fun deleteParticipant(marks: Marks)

    @Query("DELETE FROM marks_table")
    suspend fun deleteAllParticipants()

    @Query("SELECT * FROM marks_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Marks>>

}