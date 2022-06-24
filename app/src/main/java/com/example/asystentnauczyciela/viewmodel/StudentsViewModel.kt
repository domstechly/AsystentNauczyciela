package com.example.asystentnauczyciela.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.asystentnauczyciela.data.StudentsDatabase
import com.example.asystentnauczyciela.repository.StudentsRepository
import com.example.asystentnauczyciela.model.Students
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentsViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Students>>
    private val repository: StudentsRepository

    init {
        val studentsDao = StudentsDatabase.getDatabase(
            application
        ).studentsDao()
        repository = StudentsRepository(studentsDao)
        readAllData = repository.readAllData
    }

    fun addStudents(students: Students){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStudents(students)
        }
    }

    fun updateStudents(students: Students){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateStudents(students)
        }
    }

    fun deleteStudents(students: Students){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteStudents(students)
        }
    }

    fun deleteAllStudents(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllStudents()
        }
    }

}