package com.shoyoomc.aflecklauncher.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoyoomc.aflecklauncher.R
import com.shoyoomc.aflecklauncher.game.account.AccountsManager
import com.shoyoomc.aflecklauncher.game.version.installed.Version
import com.shoyoomc.aflecklauncher.game.version.installed.VersionsManager

/** Tela Início do Afleck, igual ao esqueleto desenhado pelo usuário. */
@Composable
fun AfHome(
    onPlay: (Version) -> Unit,
    onOpenVersions: () -> Unit,
    onOpenAccounts: () -> Unit,
    onOpenDownload: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    val homeSlot = rememberAfSlot("home")
    val slot916 = rememberAfSlot("card916")
    val shadersSlot = rememberAfSlot("shaders")
    val modsSlot = rememberAfSlot("mods")
    var dialog by remember { mutableStateOf<String?>(null) }

    Box(Modifier.fillMaxSize()) {
        AfSlotImage(homeSlot, null, Modifier.fillMaxSize())
        AfHomeContent(
            onPlay, onOpenVersions, onOpenAccounts, onOpenDownload, onOpenSettings,
            slot916, shadersSlot, modsSlot, onEdit = { dialog = it }
        )
    }
    when (dialog) {
        "home" -> AfSlotDialog(
            "Wallpaper do painel inicial",
            "Imagem ou GIF só para este painel. A opacidade daqui é separada do fundo do Launcher.",
            homeSlot
        ) { dialog = null }
        "card916" -> AfSlotDialog(
            "Wallpaper 9:16",
            "Imagem ou GIF só para o quadro 9:16 da tela inicial. A opacidade daqui é separada das outras.",
            slot916
        ) { dialog = null }
        "shaders" -> AfSlotDialog("Imagem de Shaders", "Imagem do cartão de Shaders.", shadersSlot) { dialog = null }
        "mods" -> AfSlotDialog("Imagem de Mods", "Imagem do cartão de Mods.", modsSlot) { dialog = null }
    }
}

@Composable
private fun AfHomeContent(
    onPlay: (Version) -> Unit,
    onOpenVersions: () -> Unit,
    onOpenAccounts: () -> Unit,
    onOpenDownload: () -> Unit,
    onOpenSettings: () -> Unit,
    slot916: AfImageSlot,
    shadersSlot: AfImageSlot,
    modsSlot: AfImageSlot,
    onEdit: (String) -> Unit,
) {
    val account by AccountsManager.currentAccountFlow.collectAsState()
    val current by VersionsManager.currentVersion.collectAsState()
    val userName = account?.username

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // ---------- Linha de cima ----------
        Row(Modifier.fillMaxWidth().height(176.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            // Bem-vindo de volta
            AfPanel(Modifier.weight(2.5f).fillMaxHeight()) {
                Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(R.drawable.ic_package_2_outlined), null, tint = AfAccent, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Bem-vindo de volta,", color = AfText, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        userName ?: "Jogador", color = AfAccent, fontSize = 30.sp, fontWeight = FontWeight.Bold,
                        maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                    Text("Sua aventura congelada continua aqui.", color = AfText, fontSize = 13.sp)
                    Spacer(Modifier.weight(1f))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        if (current != null) {
                            AfButton("JOGAR", Modifier.weight(1f), R.drawable.ic_play_arrow_filled) { current?.let(onPlay) }
                        } else {
                            AfButton("BAIXAR MINECRAFT", Modifier.weight(1f), R.drawable.ic_download_2_filled, onOpenDownload)
                        }
                        Box(Modifier.afClick { onEdit("home") }) { AfIconCircle(R.drawable.ic_image_filled, size = 50.dp) }
                    }
                }
            }
            // Conta
            AfPanel(Modifier.weight(1.6f).fillMaxHeight()) {
                Column(Modifier.fillMaxSize().padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(R.drawable.ic_person_filled), null, tint = AfAccent, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Conta", color = AfText, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Row(
                            Modifier.clip(CircleShape).background(AfAccent).afClick(onOpenAccounts)
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painterResource(R.drawable.ic_checkroom), null, tint = AfNavy, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Skins", color = AfNavy, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier.size(54.dp).clip(CircleShape).background(AfNavy).border(2.dp, AfBorder, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            if (userName == null) {
                                Icon(painterResource(R.drawable.ic_add), null, tint = AfText, modifier = Modifier.size(28.dp))
                            } else {
                                Text(userName.take(1).uppercase(), color = AfAccent, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                userName ?: "Adicionar Conta", color = AfText, fontSize = 15.sp, fontWeight = FontWeight.Bold,
                                maxLines = 1, overflow = TextOverflow.Ellipsis
                            )
                            Text(if (userName == null) "● Nenhuma conta" else "● Conta", color = AfDim, fontSize = 11.sp)
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    AfOutlineButton("Gerenciar Conta", Modifier.fillMaxWidth(), R.drawable.ic_settings_filled, onOpenAccounts)
                }
            }
            // Imagem 9:16 (plano de fundo)
            Box(
                Modifier.weight(1f).fillMaxHeight().clip(RoundedCornerShape(28.dp))
                    .border(2.dp, AfBorder, RoundedCornerShape(28.dp))
            ) {
                AfSlotImage(slot916, R.drawable.af_bg, Modifier.fillMaxSize())
                Box(Modifier.align(Alignment.TopEnd).padding(8.dp).afClick { onEdit("card916") }) {
                    AfIconCircle(R.drawable.ic_image_filled, size = 40.dp)
                }
                Text(
                    "9:16", color = AfText, fontSize = 13.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomStart).padding(10.dp).clip(RoundedCornerShape(10.dp))
                        .background(AfNavy.copy(alpha = 0.85f)).padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
        // ---------- Linha de baixo ----------
        Row(Modifier.fillMaxWidth().height(184.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            PromoCard(
                Modifier.weight(1.6f).fillMaxHeight(), R.drawable.ic_package_2_outlined, "Shaders", "Ver todas ›",
                "Deixe seu mundo mais bonito com shaders.", "Explorar ›", onOpenDownload,
                shadersSlot
            ) { onEdit("shaders") }
            PromoCard(
                Modifier.weight(1.25f).fillMaxHeight(), R.drawable.ic_extension_outlined, "Mods", null,
                "Turbine suas versões com mods novos.", "Ver mods ›", onOpenDownload,
                modsSlot
            ) { onEdit("mods") }
            // Configurações Rápidas
            AfPanel(Modifier.weight(1.5f).fillMaxHeight()) {
                Column(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(R.drawable.ic_settings_filled), null, tint = AfAccent, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Configurações Rápidas", color = AfText, fontSize = 15.sp, fontWeight = FontWeight.Bold, maxLines = 1, modifier = Modifier.weight(1f))
                        AfIconCircle(R.drawable.ic_edit_outlined, size = 34.dp)
                    }
                    Row(Modifier.weight(1f).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        QuickTile(Modifier.weight(1f), R.drawable.ic_video_settings, "Gráficos", onOpenSettings)
                        QuickTile(Modifier.weight(1f), R.drawable.ic_dashboard_filled, "Layouts de HUD", onOpenSettings)
                    }
                    Row(Modifier.weight(1f).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        QuickTile(Modifier.weight(1f), R.drawable.ic_package_2_outlined, "Aparência", onOpenSettings)
                        QuickTile(Modifier.weight(1f), R.drawable.ic_person_filled, "Contas", onOpenAccounts)
                    }
                }
            }
        }
    }
}

@Composable
private fun PromoCard(
    modifier: Modifier, icon: Int, title: String, seeAll: String?, text: String, buttonText: String, onClick: () -> Unit,
    slot: AfImageSlot, onEdit: () -> Unit
) {
    AfPanel(modifier) {
        Column(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(icon), null, tint = AfAccent, modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(8.dp))
                Text(title, color = AfText, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                if (seeAll != null) {
                    Text(seeAll, color = AfAccent, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.afClick(onClick))
                    Spacer(Modifier.width(8.dp))
                }
                Box(Modifier.afClick(onEdit)) { AfIconCircle(R.drawable.ic_edit_outlined, size = 34.dp) }
            }
            Row(Modifier.weight(1f).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    Modifier.fillMaxHeight().aspectRatio(1f).clip(RoundedCornerShape(20.dp))
                        .background(Brush.verticalGradient(listOf(AfNavy, AfPanelDeep)))
                        .border(1.5.dp, AfBorder, RoundedCornerShape(20.dp))
                        .afClick(onEdit),
                    contentAlignment = Alignment.Center
                ) {
                    if (slot.uri == null) {
                        Icon(painterResource(R.drawable.ic_image_outlined), null, tint = AfBorder, modifier = Modifier.size(30.dp))
                    } else {
                        AfSlotImage(slot, null, Modifier.fillMaxSize())
                    }
                }
                Column(Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text, color = AfText, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 3, overflow = TextOverflow.Ellipsis)
                    Spacer(Modifier.weight(1f))
                    AfButton(buttonText, Modifier.fillMaxWidth(), null, onClick)
                }
            }
        }
    }
}

@Composable
private fun QuickTile(modifier: Modifier, icon: Int, label: String, onClick: () -> Unit) {
    val shape = RoundedCornerShape(22.dp)
    Column(
        modifier = modifier.fillMaxHeight().clip(shape).background(AfNavy.copy(alpha = 0.55f))
            .border(1.5.dp, AfBorder, shape).afClick(onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(painterResource(icon), null, tint = AfAccent, modifier = Modifier.size(22.dp))
        Text(label, color = AfText, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1, textAlign = TextAlign.Center)
    }
}
