package com.shoyoomc.cyberneticlauncher.ui.screens.content.elements

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.shoyoomc.cyberneticlauncher.cyber.theme.CyberThemeStudio
import com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoUi
import com.shoyoomc.cyberneticlauncher.cyber.theme.ThemeCatalog
import com.shoyoomc.cyberneticlauncher.ui.screens.content.settings.layouts.CardPosition
import com.shoyoomc.cyberneticlauncher.ui.screens.content.settings.layouts.SettingsCard

/** Entrada "Tema do launcher": abre o Estúdio de temas (cores em degradê, ajustes e prévia). */
@Composable
fun CyberPaletteCard(modifier: Modifier = Modifier, position: CardPosition = CardPosition.Top) {
    var open by remember { mutableStateOf(false) }
    SettingsCard(
        position = position,
        title = "Estúdio de temas",
        modifier = modifier,
        summary = "Tema atual: " + ThemeCatalog.byId(ShoyoUi.themeId).name + " · toque para trocar as cores",
        onClick = { open = true }
    )
    if (open) CyberThemeStudio(onDismiss = { open = false })
}
