<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="modelo.Medico"%>
<%!
    private String escapeHtml(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    List<Medico> medicos = (List<Medico>) request.getAttribute("medicos");
    Map<Integer, String> nombresEspecialidad = (Map<Integer, String>) request.getAttribute("nombresEspecialidad");
    String filtro = (String) request.getAttribute("filtro");
    String mensaje = request.getParameter("mensaje");
    String error = request.getParameter("error");
    String textoMensaje = null;
    String claseMensaje = "success";
    if ("guardado".equals(mensaje)) textoMensaje = "El médico se registró correctamente.";
    if ("actualizado".equals(mensaje)) textoMensaje = "El médico se actualizó correctamente.";
    if ("eliminado".equals(mensaje)) textoMensaje = "El médico se eliminó correctamente.";
    if ("noEliminado".equals(mensaje)) { textoMensaje = "No se pudo eliminar el médico."; claseMensaje = "danger"; }
    if ("noEncontrado".equals(error)) textoMensaje = "No se encontró el médico solicitado.";
    if ("idInvalido".equals(error)) textoMensaje = "El código de médico no es válido.";
    if (error != null) claseMensaje = "danger";
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Médicos | Clínica</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-dark bg-primary"><div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/ListarMedicosServlet">Clínica</a>
            <div class="d-flex gap-2">
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarPacientesServlet">Pacientes</a>
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarCitasServlet">Citas</a>
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarEspecialidadesServlet">Especialidades</a>
                <a class="btn btn-light btn-sm" href="${pageContext.request.contextPath}/MostrarFormularioMedicoServlet">Nuevo médico</a>
            </div>
        </div></nav>
        <main class="container py-5">
            <div class="d-flex flex-wrap justify-content-between align-items-center gap-3 mb-4">
                <h1 class="h3 mb-0">Médicos registrados</h1>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/MostrarFormularioMedicoServlet">Registrar médico</a>
            </div>
            <% if (textoMensaje != null) { %>
                <div class="alert alert-<%= claseMensaje %>" role="status"><%= escapeHtml(textoMensaje) %></div>
            <% } %>
            <form action="${pageContext.request.contextPath}/ListarMedicosServlet" method="get" class="row g-2 mb-3">
                <div class="col-12 col-md-6 col-lg-5">
                    <label for="buscar" class="visually-hidden">Buscar médico</label>
                    <input type="search" class="form-control" id="buscar" name="buscar" value="<%= escapeHtml(filtro) %>" placeholder="Apellido, dirección, teléfono o correo">
                </div>
                <div class="col-auto"><button type="submit" class="btn btn-outline-primary">Buscar</button></div>
                <% if (filtro != null && !filtro.isEmpty()) { %>
                    <div class="col-auto"><a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/ListarMedicosServlet">Limpiar</a></div>
                <% } %>
            </form>
            <div class="table-responsive bg-white border rounded">
                <table class="table table-striped table-hover align-middle mb-0">
                    <thead class="table-light"><tr>
                        <th scope="col">Código</th><th scope="col">Apellido</th><th scope="col">Especialidad</th>
                        <th scope="col">Dirección</th><th scope="col">Teléfono</th><th scope="col">Correo</th><th scope="col">Acciones</th>
                    </tr></thead>
                    <tbody>
                        <% if (medicos == null || medicos.isEmpty()) { %>
                            <tr><td colspan="7" class="text-center text-secondary py-4">No se encontraron médicos.</td></tr>
                        <% } else { %>
                            <% for (Medico medico : medicos) { %>
                                <tr>
                                    <td><%= medico.getCodMed() %></td>
                                    <td><%= escapeHtml(medico.getApeMed()) %></td>
                                    <td><%= escapeHtml(nombresEspecialidad.get(medico.getCodEsp())) %></td>
                                    <td><%= escapeHtml(medico.getDirMed()) %></td>
                                    <td><%= escapeHtml(medico.getTelefMed()) %></td>
                                    <td><%= escapeHtml(medico.getCorreoMed()) %></td>
                                    <td class="text-nowrap">
                                        <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/EditarMedicoServlet?id=<%= medico.getCodMed() %>">Editar</a>
                                        <form action="${pageContext.request.contextPath}/MedicoServlet" method="post" class="d-inline" onsubmit="return confirm('¿Eliminar este médico?');">
                                            <input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="<%= medico.getCodMed() %>">
                                            <button type="submit" class="btn btn-sm btn-outline-danger">Eliminar</button>
                                        </form>
                                    </td>
                                </tr>
                            <% } %>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </main>
    </body>
</html>