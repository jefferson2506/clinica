package controlador;

import dao.EspecialidadDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Especialidad;

@WebServlet(name = "EspecialidadServlet", urlPatterns = {"/EspecialidadServlet"})
public class EspecialidadServlet extends HttpServlet {

    private final EspecialidadDAO especialidadDAO = new EspecialidadDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            String accion = request.getParameter("accion");
            String servlet = "nuevo".equals(accion) ? "/MostrarFormularioEspecialidadServlet"
                    : "editar".equals(accion) ? "/EditarEspecialidadServlet" : "/ListarEspecialidadesServlet";
            request.getRequestDispatcher(servlet).forward(request, response);
            return;
        }

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        String idTexto = request.getParameter("id");
        try {
            if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(idTexto);
                String mensaje = especialidadDAO.eliminar(id) ? "eliminado" : "noEliminado";
                response.sendRedirect("ListarEspecialidadesServlet?mensaje=" + mensaje);
                return;
            }
            Especialidad especialidad = new Especialidad();
            especialidad.setNombEsp(request.getParameter("nombre"));
            boolean actualizado = idTexto != null && !idTexto.isEmpty();
            boolean guardado;
            if (actualizado) {
                especialidad.setCodEsp(Integer.parseInt(idTexto));
                guardado = especialidadDAO.actualizar(especialidad);
            } else {
                guardado = especialidadDAO.insertar(especialidad);
            }
            String mensaje = guardado ? (actualizado ? "actualizado" : "guardado") : "noEncontrado";
            response.sendRedirect("ListarEspecialidadesServlet?mensaje=" + mensaje);
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
