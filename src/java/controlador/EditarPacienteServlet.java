package controlador;

import dao.PacienteDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Paciente;

@WebServlet(name = "EditarPacienteServlet", urlPatterns = {"/EditarPacienteServlet"})
public class EditarPacienteServlet extends HttpServlet {

    private final PacienteDAO pacienteDAO = new PacienteDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Paciente paciente = pacienteDAO.buscarPorId(id);
            if (paciente == null) {
                response.sendRedirect("ListarPacientesServlet?error=noEncontrado");
                return;
            }
            request.setAttribute("paciente", paciente);
            request.setAttribute("modoEdicion", true);
            request.getRequestDispatcher("FormularioPaciente.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            response.sendRedirect("ListarPacientesServlet?error=idInvalido");
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
}
