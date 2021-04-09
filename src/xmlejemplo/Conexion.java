/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlejemplo;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import java.io.File;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ANDRES
 */
public class Conexion {
    private ObjectContainer oc;
    
    private void open(){
        this.oc = Db4o.openFile("BaseDeDatos.yap");
    }
    
    public boolean Insertar(Docente objeto){
        try {
            this.open();
            oc.set(objeto);
            this.oc.close();
            return true;
        } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
            System.out.println("bdoo.ControladorPersona.guardarPersona() :");
            return false;
        }
    }
    
    public Docente guardarDocente(String id, String password, String Nombre, String username){
        Docente docentes = new Docente(id, password, Nombre, username);
        return docentes;
    }
    
//OTRA CLASE BD CON UNA VENTANA QUE PODAMOS HACER INSERTAR ELIMINAR CREAR Y MOSTRAR EN UNA TABALA TRAYENDO A TODOS LOS OBJETOS
    
    public Docente buscarPersona(Docente objeto){
        this.open();
        Docente encontrado = null;
        ObjectSet resultados = this.oc.get(objeto);
        
        if(resultados.hasNext()){
            encontrado =(Docente) resultados.next();
        }
        this.oc.close();
        return encontrado;
    }
    
    public boolean Eliminar(Docente objeto) {
        try {
            //CONSULTAMOS LOS OBJETOS ALMACENADOS EN LA BASE DE DATOS Y SI EXISTE UNA COINCIDENCIA LA ELIMINAMOS            
            this.open();
            ObjectSet resultados = this.oc.get(objeto);
            if (resultados.size() > 0) {
                Docente persona = (Docente) resultados.next();
                this.oc.delete(persona);
                this.oc.close();
                return true;
            } else {
                this.oc.close();
                return false;
            }
        } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
            System.out.println("ejemplodb4.ControladorPersona.guardarPersona() : " + e);
            return false;
        }
    }
    public Docente[] Consultar(Docente objeto) {
        try {
            //CONSULTAMOS LOS OBJETOS ALMACENADOS EN LA BASE DE DATOS Y LOS RETORNAMOS EN UN ARREGLO DE TIPO Persona
            Docente[] personas = null;
            this.open();
            ObjectSet resultados = this.oc.get(objeto);
            int i = 0;
            if (resultados.hasNext()) {
                personas = new Docente[resultados.size()];
                while (resultados.hasNext()) {
                    personas[i] = (Docente) resultados.next();
                    i++;
                }
            }
            this.oc.close();
            return personas;
        } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
            System.out.println("ejemplodb4.ControladorPersona.guardarPersona() : " + e);
            return null;
        }
    }
    
    public boolean ActualizarPersona(Docente objeto) {
        try {
            //BUSCAMOS SI EXISTE EL OBJETO, SI ES ASÍ LO ACTUALIZAMOS EN LA BASE DE DATOS
            this.open();
            ObjectSet resultados = this.oc.get(new Docente(objeto.getId(), null, null, null));
            if (resultados.size() > 0) {                
                Docente resultado = (Docente) resultados.next();
                resultado.setNombre(objeto.getNombre());
                resultado.setPassword(objeto.getPassword());
                resultado.setUsername(objeto.getUsername());
                this.oc.set(resultado);
                this.oc.close();
                return true;
            } else {
                this.oc.close();
                return false;
            }
        } catch (DatabaseClosedException | DatabaseReadOnlyException e) {
            System.out.println("ejemplodb4.ControladorPersona.actualizarPersona() : " + e);
            return false;
        }
    }
        
    
    public Docente[] leer(){
         Docente[] docentes = new Docente[10];
     try{
        Docente docentexml = null;
        File archivo = new File("datos.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(archivo);
        d.getDocumentElement().normalize();
        System.out.println("elemento primcipal: "+d.getDocumentElement().getNodeName());
        //Cargando todos los docentes en una coleccion de tipo nodo
        NodeList listaDocentes = d.getElementsByTagName("docente");
            for (int i = 0; i < listaDocentes.getLength(); i++) {
                Node nodo = listaDocentes.item(i);
                System.out.println("Docente: "+nodo.getNodeName());
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)nodo;
                    String id = element.getAttribute("id");
                    String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                    String username = element.getElementsByTagName("username").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();
                    
                    docentexml = new Docente(id, password, nombre, username);
                    docentes[i]=docentexml;
                    //controlador.insertarDocente(id, nombre,username, password);
                   /* Docente docente = new Docente();
                    if (docente!=null) {
                        controlador.insertarDocente(id, nombre, username, password);
                    }*/
                    
                    System.out.println("id: "+element.getAttribute("id"));
                    System.out.println("Nombre: "+element.getElementsByTagName("nombre").item(0).getTextContent());
                    System.out.println("Username: "+element.getElementsByTagName("username").item(0).getTextContent());
                    System.out.println("Password: "+element.getElementsByTagName("password").item(0).getTextContent());
                    Insertar(docentexml);
                }
            }

        }catch(Exception e){
            e.printStackTrace();     
        }
    return docentes;
    }
    
    /*public Docente[] comprobar(){
        Docente s1 = new Docente(); s1.getId() = "cc"; s1.getNombre() = "kk"; s1.getPassword()="aa"; s1.getUsername()="bb"; Conexion().save(s1);
        Docente s2 = new Docente(); s2.getId() = "cc"; s2.getNombre() = "kk"; s2.getPassword()="aa"; s2.getUsername()="bb"; Conexion().save(s2); 
    }*/

    
}
