package ast.visitors;

import ast.*;
import symbol_table.SymbolTable;
import symbol_table.SymbolTableRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CGeneratorVisitor implements Visitor {
    private Writer writer;
    private String code = "";
    private int lenghtAtStartFuns;
    private ArrayList<String> funPlus = new ArrayList<>();

    //Generatore di Codice C
    public CGeneratorVisitor(String fileName) throws IOException {
        fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.indexOf("."));
        fileName += ".c";
        fileName = "test_files/c_out/" + fileName;

        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
    }

    public void dispose() throws IOException {
        writer.close();
    }

    @Override
    public Object visit(AbstractSyntaxNode node) {
        try {
            if (node instanceof ProgramNode) {
                ProgramNode tmp = (ProgramNode) node;

                code = code + "#include <stdio.h>" + "\n";
                code = code + "#include <string.h>" + "\n";
                code = code + "#include <stdlib.h>" + "\n";
                code = code + "#include <math.h>" + "\n\n";

                VarDeclListNode varDeclListNode = tmp.getVarDeclListNode();
                if (varDeclListNode != null) {
                    varDeclListNode.accept(this);
                    code = code + "\n";
                }

                lenghtAtStartFuns = code.length();

                FunListNode funListNode = tmp.getFunListNode();
                if (funListNode != null) {
                    funListNode.accept(this);
                }

                code = code + "int main() {\n";

                BodyOp bodyOp = tmp.getBodyOp();
                if (bodyOp != null) {
                    bodyOp.accept(this);
                }

                code = code + "\n}";
                writer.append(code);
                dispose();
            }

            if (node instanceof FunListNode) {
                FunListNode tmp = (FunListNode) node;

                List<FunOpNode> funOpNodeList = tmp.getFunOpNodeList();
                if (funOpNodeList != null) {
                    for (FunOpNode funOpNode : funOpNodeList) {
                        if (funOpNode != null) {
                            funOpNode.accept(this);
                            code = code + "\n\n";
                        }
                    }
                }
            }

            if (node instanceof FunOpNode) {
                FunOpNode tmp = (FunOpNode) node;

                if (tmp.getTypeNode() != null) {
                    tmp.getTypeNode().accept(this);
                } else {
                    code = code + "void ";
                }

                if (tmp.getIdentifierNode() != null) {
                    tmp.getIdentifierNode().accept(this);
                }

                if (tmp.getParamDeclListNode() != null) {
                    code = code + "(";
                    tmp.getParamDeclListNode().accept(this);
                    code = code + ")";
                } else {
                    code = code + "()";
                }

                if (tmp.getBodyOp() != null) {
                    code = code + " {\n";
                    tmp.getBodyOp().accept(this);
                    code = code + "\n}";
                } else {
                    code = code + " { } ";
                }

            }

            if (node instanceof ParamDeclListNode) {
                ParamDeclListNode tmp = (ParamDeclListNode) node;
                if (tmp.getNonEmptyParamDeclListNode() != null) {
                    List<ParDeclOpNode> parDeclOpNodeList = tmp.getNonEmptyParamDeclListNode().getNonEmptyParamDeclList();
                    if (parDeclOpNodeList != null) {
                        for (int i = 0; i < parDeclOpNodeList.size(); i++) {
                            if (parDeclOpNodeList.get(i) != null) {
                                parDeclOpNodeList.get(i).accept(this);
                                if (i < parDeclOpNodeList.size() - 1) {
                                    code = code + ", ";
                                }
                            }
                        }
                    }
                }
            }

            if (node instanceof ParDeclOpNode) {
                ParDeclOpNode tmp = (ParDeclOpNode) node;

                IdentifierNode identifierNode = tmp.getIdentifierNode();
                tmp.getTypeNode().accept(this);
                if (tmp.isOut()) {
                    identifierNode.setOut(tmp.isOut());
                }
                identifierNode.accept(this);
            }

            if (node instanceof BodyOp) {
                BodyOp tmp = (BodyOp) node;
                if (tmp.getVarDeclListNode() != null) {
                    code = code + "\n";
                    tmp.getVarDeclListNode().accept(this);
                }

                if (tmp.getStatListNode() != null) {
                    code = code + "\n";
                    tmp.getStatListNode().accept(this);
                }

            }

            if (node instanceof VarDeclListNode) {
                VarDeclListNode tmp = (VarDeclListNode) node;

                List<VarDeclOpNode> varDeclOpNodeList = tmp.getVarDeclOpNodeList();

                if (varDeclOpNodeList != null) {
                    for (VarDeclOpNode varDeclOpNode : varDeclOpNodeList) {
                        if (varDeclOpNode != null) {
                            varDeclOpNode.accept(this);
                            code = code + ";\n";
                        }
                    }
                }
            }

            if (node instanceof VarDeclOpNode) {
                VarDeclOpNode tmp = (VarDeclOpNode) node;

                if (tmp.getIdInitListObblNode() != null) {
                    tmp.getIdInitListObblNode().accept(this);
                }

                if (tmp.getTypeNode() != null) {
                    tmp.getTypeNode().accept(this);
                }
                if (tmp.getIdInitListNode() != null) {
                    tmp.getIdInitListNode().accept(this);
                }
            }

            if (node instanceof TypeNode) {
                TypeNode tmp = (TypeNode) node;
                //tmp.getChild() = instanceof StringNode, RealNode,IntegerNode,BoolNode
                if (tmp.getChild() != null) {
                    tmp.getChild().accept(this);
                }
            }

            if (node instanceof IntegerNode) {
                code = code + "int ";
            }

            if (node instanceof BoolNode) {
                code = code + "int ";
            }

            if (node instanceof RealNode) {
                code = code + "float ";
            }

            if (node instanceof StringNode) {
                code = code + "char* ";
            }

            if (node instanceof IdListInitNode) {
                IdListInitNode tmp = (IdListInitNode) node;

                List<CoppiaIdExprNode> coppiaIdExprNodeList = tmp.getListCoppiaIdExprNode();
                if (coppiaIdExprNodeList != null) {
                    for (int i = 0; i < coppiaIdExprNodeList.size(); i++) {
                        if (coppiaIdExprNodeList.get(i) != null) {
                            coppiaIdExprNodeList.get(i).getFirst().accept(this);
                            // Inizializzazione
                            if (coppiaIdExprNodeList.get(i).getSecond() != null) {
                                code = code + " = ";
                                coppiaIdExprNodeList.get(i).getSecond().accept(this);
                            } else if (coppiaIdExprNodeList.get(i).getFirst().getType() == CircuitSym.STRING) {
                                if (coppiaIdExprNodeList.get(i).getSecond() != null) {
                                    if (!funPlus.contains("creaString")) {
                                        String convertReal =
                                                "char* creaString(char* string){\n" +
                                                        "\tchar* s = malloc(256);\n" +
                                                        "\tsprintf(s, \"%s\",string);\n" +
                                                        "\treturn s;\n" +
                                                        "}\n\n";
                                        String subString = code.substring(0, lenghtAtStartFuns);
                                        String subString2 = code.substring(lenghtAtStartFuns);
                                        code = subString + convertReal + subString2;
                                        funPlus.add("creaString");
                                    }
                                    code = code + " = creaString(";
                                    coppiaIdExprNodeList.get(i).getSecond().accept(this);
                                    code = code + ")";
                                } else {
                                    code = code + " = malloc(256)";
                                }
                            }
                            if (i < coppiaIdExprNodeList.size() - 1) {
                                code = code + ", ";
                            }
                        }
                    }
                }
            }

            if (node instanceof IdListInitObblNode) {
                IdListInitObblNode tmp = (IdListInitObblNode) node;

                List<CoppiaIdConstNode> coppiaIdConstNodeList = tmp.getListCoppiaIdConstNode();

                if (coppiaIdConstNodeList != null) {
                    for (int i = 0; i < coppiaIdConstNodeList.size(); i++) {
                        if (coppiaIdConstNodeList.get(i) != null) {
                            Integer type = coppiaIdConstNodeList.get(i).getSecond().getType();
                            switch (type) {
                                case CircuitSym.INTEGER:
                                    code = code + "int ";
                                    break;
                                case CircuitSym.REAL:
                                    code = code + "float ";
                                    break;
                                case CircuitSym.STRING:
                                    code = code + "char* ";
                                    break;
                                case CircuitSym.BOOL:
                                    code = code + "int ";
                                    break;
                            }
                            coppiaIdConstNodeList.get(i).getFirst().accept(this);
                            // Inizializzazione
                            if (coppiaIdConstNodeList.get(i).getSecond().getType() == CircuitSym.STRING) {
                                if (!funPlus.contains("creaString")) {
                                    String convertReal =
                                            "char* creaString(char* string){\n" +
                                                    "\tchar* s = malloc(256);\n" +
                                                    "\tsprintf(s, \"%s\",string);\n" +
                                                    "\treturn s;\n" +
                                                    "}\n\n";
                                    String subString = code.substring(0, lenghtAtStartFuns);
                                    String subString2 = code.substring(lenghtAtStartFuns);
                                    code = subString + convertReal + subString2;
                                    funPlus.add("creaString");
                                }
                                code = code + " = creaString(";
                                coppiaIdConstNodeList.get(i).getSecond().accept(this);
                                code = code + ")";
                            } else if (coppiaIdConstNodeList.get(i).getSecond() != null) {
                                code = code + " = ";
                                coppiaIdConstNodeList.get(i).getSecond().accept(this);
                            }
                            if (i < coppiaIdConstNodeList.size() - 1) {
                                code = code + ";\n";
                            }
                        }
                    }
                }
            }

            if (node instanceof BoolConstNode) {
                BoolConstNode tmp = (BoolConstNode) node;
                tmp.getChild().accept(this);
            }

            if (node instanceof ConstNode) {
                ConstNode tmp = (ConstNode) node;
                tmp.getChild().accept(this);
            }

            if (node instanceof IdentifierNode) {
                IdentifierNode tmp = (IdentifierNode) node;
                if (tmp.isOut()) {
                    code = code + "*";
                }
                code = code + tmp.getIndex();
            }

            if (node instanceof RealConstNode) {
                RealConstNode tmp = (RealConstNode) node;
                code = code + tmp.getValue();
            }

            if (node instanceof IntegerConstNode) {
                IntegerConstNode tmp = (IntegerConstNode) node;
                code = code + tmp.getValue();
            }

            if (node instanceof TrueNode) {
                code = code + "1";
            }

            if (node instanceof FalseNode) {
                code = code + "0";
            }

            if (node instanceof StringConstNode) {
                code = code + " \"";
                StringConstNode tmp = (StringConstNode) node;
                code = code + tmp.getValue() + "\"";
            }

            if (node instanceof AddOpNode) {
                ((AddOpNode) node).getLeft().accept(this);
                code = code + " + ";
                ((AddOpNode) node).getRight().accept(this);
            }

            if (node instanceof PowOpNode) {
                code = code + "pow(";
                ((PowOpNode) node).getLeft().accept(this);
                code = code + " , ";
                ((PowOpNode) node).getRight().accept(this);
                code = code + ")";
            }

            if (node instanceof AndOpNode) {
                ((AndOpNode) node).getLeft().accept(this);
                code = code + " && ";
                ((AndOpNode) node).getRight().accept(this);

            }

            if (node instanceof DiffOpNode) {
                ((DiffOpNode) node).getLeft().accept(this);
                code = code + " - ";
                ((DiffOpNode) node).getRight().accept(this);
            }

            if (node instanceof DivOpNode) {
                ((DivOpNode) node).getLeft().accept(this);
                code = code + " / ";
                ((DivOpNode) node).getRight().accept(this);
            }

            if (node instanceof DivIntOpNode) {
                ((DivIntOpNode) node).getLeft().accept(this);
                code = code + " / ";
                ((DivIntOpNode) node).getRight().accept(this);
            }

            if (node instanceof StrConcatOpNode) {
                StrConcatOpNode strConcatOpNode = (StrConcatOpNode) node;

                if (strConcatOpNode.getRight().getType() == CircuitSym.REAL) {
                    code = code + "concatRealToString(";
                    if (!funPlus.contains("concatRealToString")) {
                        String convertReal =
                                "char* concatRealToString(char *s1, float i) {\n" +
                                        "    char* s = malloc(256);\n" +
                                        "    sprintf(s, \"%s%.2f\", s1, i);\n" +
                                        "    return s;\n" +
                                        "}\n\n";
                        String subString = code.substring(0, lenghtAtStartFuns);
                        String subString2 = code.substring(lenghtAtStartFuns);
                        code = subString + convertReal + subString2;
                        funPlus.add("concatRealToString");
                    }
                    strConcatOpNode.getLeft().accept(this);
                    code = code + ",";
                    strConcatOpNode.getRight().accept(this);
                }
                if (strConcatOpNode.getRight().getType() == CircuitSym.INTEGER) {
                    code = code + "concatIntegerToString(";
                    if (!funPlus.contains("concatIntegerToString")) {
                        String convertReal =
                                "char* concatIntegerToString(char *s1, int i) {\n" +
                                        "    char* s = malloc(256);\n" +
                                        "    sprintf(s, \"%s%d\", s1, i);\n" +
                                        "    return s;\n" +
                                        "}\n\n";
                        String subString = code.substring(0, lenghtAtStartFuns);
                        String subString2 = code.substring(lenghtAtStartFuns);
                        code = subString + convertReal + subString2;
                        funPlus.add("concatIntegerToString");
                    }
                    strConcatOpNode.getLeft().accept(this);
                    code = code + ",";
                    strConcatOpNode.getRight().accept(this);
                }
                if (strConcatOpNode.getRight() instanceof StringConstNode || strConcatOpNode.getRight().getType() == CircuitSym.STRING) {
                    code = code + "concatStringToString(";
                    if (!funPlus.contains("concatStringToString")) {
                        String convertReal =
                                "char* concatStringToString(char *s1, char* i) {\n" +
                                        "    char* s = malloc(256);\n" +
                                        "    sprintf(s, \"%s%s\", s1, i);\n" +
                                        "    return s;\n" +
                                        "}\n\n";
                        String subString = code.substring(0, lenghtAtStartFuns);
                        String subString2 = code.substring(lenghtAtStartFuns);
                        code = subString + convertReal + subString2;
                        funPlus.add("concatStringToString");
                    }
                    strConcatOpNode.getLeft().accept(this);
                    code = code + ",";
                    strConcatOpNode.getRight().accept(this);
                }
                code = code + ")";
            }

            if (node instanceof EQOpNode) {
                if (((EQOpNode) node).getLeft().getType() == CircuitSym.STRING) {
                    code = code + "strcmp(";
                    ((EQOpNode) node).getLeft().accept(this);
                    code = code + ", ";
                    ((EQOpNode) node).getRight().accept(this);
                    code = code + ") == 0";
                } else {
                    ((EQOpNode) node).getLeft().accept(this);
                    code = code + "== ";
                    ((EQOpNode) node).getRight().accept(this);
                }
            }

            if (node instanceof GEOpNode) {
                if (((GEOpNode) node).getLeft().getType() == CircuitSym.STRING) {
                    code = code + "strcmp(";
                    ((GEOpNode) node).getLeft().accept(this);
                    code = code + ", ";
                    ((GEOpNode) node).getRight().accept(this);
                    code = code + ") >= 0";
                } else {
                    ((GEOpNode) node).getLeft().accept(this);
                    code = code + ">= ";
                    ((GEOpNode) node).getRight().accept(this);
                }
            }

            if (node instanceof GTOpNode) {
                if (((GTOpNode) node).getLeft().getType() == CircuitSym.STRING) {
                    code = code + "strcmp(";
                    ((GTOpNode) node).getLeft().accept(this);
                    code = code + ", ";
                    ((GTOpNode) node).getRight().accept(this);
                    code = code + ") > 0";
                } else {
                    ((GTOpNode) node).getLeft().accept(this);
                    code = code + "> ";
                    ((GTOpNode) node).getRight().accept(this);
                }
            }

            if (node instanceof LEOpNode) {
                if (((LEOpNode) node).getLeft().getType() == CircuitSym.STRING) {
                    code = code + "strcmp(";
                    ((LEOpNode) node).getLeft().accept(this);
                    code = code + ", ";
                    ((LEOpNode) node).getRight().accept(this);
                    code = code + ") <= 0";
                } else {
                    ((LEOpNode) node).getLeft().accept(this);
                    code = code + "<= ";
                    ((LEOpNode) node).getRight().accept(this);
                }
            }

            if (node instanceof LTOpNode) {
                if (((LTOpNode) node).getLeft().getType() == CircuitSym.STRING) {
                    code = code + "strcmp(";
                    ((LTOpNode) node).getLeft().accept(this);
                    code = code + ", ";
                    ((LTOpNode) node).getRight().accept(this);
                    code = code + ") < 0";
                } else {
                    ((LTOpNode) node).getLeft().accept(this);
                    code = code + "< ";
                    ((LTOpNode) node).getRight().accept(this);
                }
            }

            if (node instanceof MulOpNode) {
                ((MulOpNode) node).getLeft().accept(this);
                code = code + " * ";
                ((MulOpNode) node).getRight().accept(this);
            }

            if (node instanceof NEOpNode) {
                if (((NEOpNode) node).getLeft().getType() == CircuitSym.STRING) {
                    code = code + "strcmp(";
                    ((NEOpNode) node).getLeft().accept(this);
                    code = code + ", ";
                    ((NEOpNode) node).getRight().accept(this);
                    code = code + ") != 0";
                } else {
                    ((NEOpNode) node).getLeft().accept(this);
                    code = code + "!= ";
                    ((NEOpNode) node).getRight().accept(this);
                }
            }

            if (node instanceof LparRparNode) {
                code = code + "(";
                ((LparRparNode) node).getChild().accept(this);
                code = code + ")";
            }

            if (node instanceof NotOpNode) {
                code = code + " ! ";
                ((NotOpNode) node).getChild().accept(this);
            }

            if (node instanceof OrOpNode) {
                ((OrOpNode) node).getLeft().accept(this);
                code = code + " || ";
                ((OrOpNode) node).getRight().accept(this);
            }

            if (node instanceof UminusOpNode) {
                code = code + " - ";
                ((UminusOpNode) node).getChild().accept(this);
            }

            if (node instanceof CallFunNode) {
                CallFunNode tmp = (CallFunNode) node;

                tmp.getIdentifierNode().accept(this);
                if (tmp.getExprListNode() != null && tmp.getExprListNode().getExprNodeList() != null) {
                    for (ExprNode exprNode : tmp.getExprListNode().getExprNodeList()) {
                        if (exprNode instanceof CallFunNode && exprNode.getTypes().size() > 1) {
                            throw new Error("Imposibile generare codice C per una funzione a cui viene passata una funzione con più valori di ritorno");
                        }
                    }

                    code = code + "( ";
                    tmp.getExprListNode().accept(this);
                    code = code + ") ";
                } else {
                    code = code + "() ";
                }
            }

            if (node instanceof ExprListNode) {
                ExprListNode tmp = (ExprListNode) node;

                List<ExprNode> exprNodeList = tmp.getExprNodeList();
                for (int i = 0; i < exprNodeList.size(); i++) {
                    if (exprNodeList.get(i) != null) {
                        if (i >= exprNodeList.size() - 1) {
                            if (exprNodeList.get(i).getType() == CircuitSym.STRING && exprNodeList.get(i) instanceof IdentifierNode) {
                                code = code + "&";
                            }
                        }
                        exprNodeList.get(i).accept(this);
                        if (i < exprNodeList.size() - 1) {
                            code = code + ", ";
                        }
                    }
                }
            }

            if (node instanceof AssignStatOpNode) {
                AssignStatOpNode tmp = (AssignStatOpNode) node;

                IdentifierNode identifierNode = tmp.getIdentifierNode();
                ExprNode exprNode = tmp.getExprNode();

                if (exprNode != null) {
                    if (exprNode instanceof CallFunNode) {
                        CallFunNode callFunNode = (CallFunNode) exprNode;

                        ExprListNode exprListNodeList = callFunNode.getExprListNode();
                        if (exprListNodeList != null) {
                            for (ExprNode tmpExprNode : exprListNodeList.getExprNodeList()) {
                                if (tmpExprNode instanceof CallFunNode && tmpExprNode.getTypes().size() > 1) {
                                    throw new Error("Imposibile generare codice C per una funzione a cui viene passata una funzione con più valori di ritorno");
                                }
                            }
                        }
                        identifierNode.accept(this);
                        code = code + " = ";
                        callFunNode.getIdentifierNode().accept(this);
                        code = code + "(";
                        if (callFunNode.getExprListNode() != null) {
                            callFunNode.getExprListNode().accept(this);
                        }
                        code = code + ");\n";
                    } else {
                        if (identifierNode.getType() == CircuitSym.STRING && tmp.getExprNode().getType() == CircuitSym.STRING) {
                            code = code + "*";
                        }
                        identifierNode.accept(this);
                        code = code + " = ";
                        tmp.getExprNode().accept(this);
                        code = code + ";\n";
                    }
                }
            }

            if (node instanceof ReadStatOpNode) {
                ReadStatOpNode tmp = (ReadStatOpNode) node;

                if (tmp.getIdListNode() != null) {
                    List<Integer> identifierTypeList = tmp.getIdListNode().getTypes();
                    List<IdentifierNode> identifierNodeList = tmp.getIdListNode().getIdListNode();

                    if (tmp.getExprNode() instanceof StringConstNode) {
                        code = code + "\nprintf(";
                        tmp.getExprNode().accept(this);
                        code = code + ");\n";
                    }
                    code = code + "\nscanf(\"";
                    for (int i = 0; i < identifierTypeList.size(); i++) {
                        switch (identifierTypeList.get(i)) {
                            case CircuitSym.INTEGER:
                                code = code + "%d";
                                break;
                            case CircuitSym.REAL:
                                code = code + "%g";
                                break;
                            case CircuitSym.STRING:
                                code = code + "%s";
                                break;
                            case CircuitSym.BOOL:
                                code = code + "%d";
                                break;
                        }

                        if (i < identifierTypeList.size() - 1) {
                            code = code + " ";
                        }
                    }
                    code = code + "\", ";
                    for (int i = 0; i < identifierTypeList.size(); i++) {
                        switch (identifierTypeList.get(i)) {
                            case CircuitSym.BOOL:
                            case CircuitSym.INTEGER:
                            case CircuitSym.REAL:
                                code = code + "&";
                                identifierNodeList.get(i).accept(this);
                                break;
                            case CircuitSym.STRING:
                                identifierNodeList.get(i).accept(this);
                                break;
                        }

                        if (i < identifierTypeList.size() - 1) {
                            code = code + ", ";
                        }
                    }
                    code = code + ");\n";
                }
            }

            if (node instanceof WriteStatOpNode) {
                WriteStatOpNode tmp = (WriteStatOpNode) node;
                tmp.setType(CircuitSym.error);
                if (tmp.getExprNode() != null) {
                    ExprNode exprNode = tmp.getExprNode();
                    Integer exprType = tmp.getExprNode().getType();

                    code = code + "\n";

                    if (exprNode instanceof CallFunNode) {
                        ExprListNode exprListNodeList = ((CallFunNode) exprNode).getExprListNode();
                        if (exprListNodeList != null) {
                            for (ExprNode tmpExprNode : exprListNodeList.getExprNodeList()) {
                                if (tmpExprNode instanceof CallFunNode && tmpExprNode.getTypes().size() > 1) {
                                    throw new Error("Imposibile generare codice C per una funzione a cui viene passata una funzione con più valori di ritorno");
                                }
                            }
                        }
                    }

                    if (exprNode instanceof CallFunNode && exprNode.getType() != null) {
                        code = code + ((CallFunNode) exprNode).getIdentifierNode().getIndex() + " = ";
                        ((CallFunNode) exprNode).getIdentifierNode().accept(this);
                        code = code + "(";
                        if (((CallFunNode) exprNode).getExprListNode() != null) {
                            ((CallFunNode) exprNode).getExprListNode().accept(this);
                        }
                        code = code + ");\n";
                        String tipo = "";

                        switch (exprNode.getType()) {
                            case CircuitSym.INTEGER:
                                tipo = "int ";
                                break;
                            case CircuitSym.REAL:
                                tipo = "float ";
                                break;
                            case CircuitSym.STRING:
                                tipo = "char* ";
                                break;
                            case CircuitSym.BOOL:
                                tipo = "int ";
                                break;
                        }


                        code = code + tipo + " = " + ((CallFunNode) exprNode).getIdentifierNode().getIndex() + ";\n";
                    }

                    code = code + "printf(\"";
                    switch (exprType) {
                        case CircuitSym.INTEGER:
                            code = code + "%d";
                            break;
                        case CircuitSym.REAL:
                            code = code + "%g";
                            break;
                        case CircuitSym.STRING:
                            code = code + "%s";
                            break;
                        case CircuitSym.BOOL:
                            code = code + "%s";
                            break;
                    }
                    if (tmp.getTypeWrite() == CircuitSym.WRITELN) {
                        code = code + "\\n";
                    } else if (tmp.getTypeWrite() == CircuitSym.WRITEB) {
                        code = code + "\\b";
                    } else if (tmp.getTypeWrite() == CircuitSym.WRITET) {
                        code = code + "\\t";
                    }
                    code = code + "\",";

                    if (exprNode instanceof CallFunNode && exprNode.getType() != null) {
                        Integer tipo = exprNode.getType();
                        switch (tipo) {
                            case CircuitSym.INTEGER:
                                break;
                            case CircuitSym.REAL:
                                break;
                            case CircuitSym.STRING:
                                break;
                            case CircuitSym.BOOL:
                                code = code + " == 1 ? \"true\" : \"false\"";
                                break;
                        }
                    } else {
                        Integer tipo = null;
                        if (exprNode.getType() != null) {
                            tipo = exprNode.getType();
                        }
                        switch (tipo) {
                            case CircuitSym.INTEGER:
                                exprNode.accept(this);
                                break;
                            case CircuitSym.REAL:
                                exprNode.accept(this);
                                break;
                            case CircuitSym.STRING:
                                exprNode.accept(this);
                                break;
                            case CircuitSym.BOOL:
                                exprNode.accept(this);
                                code = code + " == 1 ? \"true\" : \"false\"";
                                break;
                        }
                    }

                    code = code + ");\n";
                }
            }

            if (node instanceof StatListNode) {
                StatListNode tmp = (StatListNode) node;

                List<StatNode> statNodeList = tmp.getStatListNode();

                for (int i = 0; i < statNodeList.size(); i++) {
                    if (statNodeList.get(i) != null) {
                        if (statNodeList.get(i) instanceof IdentifierNode || statNodeList.get(i) instanceof AddOpNode || statNodeList.get(i) instanceof DiffOpNode || statNodeList.get(i) instanceof MulOpNode || statNodeList.get(i) instanceof IntegerConstNode || statNodeList.get(i) instanceof StringConstNode || statNodeList.get(i) instanceof RealConstNode || statNodeList.get(i) instanceof BoolConstNode || statNodeList.get(i) instanceof UminusOpNode) {
                            code = code + "return ";
                        }
                        ((AbstractSyntaxNode) statNodeList.get(i)).accept(this);
                        if (statNodeList.get(i) instanceof IdentifierNode || statNodeList.get(i) instanceof AddOpNode || statNodeList.get(i) instanceof CallFunNode || statNodeList.get(i) instanceof DiffOpNode || statNodeList.get(i) instanceof MulOpNode || statNodeList.get(i) instanceof IntegerConstNode || statNodeList.get(i) instanceof StringConstNode || statNodeList.get(i) instanceof RealConstNode || statNodeList.get(i) instanceof BoolConstNode || statNodeList.get(i) instanceof UminusOpNode) {
                            code = code + ";\n";
                        }
                    }
                }
            }

            if (node instanceof IfStatOpNode) {
                IfStatOpNode tmp = (IfStatOpNode) node;

                code = code + "if (";
                tmp.getExprNode().accept(this);
                code = code + ") {\n";
                if (tmp.getBodyOp() != null) {
                    tmp.getBodyOp().accept(this);
                }
                code = code + "}";
                if (tmp.getElseNode() != null) {
                    tmp.getElseNode().accept(this);
                }
            }

            if (node instanceof ElseOpNode) {
                ElseOpNode tmp = (ElseOpNode) node;

                code = code + "else {\n";
                if (tmp.getBodyOp() != null) {
                    tmp.getBodyOp().accept(this);
                }
                code = code + "}\n";
            }

            if (node instanceof WhileStatOpNode) {
                WhileStatOpNode tmp = (WhileStatOpNode) node;

                code = code + "while (";
                tmp.getExprNode().accept(this);
                code = code + ") {\n";
                if (tmp.getBodyOp() != null) {
                    tmp.getBodyOp().accept(this);
                }
                code = code + "}";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
