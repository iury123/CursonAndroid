package com.example.iurymiguel.curso_android

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.iurymiguel.curso_android.androidViewModel.WifiViewModel
import com.example.iurymiguel.curso_android.lifecycleObserver.LifecycleAwareLogging

class MainActivity : AppCompatActivity() {

    private lateinit var wifiViewModel: WifiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(LifecycleAwareLogging())

        wifiViewModel =  ViewModelProviders.of(this).get(WifiViewModel::class.java)

        wifiViewModel.counter.observe(this,  Observer {
            println("Valor do counter $it")
        })

        wifiViewModel.sensorLiveData.observe(this, Observer {
            println("Sensor value: ${it.toString()}")
        })

        wifiViewModel.setName("Jair")

        wifiViewModel.getPerson().observe(this, Observer {
            println("${it?.name} ${it?.age}")
        })

        wifiViewModel.personMediator.observe(this, Observer {
            println("FROM MEDIATOR LIVE DATA ${it?.name} ${it?.age}")
        })
    }

    override fun onResume() {
        super.onResume()
        wifiViewModel.getConfiguredNetworks().forEach {
            println(it.SSID)
            wifiViewModel.increment()
        }
    }
}
