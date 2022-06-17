package model;

public enum TokenEnum {
	IDENTIFICADOR("1 - Identificador"),
	INTEIRO("2 - Inteiro"),
	REAL("4 - Real"),
	ESPECIAL("5 - Caracter especial"),
	ARITMETICO("6 - Operador Aritimetico"),
	ATRIBUICAO("7 - Operador Atribuição"),
	RELACIONAL("8/9 - Operador Relacional"),
	RESERVADA("11 - Palavra reservada"),
	THALIS("14 - Token especial Thalis"),
	IVSON("9 - Token especial Ivson"),
	CHAR("17 - Char"),
	FIM("99 - Fim");

	public final String tipo;

	private TokenEnum(String t) {
		this.tipo = t;
	}	
}
