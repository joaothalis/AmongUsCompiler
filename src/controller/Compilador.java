package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.Token;
import model.TokenEnum;

public class Compilador {
	private final int INICIAL = 0;
	private final int INVALIDO = -1;
	private final int ATRIBUICAO = 7;
	private final int RELACIONAL = 8;
	private final int RESERVADO = 11;
	
	private char[] conteudo;
	private int conteudoIndex;
	private int linhaPos;
	private int colunaPos;
	private int lexemaPos;
	
	public Compilador(String arquivo) {
		try {
			String dadosArquivo = new String(Files.readAllBytes(Paths.get(arquivo)));
			this.conteudo = dadosArquivo.toCharArray();
			this.conteudoIndex = 0;
			this.linhaPos = 1;
			this.colunaPos = 0;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private boolean fimDoArquivo() {
		return this.conteudoIndex > this.conteudo.length;
	}
	
	private boolean proximoChar() {
		return this.conteudoIndex < this.conteudo.length;
	}
	
	private char getChar() {
		return this.conteudo[this.conteudoIndex];
	}
	
	private void proximaPos() {
		this.conteudoIndex++;
		this.colunaPos++;
	}
	
	private void anteriorPos() {
		this.conteudoIndex--;
		this.colunaPos--;
	}
	
	private void proximaLinha() {
		this.linhaPos++;
		this.colunaPos = 0;
	}
	
	public int getLinhaPos() {
		return this.linhaPos;
	}
	
	public int getColunaPos() {
		return this.colunaPos - this.lexemaPos;
	}
	
	public Token getNextToken() {
		char caracter = Character.MIN_VALUE;
		int estado = 0;
		StringBuffer lexema = new StringBuffer();
		this.lexemaPos = lexema.length();
		boolean temCaracterProx;
		
		while(!this.fimDoArquivo()) {
			if(this.proximoChar()) {
				caracter = this.getChar();
				temCaracterProx = true;
			} else {
				if(caracter == Character.MIN_VALUE) {
					break;
				}
				temCaracterProx = false;
			}
			
			this.proximaPos();
			
			switch(estado) {
			case 0:
				if(this.proximoChar() && (estado = AnalizadorLexico.estadoInicial(caracter)) !=INVALIDO) {
					if(caracter != INICIAL) {
						lexema.append(caracter);
					} else {
						if(caracter == '\n') {
							this.proximaLinha();
						}
					}
				} else {
					throw new RuntimeException("ERRO! Caracter invalido: " + this.erroMensagem(lexema));
				}
				break;
			case 1:
				if(this.proximoChar() && (estado = AnalizadorLexico.whenOn1stState(caracter)) != INVALIDO) {
					lexema.append(caracter);
                    if (AnalizadorLexico.ehReservada(lexema.toString())) {
                        estado = RESERVADO;
                    }
                    break;
				} else {
                    this.anteriorPos();
                    return new Token(TokenEnum.IDENTIFICADOR.tipo, lexema.toString());
                }
			case 2:
                if (this.proximoChar() && (estado = AnalizadorLexico.whenOn2ndState(caracter)) != INVALIDO) {
                	lexema.append(caracter);
                    break;                        
                } else {
                	this.anteriorPos();
                    return new Token(TokenEnum.INTEIRO.tipo, lexema.toString());
                }

            case 3:
                if (this.proximoChar() && (estado = AnalizadorLexico.whenOn3rdState(caracter)) != INVALIDO) {
                	lexema.append(caracter);
                    break;
                } else {
                    throw new RuntimeException("ERRO! Real invalido: " + this.erroMensagem(lexema));
                }

            case 4:
                if (this.proximoChar() && (estado = AnalizadorLexico.whenOn4thState(caracter)) != INVALIDO) {
                	lexema.append(caracter);
                    break;
                } else {
                	this.anteriorPos();
                    return new Token(TokenEnum.REAL.tipo, lexema.toString());
                }

            case 5:
            	this.anteriorPos();
                return new Token(TokenEnum.ESPECIAL.tipo, lexema.toString());

            case 6:
            	this.anteriorPos();
                return new Token(TokenEnum.ARITMETICO.tipo, lexema.toString());

            case 7:
                if (this.proximoChar() && (estado = AnalizadorLexico.whenOn7thState(caracter)) == ATRIBUICAO) {
                	this.anteriorPos();
                    return new Token(TokenEnum.ATRIBUICAO.tipo, lexema.toString());
                }

                lexema.append(caracter);
                break;                    

            case 8:
                if (this.proximoChar() && (estado = AnalizadorLexico.whenOn8thState(caracter)) == RELACIONAL) {
                	this.anteriorPos();
                    return new Token(TokenEnum.RELACIONAL.tipo, lexema.toString());                        
                }

                lexema.append(caracter);
                break;                    
                
            case 9:
            	this.anteriorPos();
                return new Token(TokenEnum.RELACIONAL.tipo, lexema.toString());
                
            case 10:
                if (this.proximoChar() && (estado = AnalizadorLexico.whenOn10thState(caracter)) != INVALIDO) {
                	lexema.append(caracter);
                    break;
                } else {
                    throw new RuntimeException("ERRO! Relacional invalido: " + this.erroMensagem(lexema));
                }

            case 11:
            	this.anteriorPos();
                return new Token(TokenEnum.RESERVADA.tipo, lexema.toString());

            case 12:
                if (this.proximoChar() && (estado = AnalizadorLexico.whenOn12thState(caracter)) != INVALIDO) {
                	lexema.append(caracter);
                    break;
                } else {
                    throw new RuntimeException("ERRO! Palavra especial invalida: " + this.erroMensagem(lexema));
                }

            case 13:
                if (this.proximoChar() && (estado = AnalizadorLexico.whenOn13thState(caracter)) != INVALIDO) {
                	lexema.append(caracter);
                    break;
                } else {
                    throw new RuntimeException("ERRO! Palavra especial invalida: " + this.erroMensagem(lexema));
                }

            case 14:
            	this.anteriorPos();
                return new Token(TokenEnum.THALIS.tipo, lexema.toString());

            case 15:
                if (this.proximoChar() && (estado = AnalizadorLexico.whenOn15thState(caracter)) != INVALIDO) {
                	lexema.append(caracter);
                    break;
                } else {
                    throw new RuntimeException("ERRO! Token caracter invalido: " + this.erroMensagem(lexema));
                }

            case 16:
                if (this.proximoChar() && (estado = AnalizadorLexico.whenOn16thState(caracter)) != INVALIDO) {
                	lexema.append(caracter);
                    break;
                } else {
                    throw new RuntimeException("ERRO! Token caracter invalido: " + this.erroMensagem(lexema));
                }

            case 17:
            	this.anteriorPos();
                return new Token(TokenEnum.CHAR.tipo, lexema.toString());

            case 18:
                if (this.proximoChar() && (estado = AnalizadorLexico.whenOn18thState(caracter)) != INVALIDO) {
                	lexema.append(caracter);
                    break;
                } else {
                    throw new RuntimeException("ERRO! Palavra especial invalida: " + this.erroMensagem(lexema));
                }

            case 19:
                if (this.proximoChar() && (estado = AnalizadorLexico.whenOn19thState(caracter)) != INVALIDO) {
                	lexema.append(caracter);
                    break;
                } else {
                    throw new RuntimeException("ERRO! Palavra especial invalida: " + this.erroMensagem(lexema));
                }

            case 99:
            	this.anteriorPos();
                return new Token(TokenEnum.FIM.tipo, lexema.toString());
			}
		}
		return null;
	}
	
	private String erroMensagem(StringBuffer lexema) {
        return "Token invalido: " + lexema.toString() + this.conteudo[this.conteudoIndex - 1];
    }
}
