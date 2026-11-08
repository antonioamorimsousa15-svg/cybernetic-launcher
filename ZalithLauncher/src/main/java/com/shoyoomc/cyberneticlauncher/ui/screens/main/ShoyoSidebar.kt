package com.shoyoomc.cyberneticlauncher.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shoyoomc.cyberneticlauncher.R

/**
 * SHOYO: menu lateral em pílulas. Some ao escolher uma seção (tela cheia) e reabre
 * pelo botão de menu da barra superior. Sem ripple e sem animações.
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
    Column(
        modifier = modifier
            .width(210.dp)
            .fillMaxHeight()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.85f))
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SidebarItem(R.drawable.ic_home_filled, stringResource(R.string.shoyo_nav_home), inHome, onHome)
        SidebarItem(R.drawable.ic_sports_esports_filled, stringResource(R.string.shoyo_nav_versions), inVersions, onVersions)
        SidebarItem(R.drawable.ic_download_2_filled, stringResource(R.string.generic_download), inDownload, onDownload)
        SidebarItem(R.drawable.ic_person_filled, stringResource(R.string.shoyo_nav_accounts), inAccounts, onAccounts)
        SidebarItem(R.drawable.ic_group_filled, stringResource(R.string.terracotta), inMultiplayer, onMultiplayer)
        SidebarItem(R.drawable.ic_settings_filled, stringResource(R.string.generic_setting), inSettings, onSettings)
    }
}

@Composable
private fun SidebarItem(
    iconRes: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    val bg = if (selected) scheme.primaryContainer else scheme.surfaceContainerHigh.copy(alpha = 0.6f)
    val fg = if (selected) scheme.onPrimaryContainer else scheme.onSurface

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(bg)
            // indication = null: sem efeito de ripple ao tocar
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = if (selected) scheme.primary else fg,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = label,
            color = fg,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
    }
}
