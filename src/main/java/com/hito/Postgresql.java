package com.hito;


import java.io.*;
import java.sql.*;
import com.opencsv.CSVWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Postgresql
{
    public static void main( String[] args ) throws SQLException, IOException {
        //  --  CONEXION CON LA BASE DE DATOS
        // COMPROBAMOS QUE EL CONNECTOR ES RECONOCIDO.
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
        }


        // 3 : USAMOS JDBC CONNECTOR PARA ESTABLECER CONEXION CON LA BASE DE DATOS.
        String conexionbd = "jdbc:postgresql://localhost:5432/coches";
        String user = "postgres";
        String pass = "curso";
        Connection con;
        try {
            con = DriverManager.getConnection(conexionbd, user, pass);
        } catch (SQLException e) {
            System.out.println("No se ha podido establecer la conexión con la base de datos.");
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Se ha establecido la conexión con la base de datos.");








//  --  MENU POR CONSOLA
        Scanner lector = new Scanner(System.in);
        while(!lector.equals("14")) {
            System.out.println("1 : Añadir coche ");
            System.out.println("2 : Mostrar coches ");
            System.out.println("3 : Actualizar coche ");
            System.out.println("4 : Eliminar coche ");
            System.out.println("5 : Añadir propietario ");
            System.out.println("6 : Mostrar propietarios ");
            System.out.println("7 : Actualizar propietario ");
            System.out.println("8 : Eliminar propietario ");
            System.out.println("9 : Exportar los datos a CSV");
            System.out.println("10 : Generar copia de seguridad de COCHES en CSV");
            System.out.println("11 : Generar copia de seguridad de PROPIETARIOS en CSV");
            System.out.println("12 : Importar datos a la tabla COCHES");
            System.out.println("13 : Importar datos a la tabla PROPIETARIOS");
            System.out.println("14 : Cerrar programa ");
            //LA RESPUESTA DEL USUARIO LA ALMACENAMOS EN LA VARIABLE OPCION PARA DESPUES COMPARARLA EN EL SWITCH.
            String opcion = lector.nextLine();
            switch (opcion) {
                case "1":
                    // AÑADIR :
                    try {
                        Statement sentencia = con.createStatement();
                        String insert = "INSERT INTO t_coches(id_coche, marca_coche, modelo_coche, matricula_coche) VALUES (5,'Ford', 'Fiesta', 'AAA0001');";
                        sentencia.executeUpdate(insert);
                        System.out.println("Los coches han sido añadidos");


                    } catch (SQLException e) {
                        System.out.println("Error al añadir los coches");
                        System.out.println(e.getMessage());
                    }
                    break;


                case "2":
                    // LISTADO :
                    try {
                        Statement sentencia = con.createStatement();
                        ResultSet rs = sentencia.executeQuery("SELECT * FROM t_coches;");
                        //PINTAREMOS TANTOS RS.GET COMO COLUMNAS TENGA NUESTRA TABLA.
                        while (rs.next()) {
                            System.out.print(rs.getInt(1));
                            System.out.println(" - ");
                            System.out.print(rs.getString(2));
                            System.out.println(" - ");
                            System.out.print(rs.getString(3));
                            System.out.println(" - ");
                            System.out.print(rs.getString(4));
                            System.out.println(); // Retorno de carro
                        }
                        System.out.println("Todos los coches han sido mostrados");
                    } catch (SQLException e) {
                        System.out.println("No se ha podido mostrar los coches");
                        System.out.println(e.getMessage());
                    }
                    break;

                case "3":
                    //MODIFICAR :
                    try {
                        Statement sentencia = con.createStatement();
                        String updatequery = "UPDATE t_coches SET marca_coche = 'Mercedes', modelo_coche= 'AMG-GT', matricula_coche = 'CSJ4181' WHERE id_coche = 3;";
                        sentencia.executeUpdate(updatequery);
                        System.out.println("El coche se ha actualizado con exito");
                    } catch (SQLException e) {
                        System.out.println("No se ha podido mostrar los coches");
                        System.out.println(e.getMessage());
                    }
                    break;

                case "4":
                    //ELIMINAR COCHE
                    try{
                        Statement stmt = con.createStatement();
                        stmt.execute("DELETE FROM t_coches AS c WHERE c.id_coche = 3");
                        System.out.println("El coche ha sido eliminado correctamente");
                    }catch(SQLException e){
                        System.out.println("No se ha podido eliminar el coche.");
                        System.out.println(e.getMessage());
                    }
                    break;



                //  --  PROPIETARIO
                case "5":
                    // AÑADIR PROPIETARIO:
                    try {
                        Statement sentencia = con.createStatement();
                        String insert = "INSERT INTO t_propietario(id_propietario, nombre_propietario, telefono_propietario) VALUES (1, 'Sergio', '695972014');";
                        sentencia.executeUpdate(insert);
                        System.out.println("El propietario ha sido añadido");


                    } catch (SQLException e) {
                        System.out.println("Error al añadir al propietario");
                        System.out.println(e.getMessage());
                    }
                    break;



                case "6":
                    // LISTADO PROPIETARIOS:
                    try {
                        Statement sentenciaProp = con.createStatement();
                        ResultSet rsProp = sentenciaProp.executeQuery("SELECT * FROM t_propietario;");
                        //PINTAREMOS TANTOS RS.GET COMO COLUMNAS TENGA NUESTRA TABLA.
                        while (rsProp.next()) {
                            System.out.print(rsProp.getInt(1));
                            System.out.println(" - ");
                            System.out.print(rsProp.getString(2));
                            System.out.println(" - ");
                            System.out.print(rsProp.getString(3));
                            System.out.println(" - ");
                            System.out.print(rsProp.getInt(4));
                            System.out.println(); // Retorno de carro
                        }
                        System.out.println("Todos los propietarios han sido mostrados");
                    } catch (SQLException e) {
                        System.out.println("No se ha podido mostrar los propietarios");
                        System.out.println(e.getMessage());
                    }
                    break;



                case "7":
                    //MODIFICAR PROPIETARIOS:
                    try {
                        Statement sentenciaProp = con.createStatement();
                        String updatequery = "UPDATE t_propietario SET nombre_propietario = 'Adrian', telefono_propietario= '666114422', idcoche_propietario = 5 WHERE id_propietario = 2;";
                        sentenciaProp.executeUpdate(updatequery);
                        System.out.println("El propietario ha sido actualizado");
                    } catch (SQLException e) {
                        System.out.println("No se ha podido actualizar");
                        System.out.println(e.getMessage());
                    }
                    break;


                case "8":
                    //ELIMINAR PROPIETARIOS
                    try{
                        Statement stmtProp = con.createStatement();
                        stmtProp.execute("DELETE FROM t_propietario AS p WHERE p.id_propietario = 2");
                        System.out.println("Se ha eliminado al propietario");
                    }catch(SQLException e){
                        System.out.println("No se pudo eliminar al propietario");
                        System.out.println(e.getMessage());
                    }
                    break;





                case "9":
                    //EXPORTAR DATOS EN CSV.
                    try{
                        //CREAMOS EL FICHERO DONDE GUARDAREMOS LOS DATOS.
                        PrintWriter pw= new PrintWriter(new File("datos_MySQL.csv"));
                        //UTILIZAMOS UN OBJETO StringBuilder PARA ALMACENAR CADENAS DE TEXTO.
                        StringBuilder sb=new StringBuilder();

                        //SENTENCIA SELECT DONDE ALMACENARE LOS DATOS EN MEMORIA.
                        ResultSet rs2=null;
                        String query="select * from t_coches";
                        PreparedStatement ps=con.prepareStatement(query);
                        rs2=ps.executeQuery();

                        //MIENTRAS EL RESULTSET TENGA UN SIGUIENTE OBJETO CREARA UN "HIJO" CON STRINGBUILDER.
                        while(rs2.next()){
                            sb.append(rs2.getInt(1));
                            sb.append(",");
                            sb.append(rs2.getString(2));
                            sb.append(",");
                            sb.append(rs2.getString(3));
                            sb.append(",");
                            sb.append(rs2.getString(4));
                            //HACEMOS UN SALTO DE LINEA POR CADA OBJETO QUE PINTEMOS.
                            sb.append("\r\n");
                        }
                        System.out.println("Los coches han sido almacenados.");







                        //SENTENCIA SELECT DONDE ALMACENARE LOS DATOS EN MEMORIA.
                        ResultSet rsP=null;
                        String queryP="select * from t_propietario";
                        PreparedStatement psP=con.prepareStatement(queryP);
                        rsP=psP.executeQuery();

                        //MIENTRAS EL RESULTSET TENGA UN SIGUIENTE OBJETO CREARA UN "HIJO" CON STRINGBUILDER.
                        while(rsP.next()){
                            sb.append(rsP.getInt(1));
                            sb.append(",");
                            sb.append(rsP.getString(2));
                            sb.append(",");
                            sb.append(rsP.getString(3));
                            sb.append(",");
                            sb.append(rsP.getInt(4));
                            //HACEMOS UN SALTO DE LINEA POR CADA OBJETO QUE PINTEMOS.
                            sb.append("\r\n");
                        }

                        System.out.println("Los propietarios han sido almacenados");


                        //GUARDAMOS LOS DATOS DEL STRINGBUILDER COMO UN STRING.
                        pw.write(sb.toString());
                        //CERRAMOS EL PRINTWRITER.
                        pw.close();
                        System.out.println("Los datos se han almacenado correctamente");
                    } catch (Exception e) {
                        System.out.println("Error al guardar los datos");
                        System.out.println(e.getMessage());
                    }
                    break;


                case "10":
                    try {
                        //COPIA DE SEGURIDAD.
                        //CREAMOS EL FICHERO DONDE GUARDAREMOS LOS DATOS.
                        PrintWriter pw= new PrintWriter(new File("copiaSeguridad_Postgre_COCHES.csv"));
                        //UTILIZAMOS UN OBJETO StringBuilder PARA ALMACENAR CADENAS DE TEXTO.
                        StringBuilder sb=new StringBuilder();

                        //SENTENCIA SELECT DONDE ALMACENARE LOS DATOS EN MEMORIA.
                        ResultSet rs2=null;
                        String query="select * from t_coches";
                        PreparedStatement ps=con.prepareStatement(query);
                        rs2=ps.executeQuery();

                        //MIENTRAS EL RESULTSET TENGA UN SIGUIENTE OBJETO CREARA UN "HIJO" CON STRINGBUILDER.
                        while(rs2.next()){
                            sb.append(rs2.getInt(1));
                            sb.append(",");
                            sb.append(rs2.getString(2));
                            sb.append(",");
                            sb.append(rs2.getString(3));
                            sb.append(",");
                            sb.append(rs2.getString(4));
                            //HACEMOS UN SALTO DE LINEA POR CADA OBJETO QUE PINTEMOS.
                            sb.append("\r\n");
                        }
                        System.out.println("Los coches han sido almacenados.");
                        //GUARDAMOS LOS DATOS DEL STRINGBUILDER COMO UN STRING.
                        pw.write(sb.toString());
                        //CERRAMOS EL PRINTWRITER.
                        pw.close();
                        System.out.println("La copia de seguridad de Coches se ha generado con exito");

                    } catch (Exception e) {
                        System.out.println("Error al generar copia de seguridad");
                        System.out.println(e.getMessage());
                    }
                    break;

                case "11":
                    try{
                        //COPIA DE SEGURIDAD.
                        //CREAMOS EL FICHERO DONDE GUARDAREMOS LOS DATOS.
                        PrintWriter pw= new PrintWriter(new File("copiaSeguridad_Postgre_PROPIETARIO.csv"));
                        //UTILIZAMOS UN OBJETO StringBuilder PARA ALMACENAR CADENAS DE TEXTO.
                        StringBuilder sb=new StringBuilder();

                        //SENTENCIA SELECT DONDE ALMACENARE LOS DATOS EN MEMORIA.
                        ResultSet rsP=null;
                        String queryP="select * from t_propietario";
                        PreparedStatement psP=con.prepareStatement(queryP);
                        rsP=psP.executeQuery();

                        //MIENTRAS EL RESULTSET TENGA UN SIGUIENTE OBJETO CREARA UN "HIJO" CON STRINGBUILDER.
                        while(rsP.next()){
                            sb.append(rsP.getInt(1));
                            sb.append(",");
                            sb.append(rsP.getString(2));
                            sb.append(",");
                            sb.append(rsP.getString(3));
                            sb.append(",");
                            sb.append(rsP.getInt(4));
                            //HACEMOS UN SALTO DE LINEA POR CADA OBJETO QUE PINTEMOS.
                            sb.append("\r\n");
                        }
                        System.out.println("Los propietarios han sido almacenados");
                        //GUARDAMOS LOS DATOS DEL STRINGBUILDER COMO UN STRING.
                        pw.write(sb.toString());
                        //CERRAMOS EL PRINTWRITER.
                        pw.close();
                        System.out.println("La copia de seguridad de Propietarios se realizo con exito");

                    } catch (Exception e) {
                        System.out.println("Error al generar copia de seguridad");
                        System.out.println(e.getMessage());
                    }
                    break;


                case "12":
                    try{
                        con = DriverManager.getConnection(conexionbd, user, pass);
                        con.setAutoCommit(false);

                        String csvFilePath = "copiaSeguridad_Postgre_COCHES.csv";

                        int batchSize = 20;

                        String sql = "INSERT INTO t_coches (id_coche, marca_coche, modelo_coche, matricula_coche) VALUES (?, ?, ?, ?)";
                        PreparedStatement statement = con.prepareStatement(sql);

                        BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
                        String lineText = null;

                        int count = 0;

                        lineReader.readLine(); // skip header line

                        while ((lineText = lineReader.readLine()) != null) {
                            String[] data = lineText.split(",");
                            String id_coche = data[0];
                            String marca_coche = data[1];
                            String modelo_coche = data[2];
                            String matricula_coche = data[3];

                            int Intid_coche = Integer.parseInt(id_coche);
                            statement.setInt(1, Intid_coche);
                            statement.setString(2, marca_coche);
                            statement.setString(3, modelo_coche);
                            statement.setString(4, matricula_coche);


                            statement.addBatch();

                            if (count % batchSize == 0) {
                                statement.executeBatch();
                            }
                        }

                        lineReader.close();

                        // execute the remaining queries
                        statement.executeBatch();
                        con.commit();
                        System.out.println("Los coches han sido importados a la base de datos con exito");

                    } catch (IOException ex) {
                        System.out.println("Error al importar los datos a la base de datos");
                        System.err.println(ex);
                    }
                    break;


                case "13":
                    try{
                        con = DriverManager.getConnection(conexionbd, user, pass);
                        con.setAutoCommit(false);

                        String csvFilePath2 = "copiaSeguridad_Postgre_PROPIETARIO.csv";

                        int batchSize = 20;

                        String sql = "INSERT INTO t_propietario (id_propietario, nombre_propietario, telefono_propietario, idcoche_propietario) VALUES (?, ?, ?, ?)";
                        PreparedStatement statement2 = con.prepareStatement(sql);

                        BufferedReader lineReader2 = new BufferedReader(new FileReader(csvFilePath2));
                        String lineText = null;

                        int count = 0;

                        lineReader2.readLine(); // skip header line

                        while ((lineText = lineReader2.readLine()) != null) {
                            String[] data = lineText.split(",");
                            String id_propietario = data[0];
                            String nombre_propietario = data[1];
                            String telefono_propietario = data[2];
                            String idcoche_propietario = data[3];

                            int Intid_coche = Integer.parseInt(id_propietario);
                            statement2.setInt(1, Intid_coche);
                            statement2.setString(2, nombre_propietario);
                            statement2.setString(3, telefono_propietario);
                            int Intidcoche_propietario = Integer.parseInt(idcoche_propietario);
                            statement2.setInt(4, Intidcoche_propietario);

                            statement2.addBatch();

                            if (count % batchSize == 0) {
                                statement2.executeBatch();
                            }
                        }

                        lineReader2.close();

                        // execute the remaining queries
                        statement2.executeBatch();
                        con.commit();
                        System.out.println("Los propietarios han sido importados a la base de datos con exito");
                    } catch (IOException ex) {
                        System.out.println("Error al importar los datos a la base de datos");
                        System.err.println(ex);
                    }
                    break;



                case "14":
                    //CERRAR LA CONEXION CON LA BASE DE DATOS Y SALIR DEL MENU
                    try {
                        con.close();
                    } catch (SQLException e) {
                        System.out.println("No se ha podido cerrar la conexión con la BD");
                        System.out.println(e.getMessage());
                        return;
                    }
                    System.out.println("Se ha cerrado la base de datos");
                    break;


                default:
                    System.out.println("Numero incorrecto");
                    break;
            }
        }//CIERRA SWITCH
    }//CIERRA MAIN
}//CIERRA CLASS
