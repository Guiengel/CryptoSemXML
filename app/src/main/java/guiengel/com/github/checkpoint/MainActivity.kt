package guiengel.com.github.checkpoint

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import guiengel.com.github.checkpoint.service.MercadoBitcoinServiceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    val service = remember { MercadoBitcoinServiceFactory().create() }
    var lastPrice by remember { mutableStateOf<Double?>(null) }
    var lastUpdated by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    fun load() {
        isLoading = true
        error = null
        scope.launch {
            try {
                val response = service.getTicker()
                if (response.isSuccessful) {
                    val ticker = response.body()?.ticker
                    lastPrice = ticker?.last?.toDoubleOrNull()
                    lastUpdated = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                        .format(Date())
                } else {
                    error = "Erro ${response.code()}"
                }
            } catch (e: Exception) {
                error = e.message ?: "Erro ao carregar"
            } finally {
                isLoading = false
            }
        }
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Monitor de Crypto Moedas - BITCOIN") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Cyan,
            )
        ) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (error != null) {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(16.dp))
            }
            Text(
                text = "Cotação - BITCOIN",
                color = Color(0xFF808080)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = lastPrice?.let {
                    NumberFormat.getCurrencyInstance(Locale.getDefault()).format(it)
                } ?: "R$ 0,00",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold ,
                color = Color(0xFF808080)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = lastUpdated ?: "dd/mm/yyyy hh:mm:ss",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF999999)
            )
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { load() },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00FF7F),
                    contentColor = Color.Black
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                if (isLoading) {
                    CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp, color = Color.Black)
                    Spacer(Modifier.width(8.dp))
                    Text("Atualizando")
                } else {
                    Text("ATUALIZAR")
                }
            }
        }
    }
}
