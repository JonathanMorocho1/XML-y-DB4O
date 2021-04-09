/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlejemplo;

/**
 *
 * @author ANDRES
 */
public class Docente {
    //private int id, password;
    private String id, Nombre, username, password;

    public Docente(String id, String password, String Nombre, String username) {
        this.id = id;
        this.password = password;
        this.Nombre = Nombre;
        this.username = username;
    }

    public Docente() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
