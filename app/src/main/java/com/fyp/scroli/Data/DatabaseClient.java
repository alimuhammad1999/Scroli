package com.fyp.scroli.Data;

import android.content.Context;

import androidx.room.Room;

public final class DatabaseClient
{

    private Context ctx;
    private static DatabaseClient INSTANCE;

    private AppDatabase AppDB;

    private DatabaseClient(Context ctx)
    {
        this.ctx = ctx;
        AppDB = Room.databaseBuilder(ctx,AppDatabase.class,"mydatabase").allowMainThreadQueries().build();
    }

    public static DatabaseClient getInstance(Context ctx)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new DatabaseClient(ctx);
        }

        return INSTANCE;
    }

    // getters and setters
    public AppDatabase getAppDB() {
        return AppDB;
    }
}
