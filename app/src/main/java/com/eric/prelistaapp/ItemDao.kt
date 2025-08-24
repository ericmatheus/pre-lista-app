package com.eric.prelistaapp

import androidx.room.*

@Dao
interface ItemDao {

    @Insert
    suspend fun inserir(item: Item)

    @Query("SELECT * FROM itens WHERE listaId = :listaId ORDER BY ordem ASC")
    suspend fun listarPorLista(listaId: Int): List<Item>

    @Update
    suspend fun atualizar(item: Item)

    @Query("DELETE FROM itens WHERE listaId = :listaId")
    suspend fun deletarPorLista(listaId: Int)

    @Delete
    suspend fun deletar(item: Item)


}
