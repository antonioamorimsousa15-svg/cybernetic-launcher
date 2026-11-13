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
import androidx.compose.ui.draw.drawWithCache
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
class ParaShape(
    private val skew: Dp = 14.dp,
    private val rounded: Boolean = ShoyoUi.palette.rounded
) : Shape {
    override fun equals(other: Any?) = other is ParaShape && other.skew == skew && other.rounded == rounded
    override fun hashCode() = skew.hashCode() * 31 + rounded.hashCode()

    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        if (rounded) {
            // Cryogenic: cantos arredondados (skew pequeno = círculo/pílula; maior = cartão)
            val r = if (skew.value <= 7f) size.height / 2f
            else minOf(size.height / 2f, with(density) { (skew.value * 1.8f).coerceAtMost(26f).dp.toPx() })
            return Outline.Rounded(androidx.compose.ui.geometry.RoundRect(0f, 0f, size.width, size.height,
                androidx.compose.ui.geometry.CornerRadius(r, r)))
        }
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
        if (ShoyoUi.palette.rounded) return@drawBehind
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
    if (ShoyoUi.palette.rounded) return@drawBehind
    val g = gap.toPx(); val c = Color.White.copy(alpha = alpha)
    var x = -size.height
    while (x < size.width) {
        drawLine(c, Offset(x, size.height), Offset(x + size.height, 0f), 1f)
        x += g
    }
}

/** Texto de HUD em monoespaçado com LED fixo: "// VERSÃO ATIVA". */
@Composable
fun HudLabel(text: String, modifier: Modifier = Modifier, color: Color = ShoyoColors.Accent) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(6.dp).background(color))
        Spacer(Modifier.width(8.dp))
        Text(
            text.uppercase(), color = color,
            fontFamily = CyberFont, fontSize = 11.sp, letterSpacing = 2.sp
        )
    }
}

/** Botão JOGAR gigante, com chevrons que correm. */
@Composable
fun HeroPlayButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val shape = ParaShape(28.dp)
    val a = ShoyoColors.Accent; val b = ShoyoColors.AccentDeep
    var bg: Modifier = Modifier.background(Brush.horizontalGradient(listOf(a, b)))
    if (false && ShoyoUi.gradAnim) {
        // degradê em movimento (só neste botão; leitura do estado apenas na fase de desenho)
        val t = rememberInfiniteTransition(label = "grad")
        val ms = (9000 - (ShoyoUi.gradSpeed.coerceIn(5f, 100f) - 5f) / 95f * 7500f).toInt()
        val shift = t.animateFloat(0f, 1f, infiniteRepeatable(tween(ms, easing = LinearEasing), RepeatMode.Restart), label = "s")
        bg = Modifier.drawBehind {
            val x = shift.value * size.width
            drawRect(Brush.horizontalGradient(listOf(a, b), startX = x, endX = x + size.width, tileMode = androidx.compose.ui.graphics.TileMode.Mirror))
        }
    }
    Box(
        modifier
            .neonGlow(shape)
            .clip(shape)
            .then(bg)
            .tap(onClick)
            .padding(horizontal = 40.dp, vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text.uppercase(), color = ShoyoColors.OnAccent, fontSize = 26.sp,
                fontWeight = FontWeight.Black, letterSpacing = 10.sp
            )
            Text("»»", color = ShoyoColors.OnAccent, fontSize = 26.sp, fontWeight = FontWeight.Black)
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
            Text(label.uppercase(), color = ShoyoColors.TextSecondary, fontFamily = CyberFont, fontSize = 10.sp, letterSpacing = 2.sp)
            Text(value, color = ShoyoColors.TextPrimary, fontFamily = CyberFont, fontWeight = FontWeight.Bold, fontSize = 18.sp)
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
 * Fundo "aurora": brilhos suaves (cor do tema) + estrelas, tudo ESTÁTICO (sem animação, sem custo por quadro).
 * Não pinta fundo opaco, então o papel de parede aparece por trás.
 */
fun Modifier.auroraBackground(): Modifier = composed {
    val a = ShoyoColors.Accent; val b = ShoyoColors.AccentDeep; val c = ShoyoColors.Secondary
    drawWithCache {
        val w = size.width; val h = size.height
        val g1 = Brush.radialGradient(listOf(a.copy(alpha = 0.22f), Color.Transparent), Offset(w * 0.35f, h * 0.10f), w * 0.55f)
        val g2 = Brush.radialGradient(listOf(b.copy(alpha = 0.16f), Color.Transparent), Offset(w * 0.75f, h * 0.22f), w * 0.45f)
        val g3 = Brush.radialGradient(listOf(c.copy(alpha = 0.06f), Color.Transparent), Offset(w * 0.1f, h * 0.95f), w * 0.4f)
        onDrawBehind {
            drawRect(g1); drawRect(g2); drawRect(g3)
            // grade técnica sutil
            val step = 48f * density
            var gx = 0f
            while (gx < w) { drawLine(Color.White.copy(alpha = 0.025f), Offset(gx, 0f), Offset(gx, h), 1f); gx += step }
            var gy = 0f
            while (gy < h) { drawLine(Color.White.copy(alpha = 0.025f), Offset(0f, gy), Offset(w, gy), 1f); gy += step }
            for (i in 0 until 40) {
                val x = ((i * 7919) % 1000) / 1000f * w
                val y = ((i * 104729) % 1000) / 1000f * h
                drawCircle(Color.White.copy(alpha = 0.10f + (i % 5) * 0.04f), (0.6f + (i % 3) * 0.5f) * density, Offset(x, y))
            }
        }
    }
}


/** Colchetes de canto (topo-direita e base-direita) no estilo HUD técnico. Estático, sem animação. */
fun Modifier.techFrame(): Modifier = composed {
    val a = ShoyoColors.Accent
    val b = ShoyoColors.Secondary
    drawWithCache {
        val len = 26f * density
        val inset = 2f * density
        val sw = 3f * density
        onDrawWithContent {
            drawContent()
            val w = size.width; val h = size.height
            // topo-direita
            drawLine(a, Offset(w - len - inset, inset), Offset(w - inset, inset), sw)
            drawLine(a, Offset(w - inset, inset), Offset(w - inset, len + inset), sw)
            // base-direita
            drawLine(b, Offset(w - len - inset, h - inset), Offset(w - inset, h - inset), sw)
            drawLine(b, Offset(w - inset, h - len - inset), Offset(w - inset, h - inset), sw)
        }
    }
}

/** Fonte técnica (sem dependência de recursos). */
private val CyberFont = FontFamily.SansSerif
