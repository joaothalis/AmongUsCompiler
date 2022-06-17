package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.Token;

public class Compilador {
	private final int INICIAL = 0;
	private final int INVALIDO = -1;
	private final int ATRIBUICAO = 7;
	private final int RELACIONAL = 8;
	private final int RESERVADA = 11;
	
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
					throw new RuntimeException("ERROR! Caracter no formato invalido: " + lexema.toString());
				}
				break;
			}
			case 1:
				
		}
	}
}
