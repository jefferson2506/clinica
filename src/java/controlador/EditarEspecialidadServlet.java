package controlador;

import dao.EspecialidadDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Especialidad;

@WebServlet(name = "EditarEspecialidadServlet", urlPatterns = {"/EditarEspecialidadServlet"})
public class EditarEspecialidadServlet extends HttpServlet {

    private final EspecialidadDAO especialidadDAO = new EspecialidadDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Especialidad especialidad = especialidadDAO.buscarPorId(id);
            if (especialidad == null) {
                response.sendRedirect("ListarEspecialidadesServlet?error=noEncontrado");
                return;
            }
            request.setAttribute("especialidad", especialidad);
            request.setAttribute("modoEdicion", true);
            request.getRequestDispatcher("FormularioEspecialidad.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            response.sendRedirect("ListarEspecialidadesServlet?error=idInvalido");
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
