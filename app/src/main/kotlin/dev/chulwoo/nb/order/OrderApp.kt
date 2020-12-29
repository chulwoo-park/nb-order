package dev.chulwoo.nb.order

import android.app.Application
import dev.chulwoo.nb.order.di.*
import org.koin.core.context.startKoin

class OrderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                listOf(
                    domainModule,
                    dataModule,
                    presentationModule,
                    deviceModule,
                    httpModule,
                )
            )
        }
    }
}

