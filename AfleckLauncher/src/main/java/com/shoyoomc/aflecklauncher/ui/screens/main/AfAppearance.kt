package com.shoyoomc.aflecklauncher.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import com.shoyoomc.aflecklauncher.R
import kotlin.math.roundToInt

/** Tela de personalização: Estúdio de temas, vibração, contraste, opacidade e plano de fundo. */
@Composable
fun AfAppearance(bgSlot: AfImageSlot, onClose: () -> Unit) {
    var bgDialog by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize().padding(start = 8.dp, end = 8.dp, bottom = 8.dp).afClick { }) {
        AfPanel(Modifier.fillMaxSize(), deep = true) {
            Column(
                Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text("Aparência", color = AfText, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Personalize as cores, o brilho e o plano de fundo do Afleck.", color = AfDim, fontSize = 13.sp)

                Text("Estúdio de temas", color = AfAccent, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    AfThemeState.themes.forEach { t ->
                        val selected = t.id == AfThemeState.themeId
                        Column(
                            Modifier.width(120.dp).clip(RoundedCornerShape(18.dp))
                                .background(AfNavy.copy(alpha = 0.6f))
                                .border(if (selected) 3.dp else 1.dp, if (selected) AfBorder else AfDim.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
                                .afClick { AfThemeState.chooseTheme(t.id) }
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Image(
                                painterResource(t.bg), null, contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxWidth().height(64.dp).clip(RoundedCornerShape(12.dp))
                            )
                            Box(
                                Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp))
                                    .background(Brush.horizontalGradient(listOf(t.accent, t.accent2)))
                            )
                            Text(t.name, color = AfText, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                        }
                    }
                }

                SliderRow("Vibração", AfThemeState.vibrance, -100, 100, signed = true) { AfThemeState.changeVibrance(it) }
                SliderRow("Contraste", AfThemeState.contrast, -100, 100, signed = true) { AfThemeState.changeContrast(it) }
                SliderRow("Opacidade da interface", AfThemeState.panelOpacity, 30, 100) { AfThemeState.changePanelOpacity(it) }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Animar degradê", color = AfText, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text("Move as cores dos botões e destaques.", color = AfDim, fontSize = 12.sp)
                    }
                    Switch(
                        checked = AfThemeState.animate,
                        onCheckedChange = { AfThemeState.changeAnimate(it) },
                        colors = SwitchDefaults.colors(checkedThumbColor = AfNavy, checkedTrackColor = AfAccent)
                    )
                }

                Text("Plano de fundo", color = AfAccent, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(
                    if (bgSlot.uri == null) "Usando o fundo do tema" else "Usando a imagem escolhida",
                    color = AfDim, fontSize = 13.sp
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    AfButton("Plano de fundo personalizado", Modifier.weight(1f), R.drawable.ic_image_filled) { bgDialog = true }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    AfButton("Aplicar tema", Modifier.weight(1f), null, onClose)
                    AfOutlineButton("Zerar ajustes", Modifier.weight(1f)) { AfThemeState.resetAdjustments() }
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
    if (bgDialog) {
        AfSlotDialog(
            "Plano de fundo do Launcher",
            "Imagem ou GIF para todo o launcher. A opacidade daqui é separada das outras.",
            bgSlot
        ) { bgDialog = false }
    }
}

@Composable
private fun SliderRow(label: String, value: Int, min: Int, max: Int, signed: Boolean = false, onChange: (Int) -> Unit) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(label, color = AfText, fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(
                (if (signed && value > 0) "+" else "") + "$value%",
                color = AfAccent, fontSize = 15.sp, fontWeight = FontWeight.Bold
            )
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onChange(it.roundToInt()) },
            valueRange = min.toFloat()..max.toFloat(),
            colors = SliderDefaults.colors(
                thumbColor = AfAccent, activeTrackColor = AfAccent, inactiveTrackColor = AfBorder.copy(alpha = 0.35f)
            )
        )
    }
}
