package com.eric.prelistaapp.screens
//
//import android.content.ClipData
//import android.content.ClipboardManager
//import android.content.Context
//import android.widget.Toast
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ContentCopy
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import com.eric.prelistaapp.AppDatabase
//import com.eric.prelistaapp.Item
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.foundation.gestures.detectTapGestures
//import androidx.compose.material.icons.filled.DragHandle
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.ExperimentalMaterial3Api
//import org.burnoutcrew.reorderable.detectReorderAfterLongPress
//import org.burnoutcrew.reorderable.rememberReorderableLazyListState
//import org.burnoutcrew.reorderable.reorderable
//import org.burnoutcrew.reorderable.ReorderableItem
//
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TelaItensDaLista(listaId: Int) {
//    val context = LocalContext.current
//    val dao = AppDatabase.getDatabase(context).itemDao()
//    var nomeItem by remember { mutableStateOf("") }
//    var itens by remember { mutableStateOf(listOf<Item>()) }
//    var mostrarDialog by remember { mutableStateOf(false) }
//    var textoGerado by remember { mutableStateOf("") }
//    var itemSelecionado by remember { mutableStateOf<Item?>(null) }
//    var mostrarDialogExcluir by remember { mutableStateOf(false) }
//    var mostrarDialogOpcoes by remember { mutableStateOf(false) }
//    var menuAberto by remember { mutableStateOf(false) }
//    var nomeLista by remember { mutableStateOf("") }
//    val db = AppDatabase.getDatabase(context)
//    val itemDao = db.itemDao()
//    val listaDao = db.preListaDao()
//    var modoOrdenacao by remember { mutableStateOf(false) }
//    var mostrarDialogAdicionar by remember { mutableStateOf(false) }
//    var novoItemNome by remember { mutableStateOf("") }
//
//
//
//    val reorderState = rememberReorderableLazyListState(
//        onMove = { from, to ->
//            val mutableList = itens.toMutableList()
//            val item = mutableList.removeAt(from.index)
//            mutableList.add(to.index, item)
//            itens = mutableList
//        }
//    )
//
//    // Carrega os itens da lista
//    LaunchedEffect(Unit) {
//        itens = withContext(Dispatchers.IO) {
//            itemDao.listarPorLista(listaId)
//        }
//        nomeLista = withContext(Dispatchers.IO) {
//            return@withContext listaDao.buscarPorId(listaId)?.nome ?: "Itens"
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(nomeLista.uppercase()) },
//                actions = {
//                    IconButton(onClick = { menuAberto = true }) {
//                        Icon(Icons.Default.Menu, contentDescription = "Menu")
//                    }
//                    DropdownMenu(
//                        expanded = menuAberto,
//                        onDismissRequest = { menuAberto = false }
//                    ) {
//                        DropdownMenuItem(
//                            text = { Text("Adicionar item") },
//                            onClick = {
//                                menuAberto = false
//                                mostrarDialogAdicionar = true
//                            }
//                        )
//                        DropdownMenuItem(
//                            text = { Text(if (modoOrdenacao) "Concluir ordenação" else "Ordenar itens") },
//                            onClick = {
//                                menuAberto = false
//                                modoOrdenacao = !modoOrdenacao
//                            }
//                        )
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding) // aplica padding automático
//                .padding(24.dp) // seu padding manual, opcional
//        ) {
//            Spacer(Modifier.height(16.dp))
//
//            // Campo para adicionar novo item
////        Row {
////            OutlinedTextField(
////                value = nomeItem,
////                onValueChange = { nomeItem = it },
////                label = { Text("Novo item") },
////                modifier = Modifier.weight(1f)
////            )
////            Spacer(Modifier.width(8.dp))
////            Button(onClick = {
////                if (nomeItem.isNotBlank()) {
////                    val nomeSeguro = nomeItem
////                    CoroutineScope(Dispatchers.IO).launch {
////                        dao.inserir(Item(nome = nomeSeguro, listaId = listaId))
////                        val atualizados = dao.listarPorLista(listaId)
////                        withContext(Dispatchers.Main) {
////                            itens = atualizados
////                            nomeItem = ""
////                        }
////                    }
////                }
////            }) {
////                Text("Adicionar")
////            }
////        }
//
//            Spacer(Modifier.height(16.dp))
//
//            // Lista com rolagem automática
//            LazyColumn(
//                state = reorderState.listState,
//                modifier = Modifier
//                    .reorderable(reorderState)
//                    .detectReorderAfterLongPress(state = reorderState)
//                    .weight(1f)
//            ) {
//                items(itens, key = { it.id }) { item ->
//                    ReorderableItem(reorderState, key = item.id) { isDragging ->
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 8.dp),
//                                 verticalAlignment = Alignment.CenterVertically
//
//                        ) {
////                            // Ícone de arraste
////                            Icon(
////                                imageVector = Icons.Default.DragHandle,
////                                contentDescription = "Arrastar",
////                                modifier = Modifier
////                                    .padding(end = 8.dp)
////                            )
//                            Text(item.nome, modifier = Modifier
//                                    .weight(1f)
//                                    .then(
//                                        if (!modoOrdenacao)
//                                            Modifier.pointerInput(Unit) {
//                                                detectTapGestures(
//                                                    onLongPress = {
//                                                        itemSelecionado = item
//                                                        mostrarDialogOpcoes = true
//                                                    }
//                                                )
//                                            }
//                                        else Modifier
//                                    )
//                            )
//                            if (!modoOrdenacao) {
//                                IconButton(onClick = {
//                                    if (item.quantidade > 0) {
//                                        CoroutineScope(Dispatchers.IO).launch {
//                                            dao.atualizar(item.copy(quantidade = item.quantidade - 1))
//                                            val atualizados = dao.listarPorLista(listaId)
//                                            withContext(Dispatchers.Main) {
//                                                itens = atualizados
//                                            }
//                                        }
//                                    }
//                                }) {
//                                    Text("-")
//                                }
//                                Text("${item.quantidade}", modifier = Modifier.padding(horizontal = 8.dp))
//                                IconButton(onClick = {
//                                    CoroutineScope(Dispatchers.IO).launch {
//                                        dao.atualizar(item.copy(quantidade = item.quantidade + 1))
//                                        val atualizados = dao.listarPorLista(listaId)
//                                        withContext(Dispatchers.Main) {
//                                            itens = atualizados
//                                        }
//                                    }
//                                }) {
//                                    Text("+")
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (modoOrdenacao) {
//                Button(
//                    onClick = {
//                        modoOrdenacao = false
//
//                        // (Opcional) salvar nova ordem no banco:
//                        CoroutineScope(Dispatchers.IO).launch {
//                            itens.forEachIndexed { index, item ->
//                                dao.atualizar(item.copy(ordem = index))
//                            }
//                        }
//
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp)
//                ) {
//                    Text("Confirmar ordenação")
//                }
//            }
//
//            Spacer(Modifier.height(16.dp))
//
//            // Botão sempre visível
//            Button(
//                onClick = {
//                    CoroutineScope(Dispatchers.IO).launch {
//                        val texto = itens
//                            .filter { it.quantidade > 0 }
//                            .joinToString("\n") { "${it.nome.uppercase()} [${it.quantidade}]" }
//
//                        withContext(Dispatchers.Main) {
//                            textoGerado = texto
//                            mostrarDialog = true
//                        }
//                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Gerar Texto da Lista")
//            }
//            if (mostrarDialogAdicionar) {
//                AlertDialog(
//                    onDismissRequest = {
//                        mostrarDialogAdicionar = false
//                        novoItemNome = ""
//                    },
//                    confirmButton = {
//                        TextButton(onClick = {
//                            if (novoItemNome.isNotBlank()) {
//                                CoroutineScope(Dispatchers.IO).launch {
//                                    dao.inserir(Item(nome = novoItemNome, listaId = listaId))
//                                    val atualizados = dao.listarPorLista(listaId)
//                                    withContext(Dispatchers.Main) {
//                                        itens = atualizados
//                                        mostrarDialogAdicionar = false
//                                        novoItemNome = ""
//                                    }
//                                }
//                            }
//                        }) {
//                            Text("Salvar")
//                        }
//                    },
//                    dismissButton = {
//                        TextButton(onClick = {
//                            mostrarDialogAdicionar = false
//                            novoItemNome = ""
//                        }) {
//                            Text("Cancelar")
//                        }
//                    },
//                    title = { Text("Novo Item") },
//                    text = {
//                        androidx.compose.material3.OutlinedTextField(
//                            value = novoItemNome,
//                            onValueChange = { novoItemNome = it },
//                            label = { Text("Nome do item") }
//                        )
//                    }
//                )
//            }
//
//
//        }
//
//
//        // Diálogo de confirmação de exclusão
//        if (mostrarDialogExcluir && itemSelecionado != null) {
//            AlertDialog(
//                onDismissRequest = {
//                    mostrarDialogExcluir = false
//                    itemSelecionado = null
//                },
//                title = { Text("Excluir item") },
//                text = { Text("Deseja realmente excluir o item \"${itemSelecionado?.nome}\"?") },
//                confirmButton = {
//                    TextButton(onClick = {
//                        CoroutineScope(Dispatchers.IO).launch {
//                            dao.deletar(itemSelecionado!!)
//                            val atualizados = dao.listarPorLista(listaId)
//                            withContext(Dispatchers.Main) {
//                                itens = atualizados
//                                mostrarDialogExcluir = false
//                                itemSelecionado = null
//                            }
//                        }
//                    }) {
//                        Text("Sim")
//                    }
//                },
//                dismissButton = {
//                    TextButton(onClick = {
//                        mostrarDialogExcluir = false
//                        itemSelecionado = null
//                    }) {
//                        Text("Cancelar")
//                    }
//                }
//            )
//        }
//
//        // Diálogo de exibição do texto
//        if (mostrarDialog) {
//            AlertDialog(
//                onDismissRequest = { mostrarDialog = false },
//                confirmButton = {
//                    Row(
//                        horizontalArrangement = Arrangement.End,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        IconButton(onClick = {
//                            val clipboard =
//                                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//                            val clip = ClipData.newPlainText("texto_lista", textoGerado)
//                            clipboard.setPrimaryClip(clip)
//                            Toast.makeText(context, "Texto copiado!", Toast.LENGTH_SHORT).show()
//                        }) {
//                            Icon(Icons.Default.ContentCopy, contentDescription = "Copiar")
//                        }
//                        TextButton(onClick = { mostrarDialog = false }) {
//                            Text("Fechar")
//                        }
//                    }
//                },
//                title = { Text("Itens Selecionados") },
//                text = { Text(textoGerado) }
//            )
//        }
//
//        if (mostrarDialogOpcoes && itemSelecionado != null) {
//            AlertDialog(
//                onDismissRequest = {
//                    mostrarDialogOpcoes = false
//                    itemSelecionado = null
//                },
//                title = { Text("O que deseja fazer com \"${itemSelecionado?.nome}\"?") },
//                confirmButton = {
//                    Column {
//                        TextButton(onClick = {
                           ////  TODO: abrir tela ou caixa para edição (pode ser um AlertDialog com campo de texto)
//                            Toast.makeText(
//                                context,
//                                "Função de edição ainda não implementada",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            mostrarDialogOpcoes = false
//                        }) {
//                            Text("Editar")
//                        }
//                        TextButton(onClick = {
//                            CoroutineScope(Dispatchers.IO).launch {
//                                dao.deletar(itemSelecionado!!)
//                                val atualizados = dao.listarPorLista(listaId)
//                                withContext(Dispatchers.Main) {
//                                    itens = atualizados
//                                    mostrarDialogOpcoes = false
//                                    itemSelecionado = null
//                                }
//                            }
//                        }) {
//                            Text("Excluir")
//                        }
//                    }
//                },
//                dismissButton = {
//                    TextButton(onClick = {
//                        mostrarDialogOpcoes = false
//                        itemSelecionado = null
//                    }) {
//                        Text("Cancelar")
//                    }
//                }
//            )
//        }
//    }
//}