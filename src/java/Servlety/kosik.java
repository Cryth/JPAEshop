/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlety;

import Entity.Kosik;
import Entity.Objednavky;
import Entity.Polozkyobj;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
@WebServlet(name = "kosik", urlPatterns = {"/kosik"})
public class kosik extends HttpServlet {

    HttpSession session;
    Integer idP;
    EntityManagerFactory emf;
    EntityManager em;
    
    int objcena;
    boolean nobj = false;
    
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
            out.println("</style>");
            
            //session handling
            session = request.getSession();
            idP = (Integer)session.getAttribute("idUser");
            String meno = (String)session.getAttribute("Meno");
            
            
            if(request.getContentType() != null){
                String tlac = (request.getParameter("tlacidlo")).substring(0,1);
                
                // pridanie kusu
                if(tlac.equals("+")){
                    pridajKus(idP, request.getParameter("idtovaru"));
                }
                // odobratie kusu
                if(tlac.equals("-")){
                    odoberKus(idP, request.getParameter("idtovaru"));
                }
                // zmazanie tovaru
                if(tlac.equals("x")){
                    zmazTovar(idP, request.getParameter("idtovaru"));
                }
                // objednanie
                if(tlac.equals("o")){
                   vystavObj(out, idP, objcena);
                }
            }
            
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
            
            if(zistiStav(idP)){
               // vypis tovaru
                out.println("<div class='container col-md-7'>");
                out.println("<br><br><br>");
                
                vypisKosika(out);
                
                out.println("</div>");

                out.println("<div class='container col-md-7 text-center'>");
                out.println("<form action='kosik' method='post'>");
                out.println("<button class='btn btn-info btn-lg' type='submit' style='cursor: pointer;' name='tlacidlo' value='objednavka'>Vystaviť objednávku so sumou: "+objcena+"€</button>");
                out.println("</form>");
                out.println("</div>");
                out.println("<br><br><br>");
            }else{
                // vypis "prazdny kosik"
                out.println("<br><br><br>");
                out.println("<div class='row text-center'>");
                out.println("<div class='col-sm'></div>");
                out.println("<div style='background-color: rgba(255, 255, 255, 0.3)' class='col-sm jumbotron'>");
                out.println("<h1 style='color: white; -webkit-text-stroke-width: 1px; -webkit-text-stroke-color: black'>Košík je prázdny</h1>");
                if(nobj){ // ak sa prave vytvorila objednavka , vypise sa ze sa spracovava
                    nobj = false;
                    out.println("<h1 style='color: white; -webkit-text-stroke-width: 1px; -webkit-text-stroke-color: black'>Objednávka sa spracováva</h1>");
                }
                out.println("</div>");
                out.println("<div class='col-sm'></div>");
                out.println("</div>");  
              }
              
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

    private boolean zistiStav(Integer idP) {
        try{
            emf = Persistence.createEntityManagerFactory("JPAEshopPU");
            em = emf.createEntityManager();
            Query query = em.createQuery("SELECT COUNT(k.idKosik) FROM Kosik k WHERE k.idUser='"+idP+"'");
            int pocet = (int)(long)query.getSingleResult();
            em.close();
            emf.close();
            return pocet != 0;
        }catch(Exception e){
            em.close();
            emf.close();
            System.out.println("ZistiStav: "+e.toString());
            return false;
        }
    }
    
    private int zistiSklad(){
        try{
            emf = Persistence.createEntityManagerFactory("JPAEshopPU");
            em = emf.createEntityManager();
            Query query = em.createQuery("SELECT k FROM Kosik k WHERE k.idUser='"+idP+"'");
            List<Kosik> kusyK = (List<Kosik>)query.getResultList();
            for(Kosik k:kusyK){
                int kusySk = (Integer)em.createQuery("SELECT t.kusy FROM Tovar t WHERE t.idTovar='"+k.getIdTovar()+"'").getSingleResult();
                if(k.getKusy() > kusySk){
                    em.close();
                    emf.close();
                    return k.getIdTovar();
                }
            }
            em.close();
            emf.close();
            return 0;
        }catch(Exception e){
            em.close();
            emf.close();
            System.out.println("zistiSklad: "+e.toString());
            return -1;
        }
        
    }

    private void vypisKosika(PrintWriter out) {
        try{
            emf = Persistence.createEntityManagerFactory("JPAEshopPU");
            em = emf.createEntityManager();
            int celkovacena;
            objcena = 0;
            
            Query q1 = em.createQuery("SELECT k FROM Kosik k WHERE k.idUser='"+idP+"' ORDER BY k.idTovar ASC");
            List<Kosik> kosik = (List<Kosik>) q1.getResultList();
            for(Kosik k:kosik){
                celkovacena = (int)k.getCena() * k.getKusy();
                objcena += celkovacena;
                String href = (String)em.createQuery("SELECT t.href FROM Tovar t WHERE t.idTovar='"+k.getIdTovar()+"'").getSingleResult();
                String nazov = (String)em.createQuery("SELECT t.nazov FROM Tovar t WHERE t.idTovar='"+k.getIdTovar()+"'").getSingleResult();
                out.println("<div class='row'>");
                out.println("<div class='col-sm-5 mb-4 jumbotron text-center' style='background-color: rgba(0, 0, 0, 0.4);'>");
                out.println("<img src='"+href+".jpg' class='img-responsive' width='300' height='300' alt='Disky'>");
                out.println("</div>");
                out.println("<div class='col-md mb-4 jumbotron' style='background-color: rgba(0, 0, 0, 0.4);'>");
                out.println("<h2>Položka "+nazov+"</h2>");
                out.println("<h2>Cena/ks: "+k.getCena()+"€</h2>");
                out.println("<h2>Počet kusov: "+k.getKusy()+"</h2>");
                out.println("<h2>Celková cena: "+celkovacena+"€</h2>");
                out.println("<form action='kosik' method='post'>");
                out.println("<input type='hidden' name='idtovaru' value='"+k.getIdTovar()+"'>");
                out.println("<input type='hidden' name='celkcena' value='"+celkovacena+"'>");
                out.print("<button type='submit' value='-' name='tlacidlo' class='btn btn-warning' style='cursor: pointer; display: inline-flex; vertical-align: middle'>-1ks</button>");
                out.print("   ");
                out.print("<button type='submit' value='+' name='tlacidlo' class='btn btn-success' style='cursor: pointer; display: inline-flex; vertical-align: middle'>+1ks</button>");
                out.println("<br><br>");
                out.print("<button type='submit' value='x' name='tlacidlo' class='btn btn-danger' style='cursor: pointer; display: inline-flex; vertical-align: middle'><i class='material-icons' style='display: inline-flex; vertical-align: middle'>remove_shopping_cart</i>Zmazať z košíka</button>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
            }
            em.close();
            emf.close();
            
        }catch(Exception e){
            em.close();
            emf.close();
            System.out.println("VypisKosika: "+e.toString());
        }
    }

    private void pridajKus(Integer idP, String idtovar) {
        try{
            emf = Persistence.createEntityManagerFactory("JPAEshopPU");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            Query query = em.createNativeQuery("UPDATE Kosik k SET k.kusy=k.kusy+1 WHERE k.idUser='"+idP+"' AND k.idTovar='"+idtovar+"'");
            query.executeUpdate();
            em.getTransaction().commit();
            
            
            em.close();
            emf.close();
        }catch(Exception e){
            em.close();
            emf.close();
            System.out.println("pridajKus: "+e.toString());
        }
    }

    private void odoberKus(Integer idP, String idtovar) {
        try{
            emf = Persistence.createEntityManagerFactory("JPAEshopPU");
            em = emf.createEntityManager();
            em.getTransaction().begin();
            Query query = em.createNativeQuery("UPDATE Kosik k SET k.kusy=k.kusy-1 WHERE k.idUser='"+idP+"' AND k.idTovar='"+idtovar+"'");
            query.executeUpdate();
            em.getTransaction().commit();
            int pocet = (int)em.createQuery("SELECT k.kusy FROM Kosik k WHERE k.idUser='"+idP+"' AND k.idTovar='"+idtovar+"'").getSingleResult();
            if(pocet == 0){
                em.getTransaction().begin();
                query = em.createQuery("DELETE FROM Kosik k WHERE k.idUser='"+idP+"' AND k.idTovar='"+idtovar+"'");
                query.executeUpdate();
                em.getTransaction().commit();
            }
            em.close();
            emf.close();
        }catch(Exception e){
            em.close();
            emf.close();
            System.out.println("odoberKus: "+e.toString());
        }    
    }

    private void zmazTovar(Integer idP, String idtovar) {
        try{
            emf = Persistence.createEntityManagerFactory("JPAEshopPU");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Kosik k WHERE k.idUser='"+idP+"' AND k.idTovar='"+idtovar+"'");
            query.executeUpdate();
            em.getTransaction().commit();
            
            em.close();
            emf.close();
        }catch(Exception e){
            em.close();
            emf.close();
            System.out.println("zmazTovar: "+e.toString());
        }
    }

    private void vystavObj(PrintWriter out, Integer idP, int objcena) {
        try{
            int stav = zistiSklad();
            emf = Persistence.createEntityManagerFactory("JPAEshopPU");
            em = emf.createEntityManager();
            if(stav == 0){
                Objednavky o = new Objednavky();
                o.setDatum(new Date());
                o.setIdUser(idP);
                o.setSuma((double)objcena);
                o.setStav("Spracováva sa");
                em.getTransaction().begin();
                em.persist(o);
                em.getTransaction().commit();
                int idobjed = (int)em.createQuery("SELECT o.idObj FROM Objednavky o WHERE o.idUser='"+idP+"' ORDER BY o.datum DESC").getResultList().get(0);
                em.getTransaction().begin();
                em.createNativeQuery("UPDATE Objednavky o SET o.cisloobj='"+idobjed+"' WHERE o.idUser='"+idP+"' AND o.idObj='"+idobjed+"'").executeUpdate();
                em.getTransaction().commit();
                List<Kosik> KosPol = (List<Kosik>)em.createQuery("SELECT k FROM Kosik k WHERE k.idUser='"+idP+"'").getResultList();
                em.getTransaction().begin();
                for(Kosik k:KosPol){
                    Polozkyobj p = new Polozkyobj();
                    p.setIdObj(idobjed);
                    p.setCena(k.getCena());
                    p.setKusy(k.getKusy());
                    p.setIdTovar(k.getIdTovar());
                    em.persist(p);
                    em.createNativeQuery("UPDATE Tovar t SET t.kusy=t.kusy-"+k.getKusy()+" WHERE t.idTovar='"+k.getIdTovar()+"'").executeUpdate();
                }
                em.createQuery("DELETE FROM Kosik k WHERE k.idUser='"+idP+"'").executeUpdate();
                em.getTransaction().commit();
                nobj = true;
            }else{
                String prod = (String)em.createQuery("SELECT t.nazov FROM Tovar t WHERE t.idTovar='"+stav+"'").getSingleResult();
                out.println("<div class='container'>");
                out.println("<br><br><br>");
                out.println("<div class='alert alert-danger alert-dismissable' role='alert'>");
                out.println("<button type='button' class='close' data-dismiss='alert'>&times;</button>");
                out.println("<strong>Pozor!</strong> Nedostatok kusov na sklade, zmeňte počet kusov produktu: "+prod+".");
                out.println("</div>");
                out.println("</div>");
            }
            em.close();
            emf.close();
        }catch(Exception e){
            em.close();
            emf.close();
            System.out.println("vystavObj: "+e.toString());
        }
    }

}
