package com.shoyoomc.cyberneticlauncher.ui.screens.content.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoyoomc.cyberneticlauncher.cyber.theme.ParaShape
import com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoColors
import com.shoyoomc.cyberneticlauncher.cyber.theme.tap
import com.shoyoomc.cyberneticlauncher.ui.screens.TitledNavKey

/** Abas numeradas em paralelogramo, usadas em Ajustes, Versões e Download. */
@Composable
fun CyberTabRow(
    items: List<CategoryItem>,
    isSelected: (TitledNavKey) -> Boolean,
    onSelect: (TitledNavKey) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            val selected = isSelected(item.key)
            val shape = ParaShape(10.dp)
            Row(
                modifier = Modifier
                    .clip(shape)
                    .background(
                        if (selected) Brush.horizontalGradient(listOf(ShoyoColors.Accent, ShoyoColors.AccentDeep))
                        else Brush.horizontalGradient(listOf(ShoyoColors.Glass, ShoyoColors.Glass))
                    )
                    .border(1.dp, ShoyoColors.PanelBorder, shape)
                    .tap { onSelect(item.key) }
                    .padding(start = 20.dp, end = 16.dp, top = 5.dp, bottom = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides if (selected) ShoyoColors.OnAccent else ShoyoColors.Accent
                ) { item.icon() }
                Spacer(Modifier.width(7.dp))
                Column {
                    Text(
                        "%02d".format(index + 1),
                        color = if (selected) ShoyoColors.OnAccent else ShoyoColors.TextSecondary,
                        fontFamily = FontFamily.Monospace, fontSize = 8.sp, letterSpacing = 2.sp
                    )
                    Text(
                        stringResource(item.textRes), maxLines = 1,
                        color = if (selected) ShoyoColors.OnAccent else ShoyoColors.TextPrimary,
                        fontWeight = FontWeight.Bold, fontSize = 12.sp
                    )
                }
            }
        }
    }
}
