package Database.Connector;

public class Utente {
    private String nome;
    private String cognome;
    private String username;
    private String password;
    public Utente(){
        nome = "";
        cognome = "";
        username = "";
        password = "";
    }
    public Utente(String nome, String cognome, String username, String password){
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}