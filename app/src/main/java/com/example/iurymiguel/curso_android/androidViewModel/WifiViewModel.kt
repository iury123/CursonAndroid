package com.example.iurymiguel.curso_android.androidViewModel

import android.app.Application
import android.arch.lifecycle.*
import android.content.Context
import android.net.wifi.WifiManager
import com.example.iurymiguel.curso_android.Person
import com.example.iurymiguel.curso_android.extendedLiveData.SensorLiveData

class WifiViewModel(application: Application): AndroidViewModel(application) {

    var counter: MutableLiveData<Int> = MutableLiveData()
    val sensorLiveData: SensorLiveData = SensorLiveData(application)
    private var wifiManager: WifiManager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager

    private val personName: MutableLiveData<String>
    private val personsList: MutableList<Person>
    private val persons: MutableLiveData<MutableList<Person>>
    private var person: LiveData<Person>
    var personMediator: MediatorLiveData<Person> = MediatorLiveData()

    init {
        counter.value = 0

        personsList = mutableListOf(
            Person("Jose", 12),
            Person("Jair", 64),
            Person("Maria", 22),
            Person("Alex", 100),
            Person("Joao", 14))

        personName = MutableLiveData()

        persons = MutableLiveData()
        persons.value = personsList

        // Callback retorna um objeto do tipo Person.
        person = Transformations.map(personName) {
            // No momento que essa callback é executada, a callback do Observer do live data person é invocada.
            get(it)
        }

        //Callback retorna um live data e ai seu value sera usado para notificar o observer.
        person = Transformations.switchMap(personName) {
            // No momento que essa callback é executada, a callback do Observer do live data person é invocada.
            getPersonLive(it)
        }


        personMediator.addSource(personName) {
            personMediator.value = get(it!!)
            personMediator.removeSource(personName)
        }
    }

    fun increment() {
        counter.value = counter.value?.inc()
    }

    fun getConfiguredNetworks() = wifiManager.configuredNetworks

    fun getPerson() = person

    fun setName(name: String) {
        personName.value = name
    }

    fun get(name: String): Person? = personsList.find { it.name == name }

    // Cria um live data e imediatamente seta um valor para ele.
    fun getPersonLive(name: String): MutableLiveData<Person> = MutableLiveData<Person>().apply {
        value = personsList.find { it.name == name }
    }
}