package com.solodilov.playertable

import android.app.Application
import com.solodilov.playertable.di.DaggerApplicationComponent

class App : Application() {

    val appComponent = DaggerApplicationComponent.factory().create(this)
}