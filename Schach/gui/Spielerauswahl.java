package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import daten.Spieler;

public class Spielerauswahl extends JPanel implements ActionListener {
    // Anfang Attribute
    private static final long serialVersionUID = -6920443370361911344L;
    private static Color cBraunRot = new Color(172, 59, 32); 
    private static Color cHellesBeige = new Color(255, 248, 151);
    private SpielGUI parent;
    private JLabel lSpielname = new JLabel("Spielname: ");
    private JButton bSpielen = new JButton("Spiel starten");
    private JTextField tSpielname = new JTextField("          ");
    private Spieler spieler1;
    private Spieler spieler2;
    private JTextField nameWEST = new JTextField("");
    private JTextField nameEAST = new JTextField("");
    private JComboBox boxWEST;
    private JComboBox boxEAST;
    private ButtonGroup bGFarbauswahl;

    // Ende Attribute
    
    // Konstruktor 
    public Spielerauswahl(SpielGUI parent) {
        super();
        this.parent = parent;
        init();
    }
    
    private void init() {
        this.setLayout(new BorderLayout());
        bSpielen.addActionListener(this);
        bSpielen.setActionCommand(bSpielen.getText());
        setBackground(cBraunRot);        
        // North
        Container cNorth = new JPanel();
        cNorth.setLayout(new FlowLayout());
        cNorth.setBackground(cBraunRot);
        cNorth.add(lSpielname);
        tSpielname.setBackground(cHellesBeige);
        cNorth.add(tSpielname);
        this.add(cNorth, BorderLayout.NORTH);
        
        // West
        this.add(auswahlPanel("West"), BorderLayout.WEST);
       
        
        // East
        this.add(auswahlPanel("East"), BorderLayout.EAST);
        
        
        // South 
        Container cSouth = new Container();
        cSouth.setLayout(new FlowLayout());
        bSpielen.setBackground(cHellesBeige);
        cSouth.add(bSpielen);
        this.add(cSouth, BorderLayout.SOUTH);
        
        // Center
        
        // Label Farbauswahl
        Container cCenter = new JPanel();
        cCenter.setBackground(cBraunRot);
        cCenter.setLayout(new GridLayout(0, 1));
        
        Container cCenterLabel = new Container();
        cCenterLabel.setLayout(new FlowLayout());
        JLabel lFarbwahl = new JLabel("Farbe für Spieler 1 wählen");
        
        cCenterLabel.add(lFarbwahl);
        
        
        // Radio Buttons
        Container cCenterMenu = new Container();
        cCenterMenu.setLayout(new FlowLayout());
        
        JRadioButton schwarz = new JRadioButton("schwarz");
        schwarz.setActionCommand("schwarz");
        schwarz.setBackground(cBraunRot);
        schwarz.addActionListener(this);
        
        JRadioButton weiss = new JRadioButton("weiss");
        weiss.setActionCommand("weiss");
        weiss.setSelected(true);
        weiss.setBackground(cBraunRot);
        weiss.addActionListener(this);
        
        bGFarbauswahl = new ButtonGroup();
        bGFarbauswahl.add(weiss);
        bGFarbauswahl.add(schwarz);
        cCenterMenu.add(weiss);
        cCenterMenu.add(schwarz);
        
        // Center fuellen
        cCenter.add(cCenterLabel);
        cCenter.add(cCenterMenu);
       
        
        this.add(cCenter, BorderLayout.CENTER);
        
    }
    
    private JPanel auswahlPanel(String seite) {
        JPanel eingabePanel = new JPanel();
        eingabePanel.setLayout(new GridLayout(0, 1, 5, 0));
        // ComboBox
        // TODO Array mit den Spielern (spielerArray)
        String[] spielerArray = {"neuer Spieler", "Test"};
        JComboBox spielerMenu = new JComboBox(spielerArray);
        spielerMenu.addActionListener(this);
        if (seite.equals("West")) {
            spielerMenu.setActionCommand("boxWEST");
            boxWEST = spielerMenu;
        } else {
            spielerMenu.setActionCommand("boxEAST");
            boxEAST = spielerMenu;
        }
        spielerMenu.setBackground(cHellesBeige);
        eingabePanel.add(spielerMenu);
        
        // Spielername
        JLabel lName = new JLabel("Spielername");
        eingabePanel.add(lName);
        
        if (seite.equals("West")) {
            nameWEST.setBackground(cHellesBeige);
            eingabePanel.add(nameWEST);
        } else {
            nameEAST.setBackground(cHellesBeige);
            eingabePanel.add(nameEAST);
        }
        eingabePanel.setBackground(cBraunRot);
        return eingabePanel;
    }
    
    // ActionListener

    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals("Spiel starten")) {
            if (!((nameWEST.getText().equals("")) 
                || nameEAST.getText().equals(""))) {
                spieler1 = new Spieler(nameWEST.getText());
                spieler2 = new Spieler(nameEAST.getText());
                if (bGFarbauswahl.getSelection().
                    getActionCommand().equals("weiss")) {
                    spieler1.setFarbe(true);
                    spieler2.setFarbe(false);
                } else {
                    spieler1.setFarbe(false);
                    spieler2.setFarbe(true);
                }
                System.out.println(spieler1.getName());
                parent.setContentPane(new SpielfeldGUI(parent, spieler1,
                    spieler2));
                parent.revalidate();
            }
        }
        if (arg0.getActionCommand().equals("boxWEST")) {
            nameWEST.setText("TEST WEST");
        } 
        if (arg0.getActionCommand().equals("boxEAST")) {
            nameEAST.setText("TEST EAST");
        }
        if (arg0.getActionCommand().equals("weiss")) {
            // Spieler 1.setFarbe(true)
        }    
        if (arg0.getActionCommand().equals("schwarz")) {
            // Spieler 1.setFarbe(fasle)
        }
    }

}
