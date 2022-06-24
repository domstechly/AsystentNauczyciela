package com.example.asystentnauczyciela.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.fragments.add.AddFragmentStudentToClassArgs
import com.example.asystentnauczyciela.fragments.list.ListFragmentParticipantsArgs
import com.example.asystentnauczyciela.model.Participants
import com.example.asystentnauczyciela.model.Students
import com.example.asystentnauczyciela.viewmodel.ParticipantsViewModel
import com.example.asystentnauczyciela.viewmodel.StudentsViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.fragment_update_addstudent.*
import kotlinx.android.synthetic.main.fragment_update_addstudent.view.*
import kotlinx.android.synthetic.main.fragment_update_student.*
import kotlinx.android.synthetic.main.fragment_update_student.view.*

class UpdateFragmentAddStudent : Fragment() {

    private val args by navArgs<UpdateFragmentAddStudentArgs>()
    private val args2 by navArgs<ListFragmentParticipantsArgs>()

    private lateinit var mStudentsViewModel: StudentsViewModel
    private lateinit var mParticipantsViewModel: ParticipantsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_addstudent, container, false)

        mStudentsViewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)
        mParticipantsViewModel=ViewModelProvider(this).get(ParticipantsViewModel::class.java)

        view.addupdFirstName_et.setText(args.currentStudent.firstName)
        view.addupdLastName_et.setText(args.currentStudent.lastName)
        view.addupdID_et.setText(args.currentStudent.id_student.toString())
        //view.addupdID_et.setText(args2.currentUser.id.toString())

        view.addupdate_btn_student.setOnClickListener {
            insertDataToDatabase()
        }

        // Add menu
        setHasOptionsMenu(false)

        return view
    }

    private fun confirmItem() {
        val firstName = updFirstName_et.text.toString()
        val lastName = updLastName_et.text.toString()
        val id_student = updID_et.text.toString()


            // Create User Object
            val updateStudent = Students(args.currentStudent.id, firstName, lastName, Integer.parseInt(id_student))
            // Update Current User
            mStudentsViewModel.updateStudents(updateStudent)
            Toast.makeText(requireContext(), "Pomyślnie zaktualizowano!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_updateFragmentAddStudent_to_addFragmentStudentToClass)
    }
    private fun insertDataToDatabase() {
 val firstName = addupdFirstName_et.text.toString()
 val lastName = addupdLastName_et.text.toString()
 val id_student = addupdID_et.text.toString()

     // Create User Object
     val participants = Participants(
         0,
         id_class = 1,
         idstudent = args.currentStudent.id,
         firstName = firstName,
         lastName = lastName,
         id_student = Integer.parseInt(id_student.toString())
     )
     // Add Data to Database
     mParticipantsViewModel.addParticipants(participants)
     Toast.makeText(requireContext(), "Pomyślnie dodano!", Toast.LENGTH_LONG).show()
     // Navigate Back
     findNavController().navigate(R.id.action_updateFragmentAddStudent_to_addFragmentStudentToClass)
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