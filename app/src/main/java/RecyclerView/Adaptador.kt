package RecyclerView

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.javier.bryancrud.R
import jonathan.gamboa.bryancrud.ver_ppacientes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.PacientesDC

class Adaptador(private var Datos: List<PacientesDC>) : RecyclerView.Adapter<ViewHolder>() {

    fun actualizarRecyclerView(nuevaLista: List<PacientesDC>) {
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun eliminarRegistro(idPaciente: String, posicion: Int) {
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            val deletePaciente = objConexion?.prepareStatement("DELETE FROM Pacientes WHERE id = ?")!!
            deletePaciente.setString(1, idPaciente)
            deletePaciente.executeUpdate()

            val commit = objConexion.prepareStatement("COMMIT")
            commit.executeUpdate()
        }

        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }

    fun actualizarListadoDespuesDeEditar(id: String, nuevoNombre: String, nuevoApellido: String) {
        val identificador = Datos.indexOfFirst { it.UUID == id }
        Datos[identificador].nombre = nuevoNombre
        Datos[identificador].apellidos = nuevoApellido
        notifyItemChanged(identificador)
    }

    fun editarPaciente(id: String, nombre: String, apellidos: String, edad: Int, enfermedad: String, fecha: String, medicina: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            val updatePaciente = objConexion?.prepareStatement(
                "UPDATE Pacientes SET nombre = ?, apellidos = ?, edad = ?, enfermedad = ?, fecha = ?, medicina = ? WHERE id = ?"
            )!!
            updatePaciente.setString(1, nombre)
            updatePaciente.setString(2, apellidos)
            updatePaciente.setInt(3, edad)
            updatePaciente.setString(4, enfermedad)
            updatePaciente.setString(5, fecha)
            updatePaciente.setString(6, medicina)
            updatePaciente.setString(7, id)
            updatePaciente.executeUpdate()

            val commit = objConexion.prepareStatement("COMMIT")
            commit.executeUpdate()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_card_pacientes, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paciente = Datos[position]
        holder.txtNombre.text = paciente.nombre
        holder.txtApellidos.text = paciente.apellidos
        holder.txtEdad.text = paciente.edad.toString()
        holder.txtEnfermedad.text = paciente.enfermedad
        holder.txtFecha.text = paciente.fecha_nacimiento.toString()
        holder.txtMedicina.text = paciente.medicina


        holder.btnEliminar.setOnClickListener {
            val context = holder.txtNombre.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Estás seguro que deseas eliminar este paciente?")

            builder.setPositiveButton("Sí") { _, _ ->
                eliminarRegistro(paciente.UUID, position)
            }

            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val pantallaDetalle = Intent(context, ver_ppacientes::class.java)

            pantallaDetalle.putExtra("id", paciente.UUID)
            pantallaDetalle.putExtra("nombre", paciente.nombre)
            pantallaDetalle.putExtra("apellidos", paciente.apellidos)
            pantallaDetalle.putExtra("edad", paciente.edad)
            pantallaDetalle.putExtra("enfermedad", paciente.enfermedad)
            pantallaDetalle.putExtra("fecha", paciente.fecha_nacimiento)
            pantallaDetalle.putExtra("medicina", paciente.medicina)
            context.startActivity(pantallaDetalle)
        }
    }
}
