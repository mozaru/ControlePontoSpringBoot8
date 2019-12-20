package br.com.tech.ControlePonto.utils;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.ResultSetMetaData;
import br.com.tech.ControlePonto.utils.Config;

public class Banco
{
    private Connection connection;

    public Banco()
    {
        System.out.println("Carregando driver do banco ["+Config._DRIVER_+"]");
        try{
            Class.forName(Config._DRIVER_);
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
            connection = DriverManager.getConnection(Config._STRING_CONEXAO_,Config._USER_,Config._PASSWORD_);
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