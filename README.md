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
            | forStmt
            | ifStmt 
            | printStmt
            | whileStmt
            | block ;

forStmt     -> "for" "(" ( varDecl | exprStmt | ";")
                expression? ";"
                expression? ")" statement;            

whileStmt   -> "while" "(" expression ")" statement ;

ifStmt      -> "if" "(" expression ")" statement
                ( "else" statement )? ;

block       -> "{" declaration "}" ;

exprStmt    -> expression ";" ;
printStmt   -> "print" expression ";" ;

expression  -> assignment ;

assignment  -> IDENTIFIER "=" assignment 
            | logic_or ;

logic_or    -> logic_and ( "or" logic_and )* ;
logic_and   -> equality ( "and" equality )* ;

// logic_and   -> comma ( "and" comma )* ;
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
