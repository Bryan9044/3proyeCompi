package codigo;

import java_cup.runtime.*;
import java.io.*;
import java.util.ArrayList;

%%

%class Lexer
%public
%unicode
%cup
%line
%column

%{
    public static boolean hayFloat = false;
    private PrintWriter logWriter;
    private ArrayList<String> errores = new ArrayList<>();
    public static int errorCount = 0;
    private ArrayList<Token> tokens = new ArrayList<>();

    //Constructor el cual lee el código fuente y escribe un 
    //reporte de tokens con su token,lexema,linea y columna
    public Lexer(Reader in, String outputFile, ArrayList<Token> tokens) throws IOException {
        this(in);
        this.tokens = tokens; //Para guardarlos en el puntero y verlos desde el JFlexCup.java
        logWriter = new PrintWriter(new FileWriter(outputFile));
        logWriter.println("=== ANÁLISIS LÉXICO ===");
        logWriter.println("TOKEN\t\t\tLEXEMA\t\tLINEA\t\tCOLUMNA");
        logWriter.println("----------------------------------------");

        
    }
    
    private Symbol symbol(int type) {
        return symbol(type, null);
    }

    // Este es el que eescribe en el archivo de log en general
    // por cada token con su respectivo nombre, lexema, línea y columna
    private Symbol symbol(int type, Object value) {

    
        if (logWriter != null) {
            String tokenName = getTokenName(type);
            String lexeme = (value != null) ? value.toString() : "";
            logWriter.printf("%-25s\t%-15s\t%d\t%d%n", tokenName, lexeme, yyline + 1, yycolumn + 1);
            tokens.add(new Token(tokenName, lexeme, yyline + 1, yycolumn + 1));
            logWriter.flush();
        }
        return new Symbol(type, yyline, yycolumn, value);
    }
    
    // este metodo lo que hace es aumentar el contador de errores,
    // reportar el error y registrarlo, esto no detiene el programa porque funciona
    // en modo panico
    private void reportError(String message) {
        errorCount++;
        String error = String.format("Error léxico: #%d - Línea %d, Columna %d: %s", errorCount, yyline+1, yycolumn+1, message);
        errores.add(error);

        System.err.println("!!!!" + error);

        if (logWriter != null) {
            logWriter.println(">>> " + error);
            logWriter.flush();
        }
    }

    // Obtenemos la lista de errores
    public ArrayList<String> getErrores() {
        return errores;
    }
    
    // Obtenemos el conteo de errores
    public int getErrorCount() {
        return errorCount;
    }
    
    public boolean hayErrores() {
        return errorCount > 0;
    }
    
 




    private String getTokenName(int type) {
        switch(type) {
            case sym.plus_operator: return "PLUS_OPERATOR";
            case sym.minus_operator: return "MINUS_OPERATOR";
            case sym.multiplication_operator: return "MULT_OPERATOR";
            case sym.division_operator: return "DIV_OPERATOR";
            case sym.int_division_operator: return "INT_DIV_OPERATOR";
            case sym.modulo_operator: return "MOD_OPERATOR";
            case sym.power_operator: return "POWER_OPERATOR";
            case sym.increment_operator: return "INCREMENT";
            case sym.decrement_operator: return "DECREMENT";
            case sym.left_parenthesis: return "LEFT_PAREN";
            case sym.right_parenthesis: return "RIGHT_PAREN";
            case sym.left_block: return "LEFT_BLOCK";
            case sym.right_block: return "RIGHT_BLOCK";
            case sym.assignment_operator: return "ASSIGN";
            case sym.equal_operator: return "EQUAL";
            case sym.not_equal_operator: return "NOT_EQUAL";
            case sym.greater_operator: return "GREATER";
            case sym.less_operator: return "LESS";
            case sym.greater_equal_operator: return "GREATER_EQUAL";
            case sym.less_equal_operator: return "LESS_EQUAL";
            case sym.or_operator: return "OR";
            case sym.and_operator: return "AND";
            case sym.not_operator: return "NOT";
            case sym.comma_keyword: return "COMMA";
            case sym.delimiter: return "DELIMITER";
            case sym.int_literal: return "INT_LITERAL";
            case sym.float_literal: return "FLOAT_LITERAL";
            case sym.bool_literal: return "BOOL_LITERAL";
            case sym.CHAR_LITERAL: return "CHAR_LITERAL"; // Esto es por una prueba
            case sym.STRING_LITERAL: return "STRING_LITERAL"; // esto es por una prueba
            case sym.identifier: return "IDENTIFIER";
            case sym.int_keyword: return "INT";
            case sym.float_keyword: return "FLOAT";
            case sym.bool_keyword: return "BOOL";
            case sym.char_keyword: return "CHAR";
            case sym.string_keyword: return "STRING";
            case sym.void_keyword: return "VOID";
            case sym.principal_keyword: return "PRINCIPAL";
            case sym.let_keyword: return "LET";
            case sym.input_keyword: return "INPUT";
            case sym.output_keyword: return "OUTPUT";
            case sym.loop_keyword: return "LOOP";
            case sym.exit_keyword: return "EXIT";
            case sym.when_keyword: return "WHEN";
            case sym.for_keyword: return "FOR";
            case sym.step_keyword: return "STEP";
            case sym.to_keyword: return "TO";
            case sym.downto_keyword: return "DOWNTO";
            case sym.do_keyword: return "DO";
            case sym.return_keyword: return "RETURN";
            case sym.break_keyword: return "BREAK";
            case sym.decide_keyword: return "DECIDE";
            case sym.else_keyword: return "ELSE";
            case sym.EOF: return "EOF";
            default: return "UNKNOWN";
        }
    }
    
    public void closeLog() {
        if (logWriter != null) {
            logWriter.println("----------------------------------------------------------");
            logWriter.println("=== FIN DEL ANÁLISIS ===");
            if (errorCount > 0) {
                logWriter.println("\n>>> TOTAL DE ERRORES LÉXICOS: " + errorCount);
                logWriter.println("\nLISTA DE ERRORES:");
                for (String error : errores) {
                    logWriter.println("  " + error);
                }
            } else {
                logWriter.println("✓ Sin errores léxicos");
            }
            logWriter.close();
        }
    }
%}

/* Caracteres básicos */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]
Digit = [0-9]
Letter = [a-zA-Z]
Underscore = _
UniqueComment = \|[^\n]*

/* Al parecer ya funciona correctamente, seguir probado el comentario multilínea */
MultiplLineComment = \¡([^!]|\n)*\!   

/* Identificadores */
Identifier = {Underscore}? {Letter}({Letter}|{Digit}|{Underscore})*

/* Enteros */
IntegerZero = 0
IntegerPositive = [1-9]{Digit}*
IntegerNegative = "-"{IntegerPositive}
IntegerLiteral = {IntegerZero}|{IntegerPositive}|{IntegerNegative}

/* Flotantes */
FloatZero = 0\.0
FloatPositiveDecimal = 0\.{Digit}*[1-9]+
FloatNegativeDecimal = "-"{FloatPositiveDecimal}
FloatWithInteger = [1-9]{Digit}*\.{Digit}*[1-9]+
FloatWithIntegerZeroDecimal = [1-9]{Digit}*\.0
FloatNegativeWithInteger = "-"{FloatWithInteger}
FloatNegativeWithIntegerZero = "-"{FloatWithIntegerZeroDecimal}
FloatLiteral = {FloatZero}|{FloatPositiveDecimal}|{FloatNegativeDecimal}|{FloatWithInteger}|{FloatWithIntegerZeroDecimal}|{FloatNegativeWithInteger}|{FloatNegativeWithIntegerZero}

/* char y string */
ScapeSecuences = (\\n|\\t|\\b|\\r|\\a|\\'|\\?|\\f|\\v)
CharLiteral = \'([^\']|{ScapeSecuences})\'
StringLiteral = \"([^\"\n\\]|\\n)*\"

%%

<YYINITIAL> {
    {UniqueComment}   { /* ignorar */ }
    {MultiplLineComment} { /* ignorar */ }
    /* Espacios en blanco */
    {WhiteSpace}        { /* ignorar */ }
    

    "++"                { return symbol(sym.increment_operator, yytext()); }
    "--"                { return symbol(sym.decrement_operator, yytext()); }
    "//"                { return symbol(sym.int_division_operator, yytext()); } 
    "=="                { return symbol(sym.equal_operator, yytext()); }
    "!="                { return symbol(sym.not_equal_operator, yytext()); }
    ">="                { return symbol(sym.greater_equal_operator, yytext()); }
    "<="                { return symbol(sym.less_equal_operator, yytext()); }


    "~"                 { return symbol(sym.or_operator, yytext()); }
    "@"                 { return symbol(sym.and_operator, yytext()); } 
    "Σ"                 { return symbol(sym.not_operator, yytext()); }


    /* Operadores normales */
    "+"                 { return symbol(sym.plus_operator, yytext()); }
    "-"                 { return symbol(sym.minus_operator, yytext()); }
    "*"                 { return symbol(sym.multiplication_operator, yytext()); }
    "/"                 { return symbol(sym.division_operator, yytext()); }
    "%"                 { return symbol(sym.modulo_operator, yytext()); }
    "^"                 { return symbol(sym.power_operator, yytext()); }
    ">"                 { return symbol(sym.greater_operator, yytext()); }
    "<"                 { return symbol(sym.less_operator, yytext()); }
    "!"                 { return symbol(sym.left_exclamation, yytext()); }
    "¡"                 { return symbol(sym.right_exclamation, yytext()); }
    "="                 { return symbol(sym.assignment_operator, yytext()); }

    /* Paréntesis y bloques o llaves */
    "є"                 { return symbol(sym.left_parenthesis, yytext()); }
    "э"                 { return symbol(sym.right_parenthesis, yytext()); }
    "¿"                 { return symbol(sym.left_block, yytext()); }
    "?"                 { return symbol(sym.right_block, yytext()); }
    "["                 { return symbol(sym.left_bracket, yytext()); }
    "]"                 { return symbol(sym.right_bracket, yytext()); }

    /* Separadores */
    ","                 { return symbol(sym.comma_keyword, yytext()); }
    "$"                 { return symbol(sym.delimiter, yytext()); }

    "int"             { return symbol(sym.int_keyword, yytext()); }
    "float"           { hayFloat = true; return symbol(sym.float_keyword, yytext()); }
    "bool"            { return symbol(sym.bool_keyword, yytext()); }
    "char"            { return symbol(sym.char_keyword, yytext()); }
    "string"          { return symbol(sym.string_keyword, yytext()); }
    "void"            { return symbol(sym.void_keyword, yytext()); }
    "principal"       { return symbol(sym.principal_keyword, yytext()); }
    "let"             { return symbol(sym.let_keyword, yytext()); }
    "input"           { return symbol(sym.input_keyword, yytext()); }
    "output"          { return symbol(sym.output_keyword, yytext()); }
    "loop"            { return symbol(sym.loop_keyword, yytext()); }
    "exit"      { return symbol(sym.exit_keyword, yytext()); }
    "when"      { return symbol(sym.when_keyword, yytext()); }
    "for"            { return symbol(sym.for_keyword, yytext()); }
    "step"          { return symbol(sym.step_keyword, yytext()); }
    "to"        { return symbol(sym.to_keyword, yytext()); }
    "downto"       { return symbol(sym.downto_keyword, yytext()); }
    "do"    { return symbol(sym.do_keyword, yytext()); }
    "return"   { return symbol(sym.return_keyword, yytext()); }
    "break"    { return symbol(sym.break_keyword, yytext()); }
    "decide"     { return symbol(sym.decide_keyword, yytext()); }
    "of"          { return symbol(sym.of_keyword, yytext()); }
    "else"       { return symbol(sym.else_keyword, yytext()); }
    "end"       { return symbol(sym.end_keyword, yytext()); }
    "True"              { return symbol(sym.bool_literal, yytext()); }
    "False"             { return symbol(sym.bool_literal, yytext()); }

    /* Literales */
    {IntegerLiteral}    { return symbol(sym.int_literal, yytext()); }
    {FloatLiteral}      { hayFloat = true; return symbol(sym.float_literal, yytext()); }
    {CharLiteral}       {  // Char y string es ais por una prueba
        return symbol(sym.CHAR_LITERAL, yytext());
    }
    {StringLiteral}     { 
        return symbol(sym.STRING_LITERAL, yytext());
    }

    //ID
    {Identifier}        { return symbol(sym.identifier, yytext()); }



    /* RECUPERACIÓN DE ERRORES EN MODO PÁNICO */
    .   { 
        // NO lanza excepción, solo reporta y continúa
        reportError("Caracter ilegal: '" + yytext() + "'");
        // Continúa con el siguiente carácter
    }
        }

<<EOF>>                { 
    closeLog();
    return symbol(sym.EOF); 
}