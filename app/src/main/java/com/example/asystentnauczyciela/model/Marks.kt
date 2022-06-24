package com.example.asystentnauczyciela.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "marks_table")
data class Marks(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ForeignKey(entity = Students::class, parentColumns = ["id"], childColumns = ["participantid"], onDelete = ForeignKey.CASCADE )
    val idstudent : Int,
    val desc: String,
    val mark: Int
): Parcelable