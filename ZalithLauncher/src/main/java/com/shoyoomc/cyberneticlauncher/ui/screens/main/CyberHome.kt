package com.shoyoomc.cyberneticlauncher.ui.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoyoomc.cyberneticlauncher.game.account.AccountsManager
import com.shoyoomc.cyberneticlauncher.game.version.installed.Version
import com.shoyoomc.cyberneticlauncher.game.version.installed.VersionsManager

private val HCyan = Color(0xFF3DE8FF)
private val HBlue = Color(0xFF1F7BFF)
private val HNavy = Color(0xFF06142A)
private val HLine = Color(0xFF3A8FA0)
private val HDim = Color(0xFFB4CCDB)

/** Tela Início: cartões de vidro (código próprio, estilo Cryogenic). */
@Composable
fun CyberHome(
    onPlay: (Version) -> Unit,
    onOpenVersions: () -> Unit,
    onOpenAccounts: () -> Unit,
    onOpenDownload: () -> Unit,
) {
    val account by AccountsManager.currentAccountFlow.collectAsState()
    val current by VersionsManager.currentVersion.collectAsState()
    val versions by VersionsManager.versions.collectAsState()
    val userName = account?.username ?: "Jogador"
    val mc = current?.getVersionInfo()?.minecraftVersion

    Column(Modifier.fillMaxSize().padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            GlassCard(Modifier.weight(2f).fillMaxHeight()) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Bem-vindo de volta,", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text(userName, color = HCyan, fontSize = 30.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text("Sua aventura congelada continua aqui.", color = HDim, fontSize = 12.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        if (mc != null) Tag(mc)
                    }
                    Spacer(Modifier.weight(1f))
                    GradientButton(
                        "▶  Jogar Agora", Modifier.width(190.dp)
                    ) { current?.let(onPlay) ?: onOpenVersions() }
                }
            }
            GlassCard(Modifier.weight(1f).fillMaxHeight()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("VERSÃO DO MINECRAFT", color = HCyan, fontSize = 10.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
                    Text(
                        current?.getVersionName() ?: "Nenhuma versão", color = Color.White, fontSize = 20.sp,
                        fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis
                    )
                    if (mc != null) Text(mc, color = HDim, fontSize = 12.sp)
                    Spacer(Modifier.weight(1f))
                    GradientButton("JOGAR", Modifier.fillMaxWidth()) { current?.let(onPlay) ?: onOpenVersions() }
                }
            }
        }
        Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            GlassCard(Modifier.weight(1.4f).fillMaxHeight()) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("Suas versões", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text("Ver todas ›", color = HCyan, fontSize = 12.sp, modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onOpenVersions
                        ))
                    }
                    if (versions.isEmpty()) {
                        Text("Suas versões instaladas aparecem aqui.", color = HDim, fontSize = 12.sp)
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(versions) { v ->
                                Row(
                                    Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp))
                                        .background(Color(0x33FFFFFF)).padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(Modifier.weight(1f)) {
                                        Text(v.getVersionName(), color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Text(v.getVersionInfo()?.minecraftVersion ?: "", color = HDim, fontSize = 10.sp)
                                    }
                                    Box(
                                        Modifier.size(32.dp).clip(CircleShape).border(1.dp, HCyan, CircleShape)
                                            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { onPlay(v) },
                                        contentAlignment = Alignment.Center
                                    ) { Text("▶", color = Color.White, fontSize = 12.sp) }
                                }
                            }
                        }
                    }
                }
            }
            GlassCard(Modifier.weight(1f).fillMaxHeight()) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Conta", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier.size(46.dp).clip(CircleShape).background(HNavy).border(2.dp, HCyan, CircleShape),
                            contentAlignment = Alignment.Center
                        ) { Text(userName.take(1).uppercase(), color = HCyan, fontSize = 20.sp, fontWeight = FontWeight.Bold) }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(userName, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text("● Conta", color = Color(0xFF4CFF9A), fontSize = 11.sp)
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    OutlineButton("Gerenciar Conta", Modifier.fillMaxWidth(), onOpenAccounts)
                }
            }
            GlassCard(Modifier.weight(1f).fillMaxHeight()) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Baixar", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Text("Minecraft, mods, shaders, texturas e mundos.", color = HDim, fontSize = 12.sp)
                    Spacer(Modifier.weight(1f))
                    GradientButton("Abrir Download  ›", Modifier.fillMaxWidth(), onOpenDownload)
                }
            }
        }
    }
}

/** Cartão de vidro: translúcido, borda ciano e cantos em "L" (moldura técnica). */
@Composable
fun GlassCard(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    val shape = RoundedCornerShape(28.dp)
    Box(
        modifier = modifier
            .clip(shape)
            .background(Brush.verticalGradient(listOf(Color(0x8C16365C), Color(0x990A1A33))))
            .border(BorderStroke(1.dp, HLine.copy(alpha = 0.75f)), shape),
        content = content
    )
}

@Composable
private fun Tag(text: String) {
    Text(
        text, color = Color(0xFF4CFFC4), fontSize = 10.sp, fontWeight = FontWeight.Bold,
        modifier = Modifier.clip(CircleShape).border(1.dp, Color(0xFF4CFFC4).copy(alpha = 0.7f), CircleShape)
            .padding(horizontal = 9.dp, vertical = 2.dp)
    )
}

@Composable
fun GradientButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier.height(42.dp).clip(CircleShape)
            .background(Brush.horizontalGradient(listOf(HBlue, HCyan)))
            .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape)
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) { Text(text, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold) }
}

@Composable
fun OutlineButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier.height(40.dp).clip(CircleShape)
            .border(1.5.dp, HCyan.copy(alpha = 0.8f), CircleShape)
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) { Text(text, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold) }
}
