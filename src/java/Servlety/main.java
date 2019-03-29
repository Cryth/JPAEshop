/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlety;

import Entity.Kosik;
import Entity.Tovar;
import Entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
@WebServlet(name = "main", urlPatterns = {"/main"})
public class main extends HttpServlet {

    HttpSession session;
    Integer idP = 0;
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
            out.println("<link rel='stylesheet' href='https://fonts.googleapis.com/icon?family=Material+Icons'>");
            out.println("<style>");
            out.println("body{background-attachment: fixed; background-image:url(eshopbg.jpg); max-width: 100%; height: auto}");
            out.println("</style>");
            
            // session a JPA
            
            session = request.getSession();
            idP = (Integer)session.getAttribute("idUser");
            
            if(idP == null){
                if(request.getContentType() == null){
                    NeopravnenyPristup(out);
                    return;
                }
                
                if(request.getContentType() != null){
                    String tlac = (request.getParameter("tlacidlo")).substring(0,1);
                    
                    if(tlac.equals("V")){
                        idP = OverLogin(request.getParameter("meno"), request.getParameter("heslo"));
                        if(idP == 0){
                            NeopravnenyPristup(out);
                            return;
                        }
                        
                        VytvorSession(idP);                       
                    }
                }
                
                
            }
            //ak je uz prihlaseny
            if(request.getContentType() != null){
                String tlac = (request.getParameter("tlacidlo")).substring(0,1);
                
                // pridanie do kosika
                if(tlac.equals("D")){
                    DoKosika(idP, request.getParameter("idT"), request.getParameter("cena"));
                }
            }
            String meno = (String)session.getAttribute("Meno");
            
            //header
            out.println("<nav class='navbar navbar-default navbar-expand-sm navbar-dark bg-dark fixed-top'>");
                out.println("<div class='container'>");
                    out.println("<a class='navbar-brand mb-0 h1' href='main'>AutoPapuče</a>");
                    out.println("<button class='navbar-toggler' type='button' data-toggle='collapse' data-target='#navbarResponsive' aria-controls='navbarResponsive' aria-expanded='false' aria-label='Toggle navigation'>");
                    out.println("<span class='navbar-toggler-icon'></span>");
                    out.println("</button>");
                    out.println("<div class='collapse navbar-collapse' id='navbarResponsive'>");
                    out.println("<ul class='navbar-nav ml-auto'>");
                      if((boolean)session.getAttribute("admin")){ // v pripade prihlasenia admina, v navbare sa objavia moznosti "sprava objednavok" a "sprava pouzivatelov"
                        out.println("<li class='nav-item'>");
                            out.println("<a class='nav-link' style='display: inline-flex; vertical-align: middle' href='spravaobj'><i class='material-icons' style='display: inline-flex; vertical-align: middle'>archive</i> Správa Objednávok</a>");
                        out.println("</li>");
                        out.println("<li class='nav-item'>");
                            out.println("<a class='nav-link' style='display: inline-flex; vertical-align: middle' href='spravapouz'><i class='material-icons' style='display: inline-flex; vertical-align: middle'>supervisor_account</i> Správa Užívateľov</a>");
                        out.println("</li>");
                      }else{ // v pripade prihlasenia uzivatela sa objavi moznost "kosik"
                            out.println("<li class='nav-item active'>");
                            out.println("<a class='navbar-brand mb-0' href='objuziv'>"+meno+"</a>");
                            out.println("<span class='sr-only'>(current)</span>");
                            out.println("</li>");
                            out.println("<li class='nav-item'>");
                            out.println("<a class='nav-link' href='kosik' style='display: inline-flex; vertical-align: middle'><i class='material-icons' style='display: inline-flex; vertical-align: middle'>shopping_cart</i> Košík</a>");
                            out.println("</li>");
                      }
                      out.println("<li class='nav-item'>");
                        out.println("<a class='nav-link' style='display: inline-flex; vertical-align: middle' href='logout'><i class='material-icons' style='display: inline-flex; vertical-align: middle'>exit_to_app</i> Odhlásenie</a>");
                      out.println("</li>");
                    out.println("</ul>");
                  out.println("</div>");
                out.println("</div>");
              out.println("</nav>");
           
            // vypis tovaru
           out.println("<div class='container'>");
           out.println("<br><br><br>");
           out.println("<div class='row'>");
           
           vypisTovaru(out);
           
           out.println("</div>");
           out.println("</div>");
           
           //footer
           out.println("<footer class='footer py-3 bg-dark'>");
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
    
    public void NeopravnenyPristup(PrintWriter out){
        try{    
                out.println("<style>body{background-attachment: fixed; background-image:url(background.jpg);}</style>");
                out.println("<br><br><br>");
                out.println("<div class='row text-center'>");
                out.println("<div class='col-sm'></div>");
                out.println("<div style='background-color: rgba(255, 255, 255, 0.3);' class='col-sm jumbotron'>");
                out.println("<h1 style='color: white; -webkit-text-stroke-width: 1px; -webkit-text-stroke-color: black'>Neoprávnený prístup!</h1>");
                out.println("<h1 style='color: white; -webkit-text-stroke-width: 1px; -webkit-text-stroke-color: black'>Prihláste sa.</h1>");
                out.println("</div>");
                out.println("<div class='col-sm'></div>");
                out.println("</div>");
                session.invalidate();
        }catch(Exception e){
            out.println(e.toString());
        }
    }
    
    private void VytvorSession(Integer idUser) {
        emf = Persistence.createEntityManagerFactory("JPAEshopPU");
        em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM Users u WHERE u.idUser = '"+idUser+"'");
        
        List<Users> list = (List<Users>)query.getResultList();
        if(!list.isEmpty() && list.size()==1){
            for(Users u:list) {
                session.setAttribute("idUser", u.getIdUser());
                session.setAttribute("Meno", u.getMeno()+" "+u.getPriezvisko());
                session.setAttribute("admin", u.getAdmin());
            }
        }
        em.close();
        emf.close();
    }

    private int OverLogin(String meno, String heslo) {
        emf = Persistence.createEntityManagerFactory("JPAEshopPU");
        em = emf.createEntityManager();
        int vysl = 0;
        Query query = em.createQuery("SELECT u.idUser FROM Users u WHERE u.login='"+meno+"' AND u.heslo='"+heslo+"'");
        try{
        vysl = (Integer)query.getSingleResult();
        }catch(Exception e){
            // nenasli sa zaznamy alebo sa vyskytla chyba, vratime 0
            return 0;
        }
        em.close();
        emf.close();
        return vysl;
    }

    private void DoKosika(Integer idP, String idtovar, String Cena) {
        emf = Persistence.createEntityManagerFactory("JPAEshopPU");
        em = emf.createEntityManager();
        Query query = em.createQuery("SELECT COUNT(k.idKosik) FROM Kosik k WHERE k.idUser='"+idP+"' AND k.idTovar='"+Integer.parseInt(idtovar)+"'");
        int pocet = (int)(long)query.getSingleResult();
        if(pocet == 0){
            Kosik k = new Kosik();
            k.setIdTovar(Integer.parseInt(idtovar));
            k.setIdUser(idP);
            k.setCena(Double.parseDouble(Cena));
            k.setKusy(1);
            em.getTransaction().begin();
            em.persist(k);
            em.getTransaction().commit();
        }else{
            em.getTransaction().begin();
            query = em.createNativeQuery("UPDATE Kosik k SET k.kusy=k.kusy+1 WHERE k.idUser='"+idP+"' AND k.idTovar='"+idtovar+"'");
            query.executeUpdate();
            em.getTransaction().commit();
            
        }
        
        em.close();
        emf.close();
    }

    private void vypisTovaru(PrintWriter out) {
       try{
            emf = Persistence.createEntityManagerFactory("JPAEshopPU");
            em = emf.createEntityManager();
            Query query = em.createQuery("SELECT t FROM Tovar t");
            List<Tovar> list = (List<Tovar>)query.getResultList();
            for(Tovar k:list){
                out.println("<div class='col-lg-4 col-md-6 mb-4'>");
                    out.println("<div class='card h-100'>");
                        out.println("<img class='card-img-top' src='"+k.getHref()+".jpg' alt='"+k.getHref()+"'>");
                        out.println("<form action='main' method='post'>");
                        out.println("<div class='card-body'>");
                            out.println("<h4 class='card-title'>");
                                out.println(k.getNazov());
                                out.println("<input type='hidden' name='idT' value='"+k.getIdTovar()+"'>");
                            out.println("</h4>");
                            out.println("<h5>"+k.getCena()+"€/kus</h5>");
                            out.println("<input type='hidden' name='cena' value='"+k.getCena()+"'>");
                        out.println("</div>");
                        out.print("<div class='card-footer text-center'>");
                                if((boolean)session.getAttribute("admin")){// ak sa prihlasi admin, neukaze tlacidlo "do kosika" ale pocet kusov urciteho tovaru na sklade
                                    if(k.getKusy() == 0) out.println("<h2><span class='badge badge-danger'>Nieje na sklade</span></h2>");
                                    else out.println("<h2><span class='badge badge-info'>Počet kusov na sklade: "+k.getKusy()+"</span></h2>");
                                }else{// prihlaseny pouzivatel , ukaze teda tlacidlo "do kosika"
                                    if(k.getKusy() == 0) out.println("<h2><span class='badge badge-danger'>Nieje na sklade</span></h2>");
                                    else out.println("<button type='submit' style='cursor: pointer; display: inline-flex; vertical-align: middle' class='btn btn-success text-center' value='DoKosika' name='tlacidlo'><i class='material-icons' style='display: inline-flex; vertical-align: middle'>add_shopping_cart</i> Do Košíka</button>");
                                }
                        out.println("</div>");
                        out.println("</form>");
                    out.println("</div>");
                out.println("</div>");
                
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
        em.close();
        emf.close();
    }
    
    
}
