package controller;

import java.util.Arrays;
import java.util.List;

public class AnalizadorLexico {	
	private static final List<Character> EM_BRANCO = Arrays.asList(' ', '\t', '\n', '\r');
    private static final List<Character> ESPECIAL = Arrays.asList('(', ')', '{', '}', ',', ';');
    private static final List<Character> ARITMETICO = Arrays.asList('+', '-', '/', '*');
    private static final List<Character> RELACIONAL = Arrays.asList('<', '>');
    private static final List<String> RESERVADO = Arrays.asList("main", "if", "else", "while", "do", "for", "int", "float", "char");
    private static final char IGUAL = '=';
    private static final char DOLLAR = '$';
    private static final char UNDERLINE = '_';
    private static final char PONTO = '.';
    private static final char NEGATIVO = '-';
    private static final char NEGACAO = '!';
    private static final char THALIS = '@';
    private static final char IVSON = '#';

	public static int estadoInicial(char caracter) {
		if(ehEmBranco(caracter)) {
			return 0;
		} else if(ehLetra(caracter) || ehUnderline(caracter)) {
			return 1;
		} else if (ehDigito(caracter)) {
			return 2;
		} else if (ehEspecial(caracter)) {
            return 5;
        } else if (ehAritmetico(caracter)) {
            return 6;
        } else if (ehAtribuicao(caracter)) {
            return 7;
        } else if (ehRelacional(caracter)) {
            return 8;
        } else if (ehNegacao(caracter)) {
            return 10;
        } else if (ehThalis(caracter)) {
            return 12;
        } else if (ehIvson(caracter)) {
            return 15;
        } else if (ehDolar(caracter)) {
            return 99;        
        } else {
            return -1;
        }
	}
	
	public static int whenOn1stState(char caracter) {
        if (ehLetra(caracter) || ehDigito(caracter) || ehUnderline(caracter)) {
            return 1;
        } else {
            return -1;
        }
    }

    public static int whenOn2ndState(char caracter) {
        if (ehDigito(caracter)) {
            return 2;
        } else if (ehPonto(caracter)) {
            return 3;
        } else {
            return -1;
        }
    }

    public static int whenOn3rdState(char caracter) {
        if (ehDigito(caracter)) {
            return 4;
        } else {
            return -1;
        }
    }

    public static int whenOn4thState(char caracter) {
        if (ehDigito(caracter)) {
            return 4;
        } else {
            return -1;
        }
    }

    public static int whenOn7thState(char caracter) {
        if (ehAtribuicao(caracter)) {
            return 9;
        } else {
            return 7;
        }
    }

    public static int whenOn8thState(char caracter) {
        if (ehAtribuicao(caracter)) {
            return 9;
        } else {
            return 8;
        }
    }

    public static int whenOn10thState(char caracter) {
        if (ehAtribuicao(caracter)) {
            return 9;
        } else {
            return -1;
        }
    }

    public static int whenOn12thState(char caracter) {
        if (ehNegativo(caracter)) {
            return 13;
        } else if (ehAtribuicao(caracter)) {
            return 18;
        } else {
            return -1;
        }
    }

    public static int whenOn13thState(char caracter) {
        if (ehThalis(caracter)) {
            return 14;
        } else {
            return -1;
        }
    }

    public static int whenOn15thState(char caracter) {
        if (ehLetra(caracter) || ehDigito(caracter)) {
            return 16;
        } else {
            return -1;
        }
    }

    public static int whenOn16thState(char caracter) {
        if (ehIvson(caracter)) {
            return 17;
        } else {
            return -1;
        }
    }

    public static int whenOn18thState(char caracter) {
        if (ehAtribuicao(caracter)) {
            return 19;
        } else {
            return -1;
        }
    }

    public static int whenOn19thState(char currentCharacter) {
        if (ehThalis(currentCharacter)) {
            return 14;
        } else {
            return -1;
        }
    }
	
	public static boolean ehReservada(String caracters) {
        return RESERVADO.contains(caracters.toLowerCase());
    }

	private static boolean ehDolar(char caracter) {
		return caracter == DOLLAR;
	}

	private static boolean ehIvson(char caracter) {
		return caracter == IVSON;
	}

	private static boolean ehThalis(char caracter) {
		return caracter == THALIS;
	}

	private static boolean ehNegacao(char caracter) {
		return caracter == NEGACAO;
	}

	private static boolean ehRelacional(char caracter) {
		return RELACIONAL.contains(Character.valueOf(caracter));
	}

	private static boolean ehAtribuicao(char caracter) {
		return caracter == IGUAL;
	}

	private static boolean ehAritmetico(char caracter) {
		return ARITMETICO.contains(Character.valueOf(caracter));
	}

	private static boolean ehEspecial(char caracter) {
		return ESPECIAL.contains(Character.valueOf(caracter));
	}

	private static boolean ehDigito(char caracter) {
		return Character.isDigit(caracter);
	}
	
	private static boolean ehPonto(char caracter) {
		return caracter == PONTO;
	}

	private static boolean ehUnderline(char caracter) {
		return caracter == UNDERLINE;
	}

	private static boolean ehLetra(char caracter) {
		return Character.isLetter(caracter);
	}

	private static boolean ehEmBranco(char caracter) {
		return EM_BRANCO.contains(Character.valueOf(caracter));
	}
	
	private static boolean ehNegativo(char caracter) {
		return caracter == NEGATIVO;
	}
}
