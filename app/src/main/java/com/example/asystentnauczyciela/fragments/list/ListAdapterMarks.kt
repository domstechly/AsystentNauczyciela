package com.example.asystentnauczyciela.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Students
import com.example.asystentnauczyciela.model.User
import com.example.asystentnauczyciela.fragments.add.*
import com.example.asystentnauczyciela.model.Marks
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.custom_row_marks.view.*
import kotlinx.android.synthetic.main.custom_row_student.view.*
import kotlinx.android.synthetic.main.custom_row_studentsinclass.view.*
import kotlinx.android.synthetic.main.custom_row_studentsinclass.view.firstName_txt
import kotlinx.android.synthetic.main.custom_row_studentsinclass.view.id_student_txt
import kotlinx.android.synthetic.main.custom_row_studentsinclass.view.lastName_txt
import kotlinx.android.synthetic.main.custom_row_studentsinclass.view.rowLayoutStudentSInClass

class ListAdapterMarks: RecyclerView.Adapter<ListAdapterMarks.MyViewHolder>() {

    private var marksList = emptyList<Marks>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_marks, parent, false))
    }

    override fun getItemCount(): Int {
        return marksList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = marksList[position]
        holder.itemView.Desc_Txt.text = currentItem.desc
        holder.itemView.Grade_txt.text = currentItem.mark.toString()

        /*holder.itemView.rowLayoutStudentSInClass.idAddStudentIcon.setOnClickListener{
            val action = AddFragmentStudentToClassDirections.actionAddFragmentStudentToClassToListFragmentParticipants(currentItem)
            holder.itemView.findNavController().navigate(action)
        }*/
    }

    fun setData(marks: List<Marks>){
        this.marksList = marks
        notifyDataSetChanged()
    }
}
