<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="modelo.Cita"%>
<%!
    private String escapeHtml(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    List<Cita> citas = (List<Cita>) request.getAttribute("citas");
    Map<Integer, String> nombresPacientes = (Map<Integer, String>) request.getAttribute("nombresPacientes");
    Map<Integer, String> nombresEspecialidades = (Map<Integer, String>) request.getAttribute("nombresEspecialidades");
    Map<Integer, List<String>> medicosPorEspecialidad = (Map<Integer, List<String>>) request.getAttribute("medicosPorEspecialidad");
    String filtro = (String) request.getAttribute("filtro");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Citas | Clínica</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-dark bg-primary"><div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/ListarCitasServlet">Clínica</a>
            <div class="d-flex gap-2">
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarPacientesServlet">Pacientes</a>
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarMedicosServlet">Médicos</a>
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarEspecialidadesServlet">Especialidades</a>
                <a class="btn btn-light btn-sm" href="${pageContext.request.contextPath}/MostrarFormularioCitaServlet">Agendar cita</a>
            </div>
        </div></nav>
        <main class="container py-5">
            <div class="d-flex flex-wrap justify-content-between align-items-center gap-3 mb-4">
                <h1 class="h3 mb-0">Citas registradas</h1>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/MostrarFormularioCitaServlet">Agendar cita</a>
            </div>
            <% if ("guardada".equals(request.getParameter("mensaje"))) { %>
                <div class="alert alert-success" role="status">La cita se registró correctamente.</div>
            <% } %>
            <form action="${pageContext.request.contextPath}/ListarCitasServlet" method="get" class="row g-2 mb-3">
                <div class="col-12 col-md-6 col-lg-5">
                    <label for="buscar" class="visually-hidden">Buscar cita</label>
                    <input type="search" class="form-control" id="buscar" name="buscar" value="<%= escapeHtml(filtro) %>" placeholder="Código, paciente o especialidad">
                </div>
                <div class="col-auto"><button type="submit" class="btn btn-outline-primary">Buscar</button></div>
                <% if (filtro != null && !filtro.isEmpty()) { %>
                    <div class="col-auto"><a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/ListarCitasServlet">Limpiar</a></div>
                <% } %>
            </form>
            <div class="table-responsive bg-white border rounded">
                <table class="table table-striped table-hover align-middle mb-0">
                    <thead class="table-light"><tr>
                        <th scope="col">Código</th><th scope="col">Paciente</th><th scope="col">Médicos de la especialidad</th><th scope="col">Especialidad</th>
                        <th scope="col">Fecha</th><th scope="col">Monto</th>
                    </tr></thead>
                    <tbody>
                        <% if (citas == null || citas.isEmpty()) { %>
                            <tr><td colspan="5" class="text-center text-secondary py-4">No se encontraron citas.</td></tr>
                        <% } else { %>
                            <% for (Cita cita : citas) { %>
                                <tr>
                                    <td><%= cita.getCodCita() %></td>
                                    <td><%= escapeHtml(nombresPacientes.get(cita.getCodPac())) %></td>
                                    <td><%= medicosPorEspecialidad.get(cita.getCodEsp()) == null ? "Sin médicos registrados" : escapeHtml(String.join(", ", medicosPorEspecialidad.get(cita.getCodEsp()))) %></td>
                                    <td><%= escapeHtml(nombresEspecialidades.get(cita.getCodEsp())) %></td>
                                    <td><%= cita.getFechaCita() %></td>
                                    <td><%= cita.getMontoCita() %></td>
                                </tr>
                            <% } %>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </main>
    </body>
</html>