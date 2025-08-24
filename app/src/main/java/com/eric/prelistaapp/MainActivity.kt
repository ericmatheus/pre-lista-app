package com.eric.prelistaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.eric.prelistaapp.screens.TelaCadastroLista
import com.eric.prelistaapp.screens.TelaInicial
import com.eric.prelistaapp.screens.TelaItensDaLista

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "home") {
                composable("home") { TelaInicial(navController) }
                composable("cadastro") { TelaCadastroLista(navController) }
                composable("itens/{listaId}") { backStackEntry ->
                    val listaId = backStackEntry.arguments?.getString("listaId")?.toIntOrNull()
                    listaId?.let { TelaItensDaLista(listaId) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaInicial() {
    // Usamos um navController "falso" só pro preview funcionar
    TelaInicial(navController = rememberNavController())
}

