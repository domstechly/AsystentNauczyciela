package com.example.asystentnauczyciela.fragments.update

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextUtils
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Students
import com.example.asystentnauczyciela.viewmodel.StudentsViewModel
import kotlinx.android.synthetic.main.fragment_add_student.*
import kotlinx.android.synthetic.main.fragment_add_student.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.fragment_update_addstudent.view.*
import kotlinx.android.synthetic.main.fragment_update_student.*
import kotlinx.android.synthetic.main.fragment_update_student.view.*
import java.lang.Exception

class UpdateFragmentStudent : Fragment() {

    private val args by navArgs<UpdateFragmentStudentArgs>()

    private lateinit var mStudentsViewModel: StudentsViewModel
    private lateinit var updbtn_record_FirstName : ImageView
    private lateinit var updbtn_record_LastName : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_student, container, false)

        mStudentsViewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)
        updbtn_record_FirstName = view.findViewById(R.id.updbtn_record_FirstName)
        updbtn_record_LastName = view.findViewById(R.id.updbtn_record_LastName)

        view.updFirstName_et.setText(args.currentStudent.firstName)
        view.updLastName_et.setText(args.currentStudent.lastName)
        view.updID_et.setText(args.currentStudent.id_student.toString())

        view.update_btn_student.setOnClickListener {
            updateItem()
        }

        view.updbtn_record_FirstName.setOnClickListener(){
            recordSpeech()
        }
        view.updbtn_record_LastName.setOnClickListener(){
            recordSpeech2()
        }
        // Add menu
        setHasOptionsMenu(true)

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
                updFirstName_et.setText(text[0])
            }
        }
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                updLastName_et.setText(text[0])
            }
        }
    }
    private fun updateItem() {
        val firstName = updFirstName_et.text.toString()
        val lastName = updLastName_et.text.toString()
        val id_student = updID_et.text.toString()

       if (inputCheck(firstName, lastName, id_student)) {
            // Create User Object
            val updateStudent = Students(args.currentStudent.id, firstName, lastName, Integer.parseInt(id_student))
            // Update Current User
            mStudentsViewModel.updateStudents(updateStudent)
            Toast.makeText(requireContext(), "Pomyślnie zaktualizowano!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_updateFragmentStudent_to_listFragmentStudent)
        } else {
            Toast.makeText(requireContext(), "Wprowadź poprawne dane.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, id_student: String): Boolean{
        return (firstName.isNotEmpty()&&lastName.isNotEmpty()&&id_student.isNotEmpty()&&id_student.length<10)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteStudent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteStudent() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Tak") { _, _ ->
            mStudentsViewModel.deleteStudents(args.currentStudent)
            Toast.makeText(
                requireContext(),
                "Pomyślnie usunięto: ${args.currentStudent.firstName}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragmentStudent_to_listFragmentStudent)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Usunąć ${args.currentStudent.firstName}?")
        builder.setMessage("Napewno chcesz usunąć ${args.currentStudent.firstName}?")
        builder.create().show()
    }
}