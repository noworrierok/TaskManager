package com.example.taskmanager.mvp.ext

interface BaseLifeCycle {

    fun onCreate()

    fun onDestroy(){}

    fun onStop(){}
}