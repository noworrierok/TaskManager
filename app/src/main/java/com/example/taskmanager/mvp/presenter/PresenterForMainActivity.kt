package com.example.taskmanager.mvp.presenter

import android.util.Log
import com.example.taskmanager.database.model.TaskEntity
import com.example.taskmanager.mvp.ext.BaseLifeCycle
import com.example.taskmanager.mvp.ext.OnBindData
import com.example.taskmanager.mvp.model.ModelForMainActivity
import com.example.taskmanager.mvp.view.ViewForMainActivity

class PresenterForMainActivity(
    private val view: ViewForMainActivity,
    private val model: ModelForMainActivity
) : BaseLifeCycle {
    private var currentFilter: Boolean = false // نمایش پیش‌فرض = وظایف انجام‌نشده
    override fun onCreate() {
        setNewTask()
        setDataInitRecycler()
        switchListRecycler()

        // 👇 نمایش اولیه: فقط وظایف "انجام‌نشده"
        model.getData(false, object : OnBindData {
            override fun getData(taskEntity: List<TaskEntity>) {
                view.updateTasks(taskEntity)
            }
        })
    }

    private fun setNewTask() {

        view.showDialog(
            object : OnBindData {
                override fun saveData(taskEntity: TaskEntity) {
                    model.setData(taskEntity)
                }
            }
        )
    }

    private fun setDataInitRecycler() {

        view.initRecycler(
            object : OnBindData {

                override fun editData(taskEntity: TaskEntity) {
                    model.editData(taskEntity) //  دیتای مورد نیاز چک باکس در لیست آداپتر

                }

                override fun deleteData(taskEntity: TaskEntity) {
                    model.deleteData(taskEntity) // دیتای مورد نیاز ایکون حذف در لیست آداپتر
                }
            })
    }

    private fun switchListRecycler() {

        view.switchRadioBtn( // کلاس view میخواد سورس داده رو از presenter دریافت کنه
            object : OnBindData {
                override fun requestData(state: Boolean) { // بخاطر همین state رو اورده تا در قبالش داده دریافن کنه

                    model.getData(  // کلاس model با خودش داده رو اورده
                        state, // میاد state رو میگیره
                        object : OnBindData { // و داده هارو اپدیت میکنه
                            override fun getData(taskEntity: List<TaskEntity>) {
                                view.updateTasks(taskEntity)
                                Log.i("updateTask", "$taskEntity")

                            }
                        }
                    )
                }
            }
        )
    }

    override fun onDestroy() {
        model.closeDataBase()
    }
}