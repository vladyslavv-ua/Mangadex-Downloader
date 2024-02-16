package di

import API_URL
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import io.vladyslavv_ua.sql.Cache
import kotlinx.serialization.json.Json
import dataSource.ApiSource
import dataSource.pagingSource.ChapterPagingSource
import dataSource.DBSource
import org.koin.dsl.module
import repo.ApiRepo

fun singleton() = module {
    single {
        HttpClient(CIO) {
            install(Resources)


            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            defaultRequest {
                url(API_URL)
            }
        }
    }
    single {
        ApiSource(get())
    }
    single {
        ApiRepo(get(), get())
    }
    single {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Cache.Schema.create(driver)
        driver
    }
    single { DBSource(get()) }
}