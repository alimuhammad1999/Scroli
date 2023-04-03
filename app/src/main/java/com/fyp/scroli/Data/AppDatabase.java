package com.fyp.scroli.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.fyp.scroli.Data.Dao.DataAO;
import com.fyp.scroli.Data.Models.AddedUser;
import com.fyp.scroli.Data.Models.Person;
import com.fyp.scroli.Data.Models.User;

@Database(entities = {Person.class, User.class, AddedUser.class}, version = 8)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataAO dataAO();

}
