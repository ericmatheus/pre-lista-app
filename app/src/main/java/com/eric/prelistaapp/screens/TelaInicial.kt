package com.eric.prelistaapp.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.eric.prelistaapp.AppDatabase
import com.eric.prelistaapp.PreLista
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TelaInicial(navController: NavController) {
    val context = LocalContext.current
    val dao = AppDatabase.getDatabase(context).preListaDao()
    var listas by remember { mutableStateOf(listOf<PreLista>()) }
    var mostrarDialog by remember { mutableStateOf(false) }
    var listaSelecionada by remember { mutableStateOf<PreLista?>(null) }
    var nomeLista by remember { mutableStateOf("") }

    // Carrega os dados ao abrir a tela
    LaunchedEffect(Unit) {
        listas = withContext(Dispatchers.IO) {
            dao.listarTodas()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Pré-Listas Salvas", fontSize = 24.sp)

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(listas) { lista ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = lista.nome,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    navController.navigate("itens/${lista.id}")
                                }
                        )

                        IconButton(onClick = {
                            listaSelecionada = lista
                            mostrarDialog = true
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Excluir lista")
                        }
                    }


                    IconButton(onClick = {
                        listaSelecionada = lista
                        mostrarDialog = true
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Excluir lista")
                    }
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(onClick = { navController.navigate("cadastro") }) {
            Text("Criar nova pré-lista")
        }

        if (mostrarDialog && listaSelecionada != null) {
            AlertDialog(
                onDismissRequest = {
                    mostrarDialog = false
                    listaSelecionada = null
                },
                title = { Text("Excluir pré-lista") },
                text = { Text("Deseja realmente excluir a pré-lista \"${listaSelecionada?.nome}\"?") },
                confirmButton = {
                    TextButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val db = AppDatabase.getDatabase(context)
                            db.itemDao().deletarPorLista(listaSelecionada!!.id)
                            db.preListaDao().deletar(listaSelecionada!!)
                            val atualizadas = db.preListaDao().listarTodas()
                            withContext(Dispatchers.Main) {
                                listas = atualizadas
                                mostrarDialog = false
                                listaSelecionada = null
                            }
                        }
                    }) {
                        Text("Sim")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        mostrarDialog = false
                        listaSelecionada = null
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }

    if (mostrarDialog && listaSelecionada != null) {
        AlertDialog(
            onDismissRequest = {
                mostrarDialog = false
                listaSelecionada = null
            },
            title = { Text("Excluir pré-lista") },
            text = { Text("Deseja realmente excluir a pré-lista \"${listaSelecionada?.nome}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val db = AppDatabase.getDatabase(context)
                        db.itemDao().deletarPorLista(listaSelecionada!!.id)
                        db.preListaDao().deletar(listaSelecionada!!)
                        val atualizadas = db.preListaDao().listarTodas()
                        withContext(Dispatchers.Main) {
                            listas = atualizadas
                            mostrarDialog = false
                            listaSelecionada = null
                        }
                    }
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    mostrarDialog = false
                    listaSelecionada = null
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
