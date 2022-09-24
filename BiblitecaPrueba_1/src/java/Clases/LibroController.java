/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Angel Vargas
 */
public class LibroController {
    Libro[] tablaLibro;
    int indiceArray;
    private ConexionBaseDeDatos conectorBD;
    private Connection conexion;
    private PreparedStatement statement = null;
    private ResultSet result = null;
    
    public LibroController(){
        this.tablaLibro = new Libro[100];
        this.indiceArray=0;
    }
    
     public void guardarLibro(Libro libro){
        this.tablaLibro[this.indiceArray]=libro;  
        this.indiceArray=this.indiceArray+1;
        // procedimiento para almacenar en la Base de Datos
    }
    
    public Libro[] getLibros(){
        return this.tablaLibro;
    }
    
    public void abrirConexion(){
        conectorBD= new ConexionBaseDeDatos();
        conexion=conectorBD.conectar();
    }    
   
    
    
    public String guardarLibro2(Libro libro){        
        String sql = "INSERT INTO sitemabiblioteca0.libro(numero_libro, nombre, pasta, editorial, publicacion, genero_idgenero) ";
             sql += " VALUES(?,?,?,?,?,?)";              
       try{     
            abrirConexion();
            statement = conexion.prepareStatement(sql); 
            statement.setInt(1, libro.getCodigo());
            statement.setString(2, libro.getNombre());
            statement.setString(3, libro.getPasta());
            statement.setString(4, libro.getEditorial());
            statement.setString(5, libro.getPublicacion());
            statement.setInt(6, libro.getTipo());
                int resultado = statement.executeUpdate(); 
                if(resultado > 0){
                    return String.valueOf(resultado);
                }else{
                    return String.valueOf(resultado);
                }
        }catch(SQLException e){ 
            return e.getMessage();
        }
    }
    
    public void getLibros2(StringBuffer respuesta){   
        String sql="select * from sitemabiblioteca0.libro";
        try{
        abrirConexion();
        respuesta.setLength(0);       
        statement= conexion.prepareStatement(sql);                        
        result = statement.executeQuery();            
            if (result!=null){
                while (result.next()){
                respuesta.append("<tr>");
                respuesta.append("<td >").append(result.getString("numero_libro")).append("</td>");
                respuesta.append("<td >").append(result.getString("nombre")).append("</td>");
                respuesta.append("<td >").append(result.getString("editorial")).append("</td>");
                respuesta.append("<td >").append(result.getString("publicacion")).append("</td>");
                respuesta.append("<td >").append(result.getString("pasta")).append("</td>");
                respuesta.append("<td id=\"").append(result.getString("numero_libro"))
                        .append("\"  onclick=\"eliminarLibro(this.id);\">") 
                         //.append("\"  onclick=\"eliminarAlumno("+result.getString("numero_carne")+");\">") 
                        .append(" <a class=\"btn btn-warning\"'><i class=\"fas fa-edit\"></i>  </a>"
                                +" <a class=\"btn btn-danger\"'> <i class=\"fas fa-trash-alt\"></i> </a>"
                                + " <td></tr>");
                }
            }else{
                respuesta.append("error al consultar");
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public String eliminarLibro(int carne){        
        String sql = "DELETE FROM libro WHERE numero_libro="+carne;              
       try{     
            abrirConexion();
            statement = conexion.prepareStatement(sql); 
            int resultado = statement.executeUpdate();
            if(resultado > 0){
                return String.valueOf(resultado);
            }else{
                return String.valueOf(resultado);
            }
        }catch(SQLException e){ 
            return e.getMessage();
        }
    }
    
    
}
