package com.shoyoomc.cyberneticlauncher.cyber.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * CYBERNETIC — linguagem visual própria: tudo é PARALELOGRAMO.
 * A borda de cima é deslocada para a direita (inclinação constante em dp).
 */
class ParaShape(private val skew: Dp = 14.dp) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val s = with(density) { skew.toPx() }.coerceAtMost(size.width / 3f)
        val p = Path().apply {
            moveTo(s, 0f); lineTo(size.width, 0f)
            lineTo(size.width - s, size.height); lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(p)
    }
}

/** Segmentos de destaque nos cantos (topo-esquerda e base-direita) seguindo a inclinação. */
fun Modifier.paraAccents(skew: Dp = 14.dp, color: Color? = null, len: Dp = 34.dp, stroke: Dp = 3.dp): Modifier =
    drawBehind {
        val c = color ?: ShoyoColors.Accent
        val s = skew.toPx().coerceAtMost(size.width / 3f)
        val l = len.toPx(); val t = stroke.toPx()
        drawLine(c, Offset(s, t / 2), Offset(s + l, t / 2), t)
        drawLine(c, Offset(size.width - s - l, size.height - t / 2), Offset(size.width - s, size.height - t / 2), t)
    }

/** Fundo com hachuras diagonais finas, bem sutis (textura tecnológica). */
fun Modifier.hatch(alpha: Float = 0.05f, gap: Dp = 14.dp): Modifier = drawBehind {
    val g = gap.toPx(); val c = Color.White.copy(alpha = alpha)
    var x = -size.height
    while (x < size.width) {
        drawLine(c, Offset(x, size.height), Offset(x + size.height, 0f), 1f)
        x += g
    }
}

/** Texto de HUD em monoespaçado: "// VERSÃO ATIVA". */
@Composable
fun HudLabel(text: String, modifier: Modifier = Modifier, color: Color = ShoyoColors.Accent) {
    Text(
        text.uppercase(), modifier = modifier, color = color,
        fontFamily = FontFamily.Monospace, fontSize = 11.sp, letterSpacing = 2.sp
    )
}

/** Botão JOGAR gigante, com chevrons que correm. */
@Composable
fun HeroPlayButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val shape = ParaShape(28.dp)
    val t = rememberInfiniteTransition(label = "chev")
    val phase by t.animateFloat(0f, 3f, infiniteRepeatable(tween(1500, easing = LinearEasing), RepeatMode.Restart), label = "p")
    Box(
        modifier
            .neonGlow(shape)
            .clip(shape)
            .background(Brush.horizontalGradient(listOf(ShoyoColors.Accent, ShoyoColors.AccentDeep)))
            .tap(onClick)
            .padding(horizontal = 40.dp, vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text.uppercase(), color = ShoyoColors.OnAccent, fontSize = 26.sp,
                fontWeight = FontWeight.Black, letterSpacing = 10.sp
            )
            Row {
                for (i in 0 until 3) {
                    val a = (1f - ((phase - i).let { if (it < 0) it + 3 else it }) / 3f).coerceIn(0.25f, 1f)
                    Text("»", color = ShoyoColors.OnAccent.copy(alpha = a), fontSize = 26.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

/** Gráfico de barras inclinadas (telemetria). */
@Composable
fun TelemetryBars(values: List<Float>, modifier: Modifier = Modifier) {
    val c1 = ShoyoColors.Accent; val c2 = ShoyoColors.AccentDeep
    Canvas(modifier.height(54.dp).fillMaxWidth()) {
        if (values.isEmpty()) return@Canvas
        val gap = 6.dp.toPx(); val sk = 6.dp.toPx()
        val w = (size.width - gap * (values.size - 1)) / values.size
        values.forEachIndexed { i, v ->
            val h = size.height * v.coerceIn(0.05f, 1f)
            val x = i * (w + gap)
            val p = Path().apply {
                moveTo(x + sk, size.height - h); lineTo(x + w, size.height - h)
                lineTo(x + w - sk, size.height); lineTo(x, size.height); close()
            }
            drawPath(p, Brush.verticalGradient(listOf(c1, c2)))
        }
    }
}

/** Valor com legenda no estilo HUD: RENDER  GL4ES. */
@Composable
fun HudStat(label: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier.clip(ParaShape(8.dp)).background(ShoyoColors.Accent.copy(alpha = 0.10f))
            .drawBehind { drawRect(ShoyoColors.Accent, Offset.Zero, Size(3.dp.toPx(), size.height)) }
            .padding(start = 16.dp, end = 18.dp, top = 6.dp, bottom = 6.dp)
    ) {
        androidx.compose.foundation.layout.Column {
            Text(label.uppercase(), color = ShoyoColors.TextSecondary, fontFamily = FontFamily.Monospace, fontSize = 10.sp, letterSpacing = 2.sp)
            Text(value, color = ShoyoColors.TextPrimary, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}
