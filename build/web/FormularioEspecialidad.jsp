<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    private String escapeHtml(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    modelo.Especialidad especialidad = (modelo.Especialidad) request.getAttribute("especialidad");
    boolean modoEdicion = Boolean.TRUE.equals(request.getAttribute("modoEdicion"));
    String accion = modoEdicion ? "actualizar" : "guardar";
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><%= modoEdicion ? "Editar" : "Registrar" %> especialidad | Clínica</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-dark bg-primary"><div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/ListarEspecialidadesServlet">Clínica</a>
            <div class="d-flex gap-2">
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarPacientesServlet">Pacientes</a>
                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarMedicosServlet">Médicos</a>
                <a class="btn btn-light btn-sm" href="${pageContext.request.contextPath}/ListarEspecialidadesServlet">Especialidades</a>
            </div>
        </div></nav>
        <main class="container py-5">
            <div class="row justify-content-center"><div class="col-12 col-md-8 col-lg-6">
                <h1 class="h3 mb-4"><%= modoEdicion ? "Editar" : "Registrar" %> especialidad</h1>
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger" role="alert"><%= escapeHtml((String) request.getAttribute("error")) %></div>
                <% } %>
                <form action="${pageContext.request.contextPath}/EspecialidadServlet" method="post">
                    <input type="hidden" name="accion" value="<%= accion %>">
                    <% if (modoEdicion && especialidad != null) { %>
                        <input type="hidden" name="id" value="<%= especialidad.getCodEsp() %>">
                    <% } %>
                    <div class="mb-4">
                        <label for="nombre" class="form-label">Nombre de la especialidad</label>
                        <input class="form-control" id="nombre" name="nombre" value="<%= especialidad == null ? "" : escapeHtml(especialidad.getNombEsp()) %>" maxlength="100" required>
                    </div>
                    <button type="submit" class="btn btn-primary"><%= modoEdicion ? "Actualizar especialidad" : "Guardar especialidad" %></button>
                    <a class="btn btn-outline-secondary ms-2" href="${pageContext.request.contextPath}/ListarEspecialidadesServlet">Cancelar</a>
                </form>
            </div></div>
        </main>
    </body>
</html>