package br.com.tech.ControlePonto.utils;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.ResultSetMetaData;

public class Banco
{
    private Connection connection;
    public Banco()
    {
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch(Exception e)
        {
            System.out.printf("ERRO: nao foi possivel carregar o postgree");
            e.printStackTrace();
        }
    }

    private boolean conectar()
    {
        try{
            connection = DriverManager.getConnection("jdbc:postgresql://ec2-107-21-103-80.compute-1.amazonaws.com:5432/d6dorkbm03fvrl", "xxwzbzgbbzejmx", "a88d6640d66e2a8b397626d64dff89cb1bbf82b58d28da733afc8ae58e6a1874");
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    private void desconectar()
    {
        try{
        if (connection!=null)
        {
            connection.close();
            connection = null;
        }
        }catch(Exception e)
        {
            connection = null;
        }
    }

    public List<Map<String,Object>> getItens()
    {
        List<Map<String,Object>> lista = new ArrayList<Map<String,Object>>();
        try{
            conectar();
            Statement stmt = connection.createStatement();
            ResultSet r = stmt.executeQuery("SELECT id,name FROM teste order by name");
            ResultSetMetaData m = r.getMetaData();
            while (r.next()) {
                Map<String,Object> obj = new HashMap<String,Object>();
                for(int i=0;i<m.getColumnCount();i++)
                    obj.put(m.getColumnLabel(i+1),r.getObject(i+1));
                lista.add(obj);
            }
            r.close();
            stmt.close();
            desconectar();
            return lista;
        }catch (Exception e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
            return null;
        }
    }
}