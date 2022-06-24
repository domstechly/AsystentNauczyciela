package com.example.asystentnauczyciela.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.asystentnauczyciela.model.Participants

@Database(entities = [Participants::class], version = 1, exportSchema = false)
abstract class ParticipantsDatabase : RoomDatabase() {

    abstract fun participantsDao(): ParticipantsDao

    companion object {
        @Volatile
        private var INSTANCE: ParticipantsDatabase? = null

        fun getDatabase(context: Context): ParticipantsDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ParticipantsDatabase::class.java,
                    "participants_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}