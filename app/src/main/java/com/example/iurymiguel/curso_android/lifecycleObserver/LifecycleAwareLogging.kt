package com.example.iurymiguel.curso_android.lifecycleObserver

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log

class LifecycleAwareLogging: LifecycleObserver {


    private val LOG_TAG = "LifecycleAwareLogging"

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun listeningToCreate() {
        Log.d(LOG_TAG, "on Create")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun listeningToDestroy() {
        Log.d(LOG_TAG, "on pause")
    }


}