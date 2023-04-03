package com.fyp.scroli.Data.Dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.fyp.scroli.Data.Models.AddedUser;
import com.fyp.scroli.Data.Models.Person;
import com.fyp.scroli.Data.Models.User;

import java.util.List;

@Dao
public interface DataAO {
    @Query("SELECT * from Person")
    List<Person> getAllPerson();

    @Query("SELECT * from User")
    List<User> getAllUsers();

    @Query("SELECT * from AddedUser")
    AddedUser[] getAllaAddedUsers();

    @Query("SELECT * from AddedUser")
    List<AddedUser> getAllAddedUsers();

    @RawQuery
    Cursor get(SupportSQLiteQuery query);

    @RawQuery
    List<Person> getsome(SupportSQLiteQuery query);

    @Insert
    void insertPerson(Person person);

    @Insert
    void inserUser(User user);

    @Insert
    void insertAddedUser(AddedUser user);

    @Insert
    void insertAllPerson(List<Person> persons);

    @Insert
    void insertAllUser(List<User> users);

    @Delete
    void deletePerson(Person person);

    @Delete
    void deleteAddedUser(AddedUser user);

    @Delete
    void deleteUser(User user);

    @RawQuery
    int checkpoint(SupportSQLiteQuery supportSQLiteQuery);

}
