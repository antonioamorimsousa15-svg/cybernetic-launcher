package com.shoyoomc.aflecklauncher.ui.screens.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.shoyoomc.aflecklauncher.R
import coil3.compose.AsyncImage
import kotlin.math.roundToInt

/** Uma "vaga" de imagem da tela (imagem escolhida pelo usuário + opacidade), salva no aparelho. */
class AfImageSlot(private val prefs: SharedPreferences, private val key: String) {
    var uri by mutableStateOf(prefs.getString("$key.uri", null))
        private set
    var opacity by mutableIntStateOf(prefs.getInt("$key.opacity", 100))
        private set

    fun updateUri(value: String?) {
        uri = value
        prefs.edit().apply { if (value == null) remove("$key.uri") else putString("$key.uri", value) }.apply()
    }

    fun updateOpacity(value: Int) {
        opacity = value.coerceIn(0, 100)
        prefs.edit().putInt("$key.opacity", opacity).apply()
    }
}

@Composable
fun rememberAfSlot(key: String): AfImageSlot {
    val ctx = LocalContext.current
    return remember(key) { AfImageSlot(ctx.getSharedPreferences("afleck_ui", Context.MODE_PRIVATE), key) }
}

/** Mostra a imagem escolhida (ou a padrão, se houver) com a opacidade da vaga. */
@Composable
fun AfSlotImage(slot: AfImageSlot, defaultRes: Int?, modifier: Modifier = Modifier) {
    val a = slot.opacity / 100f
    val u = slot.uri
    if (u != null) {
        AsyncImage(model = u, contentDescription = null, contentScale = ContentScale.Crop, alpha = a, modifier = modifier)
    } else if (defaultRes != null) {
        Image(painterResource(defaultRes), null, contentScale = ContentScale.Crop, alpha = a, modifier = modifier)
    }
}

/** Janela "Escolher / Usar padrão / Opacidade / Fechar", como no Cryogenic. */
@Composable
fun AfSlotDialog(title: String, description: String, slot: AfImageSlot, onDismiss: () -> Unit) {
    val ctx = LocalContext.current
    val picker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            runCatching {
                ctx.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            slot.updateUri(uri.toString())
        }
    }
    Dialog(onDismissRequest = onDismiss) {
        AfPanel(Modifier.widthIn(max = 460.dp), radius = 30.dp, deep = true) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(title, color = AfText, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(description, color = AfDim, fontSize = 13.sp)
                Text(
                    if (slot.uri == null) "Usando a imagem padrão do Afleck" else "Usando a imagem escolhida",
                    color = AfAccent, fontSize = 13.sp, fontWeight = FontWeight.Bold
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    AfButton("Escolher", Modifier.weight(1f), R.drawable.ic_image_filled) { picker.launch(arrayOf("image/*")) }
                    Box(Modifier.weight(1f).alpha(if (slot.uri != null) 1f else 0.45f)) {
                        AfOutlineButton("Usar padrão", Modifier.fillMaxWidth()) { if (slot.uri != null) slot.updateUri(null) }
                    }
                }
                Text("Opacidade", color = AfText, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Slider(
                        value = slot.opacity / 100f,
                        onValueChange = { slot.updateOpacity((it * 100f).roundToInt()) },
                        modifier = Modifier.weight(1f),
                        colors = SliderDefaults.colors(
                            thumbColor = AfAccent, activeTrackColor = AfAccent, inactiveTrackColor = AfBorder.copy(alpha = 0.35f)
                        )
                    )
                    Spacer(Modifier.padding(start = 8.dp))
                    Text("${slot.opacity}%", color = AfText, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
                AfOutlineButton("Fechar", Modifier.fillMaxWidth(), onClick = onDismiss)
            }
        }
    }
}
