package controlador;

import dao.EspecialidadDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Medico;

@WebServlet(name = "MostrarFormularioMedicoServlet", urlPatterns = {"/MostrarFormularioMedicoServlet"})
public class MostrarFormularioMedicoServlet extends HttpServlet {

    private final EspecialidadDAO especialidadDAO = new EspecialidadDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("medico", new Medico());
        request.setAttribute("modoEdicion", false);
        request.setAttribute("especialidades", especialidadDAO.listar());
        request.getRequestDispatcher("FormularioMedico.jsp").forward(request, response);
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
