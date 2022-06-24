package com.example.asystentnauczyciela.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.asystentnauczyciela.data.ParticipantsDatabase
import com.example.asystentnauczyciela.repository.ParticipantsRepository
import com.example.asystentnauczyciela.model.Participants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParticipantsViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Participants>>
    private val repository: ParticipantsRepository

    init {
        val participantsDao = ParticipantsDatabase.getDatabase(
            application
        ).participantsDao()
        repository = ParticipantsRepository(participantsDao)
        readAllData = repository.readAllData
    }

    fun addParticipants(participants: Participants){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addParticipant(participants)
        }
    }

    fun deleteParticipants(participants: Participants){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteParticipant(participants)
        }
    }

    fun deleteAllParticipants(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllParticipants()
        }
    }

}