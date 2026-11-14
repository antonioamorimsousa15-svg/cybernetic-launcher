/*
 * Zalith Launcher 2
 * Copyright (C) 2025-2026 MovTery <movtery228@qq.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/gpl-3.0.txt>.
 */

package com.shoyoomc.aflecklauncher.ui.screens.splash

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shoyoomc.aflecklauncher.R
import com.shoyoomc.aflecklauncher.components.InstallableItem
import com.shoyoomc.aflecklauncher.ui.base.BaseScreen
import com.shoyoomc.aflecklauncher.ui.components.MarqueeText
import com.shoyoomc.aflecklauncher.ui.components.ScalingActionButton
import com.shoyoomc.aflecklauncher.viewmodel.SplashBackStackViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import kotlin.math.cos
import kotlin.math.sin

private object ShoyoColors {
    val Accent = Color(0xFF3DE8FF)
    val Secondary = Color(0xFF6DE8C8)
    val Glass = Color(0xB30A1C38)
    val PanelBorder = Color(0xFF3A8FA0)
    val TextPrimary = Color.White
    val TextSecondary = Color(0xFFB4CCDB)
    val OnAccent = Color(0xFF03111C)
    val Bg = Color(0xFF06142A)
}

@Composable
fun UnpackScreen(
    items: List<InstallableItem>,
    screenViewModel: SplashBackStackViewModel,
    onAgreeClick: () -> Unit = {}
) {
    BaseScreen(
        screenKey = SplashOperation.Unpacking,
        currentKey = screenViewModel.splashScreen.currentKey
    ) { _ ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .paint(painterResource(R.drawable.af_bg), contentScale = ContentScale.Crop)
                .background(Color(0x99041020))
                .padding(all = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            SetupPanel(
                modifier = Modifier.weight(4f).fillMaxHeight(),
                items = items,
                onAgreeClick = onAgreeClick
            )
            ModulesPanel(
                modifier = Modifier.weight(6f).fillMaxHeight(),
                items = items
            )
        }
    }
}

/** Arcos de canto (topo-direita em [a], base-direita em [b]) seguindo o raio do painel. */
private fun Modifier.cornerArcs(radius: Dp, stroke: Dp, a: Color, b: Color): Modifier = drawWithContent {
    drawContent()
    val r = radius.toPx()
    val sw = stroke.toPx()
    val inset = sw / 2f
    val w = size.width
    val h = size.height
    val len = r * 0.55f
    val box = Size(2 * r - 2 * inset, 2 * r - 2 * inset)
    // topo-direita
    drawArc(a, -90f, 90f, false, Offset(w - 2 * r + inset, inset), box, style = Stroke(sw, cap = StrokeCap.Round))
    drawLine(a, Offset(w - r, inset), Offset(w - r - len, inset), sw, StrokeCap.Round)
    drawLine(a, Offset(w - inset, r), Offset(w - inset, r + len), sw, StrokeCap.Round)
    // base-direita
    drawArc(b, 0f, 90f, false, Offset(w - 2 * r + inset, h - 2 * r + inset), box, style = Stroke(sw, cap = StrokeCap.Round))
    drawLine(b, Offset(w - inset, h - r), Offset(w - inset, h - r - len), sw, StrokeCap.Round)
    drawLine(b, Offset(w - r, h - inset), Offset(w - r - len, h - inset), sw, StrokeCap.Round)
}

@Composable
private fun SetupPanel(
    items: List<InstallableItem>,
    modifier: Modifier = Modifier,
    onAgreeClick: () -> Unit = {}
) {
    val states = items.map { it.state.collectAsStateWithLifecycle().value }
    val installing = states.any { it == InstallableItem.State.RUNNING }
    val hasFailed = states.any { it == InstallableItem.State.FAILED }
    val done = states.count { it == InstallableItem.State.FINISHED }
    val total = items.size.coerceAtLeast(1)
    val fraction = done.toFloat() / total
    val shape = RoundedCornerShape(36.dp)
    val canInstall = !installing && states.any {
        it == InstallableItem.State.NOT_STARTED ||
        it == InstallableItem.State.PENDING ||
        it == InstallableItem.State.FAILED
    }

    Column(
        modifier = modifier
            .clip(shape).background(ShoyoColors.Glass)
            .border(1.dp, ShoyoColors.PanelBorder, shape)
            .padding(horizontal = 22.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            Box(
                Modifier.size(54.dp).clip(CircleShape).background(ShoyoColors.Bg).border(2.dp, ShoyoColors.Accent, CircleShape),
                contentAlignment = Alignment.Center
            ) { Text("C", color = ShoyoColors.Accent, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge) }
            Column {
                Text(
                    "AFLECK // SETUP",
                    color = ShoyoColors.Accent,
                    style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 2.sp)
                )
                Text(
                    "PREPARAÇÃO INICIAL",
                    color = ShoyoColors.TextPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            val accent = ShoyoColors.Accent
            val secondary = ShoyoColors.Secondary
            val track = ShoyoColors.PanelBorder.copy(alpha = 0.35f)
            Canvas(Modifier.fillMaxHeight().aspectRatio(1f)) {
                val d = size.minDimension
                val c = Offset(size.width / 2f, size.height / 2f)
                val r = d / 2f - 6.dp.toPx()
                val ring = r - 14.dp.toPx()
                drawCircle(
                    Brush.radialGradient(listOf(accent.copy(alpha = 0.22f), Color.Transparent), c, ring),
                    radius = ring, center = c
                )
                for (i in 0 until 60) {
                    val a = Math.toRadians(i * 6.0 - 90.0)
                    val long = i % 5 == 0
                    val r1 = r + 2.dp.toPx()
                    val r2 = r + (if (long) 9.dp else 5.dp).toPx()
                    val on = i / 60f < fraction
                    drawLine(
                        if (on) accent else track,
                        Offset(c.x + (r1 * cos(a)).toFloat(), c.y + (r1 * sin(a)).toFloat()),
                        Offset(c.x + (r2 * cos(a)).toFloat(), c.y + (r2 * sin(a)).toFloat()),
                        strokeWidth = if (long) 2.dp.toPx() else 1.dp.toPx()
                    )
                }
                val tl = Offset(c.x - ring, c.y - ring)
                val sz = Size(ring * 2, ring * 2)
                drawArc(track, 0f, 360f, false, tl, sz, style = Stroke(6.dp.toPx()))
                if (fraction > 0f) {
                    drawArc(
                        Brush.sweepGradient(listOf(secondary, accent, secondary), c),
                        -90f, 360f * fraction, false, tl, sz,
                        style = Stroke(6.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "${(fraction * 100).toInt()}%",
                    color = ShoyoColors.TextPrimary,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
                Text("$done/${items.size} módulos instalados", color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.labelMedium)
            }
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = ShoyoColors.TextSecondary,
            text = if (installing) stringResource(R.string.splash_screen_installing) else stringResource(R.string.splash_screen_unpack_desc),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 4
        )

        Box(
            Modifier
                .fillMaxWidth()
                .height(52.dp)
                .alpha(if (canInstall) 1f else 0.45f)
                .clip(RoundedCornerShape(26.dp))
                .background(Brush.horizontalGradient(listOf(ShoyoColors.Accent, ShoyoColors.Secondary)))
                .then(if (canInstall) Modifier.clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onAgreeClick) else Modifier),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "↓  " + stringResource(if (hasFailed) R.string.generic_retry else R.string.splash_screen_agree),
                color = ShoyoColors.OnAccent,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun ModulesPanel(
    items: List<InstallableItem>,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(36.dp)
    Column(
        modifier = modifier
            .clip(shape).background(ShoyoColors.Glass)
            .border(1.dp, ShoyoColors.PanelBorder, shape)
            .cornerArcs(36.dp, 4.dp, ShoyoColors.Accent, ShoyoColors.Secondary)
            .padding(start = 18.dp, end = 22.dp, top = 18.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 4.dp)) {
            Text("⬡", color = ShoyoColors.Accent, style = MaterialTheme.typography.titleLarge)
            Text(
                "  MÓDULOS DO SISTEMA",
                color = ShoyoColors.Accent,
                style = MaterialTheme.typography.titleLarge.copy(letterSpacing = 1.sp),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text("SEQ ${items.size}", color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.labelLarge)
        }
        Row(Modifier.weight(1f).fillMaxWidth()) {
            Column(
                Modifier.width(26.dp).fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                for (i in 0 until 8) {
                    Text(
                        "❮",
                        color = ShoyoColors.Accent.copy(alpha = if (i == 4) 1f else 0.45f),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            LazyColumn(Modifier.weight(1f).fillMaxHeight()) {
                itemsIndexed(items) { index, item -> ModuleRow(index + 1, item) }
            }
        }
    }
}

@Composable
private fun ModuleRow(number: Int, item: InstallableItem) {
    val state by item.state.collectAsStateWithLifecycle()
    val message by item.task.taskMessage.collectAsStateWithLifecycle()
    val running = state == InstallableItem.State.RUNNING
    val finished = state == InstallableItem.State.FINISHED
    val failed = state == InstallableItem.State.FAILED
    val tint = when {
        finished -> ShoyoColors.Secondary
        failed -> Color(0xFFFF6B6B)
        else -> ShoyoColors.Accent
    }
    val label = when (state) {
        InstallableItem.State.RUNNING -> "INSTALANDO"
        InstallableItem.State.FINISHED -> "PRONTO"
        InstallableItem.State.FAILED -> "FALHOU"
        InstallableItem.State.PENDING -> "NA FILA"
        else -> "NOVO"
    }
    val cardShape = RoundedCornerShape(22.dp)
    val nodeShape = RoundedCornerShape(10.dp)
    val line = ShoyoColors.Accent.copy(alpha = 0.55f)
    Box(
        Modifier
            .fillMaxWidth()
            .drawBehind {
                val x = 17.dp.toPx()
                drawLine(line, Offset(x, 0f), Offset(x, size.height), 2.dp.toPx())
            }
            .padding(bottom = 10.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(
                Modifier.padding(top = 12.dp).size(34.dp).clip(nodeShape)
                    .background(if (finished) tint.copy(alpha = 0.35f) else ShoyoColors.Bg)
                    .border(2.dp, tint, nodeShape),
                contentAlignment = Alignment.Center
            ) {
                Text(if (finished) "✓" else "$number", color = tint, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            }
            Column(
                Modifier.weight(1f).clip(cardShape)
                    .background(if (running) tint.copy(alpha = 0.12f) else ShoyoColors.Glass)
                    .border(1.5.dp, if (running) tint else ShoyoColors.PanelBorder, cardShape)
                    .padding(horizontal = 18.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        item.name,
                        color = ShoyoColors.TextPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        label,
                        color = tint,
                        style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 2.sp),
                        fontWeight = FontWeight.Bold
                    )
                }
                item.summary?.let {
                    Text(it, color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.bodySmall)
                }
                if (running || failed) {
                    message?.let {
                        Text("> $it", color = tint, style = MaterialTheme.typography.labelSmall, maxLines = 1)
                    }
                    Box(Modifier.fillMaxWidth().padding(top = 4.dp).height(4.dp).clip(CircleShape).background(ShoyoColors.PanelBorder.copy(alpha = 0.3f)))
                }
            }
        }
    }
}
