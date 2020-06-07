package com.wednesday.machinetest.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface SongDao {
    @Query("SELECT * FROM songDetails")
    List<SongDetails> getAll();

    @Insert
    void insert(SongDetails songDetails);

    @Delete
    void delete(SongDetails songDetails);

    @Update
    void update(SongDetails songDetails);
}
