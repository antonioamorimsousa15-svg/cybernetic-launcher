package com.shoyoomc.cyberneticlauncher.cyber.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/** Aba do menu superior (Ajustes, Download). */
data class TabDef(val label: String, val icon: ImageVector)

/** Barra de abas numeradas (01, 02, 03...) com rolagem horizontal. */
@Composable
fun TabBar(tabs: List<TabDef>, selected: Int, onSelect: (Int) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .clip(PillShape)
            .background(ShoyoColors.Glass)
            .border(1.dp, ShoyoColors.PanelBorder, PillShape)
            .horizontalScroll(rememberScrollState())
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEachIndexed { i, t -> TopTab(i + 1, t.label, t.icon, i == selected) { onSelect(i) } }
    }
}

/** Painel rolável (conteúdo das telas de Ajustes). */
@Composable
fun ScrollPanel(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    NeonPanel(modifier) {
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()), content = content)
    }
}

/** Linha fina separando itens de configuração. */
@Composable
fun NeonDivider() {
    Box(Modifier.fillMaxWidth().height(1.dp).background(ShoyoColors.PanelBorder.copy(alpha = 0.5f)))
}

/** Título de seção com barrinha lateral (ex.: PLATAFORMA ALVO). */
@Composable
fun SectionTitle(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.width(3.dp).height(16.dp).clip(RoundedCornerShape(2.dp)).background(ShoyoColors.Accent))
        Spacer(Modifier.width(8.dp))
        Text(text, color = ShoyoColors.Accent, style = MaterialTheme.typography.titleMedium)
    }
}

/** Campo de texto no estilo do launcher (busca, nome da conta...). */
@Composable
fun NeonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leading: ImageVector? = Icons.Filled.Search
) {
    val shape = RoundedCornerShape(16.dp)
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(color = ShoyoColors.TextPrimary, fontSize = 15.sp, fontFamily = FontFamily.Serif),
        cursorBrush = SolidColor(ShoyoColors.Accent),
        modifier = modifier.fillMaxWidth(),
        decorationBox = { inner ->
            Row(
                Modifier.fillMaxWidth().clip(shape)
                    .background(ShoyoColors.RowBg)
                    .border(1.dp, ShoyoColors.PanelBorder, shape)
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) Text(placeholder, color = ShoyoColors.TextSecondary.copy(alpha = 0.6f), fontSize = 15.sp)
                    inner()
                }
                if (leading != null) Icon(leading, null, tint = ShoyoColors.Accent)
            }
        }
    )
}

/** Anel de progresso com marcas de relógio (tela de setup). */
@Composable
fun ProgressRing(progress: Float, subtitle: String, modifier: Modifier = Modifier, size: Dp = 190.dp) {
    val accent = ShoyoColors.Accent
    val track = ShoyoColors.Accent.copy(alpha = 0.18f)
    val ticks = ShoyoColors.PanelBorder
    Box(modifier.height(size).fillMaxWidth(), contentAlignment = Alignment.Center) {
        Canvas(Modifier.size(size)) {
            val c = Offset(this.size.width / 2f, this.size.height / 2f)
            val r = this.size.minDimension / 2f - 2.dp.toPx()
            for (i in 0 until 60) {
                val a = 2.0 * PI * i / 60.0
                val len = if (i % 5 == 0) 9.dp.toPx() else 4.dp.toPx()
                drawLine(
                    ticks,
                    Offset(c.x + (cos(a) * (r - len)).toFloat(), c.y + (sin(a) * (r - len)).toFloat()),
                    Offset(c.x + (cos(a) * r).toFloat(), c.y + (sin(a) * r).toFloat()),
                    2.dp.toPx()
                )
            }
            val inset = 18.dp.toPx()
            val arcSize = Size(this.size.width - inset * 2, this.size.height - inset * 2)
            val stroke = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
            drawArc(track, 135f, 270f, false, Offset(inset, inset), arcSize, style = stroke)
            drawArc(accent, 135f, 270f * progress.coerceIn(0f, 1f), false, Offset(inset, inset), arcSize, style = stroke)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("${(progress.coerceIn(0f, 1f) * 100).toInt()}%", color = ShoyoColors.TextPrimary, fontSize = 38.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, color = ShoyoColors.TextSecondary, fontSize = 12.sp)
        }
    }
}
