package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Cita;
import util.Conexion;

public class CitaDAO {

    public boolean insertar(Cita c) throws SQLException {
        String sql = "INSERT INTO cita(cod_pac,cod_esp,fecha_cita,monto_cita) VALUES(?,?,?,?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, c.getCodPac());
            ps.setInt(2, c.getCodEsp());
            ps.setDate(3, Date.valueOf(c.getFechaCita()));
            ps.setBigDecimal(4, c.getMontoCita());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Cita c) {
        String sql = "UPDATE cita SET cod_pac=?, cod_esp=?, fecha_cita=?, monto_cita=? WHERE cod_cita=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, c.getCodPac());
            ps.setInt(2, c.getCodEsp());
            ps.setDate(3, Date.valueOf(c.getFechaCita()));
            ps.setBigDecimal(4, c.getMontoCita());
            ps.setInt(5, c.getCodCita());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int codCita) {
        String sql = "DELETE FROM cita WHERE cod_cita=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, codCita);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Cita buscarPorId(int codCita) {
        String sql = "SELECT * FROM cita WHERE cod_cita=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, codCita);

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

    public List<Cita> listar() {
        List<Cita> lista = new ArrayList<>();

        String sql = "SELECT * FROM cita ORDER BY cod_cita DESC";

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

    public List<Cita> buscar(String texto) {
        List<Cita> lista = new ArrayList<>();

        String sql = "SELECT c.* FROM cita c " +
                 "JOIN paciente p ON p.cod_pac = c.cod_pac " +
                     "JOIN especialidad e ON e.cod_esp = c.cod_esp " +
                 "WHERE CAST(c.cod_cita AS CHAR) LIKE ? " +
                 "OR CAST(c.cod_pac AS CHAR) LIKE ? " +
                 "OR CAST(c.cod_esp AS CHAR) LIKE ? " +
                 "OR p.ape_pac LIKE ? " +
                     "OR e.nomb_esp LIKE ? " +
                 "ORDER BY c.cod_cita DESC";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            String filtro = "%" + texto + "%";

            for (int i = 1; i <= 5; i++) {
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

    private Cita mapear(ResultSet rs) throws SQLException {

        Cita c = new Cita();

        c.setCodCita(rs.getInt("cod_cita"));
        c.setCodPac(rs.getInt("cod_pac"));
        c.setCodEsp(rs.getInt("cod_esp"));
        c.setFechaCita(rs.getDate("fecha_cita").toLocalDate());
        c.setMontoCita(rs.getBigDecimal("monto_cita"));

        return c;
    }
}