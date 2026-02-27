package com.example.taskmanager.mvp.model

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.taskmanager.database.DBHandler
import com.example.taskmanager.database.model.TaskEntity
import com.example.taskmanager.mvp.ext.OnBindData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModelForMainActivity(
    private val activity: AppCompatActivity
) {

    private val dataBase = DBHandler.getDataBase(activity)
    private var currentJob: Job? = null  // ذخیره collect فعال در کوروتین

    fun setData(taskEntity: TaskEntity) {
        activity.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                dataBase.taskDao().insertTask(taskEntity)
            }
        }
    }

    fun editData(taskEntity: TaskEntity) {
        activity.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                dataBase.taskDao().updateTask(taskEntity)
            }
        }
    }

    fun deleteData(taskEntity: TaskEntity) {
        activity.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                dataBase.taskDao().deleteTask(taskEntity)
            }
        }
    }


    fun getData(state: Boolean, onBindData: OnBindData) {
        // collect قبلی رو لغو کن
        currentJob?.cancel()

        currentJob = activity.lifecycleScope.launch {
//            // متد collect باید مستقیماً در یک coroutine فعال انجام بشه،
//            // نه داخل withContext، چون collect خودش suspending هست
//            // و withContext نمی‌تونه توی Dispatchers.Main collect کنه.
            dataBase.taskDao().getTaskByColumn(state)
                .flowOn(Dispatchers.IO)
                .collect { tasks ->
                    onBindData.getData(tasks)
                }
        }
    }

    fun closeDataBase() {
        dataBase.close()
    }

}

