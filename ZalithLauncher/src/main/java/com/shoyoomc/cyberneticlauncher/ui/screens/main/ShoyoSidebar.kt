package com.shoyoomc.cyberneticlauncher.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
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
import com.shoyoomc.cyberneticlauncher.cyber.theme.NeonPanel
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
    NeonPanel(modifier.width(230.dp).fillMaxHeight()) {
        Column(
            Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SideNavItem(Icons.Filled.Home, "Início", inHome, onHome)
            SideNavItem(Icons.Filled.Games, "Versões", inVersions, onVersions)
            SideNavItem(Icons.Filled.Download, "Download", inDownload, onDownload)
            SideNavItem(Icons.Filled.Person, "Contas", inAccounts, onAccounts)
            SideNavItem(Icons.Filled.Group, "Multijogador", inMultiplayer, onMultiplayer)
            Spacer(Modifier.height(4.dp))
            NeonDivider()
            Spacer(Modifier.height(4.dp))
            SideNavItem(Icons.Filled.Settings, "Ajustes", inSettings, onSettings)
        }
    }
}
