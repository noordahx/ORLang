```agsl
--------------------------------------------------

--- ORLang - inspired by Robert Nystrom's jLox ---

--------------------------------------------------

-- Tree-walk interpreter directly executing AST --

Parser's expression grammar:
expression  -> comma ;
comman      -> ternary ( ( "," ) ternary )* ;
ternary     -> equality ( "?" expression ":" expression )* ;
equality    -> comparison ( ( "!=" | "==" ) comparison )* ;
comparison  -> term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term        -> factor ( ( "-" | "+" ) factor )* ;
factor      -> unary ( ( "/" | "*" ) factor )* ;
unary       -> ( "!" | "-" ) unary | primary ;
primary     -> NUMBER | STRING | "true" | "false" | "nil"
            | "(" expression ")" ;

where   * - while loop
        
``` 



