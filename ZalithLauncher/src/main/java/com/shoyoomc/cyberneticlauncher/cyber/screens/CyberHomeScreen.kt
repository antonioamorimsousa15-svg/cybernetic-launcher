package com.shoyoomc.cyberneticlauncher.cyber.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoyoomc.cyberneticlauncher.cyber.theme.BadgeKind
import com.shoyoomc.cyberneticlauncher.cyber.theme.GhostButton
import com.shoyoomc.cyberneticlauncher.cyber.theme.NeonButton
import com.shoyoomc.cyberneticlauncher.cyber.theme.NeonPanel
import com.shoyoomc.cyberneticlauncher.cyber.theme.SectionTitle
import com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoColors
import com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoUi
import com.shoyoomc.cyberneticlauncher.cyber.theme.StatusBadge

/**
 * Tela Início do CYBERNETIC LAUNCHER (visual do launcher antigo),
 * ligada ao núcleo: conta e versão reais, botão JOGAR inicia o jogo.
 */
@Composable
fun CyberHomeScreen(
    accountName: String,
    isOfflineAccount: Boolean,
    versionName: String?,
    onPlay: () -> Unit,
    onVersions: () -> Unit,
    onAccounts: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) { ShoyoUi.load(context.applicationContext) }

    Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        // ---- coluna da esquerda: boas-vindas + versões ----
        Column(Modifier.weight(1.4f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            NeonPanel(Modifier.fillMaxWidth().weight(1.3f)) {
                Text("Bem-vindo de volta,", color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.titleMedium)
                Text(accountName, color = ShoyoColors.TextPrimary, fontSize = 44.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Text("Sua aventura continua aqui.", color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.weight(1f))
                NeonButton("JOGAR", Icons.Filled.PlayArrow, Modifier.fillMaxWidth(), onPlay)
            }
            NeonPanel(Modifier.fillMaxWidth().weight(1f)) {
                SectionTitle("Suas versões")
                Spacer(Modifier.height(6.dp))
                Text(
                    "Escolha, instale ou troque a versão do jogo.",
                    color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.weight(1f))
                GhostButton("Ver versões", Icons.Filled.Games, Modifier.fillMaxWidth(), onVersions)
            }
        }
        // ---- coluna da direita: versão selecionada + conta ----
        Column(Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            NeonPanel(Modifier.fillMaxWidth().weight(1.2f)) {
                SectionTitle("Versão do Minecraft")
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(56.dp).clip(RoundedCornerShape(14.dp)).background(ShoyoColors.IconChip),
                        contentAlignment = Alignment.Center
                    ) { Icon(Icons.Filled.Download, null, tint = ShoyoColors.Accent) }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            versionName ?: "Nenhuma versão",
                            color = ShoyoColors.TextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold, maxLines = 1
                        )
                        Spacer(Modifier.height(4.dp))
                        if (versionName != null) StatusBadge("Selecionada", BadgeKind.Selected)
                    }
                }
                Spacer(Modifier.weight(1f))
                GhostButton("Trocar versão", Icons.Filled.Games, Modifier.fillMaxWidth(), onVersions)
            }
            NeonPanel(Modifier.fillMaxWidth().weight(1f)) {
                SectionTitle("Conta")
                Spacer(Modifier.height(6.dp))
                Text(accountName, color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                Text(
                    if (isOfflineAccount) "Conta offline" else "Conta online",
                    color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.weight(1f))
                GhostButton("Gerenciar conta", Icons.Filled.Settings, Modifier.fillMaxWidth(), onAccounts)
            }
        }
    }
}
