package com.example.taskmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.databinding.RecyclerItemBinding
import com.example.taskmanager.database.model.TaskEntity
import com.example.taskmanager.mvp.ext.OnBindData

class ListTaskAdapter(
    private val onBindData: OnBindData
) : ListAdapter<TaskEntity, ListTaskAdapter.TaskViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TaskViewHolder(
            RecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

    // این بخش یک شیء همراه برای کلاس آداپتر ایجاد می‌کنه.
    // یعنی هر بار که از کلاس آداپتر استفاده می‌کنی، این بخش فقط یک بار ساخته می‌شه.
    companion object {
        //  داره یک آبجکت از یک کلاس abstract به نام DiffUtil.ItemCallback می‌سازه،
        // و این آبجکت به صورت ناشناس (anonymous object) تعریف شده.
        // که وظیفه‌اش مقایسه‌ی آیتم‌ها
        // در لیست قدیمی و جدید هست تا فقط آیتم‌هایی که تغییر کردن، رفرش بشن.
        // اینجا TaskEntity نوع داده ی ایتم لیست هستن
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TaskEntity>() {

            // این تابع مشخص می‌کنه آیا دو آیتم نماینده‌ی یک "شیء" یکسان هستن یا نه.
            //معمولاً آیتم‌ها اگر id یکسانی داشته باشن، به‌عنوان یک آیتم شناخته می‌شن.
            override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity) =
                oldItem.id == newItem.id

            // اینجا بررسی می‌کنی آیا محتوای اون دو آیتم تغییری کرده یا نه.
            override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class TaskViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(data: TaskEntity) {

            binding.txtTitle.text = data.title
            binding.checkBox.isChecked = data.state
            binding.checkBox.setOnClickListener {
                if (data.state){
                    onBindData.editData(TaskEntity(data.id, data.title, false))
                }else{
                    onBindData.editData(TaskEntity(data.id, data.title, true))
                }
            }
            binding.imgDelete.setOnClickListener {
                onBindData.deleteData(data)
            }

        }


    }


}
