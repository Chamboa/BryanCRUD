package jonathan.javier.bryancrud

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ver_ppacientes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_ppacientes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val idRecibido = intent.getStringExtra("id")
        val nombreRecibido = intent.getStringExtra("nombre")
        val apellidosRecibidos = intent.getStringExtra("apellidos")
        val edadRecibida = intent.getIntExtra("edad", 0)
        val enfermedadRecibida = intent.getStringExtra("enfermedad")
        val fechaRecibida = intent.getStringExtra("fecha")
        val medicinaRecibida = intent.getStringExtra("medicina")

        val txtNombre = findViewById<TextView>(R.id.txtNombre)
        val txtApellidos = findViewById<TextView>(R.id.txtApellidos)
        val txtEdad = findViewById<TextView>(R.id.txtEdad)
        val txtEnfermedad = findViewById<TextView>(R.id.txtEnfermedad)
        val txtFecha = findViewById<TextView>(R.id.txtFecha)
        val txtMedicina = findViewById<TextView>(R.id.txtMedicina)

        txtNombre.text = "Nombre: $nombreRecibido"
        txtApellidos.text = "Apellidos: $apellidosRecibidos"
        txtEdad.text = "Edad: $edadRecibida"
        txtEnfermedad.text = "Enfermedad: $enfermedadRecibida"
        txtFecha.text = "Fecha: $fechaRecibida"
        txtMedicina.text = "Medicina: $medicinaRecibida"

    }
}