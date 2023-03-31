package com.example.tempoextra.roomdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CoordenaDao {

    @Insert
    void registerCoordena(CoordenaEntity coordenaEntity);

    @Query("SELECT * from Coordenador where coordenaID=(:coordenaID) and senha=(:senha)")
    CoordenaEntity login(String coordenaID, String senha);

    @Query("SELECT * from Coordenador where coordenaID=(:coordenaID)")
    CoordenaEntity loginEmail(String coordenaID);
}
