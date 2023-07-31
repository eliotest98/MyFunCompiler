package ast.visitors;

import ast.*;
import symbol_table.SymbolTable;
import symbol_table.SymbolTableRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TypeCheckerVisitor implements Visitor {

    Stack<SymbolTable<String, SymbolTableRecord>> symbolTableStack;

    public TypeCheckerVisitor() {
        this.symbolTableStack = new Stack<>();
    }

    protected Integer optype1(Integer op, Integer t1) {
        switch (op) {
            case CircuitSym.MINUS:
                if (t1 == CircuitSym.REAL || t1 == CircuitSym.INTEGER)
                    return t1;  //sarà real o integer a seconda del tipo matchato
            case CircuitSym.NOT:
                if (t1 == CircuitSym.BOOL) return CircuitSym.BOOL;
            default:
                return CircuitSym.error;
        }
    }

    protected Integer optype2(Integer op, Integer t1, Integer t2) {
        switch (op) {
            case CircuitSym.PLUS:
            case CircuitSym.MINUS:
            case CircuitSym.TIMES:
            case CircuitSym.DIV:
            case CircuitSym.POW:
                if (t1 == CircuitSym.INTEGER && t2 == CircuitSym.INTEGER) return CircuitSym.INTEGER;
                else if (t1 == CircuitSym.INTEGER && t2 == CircuitSym.REAL) return CircuitSym.REAL;
                else if (t1 == CircuitSym.REAL && t2 == CircuitSym.INTEGER) return CircuitSym.REAL;
                else if (t1 == CircuitSym.REAL && t2 == CircuitSym.REAL) return CircuitSym.REAL;
                break;
            case CircuitSym.DIVINT:
                if (t1 == CircuitSym.INTEGER && t2 == CircuitSym.INTEGER) return CircuitSym.INTEGER;
                else if (t1 == CircuitSym.INTEGER && t2 == CircuitSym.REAL) return CircuitSym.INTEGER;
                else if (t1 == CircuitSym.REAL && t2 == CircuitSym.INTEGER) return CircuitSym.INTEGER;
                else if (t1 == CircuitSym.REAL && t2 == CircuitSym.REAL) return CircuitSym.INTEGER;
                break;
            case CircuitSym.AND:
                if (t1 == CircuitSym.BOOL && t2 == CircuitSym.BOOL) return CircuitSym.BOOL;
                break;
            case CircuitSym.OR:
                if (t1 == CircuitSym.BOOL || t2 == CircuitSym.BOOL) return CircuitSym.BOOL;
                break;
            case CircuitSym.EQ:
            case CircuitSym.NE:
            case CircuitSym.GT:
            case CircuitSym.GE:
            case CircuitSym.LT:
            case CircuitSym.LE:
                if (t1 == CircuitSym.BOOL && t2 == CircuitSym.BOOL) return CircuitSym.BOOL;
                else if (t1 == CircuitSym.INTEGER && t2 == CircuitSym.INTEGER) return CircuitSym.BOOL;
                else if (t1 == CircuitSym.INTEGER && t2 == CircuitSym.REAL) return CircuitSym.BOOL;
                else if (t1 == CircuitSym.REAL && t2 == CircuitSym.INTEGER) return CircuitSym.BOOL;
                else if (t1 == CircuitSym.REAL && t2 == CircuitSym.REAL) return CircuitSym.BOOL;
                else if (t1 == CircuitSym.STRING && t2 == CircuitSym.STRING) return CircuitSym.BOOL;
                break;
            case CircuitSym.STR_CONCAT:
                if (t1 == CircuitSym.STRING && t2 == CircuitSym.STRING) return CircuitSym.STRING;
                if (t1 == CircuitSym.STRING && t2 == CircuitSym.REAL) return CircuitSym.STRING;
                if (t1 == CircuitSym.REAL && t2 == CircuitSym.STRING) return CircuitSym.STRING;
                if (t1 == CircuitSym.INTEGER && t2 == CircuitSym.STRING) return CircuitSym.STRING;
                if (t1 == CircuitSym.STRING && t2 == CircuitSym.INTEGER) return CircuitSym.STRING;
                if (t1 == CircuitSym.BOOL && t2 == CircuitSym.STRING) return CircuitSym.STRING;
                if (t1 == CircuitSym.STRING && t2 == CircuitSym.BOOL) return CircuitSym.STRING;
                break;
            default:
                return CircuitSym.error;
        }
        return CircuitSym.error;
    }

    @Override
    public Object visit(AbstractSyntaxNode node) {

        if (node instanceof ProgramNode) {
            ProgramNode tmp = (ProgramNode) node;

            symbolTableStack.push(tmp.getSymbolTable());

            VarDeclListNode varDeclListNode = tmp.getVarDeclListNode();
            if (varDeclListNode != null && varDeclListNode.getType() == null) {
                varDeclListNode.accept(this);
            }
            FunListNode funListNode = tmp.getFunListNode();
            if (funListNode != null && funListNode.getType() == null) {
                funListNode.accept(this);
            }

            BodyOp bodyOp = tmp.getBodyOp();
            if(bodyOp != null && bodyOp.getType() == null) {
                bodyOp.accept(this);
            }

            symbolTableStack.pop();
        }

        if (node instanceof FunListNode) {
            FunListNode tmp = (FunListNode) node;
            List<FunOpNode> funOpNodeList = tmp.getFunOpNodeList();
            for (FunOpNode funOpNode : funOpNodeList) {
                if (funOpNode != null && funOpNode.getType() == null) {
                    funOpNode.accept(this);
                }
            }
        }

        if (node instanceof FunOpNode) {
            FunOpNode tmp = (FunOpNode) node;
            node.setType(CircuitSym.VAR);

            IdentifierNode identifierNode = tmp.getIdentifierNode();
            SymbolTableRecord symbolTableRecord = symbolTableStack.peek().lookup(identifierNode.getIndex());

            if (symbolTableRecord == null) {
                node.setType(CircuitSym.error);
                throw new Error(tmp.getIdentifierNode().getIndex() + " non è stato dichiarato");
            }
            symbolTableRecord.setFrefTrue();
            identifierNode.setType(CircuitSym.VAR);

            symbolTableStack.push(tmp.getSymbolTable());

            //tmp.getTypeNode().setType(CircuitSym.VAR);

            ParamDeclListNode paramDeclListNode = tmp.getParamDeclListNode();
            if (paramDeclListNode != null && paramDeclListNode.getType() == null) {
                paramDeclListNode.accept(this);
            }

            BodyOp bodyOp = tmp.getBodyOp();
            if (bodyOp != null && bodyOp.getType() == null) {
                bodyOp.accept(this);
            }

            symbolTableStack.pop();
        }

        if (node instanceof ParDeclOpNode) {
            ParDeclOpNode tmp = (ParDeclOpNode) node;
            if(tmp.getTypeNode() != null){
                tmp.setType(CircuitSym.VAR);
            }

            List<Integer> typesToAdd = new ArrayList<>();

            IdentifierNode identifierNode = tmp.getIdentifierNode();
            if (identifierNode != null && identifierNode.getType() == null) {
                SymbolTableRecord symbolTableRecord = symbolTableStack.peek().lookup(identifierNode.getIndex());
                if (symbolTableRecord == null) {
                    tmp.setType(CircuitSym.error);
                    throw new Error("Parametro non presente nella tabella dei simboli: " + identifierNode.getIndex());
                }

                symbolTableRecord.setFrefTrue();
                typesToAdd.add(symbolTableRecord.getVarType());
                identifierNode.setType(symbolTableRecord.getVarType());
            }
            node.setTypes(typesToAdd);
        }

        if (node instanceof NonEmptyParamDeclListNode) {
            NonEmptyParamDeclListNode tmp = (NonEmptyParamDeclListNode) node;
            List<ParDeclOpNode> parDeclOpNodeList = tmp.getNonEmptyParamDeclList();

            if (parDeclOpNodeList != null) {
                for (ParDeclOpNode parDeclOpNode : parDeclOpNodeList) {
                    if (parDeclOpNode != null && parDeclOpNode.getTypes() == null) {
                        parDeclOpNode.accept(this);
                    }
                }
            }

            node.setType(CircuitSym.VAR);
        }

        if (node instanceof ParamDeclListNode) {
            ParamDeclListNode tmp = (ParamDeclListNode) node;
            if(tmp.getNonEmptyParamDeclListNode() != null) {
                List<ParDeclOpNode> parDeclOpNodeList = tmp.getNonEmptyParamDeclListNode().getNonEmptyParamDeclList();

                if (parDeclOpNodeList != null) {
                    for (ParDeclOpNode parDeclOpNode : parDeclOpNodeList) {
                        if (parDeclOpNode != null && parDeclOpNode.getTypes() == null) {
                            parDeclOpNode.accept(this);
                        }
                    }
                }
            }

            node.setType(CircuitSym.VAR);
        }

        if (node instanceof CoppiaIdConstNode) {
            CoppiaIdConstNode tmp = (CoppiaIdConstNode) node;
            IdentifierNode identifierNode = (IdentifierNode) tmp.getFirst();
            ConstNode constNode = (ConstNode) tmp.getSecond();

            if (constNode != null && constNode.getType() == null && constNode.getTypes() == null) {
                constNode.accept(this);
            }

            SymbolTableRecord symbolTableRecord = symbolTableStack.peek().lookup(identifierNode.getIndex());
            if (symbolTableRecord == null) {
                node.setType(CircuitSym.error);
                throw new Error(identifierNode.getIndex() + " non è stato dichiarato");
            } else {
                Integer varType = symbolTableRecord.getVarType();
                if (constNode != null) {
                    if (constNode.getType() != varType && constNode.getTypes() == null && !((varType == CircuitSym.REAL) && (constNode.getType() == CircuitSym.INTEGER))) {
                        node.setType(CircuitSym.error);
                        throw new Error("Alla variabile " + identifierNode.getIndex() + " non viene assegnato un tipo corretto");
                    } else if (constNode.getTypes() != null && constNode.getTypes().size() > 0 && constNode.getTypes().get(0) != varType && !((varType == CircuitSym.REAL) && (constNode.getTypes().get(0) == CircuitSym.INTEGER))) {
                        node.setType(CircuitSym.error);
                        throw new Error("Alla variabile " + identifierNode.getIndex() + " non viene assegnato un tipo corretto");
                    } else if (constNode.getTypes() != null && constNode.getTypes().size() > 1) {
                        node.setType(CircuitSym.error);
                        throw new Error("Alla variabile " + identifierNode.getIndex() + " non viene assegnato un tipo corretto");
                    }
                }
            }
            symbolTableRecord.setFrefTrue();
            identifierNode.setType(symbolTableRecord.getVarType());
            node.setType(symbolTableRecord.getVarType());

            tmp.setType(symbolTableRecord.getVarType());
        }

        if (node instanceof CoppiaIdExprNode) {
            CoppiaIdExprNode tmp = (CoppiaIdExprNode) node;
            IdentifierNode identifierNode = (IdentifierNode) tmp.getFirst();
            ExprNode exprNode = (ExprNode) tmp.getSecond();

            if (exprNode != null && exprNode.getType() == null && exprNode.getTypes() == null) {
                exprNode.accept(this);
            }

            SymbolTableRecord symbolTableRecord = symbolTableStack.peek().lookup(identifierNode.getIndex());
            if (symbolTableRecord == null) {
                node.setType(CircuitSym.error);
                throw new Error(identifierNode.getIndex() + " non è stato dichiarato");
            } else {
                Integer varType = symbolTableRecord.getVarType();
                if (exprNode != null && !(exprNode instanceof CallFunNode)) {
                    if (exprNode.getType() != varType && exprNode.getTypes() == null && !((varType == CircuitSym.REAL) && (exprNode.getType() == CircuitSym.INTEGER))) {
                        node.setType(CircuitSym.error);
                        throw new Error("Alla variabile " + identifierNode.getIndex() + " non viene assegnato un tipo corretto");
                    } else if (exprNode.getTypes() != null && exprNode.getTypes().size() > 0 && exprNode.getTypes().get(0) != varType && !((varType == CircuitSym.REAL) && (exprNode.getTypes().get(0) == CircuitSym.INTEGER))) {
                        node.setType(CircuitSym.error);
                        throw new Error("Alla variabile " + identifierNode.getIndex() + " non viene assegnato un tipo corretto");
                    } else if (exprNode.getTypes() != null && exprNode.getTypes().size() > 1) {
                        node.setType(CircuitSym.error);
                        throw new Error("Alla variabile " + identifierNode.getIndex() + " non viene assegnato un tipo corretto");
                    }
                }
            }
            symbolTableRecord.setFrefTrue();
            identifierNode.setType(symbolTableRecord.getVarType());
            node.setType(symbolTableRecord.getVarType());

            tmp.setType(symbolTableRecord.getVarType());
        }

        if (node instanceof VarDeclOpNode) {
            VarDeclOpNode tmp = (VarDeclOpNode) node;

            if (tmp.getTypeNode() != null) {
                tmp.getTypeNode().setType(CircuitSym.VAR);
            }

            IdListInitNode idListNode = tmp.getIdInitListNode();
            IdListInitObblNode idListObblNode = tmp.getIdInitListObblNode();
            List<Integer> typesToAdd = new ArrayList<>();

            if (idListNode != null) {
                List<CoppiaIdExprNode> coppiaIdExprNodeList = idListNode.getListCoppiaIdExprNode();

                if (coppiaIdExprNodeList != null) {
                    for (CoppiaIdExprNode coppiaIdExprNode : coppiaIdExprNodeList) {
                        if (coppiaIdExprNode != null && coppiaIdExprNode.getType() == null) {
                            coppiaIdExprNode.accept(this);
                        }
                        typesToAdd.add(coppiaIdExprNode.getType());
                    }
                }
                idListNode.setTypes(typesToAdd);
            }
            if (idListObblNode != null) {
                List<CoppiaIdConstNode> coppiaIdConstNodeList = idListObblNode.getListCoppiaIdConstNode();

                if (coppiaIdConstNodeList != null) {
                    for (CoppiaIdConstNode coppiaIdConstNode : coppiaIdConstNodeList) {

                        if (coppiaIdConstNode != null && coppiaIdConstNode.getType() == null) {
                            coppiaIdConstNode.accept(this);
                        }
                        typesToAdd.add(coppiaIdConstNode.getType());
                    }
                }
                idListObblNode.setTypes(typesToAdd);
            }
            tmp.setTypes(typesToAdd);
        }

        if (node instanceof VarDeclListNode) {
            VarDeclListNode tmp = (VarDeclListNode) node;

            List<VarDeclOpNode> varDeclOpNodeList = tmp.getVarDeclOpNodeList();

            if (varDeclOpNodeList != null) {
                for (VarDeclOpNode varDeclOpNode : varDeclOpNodeList) {
                    if (varDeclOpNode != null && varDeclOpNode.getTypes() == null) {
                        varDeclOpNode.accept(this);
                    }
                }
            }
        }

        if (node instanceof StatListNode) {
            StatListNode tmp = (StatListNode) node;

            List<StatNode> statNodeList = tmp.getStatListNode();

            if (statNodeList != null) {
                for (StatNode statNode : statNodeList) {
                    AbstractSyntaxNode abstractSyntaxNode = (AbstractSyntaxNode) statNode;
                    if (abstractSyntaxNode != null && abstractSyntaxNode.getType() == null) {
                        abstractSyntaxNode.accept(this);
                    }
                }
            }
        }

        if (node instanceof BodyOp) {
            BodyOp tmp = (BodyOp) node;
            tmp.setType(CircuitSym.VAR);
            VarDeclListNode varDeclListNode = tmp.getVarDeclListNode();
            if (varDeclListNode != null && varDeclListNode.getType() == null) {
                varDeclListNode.accept(this);
            }

            StatListNode statListNode = tmp.getStatListNode();
            if (statListNode != null && statListNode.getType() == null) {
                statListNode.accept(this);
            }
        }

        if (node instanceof RealConstNode) {
            node.setType(CircuitSym.REAL);
        }

        if (node instanceof IntegerConstNode) {
            node.setType(CircuitSym.INTEGER);
        }

        if (node instanceof IntegerNode) {
            node.setType(CircuitSym.INTEGER);
        }

        if (node instanceof StringNode) {
            node.setType(CircuitSym.STRING);
        }



        if (node instanceof RealNode) {
            node.setType(CircuitSym.REAL);
        }

        if (node instanceof BoolNode) {
            node.setType(CircuitSym.BOOL);
        }

        if (node instanceof TrueNode) {
            node.setType(CircuitSym.BOOL);
        }

        if (node instanceof FalseNode) {
            node.setType(CircuitSym.BOOL);
        }

        if (node instanceof BoolConstNode) {
            node.setType(CircuitSym.BOOL);
        }

        if (node instanceof StringConstNode) {
            node.setType(CircuitSym.STRING);
        }

        if (node instanceof StrConcatOpNode) {
            StrConcatOpNode tmp = (StrConcatOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.STR_CONCAT, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.STR_CONCAT] + ")");
        }

        if (node instanceof AddOpNode) {
            AddOpNode tmp = (AddOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.PLUS, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.PLUS] + ")");
        }

        if (node instanceof AndOpNode) {
            AndOpNode tmp = (AndOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.AND, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.AND] + ")");
        }

        if (node instanceof DiffOpNode) {
            DiffOpNode tmp = (DiffOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.MINUS, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.MINUS] + ")");
        }

        if (node instanceof DivIntOpNode) {
            DivIntOpNode tmp = (DivIntOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.DIVINT, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.DIVINT] + ")");
        }

        if (node instanceof DivOpNode) {
            DivOpNode tmp = (DivOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.DIV, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.DIV] + ")");
        }

        if (node instanceof EQOpNode) {
            EQOpNode tmp = (EQOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.EQ, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.EQ] + ")");
        }

        if (node instanceof GEOpNode) {
            GEOpNode tmp = (GEOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.GE, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.GE] + ")");
        }

        if (node instanceof GTOpNode) {
            GTOpNode tmp = (GTOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.GT, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.GT] + ")");
        }

        if (node instanceof GTOpNode) {
            GTOpNode tmp = (GTOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.GT, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.GT] + ")");
        }

        if (node instanceof LEOpNode) {
            LEOpNode tmp = (LEOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.LE, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.LE] + ")");
        }

        if (node instanceof LTOpNode) {
            LTOpNode tmp = (LTOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.LT, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.LT] + ")");
        }

        if (node instanceof MulOpNode) {
            MulOpNode tmp = (MulOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.TIMES, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.TIMES] + ")");
        }

        if (node instanceof PowOpNode) {
            PowOpNode tmp = (PowOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.POW, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.NE] + ")");
        }

        if (node instanceof NEOpNode) {
            NEOpNode tmp = (NEOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.NE, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.NE] + ")");
        }

        if (node instanceof LparRparNode) {
            LparRparNode tmp = (LparRparNode) node;
            ExprNode child = tmp.getChild();
            if (child.getType() == null && child.getTypes() == null) {
                child.accept(this);
            }
            int type1;
            if (child.getType() != null) {
                type1 = child.getType();
            } else if (child.getTypes() != null && child.getTypes().size() == 1) {
                type1 = child.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(type1);
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[child.getType()]
                        + ") per operazione unaria ("
                        + CircuitSym.terminalNames[CircuitSym.NOT] + ")");
        }

        if (node instanceof NotOpNode) {
            NotOpNode tmp = (NotOpNode) node;
            ExprNode child = tmp.getChild();
            if (child.getType() == null && child.getTypes() == null) {
                child.accept(this);
            }
            int type1;
            if (child.getType() != null) {
                type1 = child.getType();
            } else if (child.getTypes() != null && child.getTypes().size() == 1) {
                type1 = child.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype1(CircuitSym.NOT, type1));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[child.getType()]
                        + ") per operazione unaria ("
                        + CircuitSym.terminalNames[CircuitSym.NOT] + ")");
        }

        if (node instanceof OrOpNode) {
            OrOpNode tmp = (OrOpNode) node;
            ExprNode left = tmp.getLeft();
            ExprNode right = tmp.getRight();
            if (left.getType() == null && left.getTypes() == null) {
                left.accept(this);
            }
            if (right.getType() == null && right.getTypes() == null) {
                right.accept(this);
            }
            int type1;
            int type2;
            if (right.getType() != null) {
                type1 = right.getType();
            } else if (right.getTypes() != null && right.getTypes().size() == 1) {
                type1 = right.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            if (left.getType() != null) {
                type2 = left.getType();
            } else if (left.getTypes() != null && left.getTypes().size() == 1) {
                type2 = left.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype2(CircuitSym.OR, type1, type2));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[left.getType()]
                        + "," + CircuitSym.terminalNames[right.getType()]
                        + ") per operazione binaria ("
                        + CircuitSym.terminalNames[CircuitSym.OR] + ")");
        }

        if (node instanceof UminusOpNode) {
            UminusOpNode tmp = (UminusOpNode) node;
            ExprNode child = tmp.getChild();
            if (child.getType() == null && child.getTypes() == null) {
                child.accept(this);
            }
            int type1;
            if (child.getType() != null) {
                type1 = child.getType();
            } else if (child.getTypes() != null && child.getTypes().size() == 1) {
                type1 = child.getTypes().get(0);
            } else {
                throw new Error("Operazione con errori di tipo per un operando");
            }
            node.setType(optype1(CircuitSym.MINUS, type1));
            if (node.getType() == CircuitSym.error)
                throw new Error("Errore di tipo ("
                        + CircuitSym.terminalNames[child.getType()]
                        + ") per operazione unaria ("
                        + CircuitSym.terminalNames[CircuitSym.MINUS] + ")");
        }

        if (node instanceof IdentifierNode) {
            IdentifierNode tmp = (IdentifierNode) node;
            SymbolTableRecord symbolTableRecord = symbolTableStack.peek().lookup(tmp.getIndex());
            if (symbolTableRecord == null) {
                tmp.setType(CircuitSym.error);
                throw new Error("Identificatore non dichiarato: " + tmp.getIndex());
            } else {
                if (symbolTableRecord.getKind().equals("fun")) {
                    if (!(tmp.getParent() instanceof CallFunNode)) {
                        tmp.setType(CircuitSym.error);
                        throw new Error("Funzione non invocata correttamente: " + tmp.getIndex());
                    }
                    CallFunNode callFunNode = (CallFunNode) tmp.getParent();
                    List<Integer> symbolTableParamsType = symbolTableRecord.getParamsType();

                    // La funzione deve essere invocata senza parametri
                    if (symbolTableParamsType == null) {
                        if (callFunNode.getExprListNode() != null) {
                            callFunNode.getExprListNode().accept(this);
                            List<Integer> callFunNodeParamsType = callFunNode.getTypes();
                            if (callFunNodeParamsType != null && callFunNodeParamsType.size() > 0) {
                                tmp.setType(CircuitSym.error);
                                throw new Error("Funzione dovrebbe essere invocata senza parametri: " + tmp.getIndex());
                            }
                        }
                        // La funzione deve essere invocata con parametri
                    } else {
                        if (callFunNode.getExprListNode() == null) {
                            tmp.setType(CircuitSym.error);
                            throw new Error("Funzione deve avere dei parametri: " + tmp.getIndex());
                        }
                        callFunNode.getExprListNode().accept(this);
                        List<Integer> callFunNodeParamsType = callFunNode.getExprListNode().getTypes();

                        if (callFunNodeParamsType == null || callFunNodeParamsType.size() <= 0) {
                            tmp.setType(CircuitSym.error);
                            throw new Error("Funzione deve avere dei parametri: " + tmp.getIndex());
                        }

                        if (callFunNodeParamsType.size() != symbolTableParamsType.size()) {
                            tmp.setType(CircuitSym.error);
                            throw new Error("Funzione non invocata con un numero di parametri corretti: " + tmp.getIndex());
                        }

                        for (int i = 0; i < callFunNodeParamsType.size(); i++) {
                            if (callFunNodeParamsType.get(i) != symbolTableParamsType.get(i) && !((callFunNodeParamsType.get(i) == CircuitSym.INTEGER) && (symbolTableParamsType.get(i) == CircuitSym.REAL))) {
                                tmp.setType(CircuitSym.error);
                                throw new Error("Funzione non invocata con i parametri corretti: " + tmp.getIndex());
                            }
                        }
                    }
                    tmp.setType(symbolTableRecord.getReturnType());
                } else if (symbolTableRecord.getKind().equals("var")) {
                    tmp.setType(symbolTableRecord.getVarType());
                }
            }
        }

        if (node instanceof ExprListNode) {
            ExprListNode tmp = (ExprListNode) node;

            List<ExprNode> exprNodeList = tmp.getExprNodeList();
            List<Integer> types = new ArrayList<>();

            if (exprNodeList != null) {
                for (ExprNode exprNode : exprNodeList) {
                    if (exprNode != null) {
                        if (exprNode.getType() == null && exprNode.getTypes() == null) {
                            exprNode.accept(this);
                        }
                        if (exprNode.getType() != null) {
                            types.add(exprNode.getType());
                        } else if (exprNode.getTypes() != null) {
                            types.addAll(exprNode.getTypes());
                        }
                    }
                }
            }
            node.setTypes(types);
        }

        if (node instanceof CallFunNode) {
            CallFunNode tmp = (CallFunNode) node;

            IdentifierNode identifierNode = tmp.getIdentifierNode();
            if (identifierNode != null && identifierNode.getTypes() == null) {
                identifierNode.accept(this);
            }
            tmp.setType(identifierNode.getType());

            ExprListNode exprListNode = tmp.getExprListNode();
            if (exprListNode != null) {
                if (exprListNode.getTypes() == null) {
                    exprListNode.accept(this);
                }

                List<Integer> exprTypes = exprListNode.getTypes();
                List<Integer> paramsType = symbolTableStack.peek().lookup(tmp.getIdentifierNode().getIndex()).getParamsType();

                if (exprTypes.size() != paramsType.size()) {
                    node.setType(CircuitSym.error);
                    throw new Error("I parametri passati alla funzione " + tmp.getIdentifierNode().getIndex() + " non sono uguali al numero di parametri con cui è stat definita");
                }
                for (int i = 0; i < exprTypes.size(); i++) {
                    if (exprTypes.get(i) != paramsType.get(i) && !((exprTypes.get(i) == CircuitSym.INTEGER) && (paramsType.get(i) == CircuitSym.REAL))) {
                        node.setType(CircuitSym.error);
                        throw new Error("Type missmatch per l'argomento " + i + " della funzione " + tmp.getIdentifierNode().getIndex());
                    }
                }
            }
        }

        if (node instanceof IdListNode) {
            IdListNode tmp = (IdListNode) node;

            List<IdentifierNode> identifierNodeList = tmp.getIdListNode();
            List<Integer> types = new ArrayList<>();

            if (identifierNodeList != null) {
                for (IdentifierNode identifierNode : identifierNodeList) {
                    if (identifierNode != null && identifierNode.getType() == null) {
                        identifierNode.accept(this);
                    }
                    types.add(identifierNode.getType());
                }
            }

            node.setTypes(types);
        }

        if (node instanceof AssignStatOpNode) {
            AssignStatOpNode tmp = (AssignStatOpNode) node;

            IdentifierNode identifierNode = tmp.getIdentifierNode();
            if (identifierNode != null && identifierNode.getTypes() == null) {
                identifierNode.accept(this);
            }
            Integer identifierNodeType = identifierNode.getType();

            ExprNode exprNode = tmp.getExprNode();
            if (exprNode.getTypes() == null) {
                exprNode.accept(this);
            }
            Integer exprType = exprNode.getType();

                if (identifierNodeType != exprType) {
                    node.setType(CircuitSym.error);
                    throw new Error("Assegnazione con errori di tipo");
                }
                // Si può assegnare un intero ad un float
                if (identifierNodeType != exprType && !((identifierNodeType == CircuitSym.REAL) && (exprType == CircuitSym.INTEGER))) {
                    node.setType(CircuitSym.error);
                    throw new Error("Assegnazione con errori di tipo");
                }

            node.setType(CircuitSym.VAR);
        }

        if (node instanceof IfStatOpNode) {
            IfStatOpNode tmp = (IfStatOpNode) node;
            tmp.setType(CircuitSym.VAR);
            symbolTableStack.push(tmp.getSymbolTable());
            ExprNode exprNode = tmp.getExprNode();
            if (exprNode != null && exprNode.getType() == null && exprNode.getTypes() == null) {
                exprNode.accept(this);
                if (!(exprNode.getType() != null && exprNode.getType().equals(CircuitSym.BOOL)) &&
                        !(exprNode.getTypes() != null && exprNode.getTypes().size() == 1 &&
                                exprNode.getTypes().get(0).equals(CircuitSym.BOOL))) {
                    tmp.setType(CircuitSym.error);
                    throw new Error("La condizione di un if non è booleana");
                }
            }

            BodyOp bodyOp = tmp.getBodyOp();
            if (bodyOp != null) {
                bodyOp.accept(this);
            }

            symbolTableStack.pop();

            ElseOpNode elseNode = tmp.getElseNode();
            if (elseNode != null && elseNode.getType() == null) {
                elseNode.accept(this);
            }

        }

        if (node instanceof ElseOpNode) {
            ElseOpNode tmp = (ElseOpNode) node;
            tmp.setType(CircuitSym.VAR);
            symbolTableStack.push(tmp.getSymbolTable());
            if(tmp.getBodyOp() != null){
                tmp.getBodyOp().accept(this);
            }
            symbolTableStack.pop();
        }

        if (node instanceof WhileStatOpNode) {
            WhileStatOpNode tmp = (WhileStatOpNode) node;
            symbolTableStack.push(tmp.getSymbolTable());
            tmp.setType(CircuitSym.VAR);
            ExprNode exprNode = tmp.getExprNode();
            if (exprNode != null && exprNode.getType() == null && exprNode.getTypes() == null) {
                exprNode.accept(this);
                if (!(exprNode.getType() != null && exprNode.getType().equals(CircuitSym.BOOL)) &&
                        !(exprNode.getTypes() != null && exprNode.getTypes().size() == 1 &&
                                exprNode.getTypes().get(0).equals(CircuitSym.BOOL))) {
                    tmp.setType(CircuitSym.error);
                    throw new Error("La condizione di un while non è booleana");
                }
            }

            VarDeclListNode varDeclListNode = tmp.getBodyOp().getVarDeclListNode();
            if (varDeclListNode != null && varDeclListNode.getType() == null) {
                varDeclListNode.accept(this);
            }

            StatListNode bodyStatListNode = tmp.getBodyOp().getStatListNode();
            if (bodyStatListNode != null && bodyStatListNode.getType() == null) {
                bodyStatListNode.accept(this);
            }

            symbolTableStack.pop();

        }

        if (node instanceof ReadStatOpNode) {
            ReadStatOpNode tmp = (ReadStatOpNode) node;
            IdListNode idListNode = tmp.getIdListNode();
            if (idListNode != null && idListNode.getTypes() == null) {
                idListNode.accept(this);
            }

            tmp.setTypes(idListNode.getTypes());
        }

        if (node instanceof WriteStatOpNode) {
            WriteStatOpNode tmp = (WriteStatOpNode) node;
            tmp.setType(CircuitSym.VAR);

            ExprNode exprNode = tmp.getExprNode();
            if (exprNode != null && exprNode.getType() == null) {
                    if(exprNode instanceof CallFunNode){
                        CallFunNode callFunNode = (CallFunNode) exprNode;
                        SymbolTableRecord symbolTableRecord = symbolTableStack.peek().lookup(callFunNode.getIdentifierNode().getIndex());
                        if(symbolTableRecord.getReturnType()==null || symbolTableRecord.getReturnType() == CircuitSym.VAR){
                            tmp.setType(CircuitSym.error);
                            throw new Error("Non puoi stampare il valore restituito da funzioni void");
                        }
                    }
                exprNode.accept(this);
            }

        }

        return null;
    }

}
