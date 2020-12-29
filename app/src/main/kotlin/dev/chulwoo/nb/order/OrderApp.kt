package dev.chulwoo.nb.order

import android.app.Application
import dev.chulwoo.nb.order.di.dataModule
import dev.chulwoo.nb.order.di.domainModule
import dev.chulwoo.nb.order.di.presentationModule
import dev.chulwoo.nb.order.di.tmpModule
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
                    tmpModule,
                )
            )
        }
    }
}

