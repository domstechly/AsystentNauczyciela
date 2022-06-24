package com.example.asystentnauczyciela.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "participants_table")
data class Participants(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["classid"], onDelete = ForeignKey.CASCADE )
    val id_class : Int,
    @ForeignKey(entity = Students::class, parentColumns = ["id"], childColumns = ["participantid"], onDelete = ForeignKey.CASCADE )
    val idstudent : Int,
    val firstName: String,
    val lastName: String,
    val id_student: Int
): Parcelable