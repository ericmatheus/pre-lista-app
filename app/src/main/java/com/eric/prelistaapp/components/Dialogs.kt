package com.eric.prelistaapp.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy

import com.eric.prelistaapp.Item

@Composable
fun DialogExcluirItem(
    item: Item?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (item != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Excluir item") },
            text = { Text("Deseja realmente excluir o item \"${item.nome}\"?") },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun DialogAdicionarItem(
    nomeItem: String,
    onNomeChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Novo Item") },
        text = {
            OutlinedTextField(
                value = nomeItem,
                onValueChange = onNomeChange,
                label = { Text("Nome do item") }
            )
        }
    )
}

@Composable
fun DialogTextoGerado(
    texto: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("texto_lista", texto)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "Texto copiado!", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(Icons.Default.ContentCopy, contentDescription = "Copiar")
                }
                TextButton(onClick = onDismiss) {
                    Text("Fechar")
                }
            }
        },
        title = { Text("Itens Selecionados") },
        text = { Text(texto) }
    )
}


