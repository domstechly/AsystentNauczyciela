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
import com.example.asystentnauczyciela.model.Marks
import com.example.asystentnauczyciela.model.Students
import com.example.asystentnauczyciela.viewmodel.MarksViewModel
import com.example.asystentnauczyciela.viewmodel.StudentsViewModel
import kotlinx.android.synthetic.main.custom_row_student.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_add_mark.*
import kotlinx.android.synthetic.main.fragment_add_mark.view.*
import kotlinx.android.synthetic.main.fragment_add_student.*
import kotlinx.android.synthetic.main.fragment_add_student.view.*
import java.lang.Exception

class AddFragmentMark : Fragment() {

    private lateinit var mMarksViewModel: MarksViewModel
    private lateinit var btn_record_addmark: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_mark, container, false)

        btn_record_addmark = view.findViewById(R.id.btn_record_addmark)
        mMarksViewModel = ViewModelProvider(this).get(MarksViewModel::class.java)

        view.add_btn_mark.setOnClickListener {
            insertDataToDatabase()
        }

        view.btn_record_addmark.setOnClickListener(){
            recordSpeech()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                addDesc_et.setText(text[0])
            }
        }
    }
    private fun insertDataToDatabase() {
        val description = addDesc_et.text.toString()
        val grade = addMark_et.text.toString()

        if(inputCheck(description, grade)){
            // Create User Object
            val marks = Marks(
                0,
                idstudent = 1,
                desc = addDesc_et.text.toString(),
                mark = Integer.parseInt(grade)
            )
            // Add Data to Database
            mMarksViewModel.addParticipants(marks)
            Toast.makeText(requireContext(), "Pomyślnie dodano!", Toast.LENGTH_LONG).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragmentMark_to_listFragmentMarks)
        }else{
            Toast.makeText(requireContext(), "Wprowadź poprawne dane.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(desc: String, mark: String): Boolean{
        return (desc.isNotEmpty()&&mark.isNotEmpty()&&Integer.parseInt(mark)<101&&Integer.parseInt(mark)>-1)
    }

}