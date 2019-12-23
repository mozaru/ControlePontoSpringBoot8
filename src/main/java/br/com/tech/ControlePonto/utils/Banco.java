package br.com.tech.ControlePonto.utils;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.ResultSetMetaData;
import br.com.tech.ControlePonto.utils.Config;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.util.FileCopyUtils;
import java.io.Reader;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Banco
{
    private static Logger logger = LoggerFactory.getLogger(Banco.class);

    private Connection connection;

    public Banco()
    {
        logger.info("Carregando driver do banco ["+Config._DRIVER_+"]");
        try{
            Class.forName(Config._DRIVER_);
        }
        catch(Exception e)
        {
            logger.error("ERRO: nao foi possivel carregar o postgree");
            e.printStackTrace();
        }
    }

    public void criarBanco()
    {
        conectar();
        logger.info("Gerando Tabelas"); 
        try{
        Statement stmt = connection.createStatement();
        
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:script.sql");
        String txt=null;
        try (Reader reader = new InputStreamReader(resource.getInputStream(),java.nio.charset.StandardCharsets.UTF_8)) 
        {
            txt= FileCopyUtils.copyToString(reader);
        } catch (Exception e) {
            txt = null;
        }
        
        for(String cmd : txt.split(";"))
        {
            logger.info("Executando comando: "+cmd);
            try{
                stmt.executeUpdate(cmd);
                logger.info("Comando executado com sucesso!");
            }catch(Exception e)
            {
                logger.error("Comando executado falhou! ["+e.getMessage()+"]");
                e.printStackTrace();
            }
            
        }
        stmt.close();
        }catch(Exception e)
        {

        }
        logger.info("Tabelas Geradas");         
        desconectar();
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
                    obj.put(m.getColumnLabel(i+1).toLowerCase(),r.getObject(i+1));
                lista.add(obj);
            }
            r.close();
            stmt.close();
            desconectar();
            return lista;
        }catch (Exception e) {
            logger.error("Connection failure.");
            e.printStackTrace();
            return null;
        }
    }



    public List<Map<String,Object>> executarConsulta(String cmd)
    {
        List<Map<String,Object>> lista = new ArrayList<Map<String,Object>>();
        try{
            conectar();
            Statement stmt = connection.createStatement();
            ResultSet r = stmt.executeQuery(cmd);
            ResultSetMetaData m = r.getMetaData();
            while (r.next()) {
                Map<String,Object> obj = new HashMap<String,Object>();
                for(int i=0;i<m.getColumnCount();i++)
                    obj.put(m.getColumnLabel(i+1).toLowerCase(),r.getObject(i+1));
                lista.add(obj);
            }
            r.close();
            stmt.close();
            desconectar();
            return lista;
        }catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public boolean executarUpdate(String cmd)
    {
        try{
            conectar();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(cmd);
            stmt.close();
            desconectar();
            return true;
        }catch (Exception e) {
            logger.error("Connection failure.");
            e.printStackTrace();
            return false;
        }
    }

    public int executarCount(String cmd)
    {
        int resp = -1;
        try{
            conectar();
            Statement stmt = connection.createStatement();
            ResultSet r = stmt.executeQuery(cmd);
            ResultSetMetaData m = r.getMetaData();
            if (r.next())
                resp = r.getInt(1);
            r.close();
            stmt.close();
            desconectar();
            return resp;
        }catch (Exception e) {
            logger.error("Connection failure.");
            e.printStackTrace();
            return -2;
        }
    }

}