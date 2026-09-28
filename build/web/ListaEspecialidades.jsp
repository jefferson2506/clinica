<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Especialidad"%>
<%!
    private String escapeHtml(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    List<Especialidad> especialidades = (List<Especialidad>) request.getAttribute("especialidades");
    String filtro = (String) request.getAttribute("filtro");
    String mensaje = request.getParameter("mensaje");
    String error = request.getParameter("error");
    String textoMensaje = null;
    String claseMensaje = "success";
    if ("guardado".equals(mensaje)) textoMensaje = "La especialidad se registró correctamente.";
    if ("actualizado".equals(mensaje)) textoMensaje = "La especialidad se actualizó correctamente.";
    if ("eliminado".equals(mensaje)) textoMensaje = "La especialidad se eliminó correctamente.";
    if ("noEliminado".equals(mensaje)) {
        textoMensaje = "No se pudo eliminar. Puede que esta especialidad esté asignada a uno o más médicos.";
        claseMensaje = "danger";
    }
    if ("noEncontrado".equals(error)) textoMensaje = "No se encontró la especialidad solicitada.";
    if ("idInvalido".equals(error)) textoMensaje = "El código de especialidad no es válido.";
    if (error != null) claseMensaje = "danger";
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Especialidades | Clínica</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-dark bg-primary"><div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/ListarEspecialidadesServlet">Clínica</a>
            <div class="d-flex gap-2">
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarPacientesServlet">Pacientes</a>
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarCitasServlet">Citas</a>
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarMedicosServlet">Médicos</a>
                <a class="btn btn-light btn-sm" href="${pageContext.request.contextPath}/MostrarFormularioEspecialidadServlet">Nueva especialidad</a>
            </div>
        </div></nav>
        <main class="container py-5">
            <div class="d-flex flex-wrap justify-content-between align-items-center gap-3 mb-4">
                <h1 class="h3 mb-0">Especialidades registradas</h1>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/MostrarFormularioEspecialidadServlet">Registrar especialidad</a>
            </div>
            <% if (textoMensaje != null) { %>
                <div class="alert alert-<%= claseMensaje %>" role="status"><%= escapeHtml(textoMensaje) %></div>
            <% } %>
            <form action="${pageContext.request.contextPath}/ListarEspecialidadesServlet" method="get" class="row g-2 mb-3">
                <div class="col-12 col-md-6 col-lg-5">
                    <label for="buscar" class="visually-hidden">Buscar especialidad</label>
                    <input type="search" class="form-control" id="buscar" name="buscar" value="<%= escapeHtml(filtro) %>" placeholder="Nombre de especialidad">
                </div>
                <div class="col-auto"><button type="submit" class="btn btn-outline-primary">Buscar</button></div>
                <% if (filtro != null && !filtro.isEmpty()) { %>
                    <div class="col-auto"><a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/ListarEspecialidadesServlet">Limpiar</a></div>
                <% } %>
            </form>
            <div class="table-responsive bg-white border rounded">
                <table class="table table-striped table-hover align-middle mb-0">
                    <thead class="table-light"><tr><th scope="col">Código</th><th scope="col">Especialidad</th><th scope="col">Acciones</th></tr></thead>
                    <tbody>
                        <% if (especialidades == null || especialidades.isEmpty()) { %>
                            <tr><td colspan="3" class="text-center text-secondary py-4">No se encontraron especialidades.</td></tr>
                        <% } else { %>
                            <% for (Especialidad especialidad : especialidades) { %>
                                <tr>
                                    <td><%= especialidad.getCodEsp() %></td>
                                    <td><%= escapeHtml(especialidad.getNombEsp()) %></td>
                                    <td class="text-nowrap">
                                        <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/EditarEspecialidadServlet?id=<%= especialidad.getCodEsp() %>">Editar</a>
                                        <form action="${pageContext.request.contextPath}/EspecialidadServlet" method="post" class="d-inline" onsubmit="return confirm('¿Eliminar esta especialidad?');">
                                            <input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="<%= especialidad.getCodEsp() %>">
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