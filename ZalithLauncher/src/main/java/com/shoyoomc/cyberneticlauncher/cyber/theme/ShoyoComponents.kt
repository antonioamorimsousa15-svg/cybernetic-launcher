package com.shoyoomc.cyberneticlauncher.cyber.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

// ---------- Estilo de acento (principal / secundário da paleta) ----------
enum class AccentStyle { Primary, Secondary }

@Composable
fun AccentStyle.brush(): Brush = when (this) {
    AccentStyle.Primary -> Brush.horizontalGradient(listOf(ShoyoColors.Accent, ShoyoColors.AccentDeep))
    AccentStyle.Secondary -> Brush.horizontalGradient(listOf(ShoyoColors.Secondary, ShoyoColors.SecondaryDeep))
}

@Composable
fun AccentStyle.main(): Color = when (this) {
    AccentStyle.Primary -> ShoyoColors.Accent
    AccentStyle.Secondary -> ShoyoColors.Secondary
}

// ---------- Colchetes de canto (diálogos SHOYO // SYSTEM) ----------
fun Modifier.cornerBrackets(color: Color, len: Dp = 16.dp, stroke: Dp = 2.dp) =
    drawBehind {
        val l = len.toPx(); val s = stroke.toPx(); val w = size.width; val h = size.height
        drawLine(color, Offset(w - l, s / 2), Offset(w, s / 2), s)
        drawLine(color, Offset(w - s / 2, 0f), Offset(w - s / 2, l), s)
        drawLine(color, Offset(0f, h - s / 2), Offset(l, h - s / 2), s)
        drawLine(color, Offset(s / 2, h - l), Offset(s / 2, h), s)
    }

// ---------- Diálogo de sistema (Aviso, Instalação bem-sucedida, Adicionar conta...) ----------
@Composable
fun SystemDialog(
    title: String,
    icon: ImageVector,
    onDismiss: () -> Unit,
    tag: String = "SHOYO // SYSTEM",
    maxWidth: Dp = 460.dp,
    actions: @Composable RowScope.() -> Unit = {},
    body: @Composable ColumnScope.() -> Unit
) {
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        val shape = CutCornerShape(topStart = 24.dp, bottomEnd = 24.dp)
        Column(
            Modifier
                .widthIn(max = maxWidth).fillMaxWidth(0.9f)
                .clip(shape)
                .background(Brush.verticalGradient(listOf(ShoyoColors.BgAlt, ShoyoColors.Bg)))
                .border(1.5.dp, Brush.linearGradient(listOf(ShoyoColors.Secondary, ShoyoColors.AccentDeep)), shape)
                .cornerBrackets(ShoyoColors.Secondary)
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(48.dp).clip(CutCornerShape(10.dp))
                        .border(1.dp, ShoyoColors.PanelBorder, CutCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) { Icon(icon, null, tint = ShoyoColors.Secondary) }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(tag, color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.labelSmall)
                    Text(title, color = ShoyoColors.TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(12.dp))
            Box(Modifier.fillMaxWidth().height(1.dp).background(ShoyoColors.PanelBorder))
            Spacer(Modifier.height(12.dp))
            body()
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp), content = actions)
        }
    }
}

// ---------- Diálogo de progresso por etapas (Instalando o jogo / Iniciar o jogo) ----------
sealed interface StepState {
    object Done : StepState
    data class Active(val progress: Float? = null, val detail: String? = null, val speed: String? = null) : StepState
    object Pending : StepState
}

data class ProgressStep(val label: String, val state: StepState)

@Composable
fun StepProgressDialog(
    title: String,
    stepInfo: String,            // ex.: "ETAPA 2 DE 5"
    percent: Int,
    steps: List<ProgressStep>,
    onCancel: () -> Unit,
    accent: AccentStyle = AccentStyle.Primary
) {
    val main = accent.main()
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false, usePlatformDefaultWidth = false)
    ) {
        val shape = RoundedCornerShape(28.dp)
        Column(
            Modifier.widthIn(max = 520.dp).fillMaxWidth(0.92f)
                .clip(shape)
                .background(ShoyoColors.Bg)
                .border(1.dp, main.copy(alpha = 0.5f), shape)
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(48.dp).clip(RoundedCornerShape(14.dp)).background(accent.brush()))
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(title, color = ShoyoColors.TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(stepInfo, color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.labelSmall)
                }
                Text("$percent%", color = ShoyoColors.TextPrimary, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                steps.forEach { s ->
                    val c = when (s.state) {
                        StepState.Done -> main
                        is StepState.Active -> main.copy(alpha = 0.5f)
                        StepState.Pending -> ShoyoColors.BgAlt
                    }
                    Box(Modifier.weight(1f).height(5.dp).clip(CircleShape).background(c))
                }
            }
            steps.forEachIndexed { i, s -> StepRow(i + 1, s, main) }
            Row(
                Modifier.fillMaxWidth().clip(PillShape)
                    .background(ShoyoAccent.DangerBg)
                    .border(1.dp, ShoyoAccent.Danger, PillShape)
                    .tap(onCancel).padding(14.dp),
                horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Close, null, tint = ShoyoAccent.Danger)
                Spacer(Modifier.width(8.dp))
                Text("Cancelar", color = ShoyoAccent.Danger, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun StepRow(number: Int, step: ProgressStep, main: Color) {
    when (val st = step.state) {
        StepState.Done -> Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(44.dp).clip(CircleShape).background(main), contentAlignment = Alignment.Center) {
                Icon(Icons.Filled.Check, null, tint = ShoyoColors.OnAccent)
            }
            Spacer(Modifier.width(12.dp))
            Text(step.label, color = ShoyoColors.TextPrimary, fontWeight = FontWeight.Bold)
        }
        is StepState.Active -> Column(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp))
                .border(1.dp, main.copy(alpha = 0.6f), RoundedCornerShape(18.dp)).padding(12.dp)
        ) {
            Text(step.label, color = ShoyoColors.TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            st.detail?.let { Text(it, color = ShoyoColors.TextSecondary, fontSize = 12.sp) }
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.weight(1f).height(8.dp).clip(CircleShape).background(ShoyoColors.BgAlt)) {
                    Box(Modifier.fillMaxWidth(st.progress ?: 0f).fillMaxHeight().background(main))
                }
                st.speed?.let { Spacer(Modifier.width(8.dp)); Text(it, color = main, fontSize = 12.sp) }
                st.progress?.let { Spacer(Modifier.width(8.dp)); Text("${(it * 100).toInt()}%", color = main, fontWeight = FontWeight.Bold) }
            }
        }
        StepState.Pending -> Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(44.dp).clip(CircleShape).border(1.dp, ShoyoColors.PanelBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("%02d".format(number), color = ShoyoColors.TextSecondary, fontSize = 12.sp)
            }
            Spacer(Modifier.width(12.dp))
            Text(step.label, color = ShoyoColors.TextSecondary)
        }
    }
}

// ---------- Selo de status (DISPONÍVEL / SELECIONADO / BLOQUEADO / NOVO) ----------
enum class BadgeKind { Available, Selected, Blocked, Muted }

@Composable
fun StatusBadge(text: String, kind: BadgeKind) {
    val c = when (kind) {
        BadgeKind.Available, BadgeKind.Selected -> ShoyoColors.Accent
        BadgeKind.Blocked -> ShoyoAccent.Danger
        BadgeKind.Muted -> ShoyoColors.TextSecondary
    }
    Text(
        text.uppercase(), color = c, style = MaterialTheme.typography.labelSmall, fontSize = 12.sp,
        modifier = Modifier.clip(RoundedCornerShape(10.dp)).border(1.dp, c.copy(alpha = 0.7f), RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    )
}

// ---------- Linha com losango (Download, Versões, Arquivos, módulos) ----------
@Composable
fun DiamondRow(
    modifier: Modifier = Modifier,
    blocked: Boolean = false,
    onClick: (() -> Unit)? = null,
    trailing: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    val shape = CutCornerShape(topStart = 14.dp, bottomEnd = 14.dp)
    val border = if (blocked) ShoyoAccent.Danger.copy(alpha = 0.7f) else ShoyoColors.PanelBorder
    Row(
        modifier
            .fillMaxWidth().clip(shape)
            .background(ShoyoColors.RowBg)
            .border(1.dp, border, shape)
            .then(if (onClick != null) Modifier.tap(onClick) else Modifier)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(8.dp).rotate(45f).background(if (blocked) ShoyoAccent.Danger else ShoyoColors.Accent))
        Spacer(Modifier.width(12.dp))
        content()
        trailing()
    }
}

// ---------- Linhas de configuração ----------
@Composable
fun SettingToggleRow(title: String, description: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(title, color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleMedium)
            Text(description, color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(Modifier.width(12.dp))
        Switch(
            checked = checked, onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = ShoyoColors.OnAccent, checkedTrackColor = ShoyoColors.Accent,
                uncheckedThumbColor = Color.White, uncheckedTrackColor = ShoyoColors.Bg,
                uncheckedBorderColor = ShoyoColors.Accent
            )
        )
    }
}

@Composable
private fun HexStepButton(symbol: String, onClick: () -> Unit) {
    val shape = CutCornerShape(10.dp)
    Box(
        Modifier.size(40.dp).clip(shape).border(1.dp, ShoyoColors.Accent.copy(alpha = 0.7f), shape)
            .background(ShoyoColors.Glass).tap(onClick),
        contentAlignment = Alignment.Center
    ) { Text(symbol, color = ShoyoColors.TextPrimary, fontSize = 20.sp) }
}

/** Slider hexagonal + botões ‹ › + valor grande à direita (sensibilidade, RAM, escala...). */
@Composable
fun SettingSliderRow(
    title: String, description: String,
    value: Float, onValueChange: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float>, unit: String, step: Float = 1f
) {
    Column(Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
        Text(title, color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleMedium)
        Text(description, color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.bodyMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            NeonSlider(value, onValueChange, range, Modifier.weight(1f))
            HexStepButton("‹") { onValueChange((value - step).coerceIn(range)) }
            Text(
                "${value.toInt()}$unit", color = ShoyoColors.TextPrimary, fontSize = 26.sp,
                fontWeight = FontWeight.Bold, modifier = Modifier.widthIn(min = 96.dp),
                textAlign = TextAlign.Center
            )
            HexStepButton("›") { onValueChange((value + step).coerceIn(range)) }
        }
    }
}

// ---------- Opção (rádio com check) ----------
@Composable
fun RadioOptionRow(label: String, selected: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(16.dp)
    Row(
        Modifier.fillMaxWidth().clip(shape)
            .background(ShoyoColors.Glass)
            .border(1.dp, if (selected) ShoyoColors.Accent else ShoyoColors.PanelBorder, shape)
            .tap(onClick).padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier.size(30.dp).clip(CircleShape)
                .background(if (selected) ShoyoColors.Accent else Color.Transparent)
                .border(1.dp, ShoyoColors.PanelBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) { if (selected) Icon(Icons.Filled.Check, null, tint = ShoyoColors.OnAccent, modifier = Modifier.size(18.dp)) }
        Spacer(Modifier.width(12.dp))
        Text(label, color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleMedium)
    }
}

// ---------- Painel SCANNER // FILTROS (colchetes na lateral esquerda) ----------
@Composable
fun ScannerPanel(title: String, modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    val shape = CutCornerShape(topEnd = 28.dp, bottomEnd = 0.dp)
    Column(
        modifier
            .clip(shape)
            .background(ShoyoColors.PanelBottom)
            .drawBehind {
                val l = 28.dp.toPx(); val s = 3.dp.toPx()
                drawLine(ShoyoColors.Accent, Offset(0f, s / 2), Offset(l, s / 2), s)
                drawLine(ShoyoColors.Accent, Offset(s / 2, 0f), Offset(s / 2, l), s)
                drawLine(ShoyoColors.Accent, Offset(0f, size.height - s / 2), Offset(l, size.height - s / 2), s)
                drawLine(ShoyoColors.Accent, Offset(s / 2, size.height - l), Offset(s / 2, size.height), s)
            }
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(title, color = ShoyoColors.Accent, style = MaterialTheme.typography.labelSmall, fontSize = 13.sp)
        content()
    }
}

// ---------- Barra do topo: logo, breadcrumb, Arquivos, usuário ----------
@Composable
fun GlassPill(modifier: Modifier = Modifier, onClick: (() -> Unit)? = null, content: @Composable RowScope.() -> Unit) {
    Row(
        modifier.clip(PillShape)
            .background(ShoyoColors.Glass)
            .border(1.dp, ShoyoColors.PanelBorder, PillShape)
            .then(if (onClick != null) Modifier.tap(onClick) else Modifier)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically, content = content
    )
}

@Composable
fun ShoyoTopBar(
    appName: String, version: String, breadcrumb: String, userName: String,
    logo: Painter, backIcon: ImageVector, filesIcon: ImageVector,
    onBack: () -> Unit, onFiles: () -> Unit, onUser: () -> Unit = {}, onMenu: () -> Unit = {},
    extra: @Composable RowScope.() -> Unit = {}
) {
    Row(
        Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlassPill(onClick = onMenu) {
            Image(logo, contentDescription = appName, modifier = Modifier.size(36.dp))
            Spacer(Modifier.width(10.dp))
            Column {
                Text(appName, color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleMedium)
                Text("LAUNCHER · $version", color = ShoyoColors.TextSecondary, style = MaterialTheme.typography.labelSmall)
            }
        }
        GlassPill(onClick = onBack) {
            Box(Modifier.size(34.dp).clip(CircleShape).background(ShoyoColors.Accent), contentAlignment = Alignment.Center) {
                Icon(backIcon, null, tint = ShoyoColors.OnAccent)
            }
            Spacer(Modifier.width(10.dp))
            Text(breadcrumb, color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleMedium)
        }
        Spacer(Modifier.weight(1f))
        extra()
        GlassPill(onClick = onFiles) {
            Box(Modifier.size(34.dp).clip(CircleShape).background(ShoyoColors.Accent), contentAlignment = Alignment.Center) {
                Icon(filesIcon, null, tint = ShoyoColors.OnAccent)
            }
            Spacer(Modifier.width(10.dp))
            Text("Arquivos", color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleMedium)
        }
        GlassPill(onClick = onUser) {
            Box(
                Modifier.size(34.dp).clip(RoundedCornerShape(8.dp)).background(ShoyoColors.IconChip),
                contentAlignment = Alignment.Center
            ) {
                Text(userName.take(1).uppercase(), color = ShoyoColors.Accent, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(10.dp))
            Text(userName, color = ShoyoColors.TextPrimary, style = MaterialTheme.typography.titleMedium)
        }
    }
}
