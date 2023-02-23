import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JPanel {

    private JLabel CPR;
    private JLabel Title;
    private JLabel Image;

    public LoadingScreen() {

        ImageIcon aux = new ImageIcon("IMG/Slogo.png");
        Image img = aux.getImage();
        Image newimg = img.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH);

        Image = new JLabel(new ImageIcon(newimg));
        add(Image);

        Title = new JLabel("Spotify Music DataBase");
        Title.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(Title);

        CPR = new JLabel("© Katta Software Inc, 1986 - 1992");
        add(CPR);

    }
}
