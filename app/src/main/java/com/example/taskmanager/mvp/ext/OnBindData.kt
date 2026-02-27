package com.example.taskmanager.mvp.ext

import com.example.taskmanager.database.model.TaskEntity

interface OnBindData {

    fun saveData(taskEntity: TaskEntity) {}

    fun editData(taskEntity: TaskEntity) {}

    fun deleteData(taskEntity: TaskEntity) {}

    fun getData(taskEntity: List<TaskEntity>) {}

    fun requestData(state : Boolean){}
}