package RecyclerView

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jonathan.javier.bryancrud.R

class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
    val txtNombre: TextView = view.findViewById(R.id.txtNombre)
    val txtApellidos: TextView = view.findViewById(R.id.txtApellidos)
    val txtEdad: TextView = view.findViewById(R.id.txtEdad)
    val txtEnfermedad: TextView = view.findViewById(R.id.txtEnfermedad)
    val txtFecha: TextView = view.findViewById(R.id.txtFecha)
    val txtMedicina: TextView = view.findViewById(R.id.txtMedicina)
    val btnActualizar: Button = view.findViewById(R.id.btn_actualizar)
    val btnEliminar: Button = view.findViewById(R.id.btn_eliminar)


}