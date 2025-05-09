package com.hungho.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hungho.data.helper.FlavorHelper
import com.hungho.data.helper.SecretHelper
import com.hungho.data.local.database.dao.UserDao
import com.hungho.data.local.database.dao.UserRemoteKeyDao
import com.hungho.data.local.database.entity.UserEntity
import com.hungho.data.local.database.entity.UserRemoteKeyEntity
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [UserEntity::class, UserRemoteKeyEntity::class],
    version = 2,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userRemoteKeyDao(): UserRemoteKeyDao

    companion object {
        const val DATABASE_NAME = "app_database"
        fun getInstance(context: Context): AppDatabase {
            val builder = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).addMigrations(MIGRATION_1_2)
            if (FlavorHelper.isProdMode()) {
                val databaseKey = SecretHelper.getDatabaseKey()
                val passphrase = SQLiteDatabase.getBytes(databaseKey.toCharArray())
                val factory = SupportFactory(passphrase)
                builder.openHelperFactory(factory)
            }
            return builder.build()
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                        CREATE TABLE IF NOT EXISTS user_remote_keys (
                            userId INTEGER NOT NULL PRIMARY KEY,
                            prevKey INTEGER,
                            nextKey INTEGER
                        )
                    """.trimIndent()
                )
            }
        }
    }
}