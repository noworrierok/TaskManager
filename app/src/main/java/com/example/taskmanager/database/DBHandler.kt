package com.example.taskmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanager.database.dao.TaskDao
import com.example.taskmanager.database.model.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = DBHandler.DATABASE_VERSION
)
abstract class DBHandler : RoomDatabase() {

    abstract fun taskDao() : TaskDao

    companion object{
        private const val DATABASE_NAME = "main_dataBase"
        const val DATABASE_VERSION = 1

        const val TASK_TABLE = "taskTable"

        private var INSTANCE : DBHandler? = null

        fun getDataBase(context: Context): DBHandler{

            if (INSTANCE == null)

                INSTANCE = Room.databaseBuilder(
                    context,
                    DBHandler::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return INSTANCE!!


        }
    }

}