package com.example.asystentnauczyciela.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.asystentnauczyciela.model.Participants

@Dao
interface ParticipantsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addParticipant(participants: Participants)

    @Delete
    suspend fun deleteParticipant(participants: Participants)

    @Query("DELETE FROM participants_table")
    suspend fun deleteAllParticipants()

    @Query("SELECT * FROM participants_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Participants>>

    @Query("SELECT * FROM participants_table WHERE id_class=:id_wybrane ORDER BY id ASC")
    fun readSelectData(id_wybrane: Int): LiveData<List<Participants>>

}