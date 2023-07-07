package com.nic.roomdatabase.Roomdbdirectery

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskItem::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun TaskDao():TaskDao
    companion object{
     @Volatile   private var inastance: TaskDatabase? =null
        private val LOCK = Any()

   operator  fun invoke(context: Context): TaskDatabase {
         return inastance ?: synchronized(LOCK){
             inastance ?: Builddatabase(context).also {
                 inastance=it
             }
         }
     }
       private fun Builddatabase(context: Context)= Room.databaseBuilder(context.applicationContext,TaskDatabase::class.java,"Taskbardb").build()
    }
}
