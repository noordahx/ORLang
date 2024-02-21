```agsl
--------------------------------------------------

--- ORLang - inspired by Robert Nystrom's jLox ---

--------------------------------------------------

ORLang java implementation (jlox):
└── ORLang
    └── com
        └── craftinginterpreters
            ├── ORLang
            │   ├── AstPrinter.class
            │   ├── Expr.class
            │   ├── Interpreter.class
            │   ├── ORLang.class
            │   ├── Parser.class
            │   ├── RuntimeError.class
            │   ├── Scanner.class
            │   ├── Stmt.class
            │   ├── Token.class
            │   └── TokenType.class
            └── Tool
                └── GenerateAst.class



-- Tree-walk interpreter directly executing AST --

Parser's grammar rules:
program     -> declaration* EOF ;

declaration -> varDecl | statement ;

varDecl     -> "var" IDENTIFIER ("=" expression)? ";" ;

statement   -> exprStmt 
            | printStmt
            | block ;

block       -> "{" declaration "}" ;

exprStmt    -> expresssion ";" ;
printStmt   -> "print" expression ";" ;

expression  -> assignment ;

assignment  -> IDENTIFIER "=" assignment | equality ;

//assignment  -> IDENTIFIER "=" assignment | comma ;
// comma       -> ternary ( ( "," ) ternary )* ;
// ternary     -> equality ( "?" expression ":" expression )* ;

equality    -> comparison ( ( "!=" | "==" ) comparison )* ;
comparison  -> term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term        -> factor ( ( "-" | "+" ) factor )* ;
factor      -> unary ( ( "/" | "*" ) factor )* ;
unary       -> ( "!" | "-" ) unary | primary ;
primary     -> NUMBER | STRING | "true" | "false" | "nil"
            | "(" expression ")"
            | IDENTIFIER ;


where   * - multiple
        ? - at most once
        
``` 



