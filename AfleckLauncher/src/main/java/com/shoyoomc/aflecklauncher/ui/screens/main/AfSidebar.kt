package com.shoyoomc.aflecklauncher.ui.screens.main

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
import com.shoyoomc.aflecklauncher.R

/** Menu lateral: Início, Versões, Download, Contas, Ajustes. */
@Composable
fun AfSidebar(
    inHome: Boolean,
    inVersions: Boolean,
    inDownload: Boolean,
    inAccounts: Boolean,
    inSettings: Boolean,
    inAppearance: Boolean,
    onHome: () -> Unit,
    onVersions: () -> Unit,
    onDownload: () -> Unit,
    onAccounts: () -> Unit,
    onSettings: () -> Unit,
    onAppearance: () -> Unit,
    modifier: Modifier = Modifier
) {
    AfPanel(modifier = modifier.width(190.dp).fillMaxHeight(), radius = 30.dp, deep = true) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NavPill(R.drawable.ic_home_filled, "Início", inHome, onHome)
            NavPill(R.drawable.ic_sports_esports_outlined, "Versões", inVersions, onVersions)
            NavPill(R.drawable.ic_download_2_filled, "Download", inDownload, onDownload)
            NavPill(R.drawable.ic_person_filled, "Contas", inAccounts, onAccounts)
            NavPill(R.drawable.ic_settings_filled, "Ajustes", inSettings, onSettings)
            NavPill(R.drawable.ic_checkroom, "Aparência", inAppearance, onAppearance)
        }
    }
}

@Composable
private fun NavPill(icon: Int, label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().height(56.dp).clip(CircleShape)
            .background(
                if (selected) Brush.horizontalGradient(listOf(AfAccent, AfAccent2))
                else Brush.horizontalGradient(listOf(AfPanel, AfPanel))
            )
            .border(1.5.dp, AfBorder, CircleShape)
            .afClick(onClick)
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(42.dp).clip(CircleShape).background(AfNavy)
                .border(1.5.dp, AfBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(painterResource(icon), null, tint = if (selected) AfAccent else AfText, modifier = Modifier.size(21.dp))
        }
        Spacer(Modifier.width(12.dp))
        Text(
            label, color = if (selected) AfNavy else AfText,
            fontSize = 16.sp, fontWeight = FontWeight.Bold, maxLines = 1
        )
    }
}
