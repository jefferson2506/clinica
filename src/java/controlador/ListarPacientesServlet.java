package controlador;

import dao.PacienteDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Paciente;

@WebServlet(name = "ListarPacientesServlet", urlPatterns = {"/ListarPacientesServlet"})
public class ListarPacientesServlet extends HttpServlet {

    private final PacienteDAO pacienteDAO = new PacienteDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filtro = request.getParameter("buscar");
        List<Paciente> pacientes = filtro == null || filtro.trim().isEmpty()
                ? pacienteDAO.listar() : pacienteDAO.buscar(filtro.trim());
        request.setAttribute("pacientes", pacientes);
        request.setAttribute("filtro", filtro);
        request.getRequestDispatcher("ListaPacientes.jsp").forward(request, response);
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
