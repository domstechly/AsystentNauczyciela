package com.example.asystentnauczyciela.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.viewmodel.ParticipantsViewModel
import kotlinx.android.synthetic.main.fragment_list_addstudent.view.*
import kotlinx.android.synthetic.main.fragment_list_student.view.*
import kotlinx.android.synthetic.main.fragment_add_student_toclass.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class ListFragmentParticipants : Fragment() {

    private lateinit var mParticipantsViewModel: ParticipantsViewModel
    private val args by navArgs<ListFragmentParticipantsArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_addstudent, container, false)
        view.idGetPrzedmiotName.setText(args.currentUser.firstName)

        // Recyclerview
        val adapter = ListAdapterParticipants()
        val recyclerView = view.recyclerviewStudentsInClass
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mParticipantsViewModel = ViewModelProvider(this).get(ParticipantsViewModel::class.java)
        mParticipantsViewModel.readAllData.observe(viewLifecycleOwner, Observer { participants ->
            adapter.setData(participants)
        })

       view.floatingActionButtonStudentInClass.setOnClickListener {
            findNavController().navigate(R.id.action_listFragmentParticipants_to_addFragmentStudentToClass)
        }
        view.button_backtolesson.setOnClickListener {
            findNavController().navigate(R.id.action_listFragmentParticipants_to_listFragment)
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
            deleteAllParticipants()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllParticipants() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Tak") { _, _ ->
            mParticipantsViewModel.deleteAllParticipants()
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