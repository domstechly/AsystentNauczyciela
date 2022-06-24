package com.example.asystentnauczyciela.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Participants
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.custom_row_studentsinclass.view.*

class ListAdapterParticipants: RecyclerView.Adapter<ListAdapterParticipants.MyViewHolder>() {

    private var participantsList = emptyList<Participants>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_studentsinclass, parent, false))
    }

    override fun getItemCount(): Int {
        return participantsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = participantsList[position]
        holder.itemView.firstName_txt.text = currentItem.firstName
        holder.itemView.lastName_txt.text = currentItem.lastName
        holder.itemView.id_student_txt.text=currentItem.id_student.toString()

        //DODANIE OCENY
        holder.itemView.rowLayoutStudentSInClass.idAddStudentIcon.setOnClickListener {
            val action = ListFragmentParticipantsDirections.actionListFragmentParticipantsToListFragmentMarks(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(participants: List<Participants>){
        this.participantsList = participants
        notifyDataSetChanged()
    }
}
