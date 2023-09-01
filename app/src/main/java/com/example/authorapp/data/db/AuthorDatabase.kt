package com.example.authorapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.authorapp.data.model.Author

@Database(
    entities = [Author::class],
    version = 1,
    exportSchema = false
)
abstract class AuthorDatabase: RoomDatabase(){
//    abstract fun getAuthorDao(): AuthorDao
        abstract fun getAuthorDao(): AuthorDao

//    companion object {
//        @Volatile
//        private var INSTANCE: AuthorDatabase? = null
//
//        fun getInstance(context: Context): AuthorDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AuthorDatabase::class.java,
//                    "Author_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }




//    public abstract override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper

}