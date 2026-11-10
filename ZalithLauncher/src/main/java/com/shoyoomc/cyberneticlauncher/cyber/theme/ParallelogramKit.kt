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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.composed
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

/**
 * Destaques industriais: faixa de perigo (listras laranja/preto) no canto superior-esquerdo,
 * LED no canto inferior-direito e rebites nos outros dois cantos.
 */
fun Modifier.paraAccents(skew: Dp = 14.dp, color: Color? = null, len: Dp = 34.dp, stroke: Dp = 3.dp): Modifier =
    drawBehind {
        val c = color ?: ShoyoColors.Accent
        val s = skew.toPx().coerceAtMost(size.width / 3f)
        val l = len.toPx(); val t = (stroke + 2.dp).toPx()
        // faixa de perigo
        val step = 7.dp.toPx()
        var x = s
        var on = true
        while (x < s + l) {
            val w = minOf(step, s + l - x)
            drawRect(if (on) c else Color(0xFF0A0807), Offset(x, 0f), Size(w, t))
            x += step; on = !on
        }
        // LED
        val ls = 5.dp.toPx()
        drawRect(c, Offset(size.width - s - 14.dp.toPx() - ls, size.height - t / 2 - ls / 2), Size(ls, ls))
        drawRect(c.copy(alpha = 0.35f), Offset(size.width - s - 6.dp.toPx() - ls, size.height - t / 2 - ls / 2), Size(ls, ls))
        // rebites
        val r = 1.8.dp.toPx(); val rc = c.copy(alpha = 0.55f)
        drawCircle(rc, r, Offset(size.width - 10.dp.toPx(), 7.dp.toPx()))
        drawCircle(rc, r, Offset(10.dp.toPx(), size.height - 7.dp.toPx()))
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

/** Texto de HUD em monoespaçado com LED pulsante: "// VERSÃO ATIVA". */
@Composable
fun HudLabel(text: String, modifier: Modifier = Modifier, color: Color = ShoyoColors.Accent) {
    val t = rememberInfiniteTransition(label = "led")
    val a by t.animateFloat(0.35f, 1f, infiniteRepeatable(tween(900, easing = LinearEasing), RepeatMode.Reverse), label = "a")
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(6.dp).background(color.copy(alpha = a)))
        Spacer(Modifier.width(8.dp))
        Text(
            text.uppercase(), color = color,
            fontFamily = FontFamily.Monospace, fontSize = 11.sp, letterSpacing = 2.sp
        )
    }
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

/** Reflexo de vidro: faixa de luz suave no topo do painel (glassmorphism). */
fun Modifier.glassSheen(): Modifier = drawBehind {
    drawRect(
        Brush.verticalGradient(
            listOf(Color.White.copy(alpha = 0.10f), Color.Transparent),
            endY = size.height * 0.45f
        )
    )
}

/**
 * Fundo "aurora": brilhos que derivam devagar (cor do tema) + estrelas.
 * Não pinta fundo opaco, então funciona sobre papel de parede.
 */
fun Modifier.auroraBackground(): Modifier = composed {
    val t = rememberInfiniteTransition(label = "aurora")
    val drift by t.animateFloat(0f, 1f, infiniteRepeatable(tween(14000, easing = LinearEasing), RepeatMode.Reverse), label = "d")
    val a = ShoyoColors.Accent; val b = ShoyoColors.AccentDeep; val c = ShoyoColors.Secondary
    drawBehind {
        val w = size.width; val h = size.height
        drawRect(Brush.verticalGradient(listOf(ShoyoColors.Bg, ShoyoColors.BgAlt)))
        drawCircle(
            Brush.radialGradient(listOf(a.copy(alpha = 0.30f), Color.Transparent), Offset(w * (0.25f + 0.35f * drift), h * 0.12f), w * 0.55f),
            w * 0.55f, Offset(w * (0.25f + 0.35f * drift), h * 0.12f)
        )
        drawCircle(
            Brush.radialGradient(listOf(b.copy(alpha = 0.22f), Color.Transparent), Offset(w * (0.8f - 0.3f * drift), h * 0.25f), w * 0.45f),
            w * 0.45f, Offset(w * (0.8f - 0.3f * drift), h * 0.25f)
        )
        drawCircle(
            Brush.radialGradient(listOf(c.copy(alpha = 0.08f), Color.Transparent), Offset(w * 0.1f, h * 0.95f), w * 0.4f),
            w * 0.4f, Offset(w * 0.1f, h * 0.95f)
        )
        // estrelas fixas (posições pseudo-aleatórias determinísticas)
        for (i in 0 until 70) {
            val x = ((i * 7919) % 1000) / 1000f * w
            val y = ((i * 104729) % 1000) / 1000f * h
            val r = (0.6f + (i % 3) * 0.5f) * density
            drawCircle(Color.White.copy(alpha = 0.12f + (i % 5) * 0.05f), r, Offset(x, y))
        }
    }
}
