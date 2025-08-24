package com.eric.prelistaapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun TelaCadastroLista(navController: NavController) {
    var nomeLista by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Nova Pré-lista", fontSize = 22.sp)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = nomeLista,
            onValueChange = { nomeLista = it },
            label = { Text("Nome da lista") }
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            if (nomeLista.isNotBlank()) {
                val db = AppDatabase.getDatabase(context)
                val dao = db.preListaDao()

                CoroutineScope(Dispatchers.IO).launch {
                    dao.inserir(PreLista(nome = nomeLista))
                    println("Pré-lista salva: $nomeLista")
                }

                navController.popBackStack()
            }
        }) {
            Text("Salvar")
        }
    }
}