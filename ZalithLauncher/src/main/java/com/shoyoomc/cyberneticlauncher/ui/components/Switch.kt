/*
 * Zalith Launcher 2
 * Copyright (C) 2025-2026 MovTery <movtery228@qq.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/gpl-3.0.txt>.
 */

package com.shoyoomc.cyberneticlauncher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoColors

/**
 * Switch no estilo Cybernetic: trilho em pílula (degradê quando ligado, só contorno quando
 * desligado) e botão redondo. Sem animação.
 */
@Composable
fun DefaultSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource? = null
) {
    val source = interactionSource ?: remember { MutableInteractionSource() }
    val track = if (checked) {
        Modifier.background(Brush.horizontalGradient(listOf(ShoyoColors.Accent, ShoyoColors.Secondary)))
            .border(2.dp, Color.Transparent, CircleShape)
    } else {
        Modifier.background(ShoyoColors.Bg.copy(alpha = 0.55f))
            .border(2.dp, ShoyoColors.TextPrimary.copy(alpha = 0.9f), CircleShape)
    }
    Box(
        modifier = modifier
            .alpha(if (enabled) 1f else 0.45f)
            .size(width = 52.dp, height = 30.dp)
            .clip(CircleShape)
            .then(track)
            .toggleable(
                value = checked,
                enabled = enabled,
                role = Role.Switch,
                interactionSource = source,
                indication = null,
                onValueChange = onCheckedChange
            )
            .padding(4.dp),
        contentAlignment = if (checked) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            Modifier.size(22.dp).clip(CircleShape)
                .background(if (checked) ShoyoColors.Bg else Color.White),
            contentAlignment = Alignment.Center
        ) {
            if (checked) Box(Modifier.size(8.dp).clip(CircleShape).background(Color.White))
        }
    }
}
