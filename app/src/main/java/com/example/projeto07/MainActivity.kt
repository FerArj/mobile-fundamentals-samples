package com.example.projeto07

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projeto07.ui.theme.Projeto07Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Projeto07Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Tela("Android")
                }
            }
        }
    }
}

@Composable
fun Tela(name: String, modifier: Modifier = Modifier) {

    val login = remember {
        mutableStateOf("")
    }

    val senha = remember {
        mutableStateOf("")
    }

    val imageModifier = Modifier
        .size(300.dp)

    var texto = remember {
        mutableStateOf("")
    }

    val contexto = LocalContext.current

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Image(painter = painterResource(id = R.mipmap.logo), contentDescription = "logo do app", modifier = imageModifier)

        TextField(
            value = login.value,
            onValueChange = { login.value = it },
            label = { Text("Login") })

        Spacer(modifier = Modifier.size(16.dp))

        TextField(
            value = senha.value,
            onValueChange = { senha.value = it },
            label = { Text("Senha") })


        Spacer(modifier = Modifier.size(16.dp))

        Row {
            Button(onClick = {
                if (login.value == "aidmin" && senha.value == "seinha"){
                    val tela2 = Intent(contexto, Tela2::class.java)
                    contexto.startActivity(tela2)
                } else {
                    texto.value = "Credenciais inválidas"
                }
            }) {
                Text("Entrar")
            }

            Button(onClick = { texto.value = "VERIFIQUE SEU EMAIL" }) {
                Text("Esqueci a senha")
            }
        }
        Text(texto.value, color = Color.Blue)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaPreview() {
    Projeto07Theme {
        Tela("Android")
    }
}