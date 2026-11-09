/*
 * CYBERNETIC LAUNCHER
 * Derivado do Zalith Launcher 2 (GPLv3). Copyright (C) 2025-2026 MovTery <movtery228@qq.com> and contributors.
 * Este programa é software livre: você pode redistribuí-lo e/ou modificá-lo sob os termos da
 * GNU General Public License v3 (ou posterior). Veja <https://www.gnu.org/licenses/gpl-3.0.txt>.
 */
package com.shoyoomc.cyberneticlauncher.cyber.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoyoomc.cyberneticlauncher.cyber.theme.BadgeKind
import com.shoyoomc.cyberneticlauncher.cyber.theme.DiamondRow
import com.shoyoomc.cyberneticlauncher.cyber.theme.GhostButton
import com.shoyoomc.cyberneticlauncher.cyber.theme.NeonButton
import com.shoyoomc.cyberneticlauncher.cyber.theme.NeonPanel
import com.shoyoomc.cyberneticlauncher.cyber.theme.SectionTitle
import com.shoyoomc.cyberneticlauncher.cyber.theme.SelectChip
import com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoColors
import com.shoyoomc.cyberneticlauncher.cyber.theme.StatusBadge
import com.shoyoomc.cyberneticlauncher.cyber.theme.tap
import com.shoyoomc.cyberneticlauncher.game.version.installed.Version

/** Botão redondo de ícone usado nas linhas (fixar, configurações, mais). */
@Composable
fun CyberRoundIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    enabled: Boolean = true,
    active: Boolean = false,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(50)
    Box(
        Modifier.size(40.dp).clip(shape)
            .border(1.dp, if (active) ShoyoColors.Accent else ShoyoColors.PanelBorder, shape)
            .alpha(if (enabled) 1f else 0.35f)
            .then(if (enabled) Modifier.tap(onClick) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, null, tint = if (active) ShoyoColors.Accent else ShoyoColors.TextPrimary, modifier = Modifier.size(20.dp))
    }
}

@Composable
fun CyberVersionRow(
    version: Version,
    selected: Boolean,
    onSelected: () -> Unit,
    onPin: () -> Unit,
    onSettings: () -> Unit,
    onRename: () -> Unit,
    onCopy: () -> Unit,
    onExport: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val valid = remember(version) { version.isValid() }
    val name = remember(version) { version.getVersionName() }
    val subtitle = remember(version) {
        if (!valid) "Inválida" else version.getVersionInfo()?.let { info ->
            buildString {
                append(info.minecraftVersion)
                info.loaderInfos.forEach { append(" · ").append(it.loader.displayName).append(' ').append(it.version) }
            }
        }.orEmpty()
    }
    var menu by remember { mutableStateOf(false) }

    DiamondRow(
        modifier = modifier,
        blocked = !valid,
        onClick = { if (!selected) onSelected() },
        trailing = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                StatusBadge(
                    if (!valid) "Inválida" else if (selected) "Selecionada" else "Disponível",
                    if (!valid) BadgeKind.Blocked else if (selected) BadgeKind.Selected else BadgeKind.Available
                )
                CyberRoundIcon(Icons.Filled.PushPin, enabled = valid, active = version.pinnedState, onClick = onPin)
                CyberRoundIcon(Icons.Filled.Settings, enabled = valid, onClick = onSettings)
                Box {
                    CyberRoundIcon(Icons.Filled.MoreHoriz, onClick = { menu = true })
                    DropdownMenu(expanded = menu, onDismissRequest = { menu = false }) {
                        DropdownMenuItem(text = { Text("Renomear") }, leadingIcon = { Icon(Icons.Filled.Edit, null) },
                            onClick = { menu = false; onRename() })
                        DropdownMenuItem(text = { Text("Copiar") }, leadingIcon = { Icon(Icons.Filled.FileCopy, null) },
                            onClick = { menu = false; onCopy() })
                        DropdownMenuItem(text = { Text("Exportar") }, leadingIcon = { Icon(Icons.Filled.IosShare, null) },
                            enabled = valid, onClick = { menu = false; onExport() })
                        DropdownMenuItem(text = { Text("Excluir") }, leadingIcon = { Icon(Icons.Filled.Delete, null) },
                            onClick = { menu = false; onDelete() })
                    }
                }
            }
        }
    ) {
        Column(Modifier.weight(1f)) {
            Text(name, color = ShoyoColors.TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            if (subtitle.isNotEmpty()) {
                Text(subtitle, color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.bodySmall, maxLines = 1)
            }
        }
    }
}

/**
 * Painel "Suas versões": título + contagem, filtros (Todas / Vanilla / Com loader), Atualizar e Instalar nova,
 * e a lista de versões instaladas.
 */
@Composable
fun CyberVersionsPanel(
    versions: List<Version>,
    currentVersion: Version?,
    categoryLabels: List<String>,
    selectedCategory: Int,
    onCategory: (Int) -> Unit,
    onRefresh: () -> Unit,
    onInstall: () -> Unit,
    emptyText: String,
    modifier: Modifier = Modifier,
    row: @Composable (Version) -> Unit
) {
    NeonPanel(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                SectionTitle("Suas versões")
                Text("${versions.size} instaladas", color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.bodyMedium)
            }
            GhostButton("Atualizar", Icons.Filled.Refresh, Modifier, onRefresh)
            Spacer(Modifier.width(10.dp))
            NeonButton("Instalar nova", Icons.Filled.Add, Modifier, onInstall)
        }
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            categoryLabels.forEachIndexed { i, label -> SelectChip(label, i == selectedCategory) { onCategory(i) } }
        }
        Spacer(Modifier.height(10.dp))
        if (versions.isEmpty()) {
            Box(Modifier.fillMaxSize()) {
                Text(emptyText, color = ShoyoColors.TextSecondary, modifier = Modifier.align(Alignment.Center))
            }
        } else {
            LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(versions, key = { it.toString() }) { v -> row(v) }
            }
        }
    }
}
