package br.com.chiliexe.calculadora.view;

import javax.swing.*;
import java.awt.*;

public class Botao extends JButton {

    public Botao(String texto, Color cor)
    {
        setText(texto);
        setBackground(cor);
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setOpaque(true);
        setFont(new Font("courier", Font.PLAIN, 17));
    }
}
