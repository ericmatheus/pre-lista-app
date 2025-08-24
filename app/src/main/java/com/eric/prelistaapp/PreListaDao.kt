package com.eric.prelistaapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface PreListaDao {
    @Insert
    suspend fun inserir(preLista: PreLista)

    @Query("SELECT * FROM pre_listas")
    suspend fun listarTodas(): List<PreLista>

    @Delete
    suspend fun deletar(lista: PreLista)

    @Query("SELECT * FROM pre_listas WHERE id = :id")
    suspend fun buscarPorId(id: Int): PreLista?

}