package br.com.tech.ControlePonto.controllers;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import br.com.tech.ControlePonto.utils.*;

@RestController
@CrossOrigin(origins = "*")
public class CadastroPJController {
    
    @GetMapping(path = "/test")
    public ResponseEntity<String> getAll()
    {
        return ResponseEntity.ok("teste");
    }

    @GetMapping(path = "/map")
    public ResponseEntity<Map<String, Object>> getmap()
    {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("id",10);
        m.put("Nome","Joao Map");
        return ResponseEntity.ok(m);
    }

    @GetMapping(path = "/json")
    public ResponseEntity<Object> getjson()
    {
        return ResponseEntity.ok(new Object() { public final int id=10;
                                                public final String Nome="Joao Json";});
    }

    @GetMapping(path = "/oi/{nome}")
    public ResponseEntity<Object> getoi(@PathVariable("nome") String nome)
    {
        return ResponseEntity.ok(new Object() { public final int id=10;
                                                public final String Nome=nome;});
    }

    @GetMapping(path = "/msg")
    public ResponseEntity<Object> getmsg(
         @RequestParam(value = "id", defaultValue="10") int aid,
         @RequestParam(value = "nome", defaultValue="mozar") String anome)
    {
        return ResponseEntity.ok(new Object() { public final int id=aid;
                                                public final String Nome=anome;});
    }

    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> getmsg(@RequestBody Map<String, Object> dados)
    {   
        try{
            System.out.println("nome:"+dados.get("nome"));
            System.out.println("id:"+dados.get("id"));
            return ResponseEntity.ok("inserido com sucesso!");
        }catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/list", produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> getList()
    {   
        try{
            Banco bd = new Banco();
            return ResponseEntity.ok(bd.getItens());
        }catch(Exception e)
        {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
