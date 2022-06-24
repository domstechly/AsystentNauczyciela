package com.example.asystentnauczyciela.fragments.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Students
import com.example.asystentnauczyciela.viewmodel.StudentsViewModel
import kotlinx.android.synthetic.main.custom_row_student.*
import kotlinx.android.synthetic.main.fragment_add_mark.*
import kotlinx.android.synthetic.main.fragment_add_mark.view.*
import kotlinx.android.synthetic.main.fragment_add_student.*
import kotlinx.android.synthetic.main.fragment_add_student.view.*
import java.lang.Exception

class AddFragmentStudent : Fragment() {

    private lateinit var mStudentsViewModel: StudentsViewModel
    private lateinit var btn_record_FirstName : ImageView
    private lateinit var btn_record_LastName : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_student, container, false)
        btn_record_FirstName = view.findViewById(R.id.btn_record_FirstName)
        btn_record_LastName = view.findViewById(R.id.btn_record_LastName)

        mStudentsViewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)

        view.add_btn_student.setOnClickListener {
            insertDataToDatabase()
        }

        view.btn_record_FirstName.setOnClickListener(){
            recordSpeech()
        }
        view.btn_record_LastName.setOnClickListener(){
            recordSpeech2()
        }
        return view
    }
    private fun recordSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "pl-PL")
        try {
            startActivityForResult(intent, 1)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Twoje urządzenie nie wspiera funkcji głosowej",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun recordSpeech2() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "pl-PL")
        try {
            startActivityForResult(intent, 2)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Twoje urządzenie nie wspiera funkcji głosowej",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                addFirstName_et.setText(text[0])
            }
        }
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                addLastName_et.setText(text[0])
            }
        }
    }
    private fun insertDataToDatabase() {
        val firstName = addFirstName_et.text.toString()
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
    }

}