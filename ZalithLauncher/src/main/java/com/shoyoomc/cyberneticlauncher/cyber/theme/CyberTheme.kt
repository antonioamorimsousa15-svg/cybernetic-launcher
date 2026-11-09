/*
 * CYBERNETIC LAUNCHER
 * UI components for the Cybernetic versions screen.
 * Derived from Zalith Launcher 2 (GPLv3); retain the project's license notices.
 */
package com.shoyoomc.cyberneticlauncher.cyber.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

object ShoyoColors {
    val Background = Color(0xFF080B12)
    val Panel = Color(0xFF111827)
    val PanelBorder = Color(0xFF263449)
    val Accent = Color(0xFFFFC928)
    val TextPrimary = Color(0xFFF4F6FA)
    val TextSecondary = Color(0xFF9AA8BA)
    val Success = Color(0xFF36C98F)
    val Warning = Color(0xFFFFB547)
    val Danger = Color(0xFFFF647C)
}

enum class BadgeKind { Blocked, Selected, Available }

fun Modifier.tap(onClick: () -> Unit): Modifier = clickable(onClick = onClick)

@Composable
fun NeonPanel(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = ShoyoColors.Panel,
        contentColor = ShoyoColors.TextPrimary,
        border = androidx.compose.foundation.BorderStroke(1.dp, ShoyoColors.PanelBorder)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            content = content
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = ShoyoColors.TextPrimary,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun GhostButton(
    text: String,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = 40.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.size(7.dp))
        }
        Text(text, maxLines = 1)
    }
}

@Composable
fun NeonButton(
    text: String,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = 40.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ShoyoColors.Accent,
            contentColor = Color(0xFF17130A)
        )
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.size(7.dp))
        }
        Text(text, maxLines = 1, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SelectChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label, maxLines = 1) },
        shape = RoundedCornerShape(12.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = ShoyoColors.Accent.copy(alpha = 0.16f),
            selectedLabelColor = ShoyoColors.Accent,
            labelColor = ShoyoColors.TextSecondary
        )
    )
}

@Composable
fun StatusBadge(label: String, kind: BadgeKind) {
    val color = when (kind) {
        BadgeKind.Blocked -> ShoyoColors.Danger
        BadgeKind.Selected -> ShoyoColors.Accent
        BadgeKind.Available -> ShoyoColors.Success
    }
    Text(
        text = label,
        color = color,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.35f), RoundedCornerShape(50))
            .padding(horizontal = 9.dp, vertical = 5.dp),
        maxLines = 1
    )
}

@Composable
fun DiamondRow(
    modifier: Modifier = Modifier,
    blocked: Boolean = false,
    onClick: () -> Unit,
    trailing: @Composable RowScope.() -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (blocked) ShoyoColors.Panel.copy(alpha = 0.55f) else ShoyoColors.Panel)
            .border(1.dp, ShoyoColors.PanelBorder, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        content()
        trailing()
    }
}
