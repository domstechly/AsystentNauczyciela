package com.example.asystentnauczyciela.repository

import androidx.lifecycle.LiveData
import com.example.asystentnauczyciela.data.StudentsDao
import com.example.asystentnauczyciela.model.Students

class StudentsRepository(private val studentsDao: StudentsDao) {

    val readAllData: LiveData<List<Students>> = studentsDao.readSelectData(12)

    suspend fun addStudents(students: Students){
        studentsDao.addStudents(students)
    }

    suspend fun updateStudents(students: Students){
        studentsDao.updateStudents(students)
    }

    suspend fun deleteStudents(students: Students){
        studentsDao.deleteStudents(students)
    }

    suspend fun deleteAllStudents(){
        studentsDao.deleteAllStudents()
    }

}