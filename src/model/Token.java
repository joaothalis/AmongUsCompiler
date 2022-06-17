package model;

public class Token {
	private String tipo;
	private String conteudo;

	public Token(String t, String c) {
		this.tipo = t;
		this.conteudo = c;
	}

	public String getTipo() {
		return this.tipo;
	}

	public String getConteudo() {
		return this.conteudo;
	}

	public String toString() {
		return "Conteúdo lido: " + getConteudo() + " do tipo: " + getTipo() + "\n";
	}
}
