package com.example.projeto07

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projeto07.ui.theme.Projeto07Theme
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class Tela2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        setContent {
            Projeto07Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Tela2Content(extras)
                }
            }
        }
    }
}

@Composable
fun Tela2Content(name: Bundle?, modifier: Modifier = Modifier) {
    val usuarios = remember { mutableStateListOf<Usuario>() }
    
    val errorApi = remember { mutableStateOf("") }

    val api = RetrofitService.getApiUsuariosService()

    val nome = remember { mutableStateOf("") }
    val login = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }

    val get = api.get()
    val isExibirListagem = remember { mutableStateOf(false) }
    val isExibirFormulario = remember { mutableStateOf(false) }
    val isExibirLogoff = remember { mutableStateOf(false) }


    get.enqueue(object : Callback<List<Usuario>>{
        override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
            if(response.isSuccessful){
            val lista = response.body()
                if(lista != null){
                    usuarios.clear()
                    usuarios.addAll(lista)
                }
            }else {
                errorApi.value = response.errorBody().toString()
            }
        }

        override fun onFailure(call: retrofit2.Call<List<Usuario>>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextButton(onClick = {
            isExibirListagem.value = true
        }) {
            Text("Listagem")
        }
        TextButton(onClick = {
            isExibirListagem.value = false
            isExibirFormulario.value = true
        }) {
            Text("Novo")
        }
        TextButton(onClick = {
            isExibirLogoff.value = true
            isExibirFormulario.value = false
            isExibirListagem.value = false
        }) {
            Text("Sair")
        }

        if (isExibirListagem.value) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items = usuarios) { usuario ->
                    Text(text = usuario.nome)
                    Text(text = usuario.login)
                    Text(text = usuario.foto)
                }
            }
        } else {
            Text("Selecione sua ação")
        }

        if (isExibirFormulario.value) {
            Text("Novo Usuário")

            Column {
                TextField(
                    value = login.value,
                    onValueChange = { login.value = it },
                    label = { Text("Login") }
                )

                TextField(
                    value = nome.value,
                    onValueChange = {nome.value = it},
                    label = { Text("Nome") }
                )

                TextButton(onClick = {
                    val novoUsuario = Usuario(login = login.value, nome = nome.value, foto = "", id = 0)
                    val post = api.post(novoUsuario)
                    post.enqueue(object : Callback<Usuario> {
                        override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                            if (response.isSuccessful) {
                                val usuario = response.body()
                                if (usuario != null) {
                                    usuarios.add(usuario)
                                    isExibirFormulario.value = false
                                    message.value = "Usuário cadastrado com sucesso"

                                }
                            } else {
                                errorApi.value = response.errorBody().toString()
                            }
                        }

                        override fun onFailure(call: Call<Usuario>, t: Throwable) {
                            errorApi.value = t.message.toString()
                        }
                    })
                }) {
                    Text("Salvar")
                }

                Text(text = message.value)
            }
        }

        if (isExibirLogoff.value){
            Text("Deseja realmente sair?")
            Row {
                TextButton(onClick = {
                }) {
                    Text("Sim")
                }
                TextButton(onClick = {
                    isExibirLogoff.value = false
                }) {
                    Text("Não")
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Projeto07Theme {
        Tela2Content(null)
    }
}