package com.fyp.scroli.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.fyp.scroli.MainActivity;

@Database(entities = {Person.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataAO dataAO();
    public MainActivity ma = new MainActivity();

}
