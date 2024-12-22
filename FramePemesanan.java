package Codeberhasil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FramePemesanan extends JFrame {
    private DefaultTableModel modelTabelPesanan;
    private JTable tabelPesanan;
    private JTextField namaPelangganField, nomorMejaField;
    private JComboBox<String> menuComboBox;
    private JTextField jumlahField;
    private JButton tambahPesananButton, hapusPesananButton, konfirmasiPesananButton, bayarButton;

    //yusron//
    public FramePemesanan() {
        setTitle("Pemesanan Makanan");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel informasi pelanggan
        JPanel panelPelanggan = new JPanel(new GridLayout(2, 2, 10, 10));
        panelPelanggan.setBorder(BorderFactory.createTitledBorder("Informasi Pelanggan"));
        panelPelanggan.add(new JLabel("Nama Pelanggan:"));
        namaPelangganField = new JTextField();
        panelPelanggan.add(namaPelangganField);
        panelPelanggan.add(new JLabel("Nomor Meja:"));
        nomorMejaField = new JTextField();
        panelPelanggan.add(nomorMejaField);
        add(panelPelanggan, BorderLayout.NORTH);

        //nabila//
        // Panel menu pesanan
        JPanel panelMenu = new JPanel(new GridLayout(3, 2, 10, 10));
        panelMenu.setBorder(BorderFactory.createTitledBorder("Menu"));
        String[] menuMakanan = {"Nasi Goreng - Rp15000", "Mie Ayam - Rp12000", "Bakso - Rp13000"};
        menuComboBox = new JComboBox<>(menuMakanan);
        jumlahField = new JTextField();
        tambahPesananButton = new JButton("Tambah Pesanan");
        panelMenu.add(new JLabel("Pilih Menu:"));
        panelMenu.add(menuComboBox);
        panelMenu.add(new JLabel("Jumlah:"));
        panelMenu.add(jumlahField);
        panelMenu.add(new JLabel(""));
        panelMenu.add(tambahPesananButton);
        add(panelMenu, BorderLayout.WEST);

        // Tabel daftar pesanan
        modelTabelPesanan = new DefaultTableModel(new String[]{"Nama Pelanggan", "Nomor Meja", "Menu", "Jumlah", "Harga Total", "Status"}, 0);
        tabelPesanan = new JTable(modelTabelPesanan);
        JScrollPane scrollPane = new JScrollPane(tabelPesanan);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Pesanan"));
        add(scrollPane, BorderLayout.CENTER);

        // Panel tombol
        JPanel panelTombol = new JPanel(new FlowLayout());
        hapusPesananButton = new JButton("Hapus Pesanan");
        konfirmasiPesananButton = new JButton("Konfirmasi Pesanan");
        bayarButton = new JButton("Bayar");
        panelTombol.add(hapusPesananButton);
        panelTombol.add(konfirmasiPesananButton);
        panelTombol.add(bayarButton);
        add(panelTombol, BorderLayout.SOUTH);

        // Action listeners
        tambahPesananButton.addActionListener(e -> tambahPesanan());
        hapusPesananButton.addActionListener(e -> hapusPesanan());
        konfirmasiPesananButton.addActionListener(e -> konfirmasiPesanan());
        bayarButton.addActionListener(e -> bayarPesanan());

        setVisible(true);
    }

    //NAZIM//
    private void tambahPesanan() {
        try {
            String namaPelanggan = namaPelangganField.getText().trim();
            String nomorMeja = nomorMejaField.getText().trim();
            String menu = (String) menuComboBox.getSelectedItem();
            String jumlahText = jumlahField.getText().trim();

            if (namaPelanggan.isEmpty() || nomorMeja.isEmpty() || jumlahText.isEmpty() || !jumlahText.matches("\\d+")) {
                throw new IllegalArgumentException("Lengkapi semua data dengan benar!");
            }

            int jumlah = Integer.parseInt(jumlahText);
            String[] menuParts = menu.split(" - Rp");
            String namaMenu = menuParts[0];
            int hargaSatuan = Integer.parseInt(menuParts[1]);
            int totalHarga = jumlah * hargaSatuan;

            modelTabelPesanan.addRow(new Object[]{namaPelanggan, nomorMeja, namaMenu, jumlah, totalHarga, "Belum Lunas"});
            jumlahField.setText("");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menambahkan pesanan.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusPesanan() {
        int selectedRow = tabelPesanan.getSelectedRow();
        try {
            if (selectedRow == -1) {
                throw new IllegalArgumentException("Pilih pesanan yang ingin dihapus!");
            }
            modelTabelPesanan.removeRow(selectedRow);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void konfirmasiPesanan() {
        try {
            int selectedRow = tabelPesanan.getSelectedRow();
            if (selectedRow == -1) {
                throw new IllegalArgumentException("Pilih pesanan yang ingin dikonfirmasi!");
            }

            String status = (String) modelTabelPesanan.getValueAt(selectedRow, 5);
            if (!"Lunas".equals(status)) {
                throw new IllegalArgumentException("Hanya pesanan dengan status 'Lunas' yang dapat dikonfirmasi.");
            }

            new FrameKonfirmasi(modelTabelPesanan, selectedRow);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    //yusron//
    private void bayarPesanan() {
        try {
            int selectedRow = tabelPesanan.getSelectedRow();
            if (selectedRow == -1) {
                throw new IllegalArgumentException("Pilih pesanan yang ingin dibayar!");
            }

            String status = (String) modelTabelPesanan.getValueAt(selectedRow, 5);
            if ("Lunas".equals(status)) {
                throw new IllegalArgumentException("Pesanan ini sudah lunas!");
            }

            int totalHarga = (int) modelTabelPesanan.getValueAt(selectedRow, 4);
            String input = JOptionPane.showInputDialog(this, "Total Harga: Rp" + totalHarga + "\nMasukkan jumlah pembayaran:", "Pembayaran", JOptionPane.PLAIN_MESSAGE);

            if (input == null || input.isEmpty() || !input.matches("\\d+")) {
                throw new IllegalArgumentException("Masukkan jumlah pembayaran yang valid!");
            }

            int jumlahBayar = Integer.parseInt(input);
            if (jumlahBayar < totalHarga) {
                throw new IllegalArgumentException("Jumlah pembayaran kurang!");
            }

            int kembalian = jumlahBayar - totalHarga;
            JOptionPane.showMessageDialog(this, "Pembayaran berhasil! Kembalian: Rp" + kembalian, "Sukses", JOptionPane.INFORMATION_MESSAGE);
            modelTabelPesanan.setValueAt("Lunas", selectedRow, 5);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat pembayaran.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }
}
