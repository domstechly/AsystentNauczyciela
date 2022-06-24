package com.example.asystentnauczyciela.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.asystentnauczyciela.data.MarksDatabase
import com.example.asystentnauczyciela.repository.MarksRepository
import com.example.asystentnauczyciela.model.Marks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MarksViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Marks>>
    private val repository: MarksRepository

    init {
        val marksDao = MarksDatabase.getDatabase(
            application
        ).marksDao()
        repository = MarksRepository(marksDao)
        readAllData = repository.readAllData
    }

    fun addParticipants(marks: Marks){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addParticipant(marks)
        }
    }

    fun deleteParticipants(marks: Marks){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteParticipant(marks)
        }
    }

    fun deleteAllParticipants(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllParticipants()
        }
    }

}