package com.shoyoomc.cyberneticlauncher.ui.screens.main

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoyoomc.cyberneticlauncher.R
import com.shoyoomc.cyberneticlauncher.game.account.AccountsManager
import com.shoyoomc.cyberneticlauncher.game.version.installed.Version
import com.shoyoomc.cyberneticlauncher.game.version.installed.VersionsManager

/** Tela Início, no esqueleto desenhado pelo usuário. */
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
    val userName = account?.username
    val mc = current?.getVersionInfo()?.minecraftVersion

    Column(Modifier.fillMaxSize().padding(8.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(Modifier.fillMaxWidth().weight(1.25f), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            // Bem-vindo de volta
            CyPanel(Modifier.weight(2.4f).fillMaxHeight()) {
                Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(R.drawable.ic_package_2_outlined), null, tint = CyAccent, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Bem-vindo de volta,", color = CyText, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        userName ?: "Jogador", color = CyAccent, fontSize = 30.sp, fontWeight = FontWeight.Bold,
                        maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                    Text("Sua aventura congelada continua aqui.", color = CyText, fontSize = 13.sp)
                    Spacer(Modifier.weight(1f))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        if (current != null) {
                            CyButton("JOGAR", Modifier.weight(1f), R.drawable.ic_play_arrow_filled) { current?.let(onPlay) }
                        } else {
                            CyButton("BAIXAR MINECRAFT", Modifier.weight(1f), R.drawable.ic_download_2_filled, onOpenDownload)
                        }
                        CyIconCircle(R.drawable.ic_image_filled, size = 50.dp)
                    }
                }
            }
            // Conta
            CyPanel(Modifier.weight(1.5f).fillMaxHeight()) {
                Column(Modifier.fillMaxSize().padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(R.drawable.ic_person_filled), null, tint = CyAccent, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Conta", color = CyText, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Row(
                            Modifier.clip(CircleShape).background(CyAccent).cyClick(onOpenAccounts)
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painterResource(R.drawable.ic_checkroom), null, tint = CyNavy, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Skins", color = CyNavy, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier.size(54.dp).clip(CircleShape).background(CyNavy).border(2.dp, CyBorder, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            if (userName == null) {
                                Icon(painterResource(R.drawable.ic_add), null, tint = CyText, modifier = Modifier.size(28.dp))
                            } else {
                                Text(userName.take(1).uppercase(), color = CyAccent, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                userName ?: "Adicionar Conta", color = CyText, fontSize = 15.sp, fontWeight = FontWeight.Bold,
                                maxLines = 1, overflow = TextOverflow.Ellipsis
                            )
                            Text(if (userName == null) "● Nenhuma conta" else "● Conta", color = CyDim, fontSize = 11.sp)
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    CyOutlineButton("Gerenciar Conta", Modifier.fillMaxWidth(), R.drawable.ic_settings_filled, onOpenAccounts)
                }
            }
            // Imagem 9:16 (plano de fundo)
            Box(
                Modifier.weight(1f).fillMaxHeight().clip(RoundedCornerShape(28.dp))
                    .border(2.dp, CyBorder, RoundedCornerShape(28.dp))
            ) {
                Image(
                    painterResource(R.drawable.cyber_bg), null,
                    contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()
                )
                CyIconCircle(R.drawable.ic_image_filled, size = 40.dp, modifier = Modifier.align(Alignment.TopEnd).padding(8.dp))
                Text(
                    "9:16", color = CyText, fontSize = 13.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomStart).padding(10.dp).clip(RoundedCornerShape(10.dp))
                        .background(CyNavy.copy(alpha = 0.85f)).padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
        Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            // Suas versões
            CyPanel(Modifier.weight(1.7f).fillMaxHeight()) {
                Column(Modifier.fillMaxSize().padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(R.drawable.ic_package_2_outlined), null, tint = CyAccent, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Suas versões", color = CyText, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text("Ver todas ›", color = CyAccent, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.cyClick(onOpenVersions))
                    }
                    if (versions.isEmpty()) {
                        Text("Suas versões instaladas aparecem aqui.", color = CyDim, fontSize = 12.sp)
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(versions) { v ->
                                Row(
                                    Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(CyNavy.copy(alpha = 0.7f))
                                        .padding(horizontal = 12.dp, vertical = 7.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(Modifier.weight(1f)) {
                                        Text(v.getVersionName(), color = CyText, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Text(v.getVersionInfo()?.minecraftVersion ?: "", color = CyDim, fontSize = 10.sp)
                                    }
                                    Box(Modifier.cyClick { onPlay(v) }) {
                                        CyIconCircle(R.drawable.ic_play_arrow_filled, size = 34.dp, filled = true)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Versão do Minecraft
            CyPanel(Modifier.weight(1.2f).fillMaxHeight()) {
                Column(Modifier.fillMaxSize().padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(R.drawable.ic_folder_filled), null, tint = CyAccent, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Versão do Minecraft", color = CyText, fontSize = 16.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                    }
                    Text(
                        current?.getVersionName() ?: "Nenhuma versão", color = CyAccent, fontSize = 20.sp,
                        fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis
                    )
                    if (mc != null) Text(mc, color = CyDim, fontSize = 12.sp)
                    Spacer(Modifier.weight(1f))
                    if (current != null) CyButton("JOGAR", Modifier.fillMaxWidth(), R.drawable.ic_play_arrow_filled) { current?.let(onPlay) }
                    else CyOutlineButton("Escolher versão", Modifier.fillMaxWidth(), onClick = onOpenVersions)
                }
            }
        }
    }
}
