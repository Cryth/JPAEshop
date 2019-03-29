/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlety;

import Entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author splat
 */
@WebServlet(name = "registracia", urlPatterns = {"/registracia"})
public class registracia extends HttpServlet {

    EntityManagerFactory emf;
    EntityManager em;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css'>");
            out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
            out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js'></script>");
            out.println("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js'></script>");
            out.println("<style>");
            out.println("body{background-image:url(background.jpg); max-width: 100%; height: auto}");
            out.println("h1{color: white; -webkit-text-stroke-width: 1px; -webkit-text-stroke-color: black}");
            out.println("</style>");
            String tlac = (request.getParameter("tlacidlo")).substring(0,1);
                if(tlac.equals("Z")){
                    boolean vysl = Zaregistruj(request.getParameter("login"),request.getParameter("heslo"),request.getParameter("email"),request.getParameter("meno"),request.getParameter("priezvisko"),request.getParameter("adresa"));
                    if(vysl){
                        out.println("<br><br><br>");
                        out.println("<div class='row text-center'>");
                        out.println("<div class='col-sm'></div>");
                        out.println("<div style='background-color: rgba(255, 255, 255, 0.3)' class='col-sm jumbotron'>");
                        out.println("<h1>Registrácia úspešná</h1>");
                        out.println("<a href='index.html' class='btn btn-success btn-lg' style='cursor: pointer;' role='button' >Prihlásiť sa</a>");
                        out.println("</div>");
                        out.println("<div class='col-sm'></div>");
                        out.println("</div>");                        
                    }
                    else{
                        out.println("<br><br><br>");
                        out.println("<div class='row text-center'>");
                        out.println("<div class='col-sm'></div>");
                        out.println("<div style='background-color: rgba(255, 255, 255, 0.3)' class='col-sm jumbotron'>");
                        out.println("<h1>Registrácia neúspešná!</h1>");
                        out.println("<h1>Login už existuje!</h1>");
                        out.println("<a href='register.html' class='btn btn-danger btn-lg' style='cursor: pointer;' role='button' >Registrácia</a>");
                        out.println("</div>");
                        out.println("<div class='col-sm'></div>");
                        out.println("</div>");
                    }
                    
                }
            
        
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

    private boolean Zaregistruj(String login, String heslo, String email, String meno, String priezvisko, String adresa) {
        try{
            emf = Persistence.createEntityManagerFactory("JPAEshopPU");
            em = emf.createEntityManager();
            Query query = em.createQuery("SELECT COUNT(u.idUser) FROM Users u WHERE u.login='"+login+"'");
            int pocet = (int)(long)query.getSingleResult();
            if(pocet > 1){
                em.close();
                emf.close();
                return false;
            }else{
                Users u = new Users();
                u.setLogin(login);
                u.setHeslo(heslo);
                u.setEmail(email);
                u.setMeno(meno);
                u.setPriezvisko(priezvisko);
                u.setAdresa(adresa);
                u.setAdmin(false);
                em.getTransaction().begin();
                em.persist(u);
                em.getTransaction().commit();
                em.close();
                emf.close();
                return true;
            }
        }catch(Exception e){
            em.close();
            emf.close();
            System.out.println("Zaregistruj: "+e.toString());
            return false;
        }
    }

}
