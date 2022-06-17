package view;

import controller.AnalisadorSintatico;
import controller.Compilador;

public class Main {

	public static void main(String[] args) {
		String arquivo = "src\\file.txt";
		
		Compilador comp = new Compilador(arquivo);
		AnalisadorSintatico parser = new AnalisadorSintatico(comp);
		
		parser.startingPoint_nonTerminal();
	}

}
