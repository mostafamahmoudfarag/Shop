package com.mostafa.Model.DataLayer;
import com.mostafa.Model.data;
import com.sun.webkit.CursorManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class connect {
    Connection conn=null;
    private String username="root";
    private String password="123456";
    private String url="jdbc:mysql://localhost:3306/desktop";
    public connect(){
    try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(url,username,password); 
        } catch (Exception ex) {
            System.out.println("not connected"+ex.getMessage());
        }
    }
    public void add(data d){
        try {
            PreparedStatement p=conn.prepareStatement("insert into shop"
                    + " values("+d.getId()+",'"+d.getName()+"',"+d.getPrice()+",'"+d.getDate()+"','"+d.getPath()+"')");
      int b=p.executeUpdate();
      if(b==1)
          JOptionPane.showMessageDialog(null,"added");
      else
           JOptionPane.showMessageDialog(null,"not added please try again");
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }
    public List<data> getalldata(){
    
        List<data> list=new ArrayList();
        
        try {
            PreparedStatement p=conn.prepareStatement("select * from shop");
            ResultSet resultset=p.executeQuery();
            while(resultset.next()){
                list.add(new data(resultset.getInt("id"),resultset.getString("name"),resultset.getDouble("price")
                ,resultset.getDate("purchase_date")));
            }
        } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null,ex.getMessage());
        }
         return list; 
    }
    public void update(data d){
        try {
            PreparedStatement p=conn.prepareStatement("delete from shop where id="+d.getId()+"");
            p.executeUpdate();
            p=conn.prepareStatement("insert into shop"
                    + " values("+d.getId()+",'"+d.getName()+"',"+d.getPrice()+",'"+d.getDate()+"','"+d.getPath()+"')");
      int b=p.executeUpdate();
      if(b==1)
          JOptionPane.showMessageDialog(null,"updated");
      else
           JOptionPane.showMessageDialog(null,"not updated please try again");
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null,ex.getMessage());
        }
}
    public void delete(int a){

        try {
            PreparedStatement p = conn.prepareStatement("delete from shop where id="+a+"");
            p.executeUpdate();
          JOptionPane.showMessageDialog(null,"deleted succesfuly");
      }
      catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,ex.getMessage());
        }
     
    }
    public data getproductbyname(String name){
        data d=null;
        
         try {
             d=new data();
            PreparedStatement p = conn.prepareStatement("select * from shop where name='"+name+"'");
         ResultSet r=p.executeQuery();
         r.next();
         d.setId(r.getInt("id"));
         d.setName(r.getString("name"));
         d.setPrice(r.getDouble("price"));
         d.setDate(r.getDate("purchase_date"));
         d.setPath(r.getString("path"));
             System.out.println(r.getString("path"));
      }
      catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"not found");
        }
     return d;
    }
}
