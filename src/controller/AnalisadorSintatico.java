package controller;

import java.util.List;

import model.Token;
import model.TokenEnum;
import model.Variavel;

import java.util.ArrayList;

public class AnalisadorSintatico {
    private Compilador scanner;
    private Token token;
    private int currentScope = 0;
    List<Variavel> variaveis = new ArrayList<Variavel>();
    private String variavel;
    private String valor;


    public AnalisadorSintatico(Compilador compilador) {
        this.scanner = compilador;
        this.token = compilador.getNextToken();
    }

    private String mensagemErroSintax() {
        if (token != null)
            return "ERRO NA LINHA " + scanner.getLinhaPos() + " E COLUNA " + scanner.getColunaPos() + " ANTES " + token.getConteudo();

        return "ERRO NA LINHA " + scanner.getLinhaPos() + " E COLUNA " + scanner.getColunaPos();
    }

    public void startingPoint_nonTerminal() {
        if (token != null && token.getConteudo().equals("int"))
        	token = scanner.getNextToken();
        else
            throw new RuntimeException("ERRO na LINHA 1 e COLUNA 1"  + "\t 'int'Nao encontrado");

        if (token != null && token.getConteudo().equalsIgnoreCase("main"))
        	token = scanner.getNextToken();
        else
            throw new RuntimeException(mensagemErroSintax() + "\t 'main' Nao encontrado");        

        if (token != null && token.getConteudo().equals("("))
        	token = scanner.getNextToken();
        else
            throw new RuntimeException(mensagemErroSintax() + "\t '(' Nao encontrado");
        
        if (token != null && token.getConteudo().equals(")"))
        	token = scanner.getNextToken();
        else
            throw new RuntimeException(mensagemErroSintax() + "\t ')'Nao encontrado");        

        blockOfCode_nonTerminal();

        if (token != null)
            throw new RuntimeException(mensagemErroSintax() + "\t Codigo fora do bloco");
        else
            System.out.println("O Codigo esta funcionando! ;)");
    }

    private void insideBlockOfCode() {
        while (token != null && (token.getConteudo().equals("int") || token.getConteudo().equals("float") || token.getConteudo().equals("char"))){
            declaration_nonTerminal();
        }

        while (token != null && (token.getTipo().equals(TokenEnum.IDENTIFICADOR.tipo) || token.getConteudo().equals("{") || token.getConteudo().equals("while") || token.getConteudo().equals("if"))) {
            command_nonTerminal();
        }
    }

    private void blockOfCode_nonTerminal() {
        if (token != null && token.getConteudo().equals("{")) {
        	token = scanner.getNextToken();
            currentScope++;

            while (token != null && (token.getConteudo().equals("int") || token.getConteudo().equals("float") ||
            		token.getConteudo().equals("char") || token.getTipo().equals(TokenEnum.IDENTIFICADOR.tipo) ||
            		token.getConteudo().equals("{") || token.getConteudo().equals("while") ||
            		token.getConteudo().equals("if"))) {
                    insideBlockOfCode();
                }
                
        } else {
            throw new RuntimeException(mensagemErroSintax() + "\t '{' Nao encontrado");
        }

        if (token != null && token.getConteudo().equals("}")) {
        	token = scanner.getNextToken();
            currentScope--;
        } else {
            throw new RuntimeException(mensagemErroSintax() + "\t '}'Nao encontrado");
        }
    }

    private void command_nonTerminal() {        
        if (token != null && token.getConteudo().equals("while"))
            iteration_nonTerminal();
        else if (token != null && token.getConteudo().equals("if")) {
        	token = scanner.getNextToken();

            if (token.getConteudo().equals("(")) {
            	token = scanner.getNextToken();
            } else {
                throw new RuntimeException(mensagemErroSintax() + "\t '('Nao encontrado");
            }

            relationalExpression_nonTerminal();

            if (token.getConteudo().equals(")")) {
            	token = scanner.getNextToken();
            } else {
                throw new RuntimeException(mensagemErroSintax() + "\t ')'Nao encontrado");
            }

            command_nonTerminal();

            if (token.getConteudo().equals("else")) {
            	token = scanner.getNextToken();
                command_nonTerminal();
            }                     
        } else
            basicCommand_nonTerminal();
    }

    private void basicCommand_nonTerminal() {
        if (token.getTipo().equals(TokenEnum.IDENTIFICADOR.tipo))
            assignment_nonTerminal();
        else
            blockOfCode_nonTerminal();
    }

    private void iteration_nonTerminal() {
        if (token != null && token.getConteudo().equals("while"))
        	token = scanner.getNextToken();
        else 
            throw new RuntimeException(mensagemErroSintax() + "\t 'while' Nao encontrado"); 

        if (token != null && token.getConteudo().equals("("))
        	token = scanner.getNextToken();
        else
            throw new RuntimeException(mensagemErroSintax() + "\t '('Nao encontrado");

        relationalExpression_nonTerminal();

        if (token != null && token.getConteudo().equals(")"))
        	token = scanner.getNextToken();
        else 
            throw new RuntimeException(mensagemErroSintax() + "\t ')' Nao encontrado");

        command_nonTerminal();
    }

    private void assignment_nonTerminal() {
        String variavelName;

        if (token != null && token.getTipo().equals(TokenEnum.IDENTIFICADOR.tipo)) {
            variavelName = token.getConteudo();
            token = scanner.getNextToken();            
        } else {
            throw new RuntimeException(mensagemErroSintax() + "\t token precisa ser identificado");     
        }

        if (token != null && token.getConteudo().equals("="))
        	token = scanner.getNextToken();
        else
            throw new RuntimeException(mensagemErroSintax() + "\t '=' Nao encontrado");

        arithmeticExpression_nonTerminal();

        Variavel declaredVariavel = isVariavelDeclared(variavelName);
        if (declaredVariavel != null) {
            if (!declaredVariavel.getTipo().equals(valor)) {
                throw new RuntimeException(mensagemErroSintax() + "\t tipo incompativel");
            }
        } else {
            throw new RuntimeException(mensagemErroSintax() + "\t variavel '" + variavelName + "' nao esta declarada");
        }

        if (token != null && token.getConteudo().equals(";"))
        	token = scanner.getNextToken();
        else
            throw new RuntimeException(mensagemErroSintax() + "\t ';' Nao encontrado");
    }

    private Variavel isVariavelDeclared(String variavelName) {
        for (Variavel variavel : variaveis) {
            if ((variavel.getScope() == currentScope && variavel.getNome().equals(variavelName)) ||
                variavel.getNome().equals(variavelName)) {
                    return variavel;
            }
        }
        return null;
    }

    private void relationalExpression_nonTerminal() {
        arithmeticExpression_nonTerminal();
        if (token != null && token.getTipo().equals(TokenEnum.RELACIONAL.tipo)) {
        	token = scanner.getNextToken();
        }
        arithmeticExpression_nonTerminal();
    }

    private void arithmeticExpression_nonTerminal() {
        term_nonTerminal();
        arithmeticExpression_line_nonTerminal();
    }

    private void arithmeticExpression_line_nonTerminal() {
        if (token.getConteudo().equals("+") || token.getConteudo().equals("-")) {
        	token = scanner.getNextToken();
            term_nonTerminal();
        }
    }

    private void term_nonTerminal() {
        factor_terminal();
        term_line_nonTerminal();
    }

    private void term_line_nonTerminal() {
        if (token.getConteudo().equals("*") || token.getConteudo().equals("/")) {
        	token = scanner.getNextToken();
            factor_terminal();
        }
    }

    private void factor_terminal() {
        if (token != null && token.getConteudo().equals("(")) { 
            token = scanner.getNextToken();
            arithmeticExpression_nonTerminal();

            if (!token.getConteudo().equals(")")) {
                throw new RuntimeException(mensagemErroSintax() + "\t ')'Nao encontrado");
            }            
        } else if (token != null && (token.getTipo().equals(TokenEnum.IDENTIFICADOR.tipo) ||
            token.getTipo().equals(TokenEnum.REAL.tipo) || token.getTipo().equals(TokenEnum.INTEIRO.tipo) ||
            token.getTipo().equals(TokenEnum.CHAR.tipo))) {
                if (token.getTipo().equals(TokenEnum.IDENTIFICADOR.tipo)) {
                    Variavel declaredVariavel = isVariavelDeclared(token.getConteudo());
                    if (declaredVariavel != null) {
                        valor = declaredVariavel.getTipo();
                    } else {
                        throw new RuntimeException(mensagemErroSintax() + "\t variavel '" + token.getConteudo() + "' was not declared");
                    }
                } else {
                    valor = token.getTipo();
                }
                token = scanner.getNextToken();
        }
    }

    private void declaration_nonTerminal() {
        String variavelName, variavelType;

        if (token != null && (token.getConteudo().equals("int") ||
            token.getConteudo().equals("float") ||
            token.getConteudo().equals("char"))) {
                switch (token.getConteudo()) {
                    case "int":
                        variavelType = TokenEnum.INTEIRO.tipo;
                        break;
                    case "float":
                        variavelType = TokenEnum.REAL.tipo;
                        break;
                    default:
                        variavelType = TokenEnum.CHAR.tipo;
                }

            this.variavel = variavelType;
            token = scanner.getNextToken();
        } else {
            throw new RuntimeException(mensagemErroSintax() + "\t token precisa ser algum tipo");
        }
        
        if (token != null && token.getTipo().equals(TokenEnum.IDENTIFICADOR.tipo)) {
            variavelName = token.getConteudo();
            token = scanner.getNextToken();
        } else {
            throw new RuntimeException(mensagemErroSintax() + "\t token precisa ser um indentificador");
        }
        
        if (token != null && token.getConteudo().equals(";")) {
            token = scanner.getNextToken();
        } else {
            throw new RuntimeException(mensagemErroSintax() + "\t ';' Nao encontrado");
        }
        
        Variavel declaredVariavel = new Variavel(currentScope, variavelName, variavelType);
        if (!isVariavelAlreadyInScope(declaredVariavel))
            variaveis.add(new Variavel(currentScope, variavelName, variavelType));
        else 
            throw new RuntimeException(mensagemErroSintax() + "\t Variavel '" + variavelName + "' ja definida");
    }

    private boolean isVariavelAlreadyInScope(Variavel newVariavel) {
        for (Variavel variavel : variaveis) {
            if (variavel.getScope() == newVariavel.getScope() && variavel.getNome().equals(newVariavel.getNome()))
                return true;
        }

        return false;
    }
}