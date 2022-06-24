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
import com.example.asystentnauczyciela.model.User
import com.example.asystentnauczyciela.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.custom_row_student.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.lang.Exception

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var btn_record: ImageView
    private lateinit var dropdowndays: AutoCompleteTextView
    private lateinit var dropdownhoursStart: AutoCompleteTextView
    private lateinit var dropdownminutesStart: AutoCompleteTextView
    private lateinit var dropdownhoursStop: AutoCompleteTextView
    private lateinit var dropdownminutesStop: AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        dropdowndays = view.findViewById(R.id.autoCompleteTextView)
        dropdownhoursStart = view.findViewById(R.id.autoCompleteTextViewGStart)
        dropdownminutesStart = view.findViewById(R.id.autoCompleteTextViewMStart)
        dropdownhoursStop = view.findViewById(R.id.autoCompleteTextViewGStop)
        dropdownminutesStop = view.findViewById(R.id.autoCompleteTextViewMStop)

        btn_record = view.findViewById(R.id.btn_record)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val arrayAdapterDays = ArrayAdapter(requireContext() , R.layout.dropdown_days,resources.getStringArray(R.array.dni_tygodnia))
        val arrayAdapterHours = ArrayAdapter(requireContext() , R.layout.dropdown_days,resources.getStringArray(R.array.hours))
        val arrayAdapterMinutes = ArrayAdapter(requireContext() , R.layout.dropdown_days,resources.getStringArray(R.array.minutes))
        dropdowndays.setAdapter(arrayAdapterDays)
        dropdownhoursStart.setAdapter(arrayAdapterHours)
        dropdownminutesStart.setAdapter(arrayAdapterMinutes)
        dropdownhoursStop.setAdapter(arrayAdapterHours)
        dropdownminutesStop.setAdapter(arrayAdapterMinutes)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        view.btn_record.setOnClickListener(){
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
                idEdtPrzedmiotName.setText(text[0])
            }
        }
    }
    private fun insertDataToDatabase() {
        val firstName = idEdtPrzedmiotName.text.toString()
        val lastName = autoCompleteTextView.text.toString()
        val start = autoCompleteTextViewGStart.text.toString()+":"+autoCompleteTextViewMStart.text.toString()
        val stop = autoCompleteTextViewGStop.text.toString()+":"+autoCompleteTextViewMStop.text.toString()

        if(inputCheck(firstName, start, stop)){
            // Create User Object
            val user = User(
                0,
                firstName,
                lastName,
                start,
                stop
            )
            // Add Data to Database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Pomyślnie dodano!", Toast.LENGTH_LONG).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Wprowadź poprawne dane.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, start:String,stop:String): Boolean{
        return (firstName.isNotEmpty()&&
                (if(start.length==5&&stop.length==5){if(start.substring(0,2)==stop.substring(0,2)){start.substring(3,5)<stop.substring(3,5)}else{start.substring(0,2)<stop.substring(0,2)}}
                else if(start.length==4&&stop.length==4){if(start.substring(0,1)==stop.substring(0,1)){start.substring(2,4)<stop.substring(2,4)}else{start.substring(0,1)<stop.substring(0,1)}}
                else if(start.length>stop.length){return false}
                        else{return true}))
    }

}