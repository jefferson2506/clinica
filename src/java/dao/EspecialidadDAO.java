package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Especialidad;
import util.Conexion;

public class EspecialidadDAO {

    public boolean insertar(Especialidad e) {
        String sql = "INSERT INTO especialidad(nomb_esp) VALUES(?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, e.getNombEsp());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Especialidad e) {
        String sql = "UPDATE especialidad SET nomb_esp=? WHERE cod_esp=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, e.getNombEsp());
            ps.setInt(2, e.getCodEsp());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int codEsp) {
        String sql = "DELETE FROM especialidad WHERE cod_esp=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, codEsp);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Especialidad buscarPorId(int codEsp) {
        String sql = "SELECT * FROM especialidad WHERE cod_esp=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, codEsp);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public List<Especialidad> listar() {
        List<Especialidad> lista = new ArrayList<>();

        String sql = "SELECT * FROM especialidad ORDER BY cod_esp DESC";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    public List<Especialidad> buscar(String texto) {
        List<Especialidad> lista = new ArrayList<>();

        String sql = "SELECT * FROM especialidad " +
                     "WHERE nomb_esp LIKE ? " +
                     "ORDER BY nomb_esp";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, "%" + texto + "%");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    private Especialidad mapear(ResultSet rs) throws SQLException {

        Especialidad e = new Especialidad();

        e.setCodEsp(rs.getInt("cod_esp"));
        e.setNombEsp(rs.getString("nomb_esp"));

        return e;
    }
}