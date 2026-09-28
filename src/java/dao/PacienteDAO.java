package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Paciente;
import util.Conexion;

public class PacienteDAO {

    public boolean insertar(Paciente p) {
        String sql = "INSERT INTO paciente(ape_pac,dir_pac,telef_pac,correo_pac) VALUES(?,?,?,?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, p.getApePac());
            ps.setString(2, p.getDirPac());
            ps.setString(3, p.getTelefPac());
            ps.setString(4, p.getCorreoPac());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Paciente p) {
        String sql = "UPDATE paciente SET ape_pac=?, dir_pac=?, telef_pac=?, correo_pac=? WHERE cod_pac=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, p.getApePac());
            ps.setString(2, p.getDirPac());
            ps.setString(3, p.getTelefPac());
            ps.setString(4, p.getCorreoPac());
            ps.setInt(5, p.getCodPac());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int codPac) {
        String sql = "DELETE FROM paciente WHERE cod_pac=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, codPac);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Paciente buscarPorId(int codPac) {
        String sql = "SELECT * FROM paciente WHERE cod_pac=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, codPac);

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

    public List<Paciente> listar() {
        List<Paciente> lista = new ArrayList<>();

        String sql = "SELECT * FROM paciente ORDER BY cod_pac DESC";

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

    public List<Paciente> buscar(String texto) {
        List<Paciente> lista = new ArrayList<>();

        String sql = "SELECT * FROM paciente " +
                     "WHERE ape_pac LIKE ? " +
                     "OR dir_pac LIKE ? " +
                     "OR telef_pac LIKE ? " +
                     "OR correo_pac LIKE ? " +
                     "ORDER BY ape_pac";

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

    private Paciente mapear(ResultSet rs) throws SQLException {

        Paciente p = new Paciente();

        p.setCodPac(rs.getInt("cod_pac"));
        p.setApePac(rs.getString("ape_pac"));
        p.setDirPac(rs.getString("dir_pac"));
        p.setTelefPac(rs.getString("telef_pac"));
        p.setCorreoPac(rs.getString("correo_pac"));

        return p;
    }
}