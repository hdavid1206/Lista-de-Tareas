package Controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TareasServlet", urlPatterns = {"/tareas/*"})
public class TareasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        List<Tarea> listaTareas = (List<Tarea>) session.getAttribute("tareas");
        
        if (listaTareas == null) {
            listaTareas = new ArrayList<>();
            session.setAttribute("tareas", listaTareas);
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || "/".equals(pathInfo)) {
            // Listar tareas
            request.getRequestDispatcher("/listarTareas.jsp").forward(request, response);
        } else if ("/nueva".equals(pathInfo)) {
            // Formulario nueva tarea
            request.getRequestDispatcher("/nuevaTarea.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/completar/")) {
            try {
                int id = Integer.parseInt(pathInfo.substring("/completar/".length()));
                for (Tarea tarea : listaTareas) {
                    if (tarea.getId() == id) {
                        tarea.setCompletada(true);
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                // Ignorar
            }
            response.sendRedirect(request.getContextPath() + "/tareas");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        List<Tarea> listaTareas = (List<Tarea>) session.getAttribute("tareas");
        
        if (listaTareas == null) {
            listaTareas = new ArrayList<>();
            session.setAttribute("tareas", listaTareas);
        }
        
        String pathInfo = request.getPathInfo();
        
        if ("/guardar".equals(pathInfo)) {
            String descripcion = request.getParameter("descripcion");
            
            if (descripcion != null && !descripcion.trim().isEmpty()) {
                int nuevoId = 1;
                if (!listaTareas.isEmpty()) {
                    nuevoId = listaTareas.get(listaTareas.size() - 1).getId() + 1;
                }
                
                Tarea nuevaTarea = new Tarea(nuevoId, descripcion);
                listaTareas.add(nuevaTarea);
            }
            
            response.sendRedirect(request.getContextPath() + "/tareas");
        }
    }
}
