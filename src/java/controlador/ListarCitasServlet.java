package controlador;

import dao.CitaDAO;
import dao.EspecialidadDAO;
import dao.MedicoDAO;
import dao.PacienteDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Cita;
import modelo.Especialidad;
import modelo.Medico;
import modelo.Paciente;

@WebServlet(name = "ListarCitasServlet", urlPatterns = {"/ListarCitasServlet"})
public class ListarCitasServlet extends HttpServlet {

    private final CitaDAO citaDAO = new CitaDAO();
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final EspecialidadDAO especialidadDAO = new EspecialidadDAO();
    private final MedicoDAO medicoDAO = new MedicoDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filtro = request.getParameter("buscar");
        List<Cita> citas = filtro == null || filtro.trim().isEmpty()
                ? citaDAO.listar() : citaDAO.buscar(filtro.trim());
        Map<Integer, String> nombresPacientes = new HashMap<>();
        for (Paciente paciente : pacienteDAO.listar()) {
            nombresPacientes.put(paciente.getCodPac(), paciente.getApePac());
        }
        Map<Integer, String> nombresEspecialidades = new HashMap<>();
        for (Especialidad especialidad : especialidadDAO.listar()) {
            nombresEspecialidades.put(especialidad.getCodEsp(), especialidad.getNombEsp());
        }
        Map<Integer, List<String>> medicosPorEspecialidad = new HashMap<>();
        for (Medico medico : medicoDAO.listar()) {
            medicosPorEspecialidad.computeIfAbsent(medico.getCodEsp(), key -> new ArrayList<>())
                    .add(medico.getApeMed());
        }
        request.setAttribute("citas", citas);
        request.setAttribute("nombresPacientes", nombresPacientes);
        request.setAttribute("nombresEspecialidades", nombresEspecialidades);
        request.setAttribute("medicosPorEspecialidad", medicosPorEspecialidad);
        request.setAttribute("filtro", filtro);
        request.getRequestDispatcher("ListaCitas.jsp").forward(request, response);
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
