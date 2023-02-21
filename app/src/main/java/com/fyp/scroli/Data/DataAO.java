package com.fyp.scroli.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface DataAO {
    @Query("SELECT * from person")
    List<Person> getAll();

    @Insert
    void insert(Person person);

    @Insert
    void insertAll(List<Person> persons);

    @Delete
    void delete(Person person);

    @RawQuery
    int checkpoint(SupportSQLiteQuery supportSQLiteQuery);
}
