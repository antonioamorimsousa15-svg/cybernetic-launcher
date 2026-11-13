package com.shoyoomc.cyberneticlauncher.ui.screens.main

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

/** Cores do esqueleto Cybernetic (marrom-escuro + amarelo/âmbar). */
internal val CyPanel = Color(0xEB2A1B08)
internal val CyPanelDeep = Color(0xF21E1305)
internal val CyBorder = Color(0xFFFFC21A)
internal val CyAccent = Color(0xFFFFD21F)
internal val CyAccent2 = Color(0xFFFF9A1F)
internal val CyNavy = Color(0xFF1A1004)
internal val CyText = Color.White
internal val CyDim = Color(0xFFEBD7A8)

internal fun Modifier.cyClick(onClick: () -> Unit): Modifier = composed {
    clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick)
}

/** Painel de vidro escuro com borda amarela. */
@Composable
internal fun CyPanel(
    modifier: Modifier = Modifier,
    radius: Dp = 28.dp,
    deep: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(radius)
    Box(
        modifier = modifier
            .clip(shape)
            .background(if (deep) CyPanelDeep else CyPanel)
            .border(BorderStroke(1.5.dp, CyBorder), shape),
        content = content
    )
}

/** Botão principal: degradê amarelo, texto escuro. */
@Composable
internal fun CyButton(text: String, modifier: Modifier = Modifier, icon: Int? = null, onClick: () -> Unit) {
    Row(
        modifier = modifier.height(50.dp).clip(CircleShape)
            .background(Brush.horizontalGradient(listOf(CyAccent, CyAccent2)))
            .cyClick(onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            Icon(painterResource(icon), null, tint = CyNavy, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, color = CyNavy, fontSize = 15.sp, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

/** Botão secundário: só contorno amarelo. */
@Composable
internal fun CyOutlineButton(text: String, modifier: Modifier = Modifier, icon: Int? = null, onClick: () -> Unit) {
    Row(
        modifier = modifier.height(44.dp).clip(CircleShape)
            .background(CyNavy.copy(alpha = 0.5f))
            .border(1.5.dp, CyBorder, CircleShape)
            .cyClick(onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            Icon(painterResource(icon), null, tint = CyAccent, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, color = CyText, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

/** Círculo com ícone (preenchido em amarelo ou escuro com contorno). */
@Composable
internal fun CyIconCircle(icon: Int, size: Dp = 40.dp, filled: Boolean = false, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(size).clip(CircleShape)
            .background(if (filled) Brush.horizontalGradient(listOf(CyAccent, CyAccent2)) else Brush.horizontalGradient(listOf(CyNavy, CyNavy)))
            .then(if (filled) Modifier else Modifier.border(1.5.dp, CyBorder, CircleShape)),
        contentAlignment = Alignment.Center
    ) {
        Icon(painterResource(icon), null, tint = if (filled) CyNavy else CyText, modifier = Modifier.size(size * 0.5f))
    }
}
