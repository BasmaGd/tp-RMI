/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientrmi;

import dao.IDao;
import entities.Machine;
import entities.Salle;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class ClientRMI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            IDao<Machine> dao = (IDao<Machine>) Naming.lookup("rmi://localhost:1099/dao");
            IDao<Salle> dao2 = (IDao<Salle>) Naming.lookup("rmi://localhost:1099/dao2");
            Salle s=new Salle("B2");
            dao2.create(s);
            
            for(Salle sS:dao2.findAll()){
                System.out.println(sS);
            }

            dao.create(new Machine("GD33", "DELL", 2000,s));
            dao.create(new Machine("GD34", "Lenovo", 4000,s));
            dao.create(new Machine("GD35", "HP", 6000,s));
            dao.create(new Machine("GD33", "DELL", 2000,null));
            dao.create(new Machine("GD34", "Lenovo", 4000,null));
            dao.create(new Machine("GD35", "HP", 6000,null));
               for(Machine m: dao.findAll()){
            System.out.println(m);
        }
        } catch (NotBoundException ex) {
            Logger.getLogger(ClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
