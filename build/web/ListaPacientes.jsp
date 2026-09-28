<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Paciente"%>
<%!
    private String escapeHtml(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    List<Paciente> pacientes = (List<Paciente>) request.getAttribute("pacientes");
    String filtro = (String) request.getAttribute("filtro");
    String mensaje = request.getParameter("mensaje");
    String error = request.getParameter("error");
    String textoMensaje = null;
    String claseMensaje = "success";
    if ("guardado".equals(mensaje)) textoMensaje = "El paciente se registró correctamente.";
    if ("actualizado".equals(mensaje)) textoMensaje = "El paciente se actualizó correctamente.";
    if ("eliminado".equals(mensaje)) textoMensaje = "El paciente se eliminó correctamente.";
    if ("noEliminado".equals(mensaje)) {
        textoMensaje = "No se pudo eliminar el paciente.";
        claseMensaje = "danger";
    }
    if ("noEncontrado".equals(error)) textoMensaje = "No se encontró el paciente solicitado.";
    if ("idInvalido".equals(error)) textoMensaje = "El código de paciente no es válido.";
    if (error != null) claseMensaje = "danger";
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Pacientes | Clínica</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-dark bg-primary">
            <div class="container">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/ListarPacientesServlet">Clínica</a>
                <div class="d-flex gap-2">
                    <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarMedicosServlet">Médicos</a>
                    <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarEspecialidadesServlet">Especialidades</a>
                    <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarCitasServlet">Citas</a>
                    <a class="btn btn-light btn-sm" href="${pageContext.request.contextPath}/MostrarFormularioPacienteServlet">Nuevo paciente</a>
                </div>
            </div>
        </nav>
        <main class="container py-5">
            <div class="d-flex flex-wrap justify-content-between align-items-center gap-3 mb-4">
                <h1 class="h3 mb-0">Pacientes registrados</h1>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/MostrarFormularioPacienteServlet">Registrar paciente</a>
            </div>
            <% if (textoMensaje != null) { %>
                <div class="alert alert-<%= claseMensaje %>" role="status"><%= escapeHtml(textoMensaje) %></div>
            <% } %>
            <form action="${pageContext.request.contextPath}/ListarPacientesServlet" method="get" class="row g-2 mb-3">
                <div class="col-12 col-md-6 col-lg-5">
                    <label for="buscar" class="visually-hidden">Buscar paciente</label>
                    <input type="search" class="form-control" id="buscar" name="buscar" value="<%= escapeHtml(filtro) %>" placeholder="Apellido, dirección, teléfono o correo">
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-outline-primary">Buscar</button>
                </div>
                <% if (filtro != null && !filtro.isEmpty()) { %>
                    <div class="col-auto">
                        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/ListarPacientesServlet">Limpiar</a>
                    </div>
                <% } %>
            </form>
            <div class="table-responsive bg-white border rounded">
                <table class="table table-striped table-hover align-middle mb-0">
                    <thead class="table-light">
                        <tr>
                            <th scope="col">Código</th>
                            <th scope="col">Apellido</th>
                            <th scope="col">Dirección</th>
                            <th scope="col">Teléfono</th>
                            <th scope="col">Correo</th>
                            <th scope="col">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (pacientes == null || pacientes.isEmpty()) { %>
                            <tr><td colspan="6" class="text-center text-secondary py-4">No se encontraron pacientes.</td></tr>
                        <% } else { %>
                            <% for (Paciente paciente : pacientes) { %>
                                <tr>
                                    <td><%= paciente.getCodPac() %></td>
                                    <td><%= escapeHtml(paciente.getApePac()) %></td>
                                    <td><%= escapeHtml(paciente.getDirPac()) %></td>
                                    <td><%= escapeHtml(paciente.getTelefPac()) %></td>
                                    <td><%= escapeHtml(paciente.getCorreoPac()) %></td>
                                    <td class="text-nowrap">
                                        <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/MostrarFormularioCitaServlet?codPac=<%= paciente.getCodPac() %>">Agendar cita</a>
                                        <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/EditarPacienteServlet?id=<%= paciente.getCodPac() %>">Editar</a>
                                        <form action="${pageContext.request.contextPath}/PacienteServlet" method="post" class="d-inline" onsubmit="return confirm('¿Eliminar este paciente?');">
                                            <input type="hidden" name="accion" value="eliminar">
                                            <input type="hidden" name="id" value="<%= paciente.getCodPac() %>">
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