package controlador;

import dao.PacienteDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Paciente;

@WebServlet(name = "PacienteServlet", urlPatterns = {"/PacienteServlet"})
public class PacienteServlet extends HttpServlet {

    private final PacienteDAO pacienteDAO = new PacienteDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            String accion = request.getParameter("accion");
            String servlet = "nuevo".equals(accion) ? "/MostrarFormularioPacienteServlet"
                    : "editar".equals(accion) ? "/EditarPacienteServlet" : "/ListarPacientesServlet";
            request.getRequestDispatcher(servlet).forward(request, response);
            return;
        }

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        String idTexto = request.getParameter("id");
        try {
            if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(idTexto);
                String mensaje = pacienteDAO.eliminar(id) ? "eliminado" : "noEliminado";
                response.sendRedirect("ListarPacientesServlet?mensaje=" + mensaje);
                return;
            }
            Paciente paciente = new Paciente();
            paciente.setApePac(request.getParameter("apellido"));
            paciente.setDirPac(request.getParameter("direccion"));
            paciente.setTelefPac(request.getParameter("telefono"));
            paciente.setCorreoPac(request.getParameter("correo"));
            boolean actualizado = idTexto != null && !idTexto.isEmpty();
            boolean guardado;
            if (actualizado) {
                paciente.setCodPac(Integer.parseInt(idTexto));
                guardado = pacienteDAO.actualizar(paciente);
            } else {
                guardado = pacienteDAO.insertar(paciente);
            }
            String mensaje = guardado ? (actualizado ? "actualizado" : "guardado") : "noEncontrado";
            response.sendRedirect("ListarPacientesServlet?mensaje=" + mensaje);
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
