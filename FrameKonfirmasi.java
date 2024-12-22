package Codeberhasil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


//yusron//
public class FrameKonfirmasi extends JFrame {
    public FrameKonfirmasi(DefaultTableModel modelTabelPesanan, int rowIndex) {
        setTitle("Konfirmasi Pesanan");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Pesanan telah dikonfirmasi!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.CENTER);

        JButton closeButton = new JButton("Tutup");
        closeButton.addActionListener(e -> dispose());
        panel.add(closeButton, BorderLayout.SOUTH);

        modelTabelPesanan.setValueAt("Dikonfirmasi", rowIndex, 5);

        add(panel);
        setVisible(true);
    }
}
