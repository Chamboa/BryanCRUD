package modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection? {
        try {
            val ip = "jdbc:oracle:thin:@192.168.86.248:1521:xe"

            val usuario = "PTCbase"
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