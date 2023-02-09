package com.fyp.scroli.Data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataAO {
    @Query("SELECT * from person")
    List<Person> getAll();

    @Insert
    void insert(Person person);

    @Insert
    void insertAll(List<Person> persons);



}
