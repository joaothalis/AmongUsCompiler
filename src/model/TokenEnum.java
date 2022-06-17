package model;

public enum TokenEnum {
	INTEIRO("1 - Inteiro"),
	REAL("2 - Real"),
	CHAR("3 - Char"),
	IDENTIFICADOR("4 - Identificador"),
	IGUAL("5 - Operador Atribuição"),
	ARITMETICO("6 - Operador Aritimetico"),
	ESPECIAL("7 - Caracter especial"),
	THALIS("8 - Token especial Thalis"),
	IVSON("9 - Token especial Ivson"),
	FIM("99 - Fim");

	public final String tipo;

	private TokenEnum(String t) {
		this.tipo = t;
	}	
}
