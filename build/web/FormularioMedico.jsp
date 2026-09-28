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
    modelo.Medico medico = (modelo.Medico) request.getAttribute("medico");
    List<Especialidad> especialidades = (List<Especialidad>) request.getAttribute("especialidades");
    boolean modoEdicion = Boolean.TRUE.equals(request.getAttribute("modoEdicion"));
    String accion = modoEdicion ? "actualizar" : "guardar";
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><%= modoEdicion ? "Editar" : "Registrar" %> médico | Clínica</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-dark bg-primary"><div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/ListarMedicosServlet">Clínica</a>
            <div class="d-flex gap-2">
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarPacientesServlet">Pacientes</a>
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarEspecialidadesServlet">Especialidades</a>
                <a class="btn btn-light btn-sm" href="${pageContext.request.contextPath}/ListarMedicosServlet">Médicos</a>
            </div>
        </div></nav>
        <main class="container py-5">
            <div class="row justify-content-center"><div class="col-12 col-md-8 col-lg-6">
                <h1 class="h3 mb-4"><%= modoEdicion ? "Editar" : "Registrar" %> médico</h1>
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger" role="alert"><%= escapeHtml((String) request.getAttribute("error")) %></div>
                <% } %>
                <form action="${pageContext.request.contextPath}/MedicoServlet" method="post">
                    <input type="hidden" name="accion" value="<%= accion %>">
                    <% if (modoEdicion && medico != null) { %>
                        <input type="hidden" name="id" value="<%= medico.getCodMed() %>">
                    <% } %>
                    <div class="mb-3">
                        <label for="especialidad" class="form-label">Especialidad</label>
                        <select class="form-select" id="especialidad" name="especialidad" required>
                            <option value="">Selecciona una especialidad</option>
                            <% if (especialidades != null) { %>
                                <% for (Especialidad especialidad : especialidades) { %>
                                    <option value="<%= especialidad.getCodEsp() %>" <%= medico != null && medico.getCodEsp() == especialidad.getCodEsp() ? "selected" : "" %>><%= escapeHtml(especialidad.getNombEsp()) %></option>
                                <% } %>
                            <% } %>
                        </select>
                        <% if (especialidades == null || especialidades.isEmpty()) { %>
                            <div class="form-text">Primero registra una especialidad.</div>
                        <% } %>
                    </div>
                    <div class="mb-3">
                        <label for="apellido" class="form-label">Apellido</label>
                        <input class="form-control" id="apellido" name="apellido" value="<%= medico == null ? "" : escapeHtml(medico.getApeMed()) %>" maxlength="100" required>
                    </div>
                    <div class="mb-3">
                        <label for="direccion" class="form-label">Dirección</label>
                        <input class="form-control" id="direccion" name="direccion" value="<%= medico == null ? "" : escapeHtml(medico.getDirMed()) %>" maxlength="200" required>
                    </div>
                    <div class="mb-3">
                        <label for="telefono" class="form-label">Teléfono</label>
                        <input type="tel" class="form-control" id="telefono" name="telefono" value="<%= medico == null ? "" : escapeHtml(medico.getTelefMed()) %>" maxlength="30" required>
                    </div>
                    <div class="mb-4">
                        <label for="correo" class="form-label">Correo electrónico</label>
                        <input type="email" class="form-control" id="correo" name="correo" value="<%= medico == null ? "" : escapeHtml(medico.getCorreoMed()) %>" maxlength="120" required>
                    </div>
                    <button type="submit" class="btn btn-primary"><%= modoEdicion ? "Actualizar médico" : "Guardar médico" %></button>
                    <a class="btn btn-outline-secondary ms-2" href="${pageContext.request.contextPath}/ListarMedicosServlet">Cancelar</a>
                </form>
            </div></div>
        </main>
    </body>
</html>