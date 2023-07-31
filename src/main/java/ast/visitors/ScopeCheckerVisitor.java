package ast.visitors;

import ast.AbstractSyntaxNode;
import ast.ProgramNode;
import symbol_table.SymbolTable;
import symbol_table.SymbolTableRecord;
import ast.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ScopeCheckerVisitor implements Visitor {

    SymbolTable<String, SymbolTableRecord> rootSymbolTable;
    Stack<SymbolTable<String, SymbolTableRecord>> symbolTableStack;

    public ScopeCheckerVisitor() {
        this.rootSymbolTable = new SymbolTable<>(null);
        this.symbolTableStack = new Stack<>();
    }

    @Override
    public Object visit(AbstractSyntaxNode node) {

        if (node instanceof ProgramNode) {
            // Creazione nuovo scope
            symbolTableStack.push(rootSymbolTable);
            rootSymbolTable.setScopingNode(node);
            ((ProgramNode) node).setSymbolTable(rootSymbolTable);
            //lista variabili
            if (((ProgramNode) node).getVarDeclListNode() != null) {
                ((ProgramNode) node).getVarDeclListNode().accept(this);
            }
            //lista funzioni
            if (((ProgramNode) node).getFunListNode() != null) {
                ((ProgramNode) node).getFunListNode().accept(this);
            }
            //controllo l'esistenza del main
            if (((ProgramNode) node).getBodyOp() != null) {
                ((ProgramNode) node).getBodyOp().accept(this);
            } else {
                throw new Error("Mancata dichiarazione del main");
            }
            symbolTableStack.pop();
        }

        //lista variabili
        if (node instanceof VarDeclListNode && node != null && ((VarDeclListNode) node).getVarDeclOpNodeList() != null) {
            List<VarDeclOpNode> varDeclOpNodeList = ((VarDeclListNode) node).getVarDeclOpNodeList();
            for (VarDeclOpNode varDeclOpNode : varDeclOpNodeList) {
                if (varDeclOpNode != null)
                    varDeclOpNode.accept(this);
            }
        }

        //lista statements
        if (node instanceof StatListNode && node != null && ((StatListNode) node).getStatListNode() != null) {
            ArrayList<StatNode> statNodes = ((StatListNode) node).getStatListNode();
            for (StatNode statNode : statNodes) {
                if (statNode != null)
                    ((AbstractSyntaxNode) statNode).accept(this);
            }
        }

        //variabile singola da aggiungere alla tabella ProgramOp
        if (node instanceof VarDeclOpNode) {
            TypeNode typeNode = ((VarDeclOpNode) node).getTypeNode();
            //se è null vuol dire che è un var
            int varType = CircuitSym.error;
            if (typeNode != null) {
                AbstractSyntaxNode child = typeNode.getChild();
                if (child instanceof IntegerNode) {
                    varType = CircuitSym.INTEGER;
                } else if (child instanceof BoolNode) {
                    varType = CircuitSym.BOOL;
                } else if (child instanceof RealNode) {
                    varType = CircuitSym.REAL;
                } else if (child instanceof StringNode) {
                    varType = CircuitSym.STRING;
                }
                //prendo la lista delle variabili
                ArrayList<CoppiaIdExprNode> coppiaIdExprNodeList = new ArrayList<>();
                if (((VarDeclOpNode) node).getIdInitListNode() != null) {
                    coppiaIdExprNodeList.addAll(((VarDeclOpNode) node).getIdInitListNode().getListCoppiaIdExprNode());
                }
                if (!coppiaIdExprNodeList.isEmpty()) {
                    for (CoppiaIdExprNode tmp : coppiaIdExprNodeList) {
                        //prendo l'id della variabile
                        String varSymbol = ((IdentifierNode) tmp.getFirst()).getIndex();
                        //controllo se è già presente
                        if (symbolTableStack.peek().containsKey(varSymbol)) {
                            throw new Error("Identificatore già dichiarato all'interno dello scope: " + varSymbol);
                        }
                        //aggiungo la variabile alla tabella
                        SymbolTableRecord symbolTableRecord = new SymbolTableRecord(varSymbol, "var", varType);
                        symbolTableStack.peek().put(varSymbol, symbolTableRecord);
                    }
                }
            } else {
                //prendo la lista delle variabili
                ArrayList<CoppiaIdConstNode> coppiaIdConstNodeList = new ArrayList<>();

                if (((VarDeclOpNode) node).getIdInitListObblNode() != null) {
                    coppiaIdConstNodeList.addAll(((VarDeclOpNode) node).getIdInitListObblNode().getListCoppiaIdConstNode());
                }

                if (!coppiaIdConstNodeList.isEmpty()) {
                    for (CoppiaIdConstNode coppiaIdConstNode : coppiaIdConstNodeList) {
                        //prendo l'id della variabile
                        String varSymbol = ((IdentifierNode) coppiaIdConstNode.getFirst()).getIndex();
                        //controllo se è già presente
                        if (symbolTableStack.peek().containsKey(varSymbol)) {
                            throw new Error("Identificatore già dichiarato all'interno dello scope: " + varSymbol);
                        }
                        ConstNode constNode = (ConstNode) coppiaIdConstNode.getSecond();
                        if (constNode != null) {
                            AbstractSyntaxNode child = constNode.getChild();
                            if (child instanceof IntegerConstNode) {
                                varType = CircuitSym.INTEGER;
                                constNode.setType(CircuitSym.INTEGER);
                            } else if (child instanceof BoolConstNode) {
                                varType = CircuitSym.BOOL;
                                constNode.setType(CircuitSym.BOOL);
                            } else if (child instanceof RealConstNode) {
                                varType = CircuitSym.REAL;
                                constNode.setType(CircuitSym.REAL);
                            } else if (child instanceof StringConstNode) {
                                varType = CircuitSym.STRING;
                                constNode.setType(CircuitSym.STRING);
                            }
                        } else {
                            varType = CircuitSym.error;
                        }
                        //aggiungo la variabile alla tabella
                        SymbolTableRecord symbolTableRecord = new SymbolTableRecord(varSymbol, "var", varType);
                        symbolTableStack.peek().put(varSymbol, symbolTableRecord);
                    }
                }
            }
        }

        //aggiungo ogni funzione alla tabella ProgramOp
        if (node instanceof FunListNode) {
            //prendo la lista di funzioni
            List<FunOpNode> funOpNodeList = ((FunListNode) node).getFunOpNodeList();
            for (FunOpNode funOpNode : funOpNodeList) {
                if (funOpNode != null) {
                    //prendo l'id della funzione
                    String procSymbol = funOpNode.getIdentifierNode().getIndex();
                    //controllo se è già presente nella tabella
                    if (symbolTableStack.peek().containsKey(procSymbol)) {
                        throw new Error("Identificatore già dichiarato all'interno dello scope: " + procSymbol);
                    }
                    //prendo il tipo di ritorno della funzione
                    TypeNode resultTypeNode = funOpNode.getTypeNode();
                    Integer returnType = null;
                    if (resultTypeNode != null) {
                        AbstractSyntaxNode typeNode = resultTypeNode.getChild();
                        if (typeNode instanceof IntegerNode) {
                            returnType = CircuitSym.INTEGER;
                        } else if (typeNode instanceof BoolNode) {
                            returnType = CircuitSym.BOOL;
                        } else if (typeNode instanceof RealNode) {
                            returnType = CircuitSym.REAL;
                        } else if (typeNode instanceof StringNode) {
                            returnType = CircuitSym.STRING;
                        } else {
                            returnType = CircuitSym.error;
                        }
                    } else {
                        returnType = CircuitSym.VAR;
                    }
                    if (funOpNode.getParamDeclListNode() != null && funOpNode.getParamDeclListNode().getNonEmptyParamDeclListNode() != null) {
                        //prendo la lista di parametri
                        List<Integer> paramsType = new ArrayList<>();
                        List<ParDeclOpNode> parDeclOpNodeList = funOpNode.getParamDeclListNode().getNonEmptyParamDeclListNode().getNonEmptyParamDeclList();
                        for (ParDeclOpNode parDeclOpNode : parDeclOpNodeList) {
                            AbstractSyntaxNode typeNode = parDeclOpNode.getTypeNode().getChild();
                            //prendo il tipo del parametro
                            int typeToAdd = CircuitSym.error;
                            if (typeNode instanceof IntegerNode) {
                                typeToAdd = CircuitSym.INTEGER;
                            } else if (typeNode instanceof BoolNode) {
                                typeToAdd = CircuitSym.BOOL;
                            } else if (typeNode instanceof RealNode) {
                                typeToAdd = CircuitSym.REAL;
                            } else if (typeNode instanceof StringNode) {
                                typeToAdd = CircuitSym.STRING;
                            }
                            paramsType.add(typeToAdd);
                        }
                        //aggiungo il nome della funzione, la firma e il tipo di ritorno
                        SymbolTableRecord symbolTableRecord = new SymbolTableRecord(procSymbol, "fun", paramsType, returnType);
                        symbolTableStack.peek().put(procSymbol, symbolTableRecord);
                    } else {
                        //se non ha parametri aggiungo solo la funzione e il tipo di ritorno
                        SymbolTableRecord symbolTableRecord = new SymbolTableRecord(procSymbol, "fun", null, returnType);
                        symbolTableStack.peek().put(procSymbol, symbolTableRecord);
                    }

                    funOpNode.accept(this);
                }
            }
        }

        //creo la tabella per il nodo FunOp
        if (node instanceof FunOpNode) {
            //Creazione nuovo scope
            SymbolTable<String, SymbolTableRecord> symbolTable = new SymbolTable<String, SymbolTableRecord>(symbolTableStack.peek());
            symbolTableStack.push(symbolTable);
            symbolTable.setScopingNode(node);
            FunOpNode tmp = ((FunOpNode) node);
            tmp.setSymbolTable(symbolTable);

            if (tmp.getParamDeclListNode() != null) {
                if (tmp.getParamDeclListNode().getNonEmptyParamDeclListNode() != null) {
                    //prendo la lista dei parametri della firma
                    List<ParDeclOpNode> parDeclOpNodeList = tmp.getParamDeclListNode().getNonEmptyParamDeclListNode().getNonEmptyParamDeclList();
                    for (ParDeclOpNode parDeclOpNode : parDeclOpNodeList) {
                        AbstractSyntaxNode typeNode = parDeclOpNode.getTypeNode().getChild();
                        //prendo l'id del parametro
                        String idNode = parDeclOpNode.getIdentifierNode().getIndex();
                        //ricavo il tipo del parametro
                        int varType = CircuitSym.error;
                        if (typeNode instanceof IntegerNode) {
                            varType = CircuitSym.INTEGER;
                        } else if (typeNode instanceof BoolNode) {
                            varType = CircuitSym.BOOL;
                        } else if (typeNode instanceof RealNode) {
                            varType = CircuitSym.REAL;
                        } else if (typeNode instanceof StringNode) {
                            varType = CircuitSym.STRING;
                        }
                        //controllo se è già presente nella tabella
                        if (symbolTableStack.peek().containsKey(idNode)) {
                            throw new Error("Identificatore già dichiarato all'interno dello scope: " + idNode);
                        }
                        //lo aggiungo nel caso
                        SymbolTableRecord symbolTableRecord = new SymbolTableRecord(idNode, "var", varType);
                        symbolTableStack.peek().put(idNode, symbolTableRecord);
                    }
                }
            }
            if (tmp.getBodyOp().getVarDeclListNode() != null) {
                tmp.getBodyOp().getVarDeclListNode().accept(this);
            }
            if (tmp.getBodyOp().getStatListNode() != null) {
                tmp.getBodyOp().getStatListNode().accept(this);
            }
            symbolTableStack.pop();
        }

        //creo la tabella per BodyOp
        if (node instanceof BodyOp) {
            BodyOp tmp = ((BodyOp) node);
            if (tmp.getVarDeclListNode() != null) {
                List<VarDeclOpNode> varDeclOpNodeList = tmp.getVarDeclListNode().getVarDeclOpNodeList();
                for (VarDeclOpNode varDeclOpNode : varDeclOpNodeList) {
                    if (varDeclOpNode != null)
                        varDeclOpNode.accept(this);
                }
            }
            if (tmp.getStatListNode() != null) {
                tmp.getStatListNode().accept(this);
            }
        }

        //creo la tabella per IfStatNode
        if (node instanceof IfStatOpNode) {
            IfStatOpNode tmp = ((IfStatOpNode) node);
            SymbolTable<String, SymbolTableRecord> symbolTable = new SymbolTable<String, SymbolTableRecord>(symbolTableStack.peek());
            symbolTableStack.push(symbolTable);
            symbolTable.setScopingNode(node);
            tmp.setSymbolTable(symbolTable);
            if (tmp.getBodyOp() != null) {
                tmp.getBodyOp().accept(this);
            }
            symbolTableStack.pop();
            if (tmp.getElseNode() != null) {
                tmp.getElseNode().accept(this);
            }
        }

        //creo la tabella per ElseStatNode
        if (node instanceof ElseOpNode) {
            ElseOpNode tmp = ((ElseOpNode) node);
            SymbolTable<String, SymbolTableRecord> symbolTable = new SymbolTable<String, SymbolTableRecord>(symbolTableStack.peek());
            symbolTableStack.push(symbolTable);
            symbolTable.setScopingNode(node);
            if (tmp.getBodyOp() != null && tmp.getBodyOp().getVarDeclListNode() != null) {
                List<VarDeclOpNode> varDeclOpNodeList = tmp.getBodyOp().getVarDeclListNode().getVarDeclOpNodeList();
                for (VarDeclOpNode varDeclOpNode : varDeclOpNodeList) {
                    if (varDeclOpNode != null)
                        varDeclOpNode.accept(this);
                }
            }
            tmp.setSymbolTable(symbolTable);
            symbolTableStack.pop();
            if (tmp.getBodyOp() != null) {
                tmp.getBodyOp().accept(this);
            }
        }

        //creo la tabella per WhileStatNode
        if (node instanceof WhileStatOpNode) {
            WhileStatOpNode tmp = ((WhileStatOpNode) node);
            SymbolTable<String, SymbolTableRecord> symbolTable = new SymbolTable<String, SymbolTableRecord>(symbolTableStack.peek());
            symbolTableStack.push(symbolTable);
            symbolTable.setScopingNode(node);
            tmp.setSymbolTable(symbolTable);
            if (tmp.getBodyOp().getVarDeclListNode() != null) {
                List<VarDeclOpNode> varDeclOpNodeList = tmp.getBodyOp().getVarDeclListNode().getVarDeclOpNodeList();
                for (VarDeclOpNode varDeclOpNode : varDeclOpNodeList) {
                    if (varDeclOpNode != null)
                        varDeclOpNode.accept(this);
                }
            }
            symbolTableStack.pop();
            if (tmp.getBodyOp() != null) {
                tmp.getBodyOp().accept(this);
            }
        }

        return rootSymbolTable;
    }
}
