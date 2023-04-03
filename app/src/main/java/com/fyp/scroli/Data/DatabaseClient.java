package com.fyp.scroli.Data;

import android.content.Context;

import androidx.room.Room;

import com.fyp.scroli.R;

public final class DatabaseClient
{

    private Context ctx;
    private static DatabaseClient INSTANCE;

    private AppDatabase AppDB;

    private DatabaseClient(Context ctx)
    {
        this.ctx = ctx;
        AppDB = Room.databaseBuilder(ctx,AppDatabase.class,ctx.getString(R.string.databasename)).allowMainThreadQueries().fallbackToDestructiveMigration().build();
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
