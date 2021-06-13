package br.com.chiliexe.calculadora.model;

import java.util.ArrayList;

public class Memoria {

    private enum TipoOperacao {
        ZERAR, NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA, ALTERNAR, PORCENTO;
    }

    private static Memoria memoria = new Memoria();
    private boolean substituir = false;
    private TipoOperacao ultimaOperacao = null;
    private String texto = "";
    private String textoBefore = "";
    private ArrayList<MemoriaObserver> observers = new ArrayList<>();

    private Memoria(){}

    public static Memoria getInstance()
    {
        return memoria;
    }



    public void addObservers(MemoriaObserver observer)
    {
        observers.add(observer);
    }
    public String getTexto() {
        return texto.isEmpty() ? "0" : texto;
    }

    public void setTexto(String texto) {

        TipoOperacao tipoOperacao = verificarOperacao(texto);

        if(tipoOperacao == null) return;
        else if (tipoOperacao == TipoOperacao.ZERAR) {
            this.texto = ""; textoBefore = ""; substituir = false; ultimaOperacao = null;
        }else if (tipoOperacao == TipoOperacao.NUMERO || tipoOperacao == TipoOperacao.VIRGULA){
            this.texto = substituir ? texto : this.texto + texto;
            substituir = false;
        }else{
            substituir = true;
            this.texto = obterResultadoOperacao();
            textoBefore = this.texto;
            ultimaOperacao = tipoOperacao;
        }

        observers.forEach(e -> e.alterarValor(getTexto()));
    }

    private String obterResultadoOperacao() {
        if(ultimaOperacao == null || ultimaOperacao == TipoOperacao.IGUAL) return this.texto;

        double numeroBuffer = Double.parseDouble(this.textoBefore.replace(",", "."));
        double numeroAtual = Double.parseDouble(this.texto.replace(",", "."));
        double resultado = 0;

        switch (ultimaOperacao)
        {
            case SOMA: resultado = numeroBuffer + numeroAtual; break;
            case SUB: resultado = numeroBuffer - numeroAtual; break;
            case DIV: resultado = numeroBuffer / numeroAtual; break;
            case MULT: resultado = numeroBuffer * numeroAtual; break;
        }
        return Double.toString(resultado).replace(".", ",");
    }

    private TipoOperacao verificarOperacao(String texto) {

        if(this.texto.isEmpty() && texto == "0") return null;

        try{
            Integer.parseInt(texto);
            return TipoOperacao.NUMERO;
        }catch (NumberFormatException e)
        {
            switch (texto){
                case "AC": return TipoOperacao.ZERAR;
                case "/": return TipoOperacao.DIV;
                case "+": return TipoOperacao.SOMA;
                case "*": return TipoOperacao.MULT;
                case "-": return TipoOperacao.SUB;
                case "=": return TipoOperacao.IGUAL;
                case ",": if (!this.texto.contains(",")) {
                    return TipoOperacao.VIRGULA;
                }
                case "+/-": return TipoOperacao.ALTERNAR;
                case "%": return TipoOperacao.PORCENTO;
            }
        }

        return null;
    }
}
