package com.eric.prelistaapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMenu(
    nomeLista: String,
    menuAberto: MutableState<Boolean>,
    onAdicionarClick: () -> Unit,
    onOrdenarClick: () -> Unit
) {
    TopAppBar(
        title = { Text(nomeLista.uppercase()) },
        actions = {
            IconButton(onClick = { menuAberto.value = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }
            DropdownMenu(
                expanded = menuAberto.value,
                onDismissRequest = { menuAberto.value = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Adicionar item") },
                    onClick = {
                        menuAberto.value = false
                        onAdicionarClick()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Ordenar itens") },
                    onClick = {
                        menuAberto.value = false
                        onOrdenarClick()
                    }
                )
            }
        }
    )
}


