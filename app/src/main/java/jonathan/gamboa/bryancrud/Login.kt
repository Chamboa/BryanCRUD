package jonathan.gamboa.bryancrud

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        val btnLogin = findViewById<Button>(R.id.btnIniciarSesion)
        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtContra = findViewById<EditText>(R.id.txtContraseña)

        btnLogin.setOnClickListener {

            val Correo = txtCorreo.text.toString()
            val Contrasena = txtContra.text.toString()
            var hayErrores = false

            if (!Correo.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+[.]+[a-z]+"))) {
                txtCorreo.error = "Ingresa los datos que se te piden"
                hayErrores = true
            } else {
                txtCorreo.error = null
            }

            if (Contrasena.length <= 7) {
                txtContra.error = "Ingresa los datos que se te piden"
                hayErrores = true
            } else {
                txtContra.error = null
            }

            if (!hayErrores) {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val objConexion = ClaseConexion().cadenaConexion()

                        val validarusuario = objConexion?.prepareStatement(
                            "SELECT *FROM doctor WHERE nombre = ? AND dim = ?"
                        )!!
                        validarusuario.setString(1, Correo)
                        validarusuario.setString(2, txtContra.text.toString())

                        validarusuario.executeQuery()

                        val pantalla = Intent(this@Login, MainActivity::class.java)
                        startActivity(pantalla)


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