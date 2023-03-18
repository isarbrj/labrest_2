package engcom.std.labrest.resources;

public class Saudacao {
    private final long id;
    private final String nome;

    public Saudacao(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
