package controlador;

import dao.EspecialidadDAO;
import dao.MedicoDAO;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Especialidad;
import modelo.Medico;

@WebServlet(name = "ListarMedicosServlet", urlPatterns = {"/ListarMedicosServlet"})
public class ListarMedicosServlet extends HttpServlet {

    private final MedicoDAO medicoDAO = new MedicoDAO();
    private final EspecialidadDAO especialidadDAO = new EspecialidadDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filtro = request.getParameter("buscar");
        List<Medico> medicos = filtro == null || filtro.trim().isEmpty()
                ? medicoDAO.listar() : medicoDAO.buscar(filtro.trim());
        Map<Integer, String> nombresEspecialidad = new HashMap<>();
        for (Especialidad especialidad : especialidadDAO.listar()) {
            nombresEspecialidad.put(especialidad.getCodEsp(), especialidad.getNombEsp());
        }
        request.setAttribute("medicos", medicos);
        request.setAttribute("nombresEspecialidad", nombresEspecialidad);
        request.setAttribute("filtro", filtro);
        request.getRequestDispatcher("ListaMedicos.jsp").forward(request, response);
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
