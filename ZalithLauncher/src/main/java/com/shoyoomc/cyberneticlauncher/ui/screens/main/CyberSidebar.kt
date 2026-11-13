package com.shoyoomc.cyberneticlauncher.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoyoomc.cyberneticlauncher.R

/** Menu lateral: Início, Versões, Download, Contas, Ajustes. */
@Composable
fun CyberSidebar(
    inHome: Boolean,
    inVersions: Boolean,
    inDownload: Boolean,
    inAccounts: Boolean,
    inSettings: Boolean,
    onHome: () -> Unit,
    onVersions: () -> Unit,
    onDownload: () -> Unit,
    onAccounts: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    CyPanel(modifier = modifier.width(190.dp).fillMaxHeight(), radius = 30.dp, deep = true) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NavPill(R.drawable.ic_home_filled, "Início", inHome, onHome)
            NavPill(R.drawable.ic_folder_filled, "Versões", inVersions, onVersions)
            NavPill(R.drawable.ic_download_2_filled, "Download", inDownload, onDownload)
            NavPill(R.drawable.ic_person_filled, "Contas", inAccounts, onAccounts)
            NavPill(R.drawable.ic_settings_filled, "Ajustes", inSettings, onSettings)
        }
    }
}

@Composable
private fun NavPill(icon: Int, label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().height(56.dp).clip(CircleShape)
            .background(
                if (selected) Brush.horizontalGradient(listOf(CyAccent, CyAccent2))
                else Brush.horizontalGradient(listOf(CyPanel, CyPanel))
            )
            .border(1.5.dp, CyBorder, CircleShape)
            .cyClick(onClick)
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(42.dp).clip(CircleShape).background(CyNavy)
                .border(1.5.dp, CyBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(painterResource(icon), null, tint = if (selected) CyAccent else CyText, modifier = Modifier.size(21.dp))
        }
        Spacer(Modifier.width(12.dp))
        Text(
            label, color = if (selected) CyNavy else CyText,
            fontSize = 16.sp, fontWeight = FontWeight.Bold, maxLines = 1
        )
    }
}
