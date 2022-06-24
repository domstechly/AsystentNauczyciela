package com.example.asystentnauczyciela.repository

import androidx.lifecycle.LiveData
import com.example.asystentnauczyciela.data.MarksDao
import com.example.asystentnauczyciela.model.Marks

class MarksRepository(private val marksDao: MarksDao) {

    val readAllData: LiveData<List<Marks>> = marksDao.readAllData()

    suspend fun addParticipant(marks: Marks){
        marksDao.addParticipant(marks)
    }

    suspend fun deleteParticipant(marks: Marks){
        marksDao.deleteParticipant(marks)
    }

    suspend fun deleteAllParticipants(){
        marksDao.deleteAllParticipants()
    }

}