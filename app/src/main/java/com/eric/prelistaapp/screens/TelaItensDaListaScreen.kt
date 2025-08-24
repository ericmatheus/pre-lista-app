package com.eric.prelistaapp.screens

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.eric.prelistaapp.AppDatabase
import com.eric.prelistaapp.Item
import com.eric.prelistaapp.components.DialogAdicionarItem
import com.eric.prelistaapp.components.DialogExcluirItem
import com.eric.prelistaapp.components.TopBarMenu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorder
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import org.burnoutcrew.reorderable.*
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.background


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaItensDaLista(listaId: Int) {
    val context = LocalContext.current
    val dao = AppDatabase.getDatabase(context).itemDao()
    val listaDao = AppDatabase.getDatabase(context).preListaDao()

    var nomeLista by remember { mutableStateOf("") }
    var itens by remember { mutableStateOf(listOf<Item>()) }

    var mostrarDialog by remember { mutableStateOf(false) }
    var textoGerado by remember { mutableStateOf("") }

    var itemSelecionado by remember { mutableStateOf<Item?>(null) }
    var mostrarDialogExcluir by remember { mutableStateOf(false) }

    var mostrarDialogAdicionar by remember { mutableStateOf(false) }
    var novoItemNome by remember { mutableStateOf("") }

    var modoOrdenacao by remember { mutableStateOf(false) }
    var menuAberto by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        itens = withContext(Dispatchers.IO) {
            dao.listarPorLista(listaId)
        }

        nomeLista = withContext(Dispatchers.IO) {
            listaDao.buscarPorId(listaId)?.nome ?: "Itens"
        }
    }

    Scaffold(
        topBar = {
            TopBarMenu(
                nomeLista = nomeLista,
                menuAberto = remember { mutableStateOf(menuAberto) },
                onAdicionarClick = { mostrarDialogAdicionar = true },
                onOrdenarClick = { modoOrdenacao = true }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            val reorderState = rememberReorderableLazyListState(
                onMove = { from, to ->
                    val mutableList = itens.toMutableList()
                    val item = mutableList.removeAt(from.index)
                    mutableList.add(to.index, item)
                    itens = mutableList
                }
            )
            LazyColumn(
                state = reorderState.listState,
                modifier = Modifier
                    .weight(1f)
                    .reorderable(reorderState)
            ){
                items(itens, key = { it.id }) { item ->
                    ReorderableItem(reorderState, key = item.id) { isDragging ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .shadow(if (isDragging) 8.dp else 0.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .then(
                                    if (modoOrdenacao) {
                                        Modifier.detectReorder(reorderState)
                                    } else {
                                        Modifier.pointerInput(Unit) {
                                            detectTapGestures(
                                                onLongPress = {
                                                    itemSelecionado = item
                                                    mostrarDialogExcluir = true
                                                }
                                            )
                                        }
                                    }
                                )
                        )
                        {
                            Text(item.nome, modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                if (item.quantidade > 0) {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        dao.atualizar(item.copy(quantidade = item.quantidade - 1))
                                        itens = dao.listarPorLista(listaId)
                                    }
                                }
                            }) {
                                Text("-")
                            }
                            Text("${item.quantidade}", modifier = Modifier.padding(horizontal = 8.dp))
                            IconButton(onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    dao.atualizar(item.copy(quantidade = item.quantidade + 1))
                                    itens = dao.listarPorLista(listaId)
                                }
                            }) {
                                Text("+")
                            }
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            // Botão de confirmar ordenação (aparece só se modoOrdenacao for true)
            if (modoOrdenacao) {
                Button(
                    onClick = {
                        modoOrdenacao = false
                        CoroutineScope(Dispatchers.IO).launch {
                            itens.forEachIndexed { index, item ->
                                dao.atualizar(item.copy(ordem = index))
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirmar ordenação")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(Modifier.height(16.dp))

            // Botão de gerar texto
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val texto = itens
                            .filter { it.quantidade > 0 }
                            .joinToString("\n") { "${it.nome.uppercase()} [${it.quantidade}]" }

                        withContext(Dispatchers.Main) {
                            textoGerado = texto
                            mostrarDialog = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Gerar Texto da Lista")
            }

        }

        // Diálogo de confirmação de exclusão
        if (mostrarDialogExcluir && itemSelecionado != null) {

            DialogExcluirItem(
                item = itemSelecionado,
                onConfirm = {
                    CoroutineScope(Dispatchers.IO).launch {
                        dao.deletar(itemSelecionado!!)
                        val atualizados = dao.listarPorLista(listaId)
                        withContext(Dispatchers.Main) {
                            itens = atualizados
                            mostrarDialogExcluir = false
                            itemSelecionado = null
                        }
                    }
                 },
                onDismiss = { mostrarDialogExcluir = false; itemSelecionado = null }
            )
        }

        // Diálogo de adicionar item
        if (mostrarDialogAdicionar) {
            DialogAdicionarItem(
                nomeItem = novoItemNome,
                onNomeChange = { novoItemNome = it },
                onConfirm = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val ultimaOrdem = itens.maxByOrNull { it.ordem }?.ordem ?: -1
                        val novoItem = Item(nome = novoItemNome, listaId = listaId, ordem = ultimaOrdem + 1)
                        dao.inserir(novoItem)
                        val atualizados = dao.listarPorLista(listaId)
                        withContext(Dispatchers.Main) {
                            itens = atualizados
                            mostrarDialogAdicionar = false
                            novoItemNome = ""
                        }
                    }
                },
                onDismiss = { mostrarDialogAdicionar = false; novoItemNome = "" }
            )
        }

        // Diálogo de exibição do texto
        if (mostrarDialog) {
            AlertDialog(
                onDismissRequest = { mostrarDialog = false },
                confirmButton = {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = {
                            val clipboard =
                                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("texto_lista", textoGerado)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Texto copiado!", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copiar")
                        }
                        TextButton(onClick = { mostrarDialog = false }) {
                            Text("Fechar")
                        }
                    }
                },
                title = { Text("Itens Selecionados") },
                text = { Text(textoGerado) }
            )
        }
    }
}
