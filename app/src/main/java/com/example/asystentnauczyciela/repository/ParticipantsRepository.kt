package com.example.asystentnauczyciela.repository

import androidx.lifecycle.LiveData
import com.example.asystentnauczyciela.data.ParticipantsDao
import com.example.asystentnauczyciela.model.Participants

class ParticipantsRepository(private val participantsDao: ParticipantsDao) {

    val readAllData: LiveData<List<Participants>> = participantsDao.readAllData()

    suspend fun addParticipant(participants: Participants){
        participantsDao.addParticipant(participants)
    }

    suspend fun deleteParticipant(participants: Participants){
        participantsDao.deleteParticipant(participants)
    }

    suspend fun deleteAllParticipants(){
        participantsDao.deleteAllParticipants()
    }

}