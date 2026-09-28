package controlador;

import dao.EspecialidadDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Especialidad;

@WebServlet(name = "ListarEspecialidadesServlet", urlPatterns = {"/ListarEspecialidadesServlet"})
public class ListarEspecialidadesServlet extends HttpServlet {

    private final EspecialidadDAO especialidadDAO = new EspecialidadDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filtro = request.getParameter("buscar");
        List<Especialidad> especialidades = filtro == null || filtro.trim().isEmpty()
                ? especialidadDAO.listar() : especialidadDAO.buscar(filtro.trim());
        request.setAttribute("especialidades", especialidades);
        request.setAttribute("filtro", filtro);
        request.getRequestDispatcher("ListaEspecialidades.jsp").forward(request, response);
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
