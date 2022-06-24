package com.example.asystentnauczyciela.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Students
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.custom_row_student.view.firstName_txt
import kotlinx.android.synthetic.main.custom_row_student.view.lastName_txt
import kotlinx.android.synthetic.main.custom_row_student.view.id_student_txt
import kotlinx.android.synthetic.main.custom_row_student.view.*

class ListAdapterStudent: RecyclerView.Adapter<ListAdapterStudent.MyViewHolder>() {

    private var studentsList = emptyList<Students>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_student, parent, false))
    }

    override fun getItemCount(): Int {
        return studentsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = studentsList[position]
        holder.itemView.firstName_txt.text = currentItem.firstName
        holder.itemView.lastName_txt.text = currentItem.lastName
        holder.itemView.id_student_txt.text=currentItem.id_student.toString()

        /*holder.itemView.rowLayoutStudent.setOnClickListener {
            val action = ListFragmentStudentDirections.actionListFragmentStudentToUpdateFragmentStudent(currentItem)
            holder.itemView.findNavController().navigate(action)
        }*/
        holder.itemView.rowLayoutStudent.idEditIconStudent.setOnClickListener {
            val action = ListFragmentStudentDirections.actionListFragmentStudentToUpdateFragmentStudent(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(students: List<Students>){
        this.studentsList = students
        notifyDataSetChanged()
    }
}
