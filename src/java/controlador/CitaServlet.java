package controlador;

import dao.CitaDAO;
import dao.EspecialidadDAO;
import dao.PacienteDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Cita;

@WebServlet(name = "CitaServlet", urlPatterns = {"/CitaServlet"})
public class CitaServlet extends HttpServlet {

    private final CitaDAO citaDAO = new CitaDAO();
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final EspecialidadDAO especialidadDAO = new EspecialidadDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            String servlet = "nueva".equals(request.getParameter("accion"))
                    ? "/MostrarFormularioCitaServlet" : "/ListarCitasServlet";
            request.getRequestDispatcher(servlet).forward(request, response);
            return;
        }

        request.setCharacterEncoding("UTF-8");
        Cita cita = new Cita();
        try {
            cita.setCodPac(Integer.parseInt(requerido(request, "codPac")));
            cita.setCodEsp(Integer.parseInt(requerido(request, "codEsp")));
            cita.setFechaCita(LocalDate.parse(requerido(request, "fechaCita")));
            cita.setMontoCita(new BigDecimal(requerido(request, "montoCita")));
            if (citaDAO.insertar(cita)) {
                response.sendRedirect("ListarCitasServlet?mensaje=guardada");
            } else {
                request.setAttribute("error", "No se pudo guardar la cita.");
                mostrarFormulario(request, response, cita);
            }
        } catch (DateTimeParseException | IllegalArgumentException ex) {
            request.setAttribute("error", "Verifica los datos de la cita e inténtalo de nuevo.");
            mostrarFormulario(request, response, cita);
        } catch (SQLException ex) {
            log("No se pudo guardar la cita", ex);
            request.setAttribute("error", "Ocurrió un error al guardar la cita.");
            mostrarFormulario(request, response, cita);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Cita cita)
            throws ServletException, IOException {
        request.setAttribute("cita", cita);
        request.setAttribute("montoIngresado", request.getParameter("montoCita"));
        request.setAttribute("pacientes", pacienteDAO.listar());
        request.setAttribute("especialidades", especialidadDAO.listar());
        request.getRequestDispatcher("FormularioCita.jsp").forward(request, response);
    }

    private String requerido(HttpServletRequest request, String nombre) {
        String valor = request.getParameter(nombre);
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta el parámetro " + nombre);
        }
        return valor.trim();
    }
}
