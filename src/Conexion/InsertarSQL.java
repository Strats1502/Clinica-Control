package Conexion;

import ComponentesBeauty.BeautyErrorMessage;

import java.sql.*;

/**
 * Created by Juan José Estrada Valtierra on 27/04/17.
 */
public class InsertarSQL {
    public static BeautyErrorMessage errorCorreo = new BeautyErrorMessage("Ya existe un registro con ese correo");

    public static void insertarUsuario(int usuarioAlta, String rol, String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String contraseña, boolean activo, boolean administrador, String pathFoto) {
        String sql = "INSERT INTO Usuario VALUES(null, ?, current_date(), ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setInt(1, usuarioAlta);
            ps.setString(2, rol);
            ps.setString(3, nombre);
            ps.setString(4, apellidoPaterno);
            ps.setString(5, apellidoMaterno);
            ps.setString(6, correo);
            ps.setString(7, contraseña);
            ps.setBoolean(8, activo);
            ps.setBoolean(9, administrador);
            ps.setString(10, pathFoto);
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException sqlConstraint) {
            errorCorreo.setVisible(true);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public static void insertarProducto(int usuarioAlta, String nombre, String proveedor, double costo, double precio, String presentacion, boolean activo, String pathFoto) {
        String sql = "INSERT INTO Producto VALUES(null, ?, current_date(), ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setInt(1, usuarioAlta);
            ps.setString(2, nombre);
            ps.setString(3, proveedor);
            ps.setDouble(4, costo);
            ps.setDouble(5, precio);
            ps.setString(6, presentacion);
            ps.setBoolean(7, activo);
            ps.setString(8, pathFoto);
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException sqlConstraint) {
            System.out.println(sqlConstraint.getMessage());
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public static void insertarPaciente(int usuarioAlta, String nombre, String apellidoPaterno, String apellidoMaterno, String fechaNacimiento, String religion, String correo, String telefono, String telefonoEmergencias, boolean activo, String pathFoto) {
        String sql = "INSERT INTO Paciente VALUES(null, ?, current_date(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setInt(1, usuarioAlta);
            ps.setString(2, nombre);
            ps.setString(3, apellidoPaterno);
            ps.setString(4, apellidoMaterno);
            ps.setString(5,fechaNacimiento);
            ps.setString(6, religion);
            ps.setString(7, correo);
            ps.setString(8, telefono);
            ps.setString(9, telefonoEmergencias);
            ps.setBoolean(10, activo);
            ps.setString(11, pathFoto);
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException sqlIntegrity) {
            System.out.println(sqlIntegrity.getMessage());
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public static void insertarVenta(int usuarioAlta, String fechaAlta, String lugarExpedicion, String formaPago, double subtotal, double iva, double total, boolean activo) {
        String sql = "INSERT INTO Venta VALUES(null, ?, current_date(), ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setInt(1, usuarioAlta);
            ps.setString(2, fechaAlta);
            ps.setString(3, lugarExpedicion);
            ps.setString(4, formaPago);
            ps.setDouble(5, subtotal);
            ps.setDouble(6, iva);
            ps.setDouble(7, total);
            ps.setBoolean(8, activo);
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException sqlIntegrity) {
            System.out.println(sqlIntegrity.getMessage());
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public static void insertarAntecedente(int pacienteID, String tipo, String enfermedad, String descripcion) {
        String sql = "INSERT INTO Antecedente VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setInt(1, pacienteID);
            ps.setString(2, tipo);
            ps.setString(3, enfermedad);
            ps.setString(4, descripcion);
            ps.executeUpdate();
        }  catch (SQLIntegrityConstraintViolationException sqlIntegrity) {

        } catch (SQLException sqlException) {

        }
    }

    public static void insertarAntecedenteCronico(int pacienteID, String enfermedad, String añoInicio, String tratamientoActual) {
        String sql = "INSERT INTO AntecedenteCronico VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setInt(1, pacienteID);
            ps.setString(2, enfermedad);
            ps.setString(3, añoInicio);
            ps.setString(4, tratamientoActual);
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException sqlIntegrity) {
            System.out.println(sqlIntegrity.getMessage());
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public static void insertarRecomendacion(int pacienteID, int medicoID, String recomendacion) {
        String sql = "INSERT INTO Recomendacion VALUES(?, ?, ?)";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setInt(1, pacienteID);
            ps.setInt(2, medicoID);
            ps.setString(3, recomendacion);
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException sqlIntegrity) {
            System.out.println(sqlIntegrity.getMessage());
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public static void insertarVentaProducto(int ventaID, int productoID, int cantidad) {
        String sql = "INSERT INTO VentaProducto VALUES(?, ?, ?)";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setInt(1, ventaID);
            ps.setInt(2, productoID);
            ps.setInt(3, cantidad);
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException sqlIntegrity) {
            System.out.println(sqlIntegrity.getMessage());
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public static void insertarInventario(int productoID, int cantidad) {
        String sqlComprobarExistencia = "SELECT * FROM Inventario WHERE id_producto = " + productoID;
        try {
            Statement st = ConexionSQL.getConexion().createStatement();
            st.executeQuery(sqlComprobarExistencia);
            ResultSet rs = st.getResultSet();
            if(rs.next()) {
                String sql = "UPDATE Inventario SET cantidad = cantidad + ? WHERE id_producto = ?";
                    PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
                    ps.setInt(1, cantidad);
                    ps.setInt(2, productoID);
                    ps.executeUpdate();

            } else {
                String sql = "INSERT INTO Inventario VALUES(?, ?)";
                    PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
                    ps.setInt(1, productoID);
                    ps.setInt(2, cantidad);
                    ps.executeUpdate();
            }
        } catch(SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
    }

    public static void insertarModificacion(int objetoID, int usuarioModificadorID, String tipoObjetoModificado) {
        String sql = "INSERT INTO Modificacion VALUES(?, ?, current_date(), ?)";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setInt(1, objetoID);
            ps.setInt(2, usuarioModificadorID);
            ps.setString(3, tipoObjetoModificado);
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException sqlIntegrity) {
            System.out.println(sqlIntegrity.getMessage());
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

}
