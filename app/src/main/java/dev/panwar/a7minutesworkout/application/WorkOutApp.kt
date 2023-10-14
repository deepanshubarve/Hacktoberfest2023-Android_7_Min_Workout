package dev.panwar.a7minutesworkout.application

import android.app.Application
import dev.panwar.a7minutesworkout.data.HistoryDatabase

// create the application class
class WorkOutApp: Application() {

    val db: HistoryDatabase by lazy {
        HistoryDatabase.getInstance(this)
    }
}
