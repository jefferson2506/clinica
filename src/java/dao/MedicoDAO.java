package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Medico;
import util.Conexion;

public class MedicoDAO {

    public boolean insertar(Medico m) {
        String sql = "INSERT INTO medico(cod_esp,ape_med,dir_med,telef_med,correo_med) VALUES(?,?,?,?,?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, m.getCodEsp());
            ps.setString(2, m.getApeMed());
            ps.setString(3, m.getDirMed());
            ps.setString(4, m.getTelefMed());
            ps.setString(5, m.getCorreoMed());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Medico m) {
        String sql = "UPDATE medico SET cod_esp=?, ape_med=?, dir_med=?, telef_med=?, correo_med=? WHERE cod_med=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, m.getCodEsp());
            ps.setString(2, m.getApeMed());
            ps.setString(3, m.getDirMed());
            ps.setString(4, m.getTelefMed());
            ps.setString(5, m.getCorreoMed());
            ps.setInt(6, m.getCodMed());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int codMed) {
        String sql = "DELETE FROM medico WHERE cod_med=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, codMed);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Medico buscarPorId(int codMed) {
        String sql = "SELECT * FROM medico WHERE cod_med=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, codMed);

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

    public List<Medico> listar() {
        List<Medico> lista = new ArrayList<>();

        String sql = "SELECT * FROM medico ORDER BY cod_med DESC";

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

    public List<Medico> buscar(String texto) {
        List<Medico> lista = new ArrayList<>();

        String sql = "SELECT * FROM medico " +
                     "WHERE ape_med LIKE ? " +
                     "OR dir_med LIKE ? " +
                     "OR telef_med LIKE ? " +
                     "OR correo_med LIKE ? " +
                     "ORDER BY ape_med";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            String filtro = "%" + texto + "%";

            for (int i = 1; i <= 4; i++) {
                ps.setString(i, filtro);
            }

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

    private Medico mapear(ResultSet rs) throws SQLException {

        Medico m = new Medico();

        m.setCodMed(rs.getInt("cod_med"));
        m.setCodEsp(rs.getInt("cod_esp"));
        m.setApeMed(rs.getString("ape_med"));
        m.setDirMed(rs.getString("dir_med"));
        m.setTelefMed(rs.getString("telef_med"));
        m.setCorreoMed(rs.getString("correo_med"));

        return m;
    }
}