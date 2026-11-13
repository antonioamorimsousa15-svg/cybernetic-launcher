package com.shoyoomc.cyberneticlauncher.ui.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoyoomc.cyberneticlauncher.BuildConfig
import com.shoyoomc.cyberneticlauncher.R

private val Cyan = Color(0xFF3DE8FF)
private val CyanDeep = Color(0xFF1FB8D8)
private val Navy = Color(0xFF06142A)
private val Teal = Color(0xFF3A8FA0)
private val TextDim = Color(0xFFA9C4D4)

/** Coluna da esquerda: cabeçalho + menu em pílulas (visual do Cryogenic, código próprio). */
@Composable
fun CyberLeftColumn(
    inHome: Boolean,
    inVersions: Boolean,
    inDownload: Boolean,
    inAccounts: Boolean,
    inSettings: Boolean,
    inMultiplayer: Boolean,
    onHome: () -> Unit,
    onVersions: () -> Unit,
    onDownload: () -> Unit,
    onAccounts: () -> Unit,
    onSettings: () -> Unit,
    onMultiplayer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(212.dp).fillMaxHeight().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CyberHeader()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(30.dp))
                .background(Brush.verticalGradient(listOf(Color(0xB30C2440), Color(0xCC050D1C))))
                .border(BorderStroke(1.dp, Teal.copy(alpha = 0.7f)), RoundedCornerShape(30.dp))
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CyberNavItem(R.drawable.ic_home_filled, "Início", inHome, onHome)
            CyberNavItem(R.drawable.ic_folder_filled, "Versões", inVersions, onVersions)
            CyberNavItem(R.drawable.ic_download_2_filled, "Download", inDownload, onDownload)
            CyberNavItem(R.drawable.ic_person_filled, "Contas", inAccounts, onAccounts)
            CyberDivider()
            CyberNavItem(R.drawable.ic_settings_filled, "Ajustes", inSettings, onSettings)
            CyberNavItem(R.drawable.ic_group_filled, "Multijogador", inMultiplayer, onMultiplayer)
        }
    }
}

@Composable
private fun CyberHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50))
            .background(Brush.verticalGradient(listOf(Color(0xB30C2440), Color(0xCC050D1C))))
            .border(BorderStroke(1.dp, Teal.copy(alpha = 0.7f)), RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(44.dp).clip(CircleShape)
                .background(Brush.radialGradient(listOf(Color(0xFF1B5F85), Navy)))
                .border(2.dp, Cyan.copy(alpha = 0.8f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("C", color = Cyan, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(10.dp))
        Column {
            Text("Cybernetic", color = Cyan, fontSize = 20.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("LAUNCHER", color = TextDim, fontSize = 9.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(5.dp))
                Text(
                    "v" + BuildConfig.VERSION_NAME, color = Cyan, fontSize = 9.sp, fontWeight = FontWeight.Bold, maxLines = 1,
                    modifier = Modifier.clip(CircleShape).background(Navy.copy(alpha = 0.7f))
                        .border(1.dp, Cyan.copy(alpha = 0.6f), CircleShape)
                        .padding(horizontal = 6.dp, vertical = 1.dp)
                )
            }
        }
    }
}

@Composable
private fun CyberNavItem(icon: Int, label: String, selected: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(50)
    val bg = if (selected) Brush.horizontalGradient(listOf(CyanDeep, Cyan))
    else Brush.verticalGradient(listOf(Color(0x99163A5C), Color(0x99061326)))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(shape)
            .background(bg)
            .border(BorderStroke(1.5.dp, if (selected) Cyan else Teal.copy(alpha = 0.6f)), shape)
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick)
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp).clip(CircleShape)
                .background(if (selected) Navy else Color.Transparent)
                .then(if (selected) Modifier else Modifier.border(1.5.dp, Teal.copy(alpha = 0.7f), CircleShape)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon), contentDescription = label,
                tint = if (selected) Cyan else Color.White, modifier = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Text(
            label, color = if (selected) Navy else Color.White,
            fontSize = 16.sp, fontWeight = FontWeight.Bold, maxLines = 1
        )
    }
}

@Composable
private fun CyberDivider() {
    Row(Modifier.fillMaxWidth().padding(horizontal = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.weight(1f).height(1.dp).background(Teal.copy(alpha = 0.6f)))
        Box(Modifier.padding(horizontal = 6.dp).size(6.dp).clip(CircleShape).background(Cyan))
        Box(Modifier.weight(1f).height(1.dp).background(Teal.copy(alpha = 0.6f)))
    }
}
