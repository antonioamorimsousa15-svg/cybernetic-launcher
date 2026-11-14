package com.shoyoomc.aflecklauncher.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shoyoomc.aflecklauncher.BuildConfig
import com.shoyoomc.aflecklauncher.R
import com.shoyoomc.aflecklauncher.game.account.AccountsManager

/** Barra de cima: marca, seção atual, Arquivos e conta. */
@Composable
fun AfTopBar(
    sectionTitle: String,
    onLogo: () -> Unit,
    onFiles: () -> Unit,
    onUser: () -> Unit,
    modifier: Modifier = Modifier
) {
    val account by AccountsManager.currentAccountFlow.collectAsState()
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Pill(onClick = onLogo) {
            Box(
                Modifier.size(42.dp).clip(CircleShape).background(Brush.horizontalGradient(listOf(AfAccent, AfAccent2))),
                contentAlignment = Alignment.Center
            ) { Icon(painterResource(R.drawable.ic_af_bolt), null, tint = AfNavy, modifier = Modifier.size(26.dp)) }
            Spacer(Modifier.width(10.dp))
            Column {
                Text("Afleck", color = AfText, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Text("LAUNCHER · v" + BuildConfig.VERSION_NAME, color = AfAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            }
        }
        Pill {
            Box(
                Modifier.size(38.dp).clip(CircleShape).background(Brush.horizontalGradient(listOf(AfAccent, AfAccent2))),
                contentAlignment = Alignment.Center
            ) { Text("❄", color = AfNavy, fontSize = 20.sp) }
            Spacer(Modifier.width(10.dp))
            Text(sectionTitle, color = AfText, fontSize = 16.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        }
        Spacer(Modifier.weight(1f))
        Pill(onClick = onFiles) {
            AfIconCircle(R.drawable.ic_folder_filled, size = 38.dp, filled = true)
            Spacer(Modifier.width(10.dp))
            Text("Arquivos", color = AfText, fontSize = 15.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        }
        Pill(onClick = onUser) {
            AfIconCircle(R.drawable.ic_person_filled, size = 38.dp)
            Spacer(Modifier.width(10.dp))
            Text(
                account?.username ?: "Adicionar Conta", color = AfText, fontSize = 15.sp,
                fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun Pill(onClick: (() -> Unit)? = null, content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.clip(CircleShape).background(AfPanelDeep)
            .border(1.5.dp, AfBorder, CircleShape)
            .then(if (onClick != null) Modifier.afClick(onClick) else Modifier)
            .padding(start = 5.dp, end = 16.dp, top = 5.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}
