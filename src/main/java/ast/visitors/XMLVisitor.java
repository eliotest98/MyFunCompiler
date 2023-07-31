package ast.visitors;

import ast.*;

import java.util.Collections;
import java.util.List;

public class XMLVisitor implements Visitor {

    @Override
    public Object visit(AbstractSyntaxNode node) {
        //operations
        if (node instanceof AddOpNode)
            return "<AddOp> " + ((AddOpNode) node).getLeft().accept(this) +
                    " " + ((AddOpNode) node).getRight().accept(this) +
                    " </AddOp>";
        if (node instanceof AndOpNode)
            return "<AndOp> " + ((AndOpNode) node).getLeft().accept(this) +
                    " " + ((AndOpNode) node).getRight().accept(this) +
                    " </AndOp>";
        if (node instanceof AssignStatOpNode)
            return "<AssignOp> " + ((AssignStatOpNode) node).getIdentifierNode().accept(this) +
                    " " + ((AssignStatOpNode) node).getExprNode().accept(this) +
                    " </AssignOp>";
        if (node instanceof NotOpNode)
            return "<NotOp> " + ((NotOpNode) node).getChild().accept(this) +
                    " </NotOp>";
        if (node instanceof OrOpNode)
            return "<OrOp> " + ((OrOpNode) node).getLeft().accept(this) +
                    " " + ((OrOpNode) node).getRight().accept(this) +
                    " </OrOp>";
        if (node instanceof DiffOpNode)
            return "<DiffOp> " + ((DiffOpNode) node).getLeft().accept(this) +
                    " " + ((DiffOpNode) node).getRight().accept(this) +
                    " </DiffOp>";
        if (node instanceof DivOpNode)
            return "<DivOp> " + ((DivOpNode) node).getLeft().accept(this) +
                    " " + ((DivOpNode) node).getRight().accept(this) +
                    " </DivOp>";
        if (node instanceof DivIntOpNode)
            return "<DivIntOp> " + ((DivIntOpNode) node).getLeft().accept(this) +
                    " " + ((DivIntOpNode) node).getRight().accept(this) +
                    " </DivIntOp>";
        if (node instanceof EQOpNode)
            return "<EQOp> " + ((EQOpNode) node).getLeft().accept(this) +
                    " " + ((EQOpNode) node).getRight().accept(this) +
                    " </EQOp>";
        if (node instanceof GEOpNode)
            return "<GEOp> " + ((GEOpNode) node).getLeft().accept(this) +
                    " " + ((GEOpNode) node).getRight().accept(this) +
                    " </GEOp>";
        if (node instanceof GTOpNode)
            return "<GTOp> " + ((GTOpNode) node).getLeft().accept(this) +
                    " " + ((GTOpNode) node).getRight().accept(this) +
                    " </GTOp>";
        if (node instanceof LEOpNode)
            return "<LEOp> " + ((LEOpNode) node).getLeft().accept(this) +
                    " " + ((LEOpNode) node).getRight().accept(this) +
                    " </LEOp>";
        if (node instanceof LTOpNode)
            return "<LTOp> " + ((LTOpNode) node).getLeft().accept(this) +
                    " " + ((LTOpNode) node).getRight().accept(this) +
                    " </LTOp>";
        if (node instanceof MulOpNode)
            return "<MulOp> " + ((MulOpNode) node).getLeft().accept(this) +
                    " " + ((MulOpNode) node).getRight().accept(this) +
                    " </MulOp>";
        if (node instanceof NEOpNode)
            return "<NEOp> " + ((NEOpNode) node).getLeft().accept(this) +
                    " " + ((NEOpNode) node).getRight().accept(this) +
                    " </NEOp>";
        if (node instanceof PowOpNode)
            return "<PowOp> " + ((PowOpNode) node).getLeft().accept(this) +
                    " " + ((PowOpNode) node).getRight().accept(this) +
                    " </PowOp>";
        if (node instanceof UminusOpNode)
            return "<UminusOp> " + ((UminusOpNode) node).getChild().accept(this) +
                    " </UminusOp>";
        if (node instanceof LparRparNode)
            return "<LaprRparOp> " + ((LparRparNode) node).getChild().accept(this) +
                    " </LaprRparOp>";
        if (node instanceof StrConcatOpNode)
            return "<StrCatOp> " + ((StrConcatOpNode) node).getLeft().accept(this) +
                    " " + ((StrConcatOpNode) node).getRight().accept(this) +
                    " </StrCatOp>";
        if (node instanceof CoppiaIdExprNode) {
            CoppiaIdExprNode tmp = (CoppiaIdExprNode) node;
            if (tmp.getSecond() != null) {
                return "<CoppiaIdExpr> (" + tmp.getFirst().accept(this) +
                        " , " + tmp.getSecond().accept(this) +
                        ") </CoppiaIdExpr>";
            } else {
                return "<CoppiaIdExpr> (" + tmp.getFirst().accept(this) +
                        ") </CoppiaIdExpr>";
            }
        }
        if (node instanceof CoppiaIdConstNode) {
            CoppiaIdConstNode tmp = (CoppiaIdConstNode) node;
            if (tmp.getSecond() != null) {
                return "<CoppiaIdConst> (" + tmp.getFirst().accept(this) +
                        " , " + tmp.getSecond().accept(this) +
                        ") </CoppiaIdConst>";
            } else {
                return "<CoppiaIdConst> (" + tmp.getFirst().accept(this) +
                        ") </CoppiaIdConst>";
            }
        }

        //tipi
        if (node instanceof BoolNode)
            return "bool";
        if (node instanceof IntegerNode)
            return "int";
        if (node instanceof StringNode)
            return "string";
        if (node instanceof TrueNode)
            return "true";
        if (node instanceof RealNode)
            return "real";
        if (node instanceof FalseNode)
            return "false";
        //costanti
        if (node instanceof IntegerConstNode)
            return "(integer_const, \"" + ((IntegerConstNode) node).getValue() + "\")";
        if (node instanceof StringConstNode)
            return "(string_const, \"" + ((StringConstNode) node).getValue() + "\")";
        if (node instanceof RealConstNode)
            return "(real_const, \"" + ((RealConstNode) node).getValue() + "\")";
        if (node instanceof BoolConstNode) {
            BoolConstNode temp = (BoolConstNode) node;
            if(temp.getChild() instanceof BoolConstNode) {
                return "(bool_const, \"" + ((BoolConstNode) temp.getChild()).getChild().accept(this) + "\")";
            }
            return "(bool_const, \"" + temp.getChild().accept(this) + "\")";
        }
        //ID
        if (node instanceof IdentifierNode)
            return "(id, \"" + ((IdentifierNode) node).getIndex() +
                    "\")";
        //Op
        if (node instanceof CallFunNode) {
            CallFunNode tmp = (CallFunNode) node;
            if (tmp.getExprListNode() != null) {
                return "<CallFunOp> " + tmp.getIdentifierNode().accept(this) +
                        " <ParamOp> " + tmp.getExprListNode().accept(this) +
                        " </ParamOp> </CallFunOp>";
            } else {
                return "<CallFunOp> " + tmp.getIdentifierNode().accept(this) +
                        " </CallFunOp>";
            }
        }
        if (node instanceof BodyOp) {
            return "<BodyOp>" + ((BodyOp) node).getVarDeclListNode().accept(this) +
                    " " + ((BodyOp) node).getStatListNode().accept(this) +
                    " </BodyOp>";
        }
        if (node instanceof ElseOpNode) {
            if (node != null && ((ElseOpNode) node).getBodyOp() != null)
                return ((ElseOpNode) node).getBodyOp().accept(this);
            else
                return "";
        }
        if (node instanceof ExprListNode) {
            if (node == null)
                return "";
            List<ExprNode> exprNodeList = ((ExprListNode) node).getExprNodeList();
            Collections.reverse(exprNodeList);
            String tmp = "";
            for (ExprNode expr : exprNodeList) {
                if (expr != null)
                    tmp += expr.accept(this);
            }
            return tmp;
        }
        if (node instanceof FunListNode) {
            if (node == null)
                return "";
            List<FunOpNode> funOpNodeList = ((FunListNode) node).getFunOpNodeList();
            Collections.reverse(funOpNodeList);
            String tmp = "";
            for (FunOpNode expr : funOpNodeList) {
                if (expr != null)
                    tmp += " <FunOp> " + expr.accept(this) + " </FunOp> ";
            }
            return tmp;
        }
        if (node instanceof IdListInitNode) {
            if (node == null)
                return "";
            List<CoppiaIdExprNode> coppiaIdExprNodeList = ((IdListInitNode) node).getListCoppiaIdExprNode();
            String tmp = "";
            for (CoppiaIdExprNode coppiaIdExprNode : coppiaIdExprNodeList) {
                if (coppiaIdExprNode != null) {
                    if (coppiaIdExprNode.getSecond() != null)
                        tmp += "<IdInitOp> " + coppiaIdExprNode.getFirst().accept(this) +
                                " " + coppiaIdExprNode.getSecond().accept(this) + " </IdInitOp>";
                    else
                        tmp += coppiaIdExprNode.getFirst().accept(this);
                }
            }
            return tmp;
        }
        if (node instanceof IdListInitObblNode) {
            if (node == null)
                return "";
            List<CoppiaIdConstNode> coppiaIdConstNodeList = ((IdListInitObblNode) node).getListCoppiaIdConstNode();
            String tmp = "";
            for (CoppiaIdConstNode coppiaIdConstNode : coppiaIdConstNodeList) {
                if (coppiaIdConstNode != null) {
                    if (coppiaIdConstNode.getSecond() != null)
                        tmp += "<IdInitOp> " + coppiaIdConstNode.getFirst().accept(this) +
                                " " + coppiaIdConstNode.getSecond().accept(this) + " </IdInitOp>";
                    else
                        tmp += coppiaIdConstNode.getFirst().accept(this);
                }
            }
            return tmp;
        }
        if (node instanceof IdListNode) {
            if (node == null)
                return "";
            List<IdentifierNode> identifierNodeList = ((IdListNode) node).getIdListNode();
            String tmp = "";
            for (IdentifierNode identifierNode : identifierNodeList) {
                if (identifierNode != null)
                    tmp += "(id, \"" + identifierNode.getIndex() + "\")";
            }
            return tmp;
        }
        if (node instanceof ConstNode) {
            ConstNode tmp = (ConstNode) node;
            return tmp.getChild().accept(this);
        }
        if (node instanceof IfStatOpNode) {
            IfStatOpNode tmp = (IfStatOpNode) node;
            return "<IfStatOp> " + tmp.getExprNode().accept(this) +
                    " " + tmp.getBodyOp().accept(this) +
                    " " + tmp.getElseNode().accept(this) +
                    "  </IfStatOp>";
        }
        if (node instanceof FunOpNode) {
            FunOpNode tmp = (FunOpNode) node;
            if (tmp.getTypeNode() == null) {
                return "" + tmp.getIdentifierNode().accept(this) +
                        " " + tmp.getParamDeclListNode().accept(this) +
                        " " + tmp.getBodyOp().accept(this);
            }
            return "" + tmp.getIdentifierNode().accept(this) +
                    " " + tmp.getParamDeclListNode().accept(this) +
                    " " + tmp.getTypeNode().accept(this) +
                    " " + tmp.getBodyOp().accept(this);
        }
        if (node instanceof NonEmptyParamDeclListNode) {
            List<ParDeclOpNode> parDeclOpNodeList = ((NonEmptyParamDeclListNode) node).getNonEmptyParamDeclList();
            String tmp = "";
            for (ParDeclOpNode parDeclOpNode : parDeclOpNodeList) {
                if (parDeclOpNode != null)
                    tmp += parDeclOpNode.accept(this) + " ";
            }
            return tmp;
        }

        if (node instanceof ParamDeclListNode) {
            if (node == null) {
                return "";
            }
            if(((ParamDeclListNode) node).getNonEmptyParamDeclListNode() != null) {
                return ((ParamDeclListNode) node).getNonEmptyParamDeclListNode().accept(this);
            }
        }
        if (node instanceof ParDeclOpNode) {
            if (((ParDeclOpNode) node).isOut()) {
                return "<ParDeclOp> " + "<ModeOp> out </ModeOp>" +
                        " " + ((ParDeclOpNode) node).getTypeNode().accept(this) +
                        " " + ((ParDeclOpNode) node).getIdentifierNode().accept(this) +
                        " </ParDeclOp>";
            }
            return "<ParDeclOp> " + "<ModeOp> in </ModeOp>" +
                    " " + ((ParDeclOpNode) node).getTypeNode().accept(this) +
                    " " + ((ParDeclOpNode) node).getIdentifierNode().accept(this) +
                    " </ParDeclOp>";
        }
        if (node instanceof ProgramNode) {
            return "<ProgramOp> " + ((ProgramNode) node).getVarDeclListNode().accept(this) +
                    " " + ((ProgramNode) node).getFunListNode().accept(this) +
                    " " + ((ProgramNode) node).getBodyOp().accept(this) +
                    " </ProgramOp>";
        }
        if (node instanceof ReadStatOpNode) {
            ReadStatOpNode tmp = (ReadStatOpNode) node;
            if (tmp.getExprNode() == null) {
                return "<ReadStatOp> " + ((ReadStatOpNode) node).getIdListNode().accept(this) +
                        " </ReadStatOp>";
            }
            return "<ReadStatOp> " + ((ReadStatOpNode) node).getIdListNode().accept(this) +
                    " " + ((ReadStatOpNode) node).getExprNode().accept(this) +
                    " </ReadStatOp>";
        }
        if (node instanceof StatListNode) {
            if (node == null)
                return "";
            List<StatNode> statNodeList = ((StatListNode) node).getStatListNode();
            String tmp = "";
            Collections.reverse(statNodeList);
            for (StatNode statNode : statNodeList) {
                if (statNode != null)
                    tmp += ((AbstractSyntaxNode) statNode).accept(this) + " ";
            }
            return tmp;
        }
        if (node instanceof TypeNode) {
            return "<TypeOp> " + ((TypeNode) node).getChild().accept(this) +
                    " </TypeOp>";
        }
        if (node instanceof VarDeclListNode) {
            if (node == null && ((VarDeclListNode) node).getVarDeclOpNodeList() == null)
                return "";
            List<VarDeclOpNode> varDeclOpNodeList = ((VarDeclListNode) node).getVarDeclOpNodeList();
            Collections.reverse(varDeclOpNodeList);
            String tmp = "";
            for (VarDeclOpNode varDeclOpNode : varDeclOpNodeList) {
                if (varDeclOpNode != null)
                    tmp += varDeclOpNode.accept(this) + " ";
            }
            return tmp;
        }
        if (node instanceof VarDeclOpNode) {
            VarDeclOpNode tmp = (VarDeclOpNode) node;
            String returns = "";
            if (tmp.getTypeNode() == null && tmp.getIdInitListObblNode() != null) {
                returns += "<VarDeclOp> <TypeOp> var </TypeOp> " + tmp.getIdInitListObblNode().accept(this) + "</VarDeclOp>";
            }
            if(tmp.getTypeNode() != null && tmp.getIdInitListNode() != null) {
                returns += "<VarDeclOp> " + tmp.getTypeNode().accept(this) +
                        " " + tmp.getIdInitListNode().accept(this) +
                        " </VarDeclOp>";
            }
            return returns;
        }
        if (node instanceof WhileStatOpNode)
            return "<WhileOp> " + ((WhileStatOpNode) node).getExprNode().accept(this) +
                    " " + ((WhileStatOpNode) node).getBodyOp().accept(this) +
                    " </WhileOp>";

        if (node instanceof WriteStatOpNode) {
            return "<WriteOp> (" + convert(((WriteStatOpNode) node).getTypeWrite()) + ") " + ((WriteStatOpNode) node).getExprNode().accept(this) +
                    " </WriteOp>";
        }
        return "";
    }

    public String convert(Integer typeWrite) {
        switch (typeWrite) {
            case CircuitSym.WRITE: {
                return "write";
            }
            case CircuitSym.WRITEB: {
                return "writeb";
            }
            case CircuitSym.WRITELN: {
                return "writeln";
            }
            case CircuitSym.WRITET: {
                return "writet";
            }
            default: {
                return "";
            }
        }
    }
}