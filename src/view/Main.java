package view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import controller.AnalizadorLexico;
import controller.Compilador;

public class Main {

	public static void main(String[] args) {
		String arquivo = "src\\AnalisadorLexico\\file.txt";
		//String dadosArquivo;

		//AnalizadorLexico lexico = new AnalizadorLexico(conteudo);
		Compilador comp = new Compilador(arquivo);

		//Token token = null;
		//while((token = lexico.nextToken()) != null) {
		//	System.out.println(token);
		//}
	}

}
