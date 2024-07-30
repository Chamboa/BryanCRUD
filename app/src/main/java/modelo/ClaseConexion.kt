package modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection? {
        try {
            val ip = "jdbc:oracle:thin:@10.10.2.61:1521:xe"

            val usuario = "BryanCRUD"
            val contrasena = "."

            val connection = DriverManager.getConnection(ip, usuario, contrasena)
            return connection
        }
        catch (e: Exception) {
            println("Ha ocurrido un error: $e")
            return null

        }

    }
}