package com.eric.prelistaapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itens")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val quantidade: Int = 0,
    val listaId: Int,
    val ordem: Int = 0
)