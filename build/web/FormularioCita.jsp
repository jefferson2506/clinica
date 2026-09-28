<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Paciente"%>
<%@page import="modelo.Especialidad"%>
<%!
    private String escapeHtml(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    modelo.Cita cita = (modelo.Cita) request.getAttribute("cita");
    List<Paciente> pacientes = (List<Paciente>) request.getAttribute("pacientes");
    List<Especialidad> especialidades = (List<Especialidad>) request.getAttribute("especialidades");
    String monto = (String) request.getAttribute("montoIngresado");
    if (monto == null && cita != null && cita.getMontoCita() != null) monto = cita.getMontoCita().toPlainString();
    if (monto == null) monto = "";
    String fecha = cita == null || cita.getFechaCita() == null ? "" : cita.getFechaCita().toString();
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Agendar cita | Clínica</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-dark bg-primary"><div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/ListarCitasServlet">Clínica</a>
            <div class="d-flex gap-2">
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarPacientesServlet">Pacientes</a>
                <a class="btn btn-light btn-sm" href="${pageContext.request.contextPath}/ListarCitasServlet">Citas</a>
            </div>
        </div></nav>
        <main class="container py-5">
            <div class="row justify-content-center"><div class="col-12 col-md-8 col-lg-6">
                <h1 class="h3 mb-4">Agendar cita</h1>
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger" role="alert"><%= escapeHtml((String) request.getAttribute("error")) %></div>
                <% } %>
                <% if (pacientes == null || pacientes.isEmpty() || especialidades == null || especialidades.isEmpty()) { %>
                    <div class="alert alert-warning">Para agendar, registra al menos un paciente y una especialidad.</div>
                    <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/ListarPacientesServlet">Ver pacientes</a>
                    <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/ListarEspecialidadesServlet">Ver especialidades</a>
                <% } else { %>
                    <form action="${pageContext.request.contextPath}/CitaServlet" method="post">
                        <div class="mb-3">
                            <label for="codPac" class="form-label">Paciente</label>
                            <select class="form-select" id="codPac" name="codPac" required>
                                <option value="">Selecciona un paciente</option>
                                <% for (Paciente paciente : pacientes) { %>
                                    <option value="<%= paciente.getCodPac() %>" <%= cita != null && cita.getCodPac() == paciente.getCodPac() ? "selected" : "" %>><%= escapeHtml(paciente.getApePac()) %> (código <%= paciente.getCodPac() %>)</option>
                                <% } %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="codEsp" class="form-label">Especialidad</label>
                            <select class="form-select" id="codEsp" name="codEsp" required>
                                <option value="">Selecciona una especialidad</option>
                                <% for (Especialidad especialidad : especialidades) { %>
                                    <option value="<%= especialidad.getCodEsp() %>" <%= cita != null && cita.getCodEsp() == especialidad.getCodEsp() ? "selected" : "" %>><%= escapeHtml(especialidad.getNombEsp()) %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="fechaCita" class="form-label">Fecha</label>
                            <input type="date" class="form-control" id="fechaCita" name="fechaCita" value="<%= fecha %>" min="<%= java.time.LocalDate.now() %>" required>
                        </div>
                        <div class="mb-4">
                            <label for="montoCita" class="form-label">Monto</label>
                            <input type="number" class="form-control" id="montoCita" name="montoCita" value="<%= escapeHtml(monto) %>" min="0" step="0.01" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Guardar cita</button>
                        <a class="btn btn-outline-secondary ms-2" href="${pageContext.request.contextPath}/ListarCitasServlet">Cancelar</a>
                    </form>
                <% } %>
            </div></div>
        </main>
    </body>
</html>