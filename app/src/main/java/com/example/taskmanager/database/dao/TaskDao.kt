package com.example.taskmanager.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskmanager.database.DBHandler
import com.example.taskmanager.database.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    fun insertTask(vararg taskEntity: TaskEntity)

    @get:Query("SELECT * FROM ${DBHandler.TASK_TABLE}")
    val getTask : Flow<List<TaskEntity>>

    @Query("SELECT * FROM ${DBHandler.TASK_TABLE} WHERE state = :type")
    fun getTaskByColumn(type : Boolean) : Flow<List<TaskEntity>>

    @Update
    fun updateTask(vararg tasks : TaskEntity) : Int

    @Delete
    fun deleteTask(vararg tasks : TaskEntity) : Int

    @Query("DELETE FROM ${DBHandler.TASK_TABLE}")
    fun deleteAllTask()
}