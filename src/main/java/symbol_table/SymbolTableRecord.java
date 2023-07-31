package symbol_table;

import ast.visitors.CircuitSym;

import java.util.ArrayList;
import java.util.List;

public class SymbolTableRecord {
    private String symbol;
    private String kind;   // variabile o procedura
    private Integer varType;
    private List<Integer> paramsType = new ArrayList<>();
    private Integer returnType;
    private Boolean fref;

    /**
     * Nel caso di una variabile
     *
     * @param symbol
     * @param kind
     * @param varType
     */
    public SymbolTableRecord(String symbol, String kind, Integer varType) {
        this.symbol = symbol;
        this.kind = kind;
        this.varType = varType;
        this.fref = false;
    }

    /**
     * Nel caso di una funzione
     *
     * @param symbol
     * @param kind
     * @param paramsType
     * @param returnType
     */
    public SymbolTableRecord(String symbol, String kind, List<Integer> paramsType, Integer returnType) {
        this.symbol = symbol;
        this.kind = kind;
        this.paramsType = paramsType;
        this.returnType = returnType;
        this.fref = false;
    }

    /**
     * Nel caso di una funzione senza return
     *
     * @param symbol
     * @param kind
     * @param paramsType
     */
    public SymbolTableRecord(String symbol, String kind, List<Integer> paramsType) {
        this.symbol = symbol;
        this.kind = kind;
        this.paramsType = paramsType;
        this.returnType = null;
        this.fref = false;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Integer getVarType() {
        return varType;
    }

    public void setVarType(Integer varType) {
        this.varType = varType;
    }

    public List<Integer> getParamsType() {
        return paramsType;
    }

    public Integer getReturnType() {
        return returnType;
    }

    public void addParamType(Integer type) {
        paramsType.add(type);
    }

    public void setReturnType(Integer type) {
        this.returnType = type;
    }

    public Boolean getFref() {
        return fref;
    }

    public void setFrefFalse() {
        this.fref = false;
    }

    public void setFrefTrue() {
        this.fref = true;
    }

    @Override
    public String toString() {
        if (kind != null) {
            if (kind.equals("var")) {
                return "SymbolTableRecord{" +
                        "symbol='" + symbol + '\'' +
                        ", kind='" + kind + '\'' +
                        ", varType=" + CircuitSym.terminalNames[varType] +
                        '}';
            } else if (kind.equals("fun")) {
                String tmpParams = "";
                String tmpReturn = "";
                if (paramsType != null) {
                    for (Integer tmp : paramsType) {
                        tmpParams += CircuitSym.terminalNames[tmp] + " ";
                    }
                }
                if (returnType != null) {
                    tmpReturn += CircuitSym.terminalNames[returnType] + " ";
                }

                return "SymbolTableRecord{" +
                        "symbol='" + symbol + '\'' +
                        ", kind='" + kind + '\'' +
                        ", paramsType=" + tmpParams +
                        ", returnType=" + tmpReturn +
                        '}';
            }
        }
        return null;

    }
}
