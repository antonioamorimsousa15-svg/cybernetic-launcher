package com.shoyoomc.cyberneticlauncher.cyber.screens

import androidx.compose.foundation.background
import com.shoyoomc.cyberneticlauncher.cyber.theme.techFrame
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoyoomc.cyberneticlauncher.cyber.theme.ParaShape
import com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoColors
import com.shoyoomc.cyberneticlauncher.cyber.theme.ShoyoUi
import com.shoyoomc.cyberneticlauncher.cyber.theme.tap

@Composable
fun CyberHomeScreen(
    accountName: String,
    isOfflineAccount: Boolean,
    versionName: String?,
    onPlay: () -> Unit,
    onVersions: () -> Unit,
    onAccounts: () -> Unit,
    onSettings: () -> Unit = {}
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) { ShoyoUi.load(context.applicationContext) }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ---- linha 1: boas-vindas + versão ----
        Row(Modifier.fillMaxWidth().height(180.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            HomeCard(Modifier.weight(1.7f).fillMaxSize()) {
                Text("Bem-vindo de volta,", color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleSmall)
                Text(accountName, color = ShoyoColors.Accent, fontSize = 34.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Text("Sua aventura congelada continua aqui.", color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.weight(1f))
                Pill(if (versionName != null) "▶  Jogar Agora" else "⬇  Baixar Minecraft", Modifier.width(200.dp),
                    onClick = if (versionName != null) onPlay else onVersions)
            }
            HomeCard(Modifier.weight(1f).fillMaxSize()) {
                Text("Versão do Minecraft", color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(8.dp))
                Text(versionName ?: "Nenhuma versão", color = ShoyoColors.TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(if (versionName != null) "Pronta para jogar" else "Escolha uma versão para começar",
                    color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.labelSmall)
                Spacer(Modifier.weight(1f))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Pill(if (versionName != null) "JOGAR" else "Instalar", Modifier.weight(1f),
                        onClick = if (versionName != null) onPlay else onVersions)
                    RoundIcon("⚙", onVersions)
                }
            }
        }

        // ---- linha 2: versões, conta, destaque ----
        Row(Modifier.fillMaxWidth().height(170.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            HomeCard(Modifier.weight(1.25f).fillMaxSize()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Suas versões", color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                    Text("Ver todas ›", color = ShoyoColors.Accent, style = MaterialTheme.typography.labelMedium, modifier = Modifier.tap(onVersions))
                }
                Spacer(Modifier.weight(1f))
                Text(versionName ?: "Suas versões instaladas aparecerão aqui.", color = ShoyoColors.TextSecondary,
                    style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(Modifier.weight(1f))
            }
            HomeCard(Modifier.weight(1.25f).fillMaxSize()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Conta", color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                    Pill("Skins", Modifier.width(80.dp), onClick = onAccounts)
                }
                Spacer(Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(44.dp).clip(CircleShape).background(ShoyoColors.IconChip)
                            .border(1.dp, ShoyoColors.Accent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) { Text(accountName.take(1).uppercase(), color = ShoyoColors.Accent, fontWeight = FontWeight.Bold, fontSize = 20.sp) }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(accountName, color = ShoyoColors.TextPrimary, fontWeight = FontWeight.Bold, maxLines = 1)
                        Text(if (isOfflineAccount) "● Conta offline" else "● Conta online", color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.labelSmall)
                    }
                }
                Spacer(Modifier.weight(1f))
                GhostPill("⚙  Gerenciar Conta", Modifier.fillMaxWidth(), onAccounts)
            }
            Box(
                Modifier.weight(0.8f).fillMaxSize().clip(ParaShape(22.dp))
                    .background(Brush.verticalGradient(listOf(ShoyoColors.AccentDeep, ShoyoColors.Bg, ShoyoColors.SecondaryDeep)))
                    .border(1.dp, ShoyoColors.PanelBorder, ParaShape(22.dp)),
                contentAlignment = Alignment.Center
            ) { Text("❄", color = ShoyoColors.Accent, fontSize = 48.sp) }
        }

        // ---- linha 3: shaders e mods ----
        Row(Modifier.fillMaxWidth().height(150.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            HomeCard(Modifier.weight(1f).fillMaxSize()) {
                Text("💡 Shaders", color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleSmall)
                Text("Deixe seu mundo mais bonito com shaders.", color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.weight(1f))
                Pill("Explorar ›", Modifier.fillMaxWidth(), onClick = onVersions)
            }
            HomeCard(Modifier.weight(1f).fillMaxSize()) {
                Text("🧩 Mods", color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleSmall)
                Text("Turbine suas versões com mods novos.", color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.weight(1f))
                Pill("Ver mods ›", Modifier.fillMaxWidth(), onClick = onVersions)
            }
        }

        // ---- configurações rápidas ----
        HomeCard(Modifier.fillMaxWidth()) {
            Text("⚙  Configurações Rápidas", color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf("Gráficos", "Jogo", "Toque", "Aparência").forEach {
                    Box(
                        Modifier.weight(1f).height(54.dp).clip(ParaShape(14.dp))
                            .background(ShoyoColors.Glass).border(1.dp, ShoyoColors.PanelBorder, ParaShape(14.dp))
                            .tap(onSettings),
                        contentAlignment = Alignment.Center
                    ) { Text(it, color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.labelLarge) }
                }
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun HomeCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    val shape = ParaShape(22.dp)
    Column(
        modifier.clip(shape).background(ShoyoColors.Glass).border(1.dp, ShoyoColors.PanelBorder, shape)
            .techFrame().padding(14.dp),
        content = content
    )
}

@Composable
private fun Pill(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier.height(40.dp).clip(CircleShape)
            .background(Brush.horizontalGradient(listOf(ShoyoColors.Accent, ShoyoColors.Secondary)))
            .tap(onClick),
        contentAlignment = Alignment.Center
    ) { Text(text, color = ShoyoColors.OnAccent, fontWeight = FontWeight.Bold, maxLines = 1) }
}

@Composable
private fun GhostPill(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier.height(38.dp).clip(CircleShape).border(1.dp, ShoyoColors.PanelBorder, CircleShape).tap(onClick),
        contentAlignment = Alignment.Center
    ) { Text(text, color = ShoyoColors.TextPrimary, fontWeight = FontWeight.Bold, maxLines = 1) }
}

@Composable
private fun RoundIcon(glyph: String, onClick: () -> Unit) {
    Box(
        Modifier.size(40.dp).clip(CircleShape).border(1.dp, ShoyoColors.PanelBorder, CircleShape).tap(onClick),
        contentAlignment = Alignment.Center
    ) { Text(glyph, color = ShoyoColors.TextPrimary) }
}
