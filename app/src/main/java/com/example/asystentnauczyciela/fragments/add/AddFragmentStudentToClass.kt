package com.example.asystentnauczyciela.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.fragments.list.ListAdapterAddStudent
import com.example.asystentnauczyciela.fragments.list.ListAdapterStudent
import com.example.asystentnauczyciela.fragments.list.ListFragmentParticipantsArgs
import com.example.asystentnauczyciela.fragments.list.ListFragment
import com.example.asystentnauczyciela.model.Participants
import com.example.asystentnauczyciela.model.User
import com.example.asystentnauczyciela.viewmodel.ParticipantsViewModel
import com.example.asystentnauczyciela.viewmodel.StudentsViewModel
import kotlinx.android.synthetic.main.custom_row_studentsinclass.*
import kotlinx.android.synthetic.main.fragment_add_student_toclass.*
import kotlinx.android.synthetic.main.fragment_add_student_toclass.view.*
import kotlinx.android.synthetic.main.fragment_list_addstudent.view.*
import android.app.AlertDialog
import android.view.*
import com.example.asystentnauczyciela.fragments.update.UpdateFragmentAddStudent
import com.example.asystentnauczyciela.fragments.update.UpdateFragmentAddStudentArgs
import kotlinx.android.synthetic.main.fragment_add_student_toclass.view.*

class AddFragmentStudentToClass : Fragment() {

    private lateinit var mParticipantsViewModel: ParticipantsViewModel
    private lateinit var mStudentsViewModel: StudentsViewModel
    private val args by navArgs<AddFragmentStudentToClassArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_student_toclass, container, false)
        val view2 = inflater.inflate(R.layout.fragment_list_addstudent, container, false)

        val adapter = ListAdapterAddStudent()
        val recyclerView = view.recyclerviewStudentsAddClass
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        mParticipantsViewModel = ViewModelProvider(this).get(ParticipantsViewModel::class.java)
        mStudentsViewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)
        mStudentsViewModel.readAllData.observe(viewLifecycleOwner, Observer { students ->
            adapter.setData(students)
        })


        return view
    }

    private fun insertDataToDatabase() {
       /* val firstName = addFirstName_et.text.toString()
        val lastName = addLastName_et.text.toString()
        val id_student = addID_et.text.toString()

        if(inputCheck(firstName, lastName, id_student)){
            // Create User Object
            val student = Students(
                0,
                firstName,
                lastName,
                Integer.parseInt(id_student.toString())
            )
            // Add Data to Database
            mStudentsViewModel.addStudents(student)
            Toast.makeText(requireContext(), "Pomyślnie dodano!", Toast.LENGTH_LONG).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragmentStudent_to_listFragmentStudent)
        }else{
            Toast.makeText(requireContext(), "Wprowadź poprawne dane.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, id_student: String): Boolean{
        return (firstName.isNotEmpty()&&lastName.isNotEmpty()&&id_student.isNotEmpty()&&id_student.length<10)
    }*/

}}