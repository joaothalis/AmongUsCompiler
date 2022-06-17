package model;

public class Variavel {
	private int scope;
    private String nome;
    private String tipo;

	public Variavel(int scope, String nome, String tipo) {
		this.scope = scope;
        this.nome = nome;
        this.tipo = tipo;
	}

	public int getScope() {
		return this.scope;
	}

	public String getNome() {
		return this.nome;
	}

	public String getTipo() {
		return this.tipo;
	}

}
