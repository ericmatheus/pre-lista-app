package com.eric.prelistaapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pre_listas")
data class PreLista(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String
)