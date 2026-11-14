package com.shoyoomc.aflecklauncher.ui.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Cores do esqueleto Cybernetic (azul-marinho + ciano). */
internal val AfPanel = Color(0xEB0A3558)
internal val AfPanelDeep = Color(0xF2082848)
internal val AfBorder = Color(0xFF1ED8F5)
internal val AfAccent = Color(0xFF22E3F7)
internal val AfAccent2 = Color(0xFF4DF2D8)
internal val AfNavy = Color(0xFF05213F)
internal val AfText = Color.White
internal val AfDim = Color(0xFFB9D6E6)

internal fun Modifier.afClick(onClick: () -> Unit): Modifier = composed {
    clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick)
}

/** Painel de vidro azul-marinho com borda ciano. */
@Composable
internal fun AfPanel(
    modifier: Modifier = Modifier,
    radius: Dp = 28.dp,
    deep: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(radius)
    Box(
        modifier = modifier
            .clip(shape)
            .background(if (deep) AfPanelDeep else AfPanel)
            .border(BorderStroke(1.5.dp, AfBorder), shape),
        content = content
    )
}

/** Botão principal: degradê ciano, texto escuro. */
@Composable
internal fun AfButton(text: String, modifier: Modifier = Modifier, icon: Int? = null, onClick: () -> Unit) {
    Row(
        modifier = modifier.height(50.dp).clip(CircleShape)
            .background(Brush.horizontalGradient(listOf(AfAccent, AfAccent2)))
            .afClick(onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            Icon(painterResource(icon), null, tint = AfNavy, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, color = AfNavy, fontSize = 15.sp, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

/** Botão secundário: só contorno ciano. */
@Composable
internal fun AfOutlineButton(text: String, modifier: Modifier = Modifier, icon: Int? = null, onClick: () -> Unit) {
    Row(
        modifier = modifier.height(44.dp).clip(CircleShape)
            .background(AfNavy.copy(alpha = 0.5f))
            .border(1.5.dp, AfBorder, CircleShape)
            .afClick(onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            Icon(painterResource(icon), null, tint = AfAccent, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, color = AfText, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

/** Círculo com ícone (preenchido em ciano ou escuro com contorno). */
@Composable
internal fun AfIconCircle(icon: Int, size: Dp = 40.dp, filled: Boolean = false, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(size).clip(CircleShape)
            .background(if (filled) Brush.horizontalGradient(listOf(AfAccent, AfAccent2)) else Brush.horizontalGradient(listOf(AfNavy, AfNavy)))
            .then(if (filled) Modifier else Modifier.border(1.5.dp, AfBorder, CircleShape)),
        contentAlignment = Alignment.Center
    ) {
        Icon(painterResource(icon), null, tint = if (filled) AfNavy else AfText, modifier = Modifier.size(size * 0.5f))
    }
}
