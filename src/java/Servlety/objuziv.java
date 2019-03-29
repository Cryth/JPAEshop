/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlety;

import Entity.Objednavky;
import Entity.Polozkyobj;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author splat
 */
@WebServlet(name = "objuziv", urlPatterns = {"/objuziv"})
public class objuziv extends HttpServlet {

    HttpSession session;
    Integer idP;
    EntityManagerFactory emf;
    EntityManager em;
    String meno;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css'>");
            out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
            out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js'></script>");
            out.println("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js'></script>");
            out.println("<link rel='stylesheet' href='https://fonts.googleapis.com/icon?family=Material+Icons'>");
            out.println("<style>");
            out.println("body{background-attachment: fixed; background-image:url(eshopbg.jpg); max-width: 100%; height: auto}");
            out.println("h2{color: white; -webkit-text-stroke-width: 1px; -webkit-text-stroke-color: black}");
            out.println("h4{color: white;}");
            out.println("li{color: white;}");
            out.println("</style>");
            
            session = request.getSession();
            idP = (Integer)session.getAttribute("idUser");
            meno = (String)session.getAttribute("Meno");
            
            
            //header
            out.println("<nav class='navbar navbar-default navbar-expand-sm navbar-dark bg-dark fixed-top'>");
                out.println("<div class='container'>");
                    out.println("<a class='navbar-brand mb-0 h1' href='main'>AutoPapuče</a>");
                    out.println("<button class='navbar-toggler' type='button' data-toggle='collapse' data-target='#navbarResponsive' aria-controls='navbarResponsive' aria-expanded='false' aria-label='Toggle navigation'>");
                    out.println("<span class='navbar-toggler-icon'></span>");
                    out.println("</button>");
                    out.println("<div class='collapse navbar-collapse' id='navbarResponsive'>");
                    out.println("<ul class='navbar-nav ml-auto'>");
                    out.println("<li class='nav-item active'>");
                    out.println("<a class='navbar-brand mb-0' href='objuziv'>"+meno+"</a>");
                    out.println("<span class='sr-only'>(current)</span>");
                    out.println("</li>");
                      out.println("<li class='nav-item'>");
                       out.println("<a class='nav-link' href='kosik' style='display: inline-flex; vertical-align: middle' href='kosik'><i class='material-icons' style='display: inline-flex; vertical-align: middle'>shopping_cart</i> Košík</a>");
                      out.println("</li>");
                      out.println("<li class='nav-item'>");
                        out.println("<a class='nav-link' style='display: inline-flex; vertical-align: middle' href='logout'><i class='material-icons' style='display: inline-flex; vertical-align: middle'>exit_to_app</i> Odhlásenie</a>");
                      out.println("</li>");
                    out.println("</ul>");
                  out.println("</div>");
                out.println("</div>");
              out.println("</nav>");
              
              
              out.println("<div class='container col-md-7 justify-content-center'>");
              out.println("<br><br><br>");
              vypisObjednavok(out);
              out.println("</div>");
              out.println("<br><br>");
              
              //footer
            out.println("<footer class='footer py-3 bg-dark fixed-bottom'>");
            out.println("<div class='container'>");
            out.println("<p class='m-0 text-center text-white'>Copyright &copy; Patrik Tariška 2017</p>");
            out.println("</div>");
            out.println("</footer>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void vypisObjednavok(PrintWriter out) {
        try{
            emf = Persistence.createEntityManagerFactory("JPAEshopPU");
            em = emf.createEntityManager();
            List<Objednavky> lobj = (List<Objednavky>) em.createQuery("SELECT o FROM Objednavky o WHERE o.idUser='"+idP+"'").getResultList();
            for(Objednavky o:lobj){
                List<Polozkyobj> polozky = (List<Polozkyobj>) em.createQuery("SELECT p FROM Polozkyobj p WHERE p.idObj='"+o.getIdObj()+"'").getResultList();
                out.println("<div class='col-sm mb-4 jumbotron' style='background-color: rgba(0, 0, 0, 0.4);'>");
                out.println("<h2>Objednávka č.: "+o.getCisloObj()+"</h2>");
                out.println("<h4>Suma: "+o.getSuma()+"€</h4>");
                out.println("<h4>Stav: "+o.getStav()+"</h4>");
                out.println("<ul>");
                for(Polozkyobj p:polozky){
                    out.print("<li>Položka id: "+p.getIdTovar()+", kusov: "+p.getKusy()+"</li>");
                }
                out.println("</ul>");
                out.println("</div>");
            }
            em.close();
            emf.close();
            
        }catch(Exception e){
            em.close();
            emf.close();
            System.out.println("vypisObj: "+e.toString());
        }
    }

}
