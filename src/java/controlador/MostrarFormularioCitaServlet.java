package controlador;

import dao.EspecialidadDAO;
import dao.PacienteDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Cita;

@WebServlet(name = "MostrarFormularioCitaServlet", urlPatterns = {"/MostrarFormularioCitaServlet"})
public class MostrarFormularioCitaServlet extends HttpServlet {

    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final EspecialidadDAO especialidadDAO = new EspecialidadDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cita cita = new Cita();
        String codPac = request.getParameter("codPac");
        if (codPac != null && !codPac.isEmpty()) {
            try {
                cita.setCodPac(Integer.parseInt(codPac));
            } catch (NumberFormatException ex) {
                request.setAttribute("error", "El código del paciente no es válido.");
            }
        }
        request.setAttribute("cita", cita);
        request.setAttribute("pacientes", pacienteDAO.listar());
        request.setAttribute("especialidades", especialidadDAO.listar());
        request.getRequestDispatcher("FormularioCita.jsp").forward(request, response);
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
}
