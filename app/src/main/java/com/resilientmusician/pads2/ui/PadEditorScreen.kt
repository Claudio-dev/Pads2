package com.resilientmusician.pads2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.resilientmusician.pads2.model.MessageType
import com.resilientmusician.pads2.model.Pad
import com.resilientmusician.pads2.model.PadBehavior

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PadEditorScreen(pad: Pad, onSave: (Pad) -> Unit) {
    var label by remember { mutableStateOf(pad.label) }
    var channel by remember { mutableIntStateOf(pad.channel) }
    var messageType by remember { mutableStateOf(pad.messageType) }
    var controlNumber by remember { mutableIntStateOf(pad.controlNumber) }
    var behavior by remember { mutableStateOf(pad.behavior) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Editando Pad ${pad.id}", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = label,
            onValueChange = { label = it },
            label = { Text("Label") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Canal MIDI")
        Slider(
            value = channel.toFloat(),
            onValueChange = { channel = it.toInt().coerceIn(1, 16) },
            valueRange = 1f..16f,
            steps = 15
        )
        Text("Canal: $channel")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Tipo de Mensaje")
        Row {
            MessageType.values().forEach {
                RadioButton(
                    selected = messageType == it,
                    onClick = { messageType = it }
                )
                Text(it.name)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Número de Control")
        Slider(
            value = controlNumber.toFloat(),
            onValueChange = { controlNumber = it.toInt().coerceIn(0, 127) },
            valueRange = 0f..127f,
            steps = 126
        )
        Text("Control: $controlNumber")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Comportamiento")
        Row {
            PadBehavior.values().forEach {
                RadioButton(
                    selected = behavior == it,
                    onClick = { behavior = it }
                )
                Text(it.name)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            val updatedPad = pad.copy(
                label = label,
                channel = channel,
                messageType = messageType,
                controlNumber = controlNumber,
                behavior = behavior
            )
            onSave(updatedPad)
        }) {
            Text("Guardar")
        }
    }
}