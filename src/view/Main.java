package view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import controller.AnalizadorLexico;
import controller.Compilador;

public class Main {

	public static void main(String[] args) {
		String arquivo = "src\\AnalisadorLexico\\file.txt";
		
		Compilador comp = new Compilador(arquivo);
		//AnalizadorSintatico parser = new AnalizadorSintatico(comp);
		
		//parser.();
	}

}
