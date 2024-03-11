package com.craftinginterpreters.Tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, "Expr", Arrays.asList(
                "Assign     : Token name, Expr value",
                "Binary     : Expr left, Token operator, Expr right",
                "Call       : Expr callee, Token paren, List<Expr> arguments",
                "Grouping   : Expr expression",
                "Literal    : Object value",
                "Logical    : Expr left, Token operator, Expr right",
                "Unary      : Token operator, Expr right",
                "Condition  : Expr condition, Expr trueStatement, Expr falseStatement",
                "Nothing    : String nothing",
                "Variable   : Token name"
        ));

        defineAst(outputDir, "Stmt", Arrays.asList(
                "Block      : List<Stmt> statements",
                "Break      : ",
                "Expression : Expr expression",
                "Function   : Token name, List<Token> params, List<Stmt> body",
                "If         : Expr condition, Stmt thenBranch, Stmt elseBranch",
                "Print      : Expr expression",
                "Return     : Token keyword, Expr value",
                "Var        : Token name, Expr initializer",
                "While      : Expr condition, Stmt body"
        ));
    }

    private static void defineAst(
            String outputDir, String baseName, List<String> types)
            throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);

        writer.println("package com.craftinginterpreters.ORLang;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + baseName + " {");

        defineVisitor(writer, baseName, types);

        // The AST classes
        for (String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields);
        }

        // The base accept() method
        writer.println();
        writer.println("\tabstract <R> R accept(Visitor<R> visitor);");

        writer.println("}");
        writer.close();
    }

    private static void defineType(
            PrintWriter writer, String baseName,
            String className, String fieldList) {
        writer.println(String.format("\tstatic class %s extends %s {", className, baseName));

        // Constructor
        writer.println(String.format("\t\t%s(%s) {", className, fieldList));
        // Store parameters in fields (condition for expressions/statements with empty params)
        String[] fields;
        if (fieldList.isEmpty()) {
            fields = new String[0];
        } else {
            fields= fieldList.split(", ");
        }

        for (String field : fields) {
            if (field.split(" ").length > 1) {
                String name = field.split(" ")[1];
                writer.println(String.format("\t\t\tthis.%s = %s;", name, name));
            }
        }


        writer.println("\t\t}");

        writer.println();
        writer.println("\t\t@Override");
        writer.println("\t\t<R> R accept(Visitor<R> visitor) {");
        writer.println(String.format("\t\t\treturn visitor.visit%s%s(this);",
                className, baseName));
        writer.println("\t\t}\n");

        // Fields
        for (String field : fields) {
            writer.println(String.format("\t\tfinal %s;", field));
        }
        writer.println("\t}\n");
    }

    private static void defineVisitor(
            PrintWriter writer, String baseName, List<String> types
    ) {
        writer.println("\tinterface Visitor<R> {");

        for (String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.print(String.format("\t\tR visit%s%s (%s %s);\n", typeName, baseName, typeName, baseName.toLowerCase()));
        }

        writer.println("\t}");
    }
}
