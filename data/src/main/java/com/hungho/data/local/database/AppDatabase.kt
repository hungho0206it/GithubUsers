package com.hungho.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hungho.data.local.database.dao.UserDao
import com.hungho.data.local.database.entity.UserEntity
import com.hungho.data.local.storage.helper.SecretHelper
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "app_database"
        fun getInstance(context: Context): AppDatabase {
            val databaseKey = SecretHelper.getDatabaseKey()
            val passphrase = SQLiteDatabase.getBytes(databaseKey.toCharArray())
            val factory = SupportFactory(passphrase)
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).openHelperFactory(factory).build()
        }
    }
}