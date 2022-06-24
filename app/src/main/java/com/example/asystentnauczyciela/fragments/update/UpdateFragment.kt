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
import com.example.asystentnauczyciela.model.User
import com.example.asystentnauczyciela.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.lang.Exception

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var dropdowndays: AutoCompleteTextView
    private lateinit var dropdownhoursStart: AutoCompleteTextView
    private lateinit var dropdownminutesStart: AutoCompleteTextView
    private lateinit var dropdownhoursStop: AutoCompleteTextView
    private lateinit var dropdownminutesStop: AutoCompleteTextView
    private lateinit var update_btn_record: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        dropdowndays = view.findViewById(R.id.UpdautoCompleteTextView)
        dropdownhoursStart = view.findViewById(R.id.UpdautoCompleteTextViewGStart)
        dropdownminutesStart = view.findViewById(R.id.UpdautoCompleteTextViewMStart)
        dropdownhoursStop = view.findViewById(R.id.UpdautoCompleteTextViewGStop)
        dropdownminutesStop = view.findViewById(R.id.UpdautoCompleteTextViewMStop)
        update_btn_record=view.findViewById(R.id.update_btn_record)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val arrayAdapterDays = ArrayAdapter(requireContext() , R.layout.dropdown_days,resources.getStringArray(R.array.dni_tygodnia))
        val arrayAdapterHours = ArrayAdapter(requireContext() , R.layout.dropdown_days,resources.getStringArray(R.array.hours))
        val arrayAdapterMinutes = ArrayAdapter(requireContext() , R.layout.dropdown_days,resources.getStringArray(R.array.minutes))



        view.idUpdPrzedmiotName.setText(args.currentUser.firstName)
        view.UpdautoCompleteTextView.setText(args.currentUser.lastName)
        if(args.currentUser.start.length==5&&args.currentUser.stop.length==5){
        dropdownhoursStart.setText(args.currentUser.start.substring(0,2))
        dropdownminutesStart.setText(args.currentUser.start.substring(3,5))
        dropdownhoursStop.setText(args.currentUser.stop.substring(0,2))
        dropdownminutesStop.setText(args.currentUser.stop.substring(3,5))}
        else if(args.currentUser.start.length==4&&args.currentUser.stop.length==4){
            dropdownhoursStart.setText(args.currentUser.start.substring(0,1))
            dropdownminutesStart.setText(args.currentUser.start.substring(2,4))
            dropdownhoursStop.setText(args.currentUser.stop.substring(0,1))
            dropdownminutesStop.setText(args.currentUser.stop.substring(2,4))}
        else if(args.currentUser.start.length==5&&args.currentUser.stop.length==4){
            dropdownhoursStart.setText(args.currentUser.start.substring(0,2))
            dropdownminutesStart.setText(args.currentUser.start.substring(3,5))
            dropdownhoursStop.setText(args.currentUser.stop.substring(0,1))
            dropdownminutesStop.setText(args.currentUser.stop.substring(2,4))}
        else {
            dropdownhoursStart.setText(args.currentUser.start.substring(0,1))
            dropdownminutesStart.setText(args.currentUser.start.substring(2,4))
            dropdownhoursStop.setText(args.currentUser.stop.substring(0,2))
            dropdownminutesStop.setText(args.currentUser.stop.substring(3,5))}

        dropdowndays.setAdapter(arrayAdapterDays)
        dropdownhoursStart.setAdapter(arrayAdapterHours)
        dropdownminutesStart.setAdapter(arrayAdapterMinutes)
        dropdownhoursStop.setAdapter(arrayAdapterHours)
        dropdownminutesStop.setAdapter(arrayAdapterMinutes)
        view.update_btn.setOnClickListener {
            updateItem()
        }

        view.update_btn_record.setOnClickListener(){
            recordSpeech()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                idUpdPrzedmiotName.setText(text[0])
            }
        }
    }
    private fun updateItem() {
        val firstName = idUpdPrzedmiotName.text.toString()
        val lastName = UpdautoCompleteTextView.text.toString()
        val start = UpdautoCompleteTextViewGStart.text.toString()+":"+UpdautoCompleteTextViewMStart.text.toString()
        val stop = UpdautoCompleteTextViewGStop.text.toString()+":"+UpdautoCompleteTextViewMStop.text.toString()

        if (inputCheck(firstName, start, stop)) {
            // Create User Object
            val updatedUser = User(args.currentUser.id, firstName, lastName, start,stop)
            // Update Current User
            mUserViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Pomyślnie zaktualizowano!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Wprowadź poprawne dane.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(firstName: String, start:String,stop:String): Boolean{
        return (firstName.isNotEmpty()&&
                (if(start.length==5&&stop.length==5){if(start.substring(0,2)==stop.substring(0,2)){start.substring(3,5)<stop.substring(3,5)}else{start.substring(0,2)<stop.substring(0,2)}}
                else if(start.length==4&&stop.length==4){if(start.substring(0,1)==stop.substring(0,1)){start.substring(2,4)<stop.substring(2,4)}else{start.substring(0,1)<stop.substring(0,1)}}
                else if(start.length>stop.length){return false}
                else{return true}))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Tak") { _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(
                requireContext(),
                "Pomyślnie usunięto: ${args.currentUser.firstName}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Usuń ${args.currentUser.firstName}?")
        builder.setMessage("Napewno chcesz usunąć ${args.currentUser.firstName}?")
        builder.create().show()
    }
}