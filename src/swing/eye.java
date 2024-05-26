package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class eye extends JPanel {
    private static final Icon OPEN_EYE = new ImageIcon(eye.class.getResource("/img/mataterbuka.png"));
    private static final Icon CLOSED_EYE = new ImageIcon(eye.class.getResource("/img/matatertutup.png"));

    private boolean isEyeOpen = false;
    private JLabel eyeLabel;

    public eye() {
        // Initialize JLabel
        eyeLabel = new JLabel();
        eyeLabel.setIcon(CLOSED_EYE);
        eyeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        eyeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        eyeLabel.setForeground(Color.WHITE);
        
        // Add mouse listener to toggle icon on click
        eyeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleEye();
            }
        });

        // Add JLabel to JPanel
        setLayout(new BorderLayout());
        add(eyeLabel, BorderLayout.CENTER);
    }

    private void toggleEye() {
        isEyeOpen = !isEyeOpen;
        eyeLabel.setIcon(isEyeOpen ? OPEN_EYE : CLOSED_EYE);
    }
}
