package com.wednesday.machinetest.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SongDetails.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SongDao songDao();
}
