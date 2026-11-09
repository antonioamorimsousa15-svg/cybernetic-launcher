package com.shoyoomc.cyberneticlauncher.cyber.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoyoomc.cyberneticlauncher.cyber.theme.BadgeKind
import com.shoyoomc.cyberneticlauncher.cyber.theme.HeroPlayButton
import com.shoyoomc.cyberneticlauncher.cyber.theme.HudLabel
import com.shoyoomc.cyberneticlauncher.cyber.theme.HudStat
import com.shoyoomc.cyberneticlauncher.cyber.theme.NeonPanel
import com.shoyoomc.cyberneticlauncher.cyber.theme.ParaShape
import com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoColors
import com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoUi
import com.shoyoomc.cyberneticlauncher.cyber.theme.StatusBadge
import com.shoyoomc.cyberneticlauncher.cyber.theme.TelemetryBars
import com.shoyoomc.cyberneticlauncher.cyber.theme.tap

/** Início do CYBERNETIC: HUD em paralelogramos, ligado ao núcleo (conta/versão reais, JOGAR inicia o jogo). */
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
        // ---------- coluna principal ----------
        Column(Modifier.weight(1.5f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            NeonPanel(Modifier.fillMaxWidth().weight(1.35f)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    HudLabel("// bem-vindo de volta, operador")
                    HudLabel(if (isOfflineAccount) "conta offline" else "conta online", color = ShoyoColors.TextSecondary)
                }
                Text(
                    accountName + "_", color = ShoyoColors.TextPrimary, fontSize = 46.sp,
                    fontWeight = FontWeight.Black, maxLines = 1
                )
                Text("Sua aventura continua aqui.", color = ShoyoColors.TextSecondary, fontSize = 15.sp)
                Spacer(Modifier.weight(1f))
                HeroPlayButton("Jogar", Modifier.fillMaxWidth(), onPlay)
            }
            NeonPanel(Modifier.fillMaxWidth().weight(1f)) {
                HudLabel("// suas versões")
                Spacer(Modifier.height(8.dp))
                Text("Escolha, instale ou troque a versão do jogo.", color = ShoyoColors.TextSecondary, fontSize = 14.sp)
                Spacer(Modifier.weight(1f))
                Box(
                    Modifier.fillMaxWidth().clip(ParaShape(12.dp))
                        .border(1.dp, ShoyoColors.PanelBorder, ParaShape(12.dp))
                        .tap(onVersions).padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("VER VERSÕES", color = ShoyoColors.TextPrimary, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
                }
            }
        }
        // ---------- coluna lateral ----------
        Column(Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            NeonPanel(Modifier.fillMaxWidth().weight(1.25f)) {
                HudLabel("// versão ativa")
                Text(
                    versionName ?: "NENHUMA", color = ShoyoColors.TextPrimary, fontSize = 30.sp,
                    fontWeight = FontWeight.Black, maxLines = 1
                )
                Spacer(Modifier.height(6.dp))
                if (versionName != null) StatusBadge("Selecionada", BadgeKind.Selected)
                Spacer(Modifier.weight(1f))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    HudStat("Render", "GL")
                    HudStat("Perfil", "OFFLINE".takeIf { isOfflineAccount } ?: "ONLINE")
                }
                Spacer(Modifier.height(8.dp))
                Box(
                    Modifier.fillMaxWidth().clip(ParaShape(10.dp)).border(1.dp, ShoyoColors.PanelBorder, ParaShape(10.dp))
                        .tap(onVersions).padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) { Text("TROCAR VERSÃO", color = ShoyoColors.TextPrimary, fontWeight = FontWeight.Bold, letterSpacing = 2.sp, fontSize = 12.sp) }
            }
            NeonPanel(Modifier.fillMaxWidth().weight(1f)) {
                HudLabel("// conta ativa")
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(46.dp).clip(ParaShape(10.dp)).background(ShoyoColors.IconChip),
                        contentAlignment = Alignment.Center
                    ) { Text(accountName.take(1).uppercase(), color = ShoyoColors.Accent, fontWeight = FontWeight.Black, fontSize = 22.sp) }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(accountName, color = ShoyoColors.TextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp, maxLines = 1)
                        Text("GUARDA-ROUPA DISPONÍVEL", color = ShoyoColors.TextSecondary, fontFamily = FontFamily.Monospace, fontSize = 10.sp, letterSpacing = 1.sp)
                    }
                }
                Spacer(Modifier.weight(1f))
                Box(
                    Modifier.fillMaxWidth().clip(ParaShape(10.dp)).border(1.dp, ShoyoColors.PanelBorder, ParaShape(10.dp))
                        .tap(onAccounts).padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) { Text("GERENCIAR CONTA", color = ShoyoColors.TextPrimary, fontWeight = FontWeight.Bold, letterSpacing = 2.sp, fontSize = 12.sp) }
            }
        }
    }
}
