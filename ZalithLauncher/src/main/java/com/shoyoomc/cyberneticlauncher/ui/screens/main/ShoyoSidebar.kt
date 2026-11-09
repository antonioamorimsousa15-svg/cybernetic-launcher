package com.shoyoomc.cyberneticlauncher.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shoyoomc.cyberneticlauncher.cyber.theme.NeonDivider
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.shoyoomc.cyberneticlauncher.cyber.theme.HudLabel
import com.shoyoomc.cyberneticlauncher.cyber.theme.ParaShape
import com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoColors
import com.shoyoomc.cyberneticlauncher.cyber.theme.paraAccents
import com.shoyoomc.cyberneticlauncher.cyber.theme.SideNavItem

/**
 * CYBERNETIC: menu lateral em pílulas (visual do launcher antigo).
 * Some ao escolher uma seção (tela cheia) e reabre pelo logo na barra superior.
 */
@Composable
fun ShoyoSidebar(
    inHome: Boolean,
    inVersions: Boolean,
    inDownload: Boolean,
    inAccounts: Boolean,
    inMultiplayer: Boolean,
    inSettings: Boolean,
    onHome: () -> Unit,
    onVersions: () -> Unit,
    onDownload: () -> Unit,
    onAccounts: () -> Unit,
    onMultiplayer: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.width(262.dp).fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SideNavItem(Icons.Filled.Home, "Início", inHome, onHome, 1, 0.dp)
            SideNavItem(Icons.Filled.Games, "Versões", inVersions, onVersions, 2, 8.dp)
            SideNavItem(Icons.Filled.Download, "Download", inDownload, onDownload, 3, 16.dp)
            SideNavItem(Icons.Filled.Person, "Contas", inAccounts, onAccounts, 4, 24.dp)
            SideNavItem(Icons.Filled.Group, "Multijogador", inMultiplayer, onMultiplayer, 5, 32.dp)
            Spacer(Modifier.height(2.dp))
            NeonDivider()
            Spacer(Modifier.height(2.dp))
            SideNavItem(Icons.Filled.Settings, "Ajustes", inSettings, onSettings, 6, 40.dp)
        }
        Spacer(Modifier.height(8.dp))
        // leitura de HUD do aparelho
        Column(
            Modifier.fillMaxWidth().clip(ParaShape(14.dp))
                .background(ShoyoColors.Glass)
                .border(1.dp, ShoyoColors.PanelBorder, ParaShape(14.dp))
                .paraAccents(14.dp)
                .padding(start = 28.dp, end = 20.dp, top = 12.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            HudLabel("// SISTEMA")
            Text("DISPOSITIVO  " + android.os.Build.MODEL.uppercase(), color = ShoyoColors.TextPrimary, fontFamily = FontFamily.Monospace, fontSize = 11.sp, maxLines = 1)
            Text("NÚCLEOS  " + Runtime.getRuntime().availableProcessors(), color = ShoyoColors.TextPrimary, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
            Text("ANDROID  " + android.os.Build.VERSION.RELEASE, color = ShoyoColors.TextPrimary, fontFamily = FontFamily.Monospace, fontSize = 11.sp)
        }
    }
}
