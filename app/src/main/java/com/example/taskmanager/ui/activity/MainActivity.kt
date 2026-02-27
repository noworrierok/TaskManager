package com.example.taskmanager.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanager.R
import com.example.taskmanager.mvp.model.ModelForMainActivity
import com.example.taskmanager.mvp.presenter.PresenterForMainActivity
import com.example.taskmanager.mvp.view.ViewForMainActivity

class MainActivity : AppCompatActivity() {
    private lateinit var presenter : PresenterForMainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ViewForMainActivity(this)
        setContentView(view.binding.root)

        presenter = PresenterForMainActivity(view, ModelForMainActivity(this))
        presenter.onCreate()

    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}