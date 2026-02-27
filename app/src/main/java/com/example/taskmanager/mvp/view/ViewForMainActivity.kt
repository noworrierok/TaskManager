package com.example.taskmanager.mvp.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.adapter.ListTaskAdapter
import com.example.taskmanager.databinding.ActivityMainBinding
import com.example.taskmanager.databinding.FabCustomDialogBinding
import com.example.taskmanager.database.model.TaskEntity
import com.example.taskmanager.mvp.ext.OnBindData

class ViewForMainActivity(
    contextInstance: Context
) : FrameLayout(contextInstance) {

    val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))
//    private lateinit var adapter: RecyclerTaskAdapter
    private lateinit var adapter: ListTaskAdapter

    fun showDialog(onBindData: OnBindData) {

        binding.fab.setOnClickListener {

            val view = FabCustomDialogBinding.inflate(LayoutInflater.from(context))

            val dialog = Dialog(context)
            dialog.setContentView(view.root)
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            view.btnCancel.setOnClickListener { dialog.dismiss() }

            view.btnSave.setOnClickListener {

                val text = view.edtTask.text.toString()

                if (text.isNotEmpty()) {
                    onBindData.saveData(TaskEntity(title = text, state = false))
                    Toast.makeText(context, "وظیفه با موفقیت ایجاد شد", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                } else {
                    Toast.makeText(context, "وظیفه را وارد کنید", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun initRecycler(onBindData: OnBindData) {

        adapter = ListTaskAdapter(onBindData)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.recyclerView.adapter = adapter


    }

    // بر اساس داده های جدیدی که بهت میدم
    // بیا و ایتم های لیست ویو رو اپدیت کن
    fun updateTasks(tasks: List<TaskEntity>) {

        val data = ArrayList<TaskEntity>()
        tasks.forEach { data.add(it) }

//        adapter.dataUpdate(data)
        adapter.submitList(data)

    }

    // Set Data with RadioButton For Recycler
    fun switchRadioBtn(onBindData: OnBindData) {

        onBindData.requestData(true)

        binding.radioBtnTrue.setOnClickListener {
            onBindData.requestData(false)
        }

        binding.radioBtnFalse.setOnClickListener {
            onBindData.requestData(true)
        }
    }
}