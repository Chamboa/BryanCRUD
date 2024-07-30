package jonathan.javier.bryancrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginTitle)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLogin = findViewById<Button>(R.id.btnIniciarSesion)
        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtApellidos = findViewById<EditText>(R.id.txtApellidos)

        btnLogin.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val apellidos = txtApellidos.text.toString()
            var hayErrores = false

            if (nombre.isEmpty() || apellidos.isEmpty()) {
                hayErrores = true
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }

            if (!hayErrores) {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val objConexion = ClaseConexion().cadenaConexion()

                        val validarUsuario = objConexion?.prepareStatement(
                            "select *from USUARIO where nombre = ? and apellidos = ?"
                        )!!
                        validarUsuario.setString(1, nombre)
                        validarUsuario.setString(2, apellidos)

                        val resultado = validarUsuario.executeQuery()

                        withContext(Dispatchers.Main) {
                            if (resultado.next()) {
                                // Usuario autenticado correctamente
                                val pantalla = Intent(this@Login, MainActivity::class.java)
                                startActivity(pantalla)
                                finish()
                            } else {
                                // Usuario no encontrado
                                Toast.makeText(this@Login, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                            }
                        }

                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Login, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}