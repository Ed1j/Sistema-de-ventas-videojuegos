package com.juegos;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {

    @FXML ComboBox <String> cboxFiltro;
    @FXML ComboBox <String> cboxPlataforma;
    @FXML ListView <String> ListView_ListaJuegos;
    
    @FXML TextField Titulo_Seleccion;
    @FXML TextField Titulo_Actualizar;
    @FXML TextField Anio_Actualizar;
    @FXML TextField Desarrollador_Actualizar;
    @FXML TextField Codigo_Actualizar;
    @FXML TextField Plataforma_Actualizar;
    @FXML TextArea Resena_Actualizar;
//Agregar datos
    @FXML TextField txtfield_Titulo;
    @FXML TextField txtfield_Anio;
    @FXML TextField txtfield_Desarrollador;
    @FXML TextField txtfield_Codigo;
    @FXML TextArea txtfield_Resena;
//Eliminar videojuego
    @FXML TextField txtfield_eliminar;    
   /*  @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    } */

    @FXML
    private void cboxFiltro_Change(){//metodo que lee la opcion del año seleccionado y mete datos a lista juegos

        ListView_ListaJuegos.getItems().clear();
        String sel= cboxFiltro.getValue();

        //conexion a BD
        Connection cnx=null;
        String base="videojuegos";
        String srv="127.0.0.1";
        String usr="root";
        String pass="toor";
        
        String cnxstr="jdbc:mysql://"+srv+"/"+base+"?useSSL=false";
        String drvclass="com.mysql.cj.jdbc.Driver";
        
        try{
            //intentar caragr el driver
            Class.forName(drvclass);
            cnx=DriverManager.getConnection(cnxstr, usr, pass);
            System.out.println("Conexion con BD establecida...");
        
        }catch(Exception ex){
            System.err.println("Error en la carga del driver: "+ex.getMessage());
        
        }


        //seleccionar datos
        ArrayList<String>regs=new ArrayList<String>();
        try{
            String query="select titulo from juego where plataforma = '"+sel+"';";
            Statement cmd= cnx.createStatement();
            ResultSet res=cmd.executeQuery(query);

            while(res.next()){
                regs.add(res.getString("titulo"));

            }
            res.close();
            cmd.close();
            cnx.close();

        }catch(SQLException ex){
            System.err.println("Error en el query: "+ex.getMessage());
        }


        ListView_ListaJuegos.getItems().addAll(regs);

    }

//Boton para eliminar videojuego    
    @FXML
    private void btnEliminar_click(){
        String mensajeUser = "";
        
        //conexion a BD
        Connection cnx=null;
        String base="videojuegos";
        String srv="127.0.0.1";
        String usr="root";
        String pass="toor";
          
        String cnxstr="jdbc:mysql://"+srv+"/"+base+"?useSSL=false";
        String drvclass="com.mysql.cj.jdbc.Driver";
        if(!validarTitulo(txtfield_eliminar.getText().trim())){
            if(txtfield_eliminar.getText() == null || txtfield_eliminar.getText().isEmpty()){
                mensajeUser += "Por favor escriba el nombre del videojuego a borrar.";
            }else{
                mensajeUser += "Error de sintaxis; Introduzca letras y numeros solamente.";
            }
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ELIMINAR: Mensaje del Sistema");
            alert.setHeaderText(null);
            alert.setContentText(mensajeUser);
            alert.showAndWait();
        }else{
            try{
                //intentar caragr el driver
                Class.forName(drvclass);
                cnx=DriverManager.getConnection(cnxstr, usr, pass);
                System.out.println("Conexion con BD establecida...");
              
                PreparedStatement ps = cnx.prepareStatement("delete from juego where titulo = ? ");
                ps.setString(1, txtfield_eliminar.getText());
                ListView_ListaJuegos.getItems().remove( txtfield_eliminar.getText());
    
                ps.executeUpdate();
                ps.close();
                cnx.close();
            }catch(Exception ex){
                System.err.println("Error en la carga del driver: "+ex.getMessage());
            }
            txtfield_eliminar.setText("");
        } 
    }

   
    //VALIDACIONES DE EXPRESIONES REGULARES
    public static boolean validarAnio(String datos){
        if(datos.matches("[0-9]{4}")){
            int num = Integer.parseInt(datos);
            LocalDate current_date = LocalDate.now();
            int current_Year = current_date.getYear();
            if(num <= current_Year){
                return true;
            }
        }
        return false;  //valida que sean numeros y que sean 4 numeros
    }
    public static boolean validarCodigo(String datos){
        return datos.matches("[0-9]{10}"); //valida que sean numeros y que sean 10 numeros
    }
    public static boolean validarTitulo(String datos){
        return datos.matches("^[\\p{L} ,|.'-A-Za-z0-9\\s]+$");
    }

    public static boolean validarCampoTexto(String datos){
        return datos.matches("^[\\p{L} ,|.'-A-Za-z0-9\\s]+$");
    }

//Boton para Agregar juego
    @FXML
    private void btnAgregar_click(){
        int contador = 0;
        String mensajeUser = "";
    //Validacion de datos
        if (!validarTitulo(txtfield_Titulo.getText().trim())) {//Validá el titulo de agregar
            if(txtfield_Titulo.getText() == null || txtfield_Titulo.getText().isEmpty()){
                mensajeUser += "Titulo: Por favor, llene el campo.\n";
            }else{
                mensajeUser += "Titulo: Introduzca letras y numeros solamente.\n";
            }
            contador++;
        }
        if (!validarAnio(txtfield_Anio.getText().trim())){ //Validá el año de agregar
            if(txtfield_Anio.getText() == null || txtfield_Anio.getText().isEmpty()){
                mensajeUser += "Año: Por favor, llene el campo.\n";
            }else{
                mensajeUser += "Año: Favor de introducir bien el año.\n";
            }
            contador++;
        }
        if(!validarCampoTexto(txtfield_Desarrollador.getText().trim())){
            if(txtfield_Desarrollador.getText() == null || txtfield_Desarrollador.getText().isEmpty()){
                mensajeUser += "Desarrollador: Por favor, llene el campo.\n";
            }else{
                mensajeUser += "Desarrollador: Introduzca solamente texto y sin saltos de linea.\n"; 
            }
            contador++;
        }
        if (!validarCodigo(txtfield_Codigo.getText().trim())) {//Validá el codigo del videojuego de agregar
            if(txtfield_Codigo.getText() == null || txtfield_Codigo.getText().isEmpty()){
                mensajeUser += "Codigo del videojuego: Por favor, llene el campo.\n";
            }else{
                mensajeUser += "Codigo del videojuego: Se requiere 10 numeros consecutivos.\n"; 
            }
            contador++;
        }
        if(cboxPlataforma.getValue() == null || cboxPlataforma.getValue().isEmpty()){
            mensajeUser += "Plataforma: Por favor seleccione una opcion.\n";
            contador++;
        }
        if (!validarCampoTexto(txtfield_Resena.getText().trim())) {//Validá la resenia de agregar
            if(txtfield_Resena.getText() == null || txtfield_Resena.getText().isEmpty()){
                mensajeUser += "Reseña: Por favor, llene el campo.\n";
            }else{
                mensajeUser += "Reseña: Introduzca solamente texto y sin salto de linea.\n"; 
            }
            contador++;
        }     
        if(contador > 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("AGREGAR: Mensaje(s) del Sistema");
            alert.setHeaderText(null);
            alert.setContentText(mensajeUser);
            alert.showAndWait();
        }else{ 
            //conexion a BD
            Connection cnx=null;
            String base="videojuegos";
            String srv="127.0.0.1";
            String usr="root";
            String pass="toor";
            
            String cnxstr="jdbc:mysql://"+srv+"/"+base+"?useSSL=false";
            String drvclass="com.mysql.cj.jdbc.Driver";

            try{
                //intentar caragr el driver
                Class.forName(drvclass);
                cnx=DriverManager.getConnection(cnxstr, usr, pass);
                System.out.println("Conexion con BD establecida...");
            
                PreparedStatement ps = cnx.prepareStatement("insert into juego (titulo, anio,desarrollador,codigo_juego,plataforma,resenia) values(?, ?, ?, ?, ?, ?)");
                ps.setString(1, txtfield_Titulo.getText());
                ps.setString(2, txtfield_Anio.getText());
                ps.setString(3, txtfield_Desarrollador.getText());
                ps.setString(4, txtfield_Codigo.getText());
                ps.setString(5, cboxPlataforma.getValue().toString());
                ps.setString(6, txtfield_Resena.getText());
                
                ListView_ListaJuegos.getItems().addAll();

                ps.executeUpdate();
                ps.close();
                cnx.close();

            }catch(Exception ex){
                System.err.println("Error en la carga del driver: "+ex.getMessage());
            }
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("¡VIDEOJUEGO AGREGADO!");
            alert.setHeaderText(null);
            alert.setContentText("El videjuego '"+txtfield_Titulo.getText()+"' se agregó con éxito.");
            alert.showAndWait();
            txtfield_Titulo.setText("");
            txtfield_Anio.setText("");
            txtfield_Desarrollador.setText("");
            txtfield_Codigo.setText("");
            txtfield_Resena.setText("");
        }
    }
    

    @FXML
    private void btnAyuda_click()throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("secondary.fxml"));
        Parent root = loader.load();
        Scene scene =new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Otro formulario");
        stage.setScene(scene);
        stage.show();  
    }


    @FXML
    private void btnVerTodo_click(){//metodo que permite ver todos los juegos
        ListView_ListaJuegos.getItems().clear();

        //conexion a BD
        Connection cnx=null;
        String base="videojuegos";
        String srv="127.0.0.1";
        String usr="root";
        String pass="toor";
        
        String cnxstr="jdbc:mysql://"+srv+"/"+base+"?useSSL=false";
        String drvclass="com.mysql.cj.jdbc.Driver";
        
        try{
            //intentar caragr el driver
            Class.forName(drvclass);
            cnx=DriverManager.getConnection(cnxstr, usr, pass);
            System.out.println("Conexion con BD establecida...");
        
        }catch(Exception ex){
            System.err.println("Error en la carga del driver: "+ex.getMessage());
        
        }


        //seleccionar datos
        ArrayList<String>regs=new ArrayList<String>();
        try{
            String query="select distinct(titulo) from juego";
            Statement cmd= cnx.createStatement();
            ResultSet res=cmd.executeQuery(query);

            while(res.next()){
                regs.add(res.getString("titulo"));

            }
            res.close();
            cmd.close();
            cnx.close();

        }catch(SQLException ex){
            System.err.println("Error en el query: "+ex.getMessage());
        }


        ListView_ListaJuegos.getItems().addAll(regs);
    }


    @FXML
    private void btnDetalles_click(){//metodo que llena los campos de detalles
        String sel = Titulo_Seleccion.getText();
        String mensajeUser ="";
        //conexion a BD
        Connection cnx=null;
        String base="videojuegos";
        String srv="127.0.0.1";
        String usr="root";
        String pass="toor";
        
        String cnxstr="jdbc:mysql://"+srv+"/"+base+"?useSSL=false";
        String drvclass="com.mysql.cj.jdbc.Driver";
        if(!validarTitulo(Titulo_Seleccion.getText().trim())){
            if(Titulo_Seleccion.getText() == null || Titulo_Seleccion.getText().isEmpty()){
                mensajeUser += "Por favor escriba en el campo el nombre del titulo del videojuego para ver detalles.\nTambién puede seleccionar una opcion que esta en el recuadro de la izquierda, si no logra visualizarlos, pulse en 'Ver todo'.";
            }else{
                mensajeUser += "Error de sintaxis; Introduzca letras y numeros solamente.";
            }
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("VER DETALLES: Mensaje del Sistema");
            alert.setHeaderText(null);
            alert.setContentText(mensajeUser);
            alert.showAndWait();
        }else{
            try{
                //intentar caragr el driver
                Class.forName(drvclass);
                cnx=DriverManager.getConnection(cnxstr, usr, pass);
                System.out.println("Conexion con BD establecida...");
            
            }catch(Exception ex){
                System.err.println("Error en la carga del driver: "+ex.getMessage());
            
            }
            //seleccionar datos
            /*titulo,desarrolladora,reseña,plataforma,codigo del juego*/
            try{
                String query="select * from juego where titulo = '"+sel+"';";
                Statement cmd= cnx.createStatement();
                ResultSet res=cmd.executeQuery(query);
    
                while(res.next()){
                    Titulo_Actualizar.setText(res.getString("titulo"));
                    Anio_Actualizar.setText(res.getString("anio"));
                    Desarrollador_Actualizar.setText(res.getString("desarrollador"));
                    Codigo_Actualizar.setText(res.getString("codigo_juego"));
                    Plataforma_Actualizar.setText(res.getString("plataforma"));
                    Resena_Actualizar.setText(res.getString("resenia"));
    
                }
                res.close();
                cmd.close();
                cnx.close();
    
            }catch(SQLException ex){
                System.err.println("Error en el query: "+ex.getMessage());
            }
        }
    }



    @FXML
    private void btnActualizar_click(){//boton para actualziar la reseña de los juegos
        String sel= Codigo_Actualizar.getText();//usamos el codidgo del juego para buscarlo en la base
        String leer= Resena_Actualizar.getText();
        String mensajeUser = "";
        //conexion a BD
        Connection cnx=null;
        String base="videojuegos";
        String srv="127.0.0.1";
        String usr="root";
        String pass="toor";
        
        String cnxstr="jdbc:mysql://"+srv+"/"+base+"?useSSL=false";
        String drvclass="com.mysql.cj.jdbc.Driver";
        if (!validarCampoTexto(Resena_Actualizar.getText().trim())) {//Validá la resenia de agregar
            if(Resena_Actualizar.getText() == null || Resena_Actualizar.getText().isEmpty()){
                mensajeUser += "El campo reseña esta vacio, favor de llenar dicho campo.\n";
            }else{
                mensajeUser += "La sintaxis del campo de reseña esta incorrecto, introduzca solamente texto y sin salto de linea.\n"; 
            }
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Actualizar: Mensaje del Sistema");
            alert.setHeaderText(null);
            alert.setContentText(mensajeUser);
            alert.showAndWait();
        }else{
            try{
                //intentar caragr el driver
                Class.forName(drvclass);
                cnx=DriverManager.getConnection(cnxstr, usr, pass);
                System.out.println("Conexion con BD establecida...");
            }catch(Exception ex){
                System.err.println("Error en la carga del driver: "+ex.getMessage());
    
            }
            //seleccionar datos
            try{
                String query="update juego set resenia = '"+leer+"' where codigo_juego = '"+sel+"';";
                Statement cmd= cnx.createStatement();
                //ResultSet res=cmd.executeQuery(query);
                cmd.executeUpdate(query);
                //res.close();
                cmd.close();
                cnx.close();
            }catch(SQLException ex){
                System.err.println("Error en el query: "+ex.getMessage());
            }
            Titulo_Actualizar.setText("");
            Anio_Actualizar.setText("");
            Desarrollador_Actualizar.setText("");
            Codigo_Actualizar.setText("");
            Plataforma_Actualizar.setText("");
            Resena_Actualizar.setText("");
        }
    }


    private void list_juegos_itemSelected(String value){//metodo que inserta el lbro seleccionado a la casilla libro seleccionado
        this.Titulo_Seleccion.setText(value);;

    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {//metodo que inicializa la lista
 
        //inicializa componentes
        this.ListView_ListaJuegos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                list_juegos_itemSelected(newValue);
                
            }
            
        });


        //conexion a BD
        Connection cnx=null;
        String base="videojuegos";
        String srv="127.0.0.1";
        String usr="root";
        String pass="toor";
        
        String cnxstr="jdbc:mysql://"+srv+"/"+base+"?useSSL=false";
        String drvclass="com.mysql.cj.jdbc.Driver";
        
        try{
            //intentar caragr el driver
            Class.forName(drvclass);
            cnx=DriverManager.getConnection(cnxstr, usr, pass);
            System.out.println("Conexion con BD establecida...");
        
        }catch(Exception ex){
            System.err.println("Error en la carga del driver: "+ex.getMessage());
        
        }


        //seleccionar datos
        ArrayList<String>lstplata=new ArrayList<String>();
        try{
            String query="Select distinct(plataforma) from juego;";
            Statement cmd= cnx.createStatement();
            ResultSet res=cmd.executeQuery(query);

            while(res.next()){
                lstplata.add(res.getString("plataforma"));

            }
            res.close();
            cmd.close();
            cnx.close();

        }catch(SQLException ex){
            System.err.println("Error en el query: "+ex.getMessage());
        }

        //cargar datos
        this.cboxFiltro.getItems().addAll(lstplata);
        //En la parte de agregar juego, mostrara la plataforma en el ComboBox
        this.cboxPlataforma.getItems().addAll(lstplata);
        
    }



}
