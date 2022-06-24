package com.example.asystentnauczyciela.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.asystentnauczyciela.model.Marks

@Database(entities = [Marks::class], version = 1, exportSchema = false)
abstract class MarksDatabase : RoomDatabase() {

    abstract fun marksDao(): MarksDao

    companion object {
        @Volatile
        private var INSTANCE: MarksDatabase? = null

        fun getDatabase(context: Context): MarksDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarksDatabase::class.java,
                    "marks_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}