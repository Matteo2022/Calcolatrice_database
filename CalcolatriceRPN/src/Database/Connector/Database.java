package Database.Connector;

import java.sql.*;

public class Database {
    private Connection connessione(){
        final String URL_DB = "jdbc:mysql://localhost:3306/utenti";
        final String USERNAME = "root";
        final String PASSWORD = "";
        Connection con;

        try{
            con = DriverManager.getConnection(URL_DB, USERNAME, PASSWORD);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return con;
    }

    public int registrazione(String nome, String cognome, String username, String password) throws SQLException{
        if(nome.isEmpty() || cognome.isEmpty() || username.isEmpty() || password.isEmpty()){
            return -1;
        }

        Connection con = connessione();
        Statement stm = con.createStatement(); //preparo la query, serve per evitare di inserire valori dannosi per il db
        String sql = "INSERT INTO utenti(nome,cognome,username,password) VALUES " + "(?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,nome);
        ps.setString(2,cognome);
        ps.setString(3,username);
        ps.setString(4,password);
        ps.executeUpdate();

        stm.close();
        con.close();

        return 1;
    }

    public int login(String username, String password){
        if(username.isEmpty() || password.isEmpty()){
            return -1;
        }
        Connection con = connessione();
        try{
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM utenti WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet resultset = ps.executeQuery(); //risultato della query
            if(resultset.next()){   //se ha almeno un valore
                System.out.println("login completato");
                stm.close();
                con.close();
                return 1;
            }else{
                System.out.println("login fallito");
                stm.close();
                con.close();
                return -1;
            }
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("login fallito");
            return -1;
        }
    }
}
