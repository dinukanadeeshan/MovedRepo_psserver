
import com.redonz.pms.common.model.CustomerOrder;
import com.redonz.pms.common.model.CustomerOrderDetail;
import com.redonz.pms.server.controller.impl.CustomerOrderControllerImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author DI_SH
 */
public class NewClass {

    public static void main(String[] args) throws RemoteException, ClassNotFoundException, SQLException {
        try {
            //        ArrayList<CustomerOrderDetail> al = new ArrayList<>();
            //        al.add(new CustomerOrderDetail("BI0001", "OD00010", 10, 12.50, "2014-07-07"));
            //        al.add(new CustomerOrderDetail("BI0002", "OD00010", 15, 10.50, "2014-15-07"));
            //        CustomerOrder customerOrder = new CustomerOrder("C0000", "OD00010", "2014-05-01", 1500, 1500, 0, al);
            //        boolean saveCustomerOrder = new CustomerOrderControllerImpl().saveCustomerOrder(customerOrder);
            //        System.out.println(saveCustomerOrder);
            //        System.out.println("X\u00B7");
            Properties properties = new Properties();
            properties.setProperty("host", "localhost");
            properties.setProperty("user", "root");
            properties.setProperty("password", "123");
            properties.setProperty("dbname", "ps");
            properties.store(new FileOutputStream(new File("dbconfig.properties")), "");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
