/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import dao.IDao;
import entities.Machine;
import entities.Salle;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import service.MachineService;
import service.SalleService;

/**
 *
 * @author DELL
 */
public class Connexion {
    private static InitialContext ctx;
    public static void initJNDI(){
      
        try {
            LocateRegistry.createRegistry(1099);
            final Hashtable<Object,Object> jndiProperties = new Hashtable<Object,Object>();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
            jndiProperties.put(Context.PROVIDER_URL, "rmi://localhost:1099");
            ctx=new InitialContext(jndiProperties);
        } catch (RemoteException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static void bindJNDI(String name,Object obj) throws NamingException{
        ctx.bind(name, obj);
    }
    public static void unbindJNDI(String name) throws NamingException{
        ctx.unbind(name);
    }
    public static void unInitJNDI() throws NamingException{
        ctx.close();
    }
    public static void main(String[] args) {
    
        try {
            initJNDI();
            IDao<Salle> dao2 = new SalleService();
            IDao<Machine> dao = new MachineService();
            ctx.bind("dao", dao);
             ctx.bind("dao2", dao2);
             System.out.println("en attente de clients");
        } catch (RemoteException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
}
