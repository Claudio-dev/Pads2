package com.resilientmusician.pads2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.resilientmusician.pads2.model.Pad
import com.resilientmusician.pads2.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PadsApp() {
    val navController = rememberNavController()
    var pads by remember { mutableStateOf(List(6) { i -> Pad(i + 1) }) }
    var isEditing by remember { mutableStateOf(false) }
    var selectedPad by remember { mutableStateOf<Pad?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PADS2") },
                actions = {
                    IconButton(onClick = { isEditing = !isEditing }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        NavHost(navController = navController, startDestination = Screen.Pads.route) {
            composable(Screen.Pads.route) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                ) {
                    // Grid de pads 2x3 con separación uniforme
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Primera fila (pads 1, 2, 3)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            pads.take(3).forEach { pad ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .padding(2.dp) // Separación mínima
                                ) {
                                    PadItem(
                                        pad = pad,
                                        isEditing = isEditing,
                                        onEdit = {
                                            selectedPad = pad
                                            navController.navigate(Screen.Editor.route)
                                        },
                                        onTrigger = {
                                            // Aquí se envía el MIDI
                                        }
                                    )
                                }
                            }
                        }

                        // Segunda fila (pads 4, 5, 6)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            pads.drop(3).forEach { pad ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .padding(2.dp) // Separación mínima
                                ) {
                                    PadItem(
                                        pad = pad,
                                        isEditing = isEditing,
                                        onEdit = {
                                            selectedPad = pad
                                            navController.navigate(Screen.Editor.route)
                                        },
                                        onTrigger = {
                                            // Aquí se envía el MIDI
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            composable(Screen.Editor.route) {
                selectedPad?.let {
                    PadEditorScreen(
                        pad = it,
                        onSave = { updatedPad ->
                            pads = pads.map { p -> if (p.id == updatedPad.id) updatedPad else p }
                            navController.popBackStack()
                        }
                    )
                }
            }
            composable(Screen.Settings.route) {
                SettingsScreen { navController.popBackStack() }
            }
        }
    }
}