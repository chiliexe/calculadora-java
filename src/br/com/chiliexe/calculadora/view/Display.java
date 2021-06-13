package br.com.chiliexe.calculadora.view;

import br.com.chiliexe.calculadora.model.Memoria;
import br.com.chiliexe.calculadora.model.MemoriaObserver;

import javax.swing.*;
import java.awt.*;

public class Display extends JPanel implements MemoriaObserver {

    private final JLabel label;

    public Display()
    {
        Memoria.getInstance().addObservers(this);

        setBackground(new Color(46,49,50));
        label = new JLabel(Memoria.getInstance().getTexto());
        label.setForeground(Color.WHITE);
        label.setFont(new Font("courier", Font.PLAIN, 23));
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 25));
        add(label);
    }

    @Override
    public void alterarValor(String valor) {
        label.setText(valor);
    }
}
