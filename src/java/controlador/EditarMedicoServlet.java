package controlador;

import dao.MedicoDAO;
import dao.EspecialidadDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Medico;

@WebServlet(name = "EditarMedicoServlet", urlPatterns = {"/EditarMedicoServlet"})
public class EditarMedicoServlet extends HttpServlet {

    private final MedicoDAO medicoDAO = new MedicoDAO();
    private final EspecialidadDAO especialidadDAO = new EspecialidadDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Medico medico = medicoDAO.buscarPorId(id);
            if (medico == null) {
                response.sendRedirect("ListarMedicosServlet?error=noEncontrado");
                return;
            }
            request.setAttribute("medico", medico);
            request.setAttribute("modoEdicion", true);
            request.setAttribute("especialidades", especialidadDAO.listar());
            request.getRequestDispatcher("FormularioMedico.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            response.sendRedirect("ListarMedicosServlet?error=idInvalido");
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
