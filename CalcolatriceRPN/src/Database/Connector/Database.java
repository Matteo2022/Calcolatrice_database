package Database.Connector;

import java.sql.*;

public class Database {
    public Utente getUtente() {
        return utente;
    }

    public static Utente utente;    //salvo l'utente corrente, static perchè così quando creo oggetti database in altre classe utente non va a null e tutti avranno lo stesso utente

    private Connection connessione(){
        final String URL_DB = "jdbc:mysql://localhost:3306/utenti";
        final String USERNAME = "root";
        final String PASSWORD = "";
        Connection con;     //oggetto che permette di creare la connesione

        try{
            con = DriverManager.getConnection(URL_DB, USERNAME, PASSWORD);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return con;
    }

    public int registrazione(String nome, String cognome, String username, String password) throws SQLException{
        if(nome.isEmpty() || cognome.isEmpty() || username.isEmpty() || password.isEmpty()){
            return -1;  //errore
        }

        Connection con = connessione();
        Statement stm = con.createStatement(); //preparo la query, serve per evitare di inserire valori dannosi per il db
        String sql = "INSERT INTO utenti(nome,cognome,username,password) VALUES " + "(?,?,?,?)";    //query per inserire dati nella tabella del db

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,nome);
        ps.setString(2,cognome);
        ps.setString(3,username);
        ps.setString(4,password);
        ps.executeUpdate();

        utente = new Utente(nome, cognome, username, password);

        stm.close();    //chiudo lo statement
        con.close();    //chiudo la connessione al db

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
                utente = new Utente(resultset.getString("nome"),resultset.getString("cognome"),resultset.getString("username"), resultset.getString("password")); //prendo i valori dalle colonne e creo nuovo utente
                System.out.println("login completato");
                stm.close();
                con.close();
                return 1;   //ok
            }else{
                System.out.println("login fallito");
                stm.close();
                con.close();
                return -1;  //errore
            }
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("login fallito");
            return -1;
        }
    }

    public int Cronologia(String equazione){
        if(equazione.isEmpty() || utente.getUsername().isEmpty()){
            return -1;  //errore
        }
        Connection con = connessione();
        try {
            Statement stm = con.createStatement();
            String sql = "INSERT INTO cronologia(username, equazione) VALUES" + "(?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, utente.getUsername());
            ps.setString(2, equazione);
            ps.executeUpdate();
            stm.close();
            con.close();
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return 1;
    }

    public String stampaCronologia(){
        Connection con = connessione();
        String ris = "";
        try{
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM cronologia WHERE username = ?";     //seleziono le righe della tabella con lo stesso username
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,utente.getUsername());
            ResultSet resultset = ps.executeQuery(); //risultato della query

            while(resultset.next()){
                ris += resultset.getString("equazione") + "\n";
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return ris;
    }


}
