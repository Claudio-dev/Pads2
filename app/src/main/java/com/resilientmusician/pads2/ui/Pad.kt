package com.resilientmusician.pads2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resilientmusician.pads2.model.Pad
import com.resilientmusician.pads2.ui.theme.PadColor
import com.resilientmusician.pads2.ui.theme.PadPressedColor
import com.resilientmusician.pads2.ui.theme.PadBorderColor

@Composable
fun PadItem(pad: Pad, isEditing: Boolean, onEdit: () -> Unit, onTrigger: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = if (isPressed) PadPressedColor else PadColor,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 2.dp,
                color = PadBorderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (isEditing) {
                            onEdit()
                        } else {
                            onTrigger()
                            isPressed = true
                        }
                    },
                    onLongPress = {
                        if (isEditing) onEdit()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            pad.iconRes?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .weight(1f, fill = false)
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = pad.label,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f, fill = false)
            )
            if (isEditing) {
                Text(
                    text = "Pad ${pad.id}",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    // Reset pressed state after a short delay
    if (isPressed) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(100)
            isPressed = false
        }
    }
}