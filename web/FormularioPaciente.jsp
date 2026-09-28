<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    private String escapeHtml(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    modelo.Paciente paciente = (modelo.Paciente) request.getAttribute("paciente");
    String apellido = paciente == null ? "" : escapeHtml(paciente.getApePac());
    String direccion = paciente == null ? "" : escapeHtml(paciente.getDirPac());
    String telefono = paciente == null ? "" : escapeHtml(paciente.getTelefPac());
    String correo = paciente == null ? "" : escapeHtml(paciente.getCorreoPac());
    boolean modoEdicion = Boolean.TRUE.equals(request.getAttribute("modoEdicion"));
    String accion = modoEdicion ? "actualizar" : "guardar";
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><%= modoEdicion ? "Editar" : "Registrar" %> paciente | Clínica</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <nav class="navbar navbar-dark bg-primary">
            <div class="container">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/ListarPacientesServlet">Clínica</a>
                <div class="d-flex gap-2">
                    <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarMedicosServlet">Médicos</a>
                    <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/ListarEspecialidadesServlet">Especialidades</a>
                    <a class="btn btn-light btn-sm" href="${pageContext.request.contextPath}/ListarPacientesServlet">Pacientes</a>
                </div>
            </div>
        </nav>
        <main class="container py-5">
            <div class="row justify-content-center">
                <div class="col-12 col-md-8 col-lg-6">
                    <h1 class="h3 mb-4"><%= modoEdicion ? "Editar" : "Registrar" %> paciente</h1>
                    <% if (request.getAttribute("error") != null) { %>
                        <div class="alert alert-danger" role="alert"><%= escapeHtml((String) request.getAttribute("error")) %></div>
                    <% } %>
                    <form action="${pageContext.request.contextPath}/PacienteServlet" method="post">
                        <input type="hidden" name="accion" value="<%= accion %>">
                        <% if (modoEdicion && paciente != null) { %>
                            <input type="hidden" name="id" value="<%= paciente.getCodPac() %>">
                        <% } %>
                        <div class="mb-3">
                            <label for="apellido" class="form-label">Apellido</label>
                            <input type="text" class="form-control" id="apellido" name="apellido" value="<%= apellido %>" maxlength="100" required>
                        </div>
                        <div class="mb-3">
                            <label for="direccion" class="form-label">Dirección</label>
                            <input type="text" class="form-control" id="direccion" name="direccion" value="<%= direccion %>" maxlength="200" required>
                        </div>
                        <div class="mb-3">
                            <label for="telefono" class="form-label">Teléfono</label>
                            <input type="tel" class="form-control" id="telefono" name="telefono" value="<%= telefono %>" maxlength="30" required>
                        </div>
                        <div class="mb-4">
                            <label for="correo" class="form-label">Correo electrónico</label>
                            <input type="email" class="form-control" id="correo" name="correo" value="<%= correo %>" maxlength="120" required>
                        </div>
                        <button type="submit" class="btn btn-primary"><%= modoEdicion ? "Actualizar paciente" : "Guardar paciente" %></button>
                        <a class="btn btn-outline-secondary ms-2" href="${pageContext.request.contextPath}/ListarPacientesServlet">Cancelar</a>
                    </form>
                </div>
            </div>
        </main>
    </body>
</html>