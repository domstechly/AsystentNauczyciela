package com.example.asystentnauczyciela.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.viewmodel.StudentsViewModel
import kotlinx.android.synthetic.main.fragment_list_student.view.*

class ListFragmentStudent : Fragment() {

    private lateinit var mStudentsViewModel: StudentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_student, container, false)

        // Recyclerview
        val adapter = ListAdapterStudent()
        val recyclerView = view.recyclerviewStudent
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mStudentsViewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)
        mStudentsViewModel.readAllData.observe(viewLifecycleOwner, Observer { student ->
            adapter.setData(student)
        })

        view.floatingActionButtonStudent.setOnClickListener {
            findNavController().navigate(R.id.action_listFragmentStudent_to_addFragmentStudent)
        }
        view.button_toLessons.setOnClickListener {
            findNavController().navigate(R.id.action_listFragmentStudent_to_listFragment)
        }

        // Add menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllStudents()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllStudents() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Tak") { _, _ ->
            mStudentsViewModel.deleteAllStudents()
            Toast.makeText(
                requireContext(),
                "Pomyślnie usunięto wszystko",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Nie") { _, _ -> }
        builder.setTitle("Usuń Wszystko?")
        builder.setMessage("Napewno chcesz wszystko usunąć?")
        builder.create().show()
    }
}