package controlador;

import dao.MedicoDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Medico;

@WebServlet(name = "MedicoServlet", urlPatterns = {"/MedicoServlet"})
public class MedicoServlet extends HttpServlet {

    private final MedicoDAO medicoDAO = new MedicoDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            String accion = request.getParameter("accion");
            String servlet = "nuevo".equals(accion) ? "/MostrarFormularioMedicoServlet"
                    : "editar".equals(accion) ? "/EditarMedicoServlet" : "/ListarMedicosServlet";
            request.getRequestDispatcher(servlet).forward(request, response);
            return;
        }

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        String idTexto = request.getParameter("id");
        try {
            if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(idTexto);
                String mensaje = medicoDAO.eliminar(id) ? "eliminado" : "noEliminado";
                response.sendRedirect("ListarMedicosServlet?mensaje=" + mensaje);
                return;
            }
            Medico medico = new Medico();
            medico.setCodEsp(Integer.parseInt(requerido(request, "especialidad")));
            medico.setApeMed(request.getParameter("apellido"));
            medico.setDirMed(request.getParameter("direccion"));
            medico.setTelefMed(request.getParameter("telefono"));
            medico.setCorreoMed(request.getParameter("correo"));
            boolean actualizado = idTexto != null && !idTexto.isEmpty();
            boolean guardado;
            if (actualizado) {
                medico.setCodMed(Integer.parseInt(idTexto));
                guardado = medicoDAO.actualizar(medico);
            } else {
                guardado = medicoDAO.insertar(medico);
            }
            String mensaje = guardado ? (actualizado ? "actualizado" : "guardado") : "noEncontrado";
            response.sendRedirect("ListarMedicosServlet?mensaje=" + mensaje);
        } catch (IllegalArgumentException ex) {
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

    private String requerido(HttpServletRequest request, String nombre) {
        String valor = request.getParameter(nombre);
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta el parámetro " + nombre);
        }
        return valor.trim();
    }
}
