package GUI;

import model.Inventory;
import model.Order;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MainFrame extends JFrame {

    // ── Model ───────────────────────────────────────────────
    private model.Menu menu  = new model.Menu();   // dùng fully qualified để tránh conflict với javax.swing.JMenu
    private Order      order = new Order();
    private CartPanel cartPanel;
    private MenuPanel menuPanel;

    // ── Colors ──────────────────────────────────────────────
    static final Color BROWN      = new Color(101, 67, 33);
    static final Color BROWN_DARK = new Color(60,  35, 10);
    static final Color GOLD       = new Color(196, 155, 66);
    static final Color CREAM      = new Color(255, 248, 235);
    static final Color RED_BTN    = new Color(192,  57, 43);
    static final Color BG         = new Color(250, 247, 242);
    static final Color BORDER_C   = new Color(220, 200, 170);

    public MainFrame() {
        setTitle("MeoMeoTea");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1150, 760);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        buildHeader();
        buildBody();
        buildFooter();

        setVisible(true);
    }

    // ══════════════════════════════════════════════════════════
    //  HEADER
    // ══════════════════════════════════════════════════════════
    private void buildHeader() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, BROWN_DARK, getWidth(), 0, new Color(90, 50, 20));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(0, 72));
        header.setBorder(new EmptyBorder(0, 28, 0, 28));

        // Logo + tagline
        JPanel logoArea = new JPanel(new GridLayout(2, 1, 0, 2));
        logoArea.setOpaque(false);

        JLabel logo = new JLabel("🧋  MeoMeoTea");
        logo.setFont(new Font("Serif", Font.BOLD, 28));
        logo.setForeground(GOLD);

        JLabel tagline = new JLabel("Thêm một ly, thêm yêu đời");
        tagline.setFont(new Font("Serif", Font.ITALIC, 13));
        tagline.setForeground(new Color(220, 190, 130));

        logoArea.add(logo);
        logoArea.add(tagline);

        // Cart toggle button
        JButton cartBtn = new JButton("🛒  Xem Giỏ Hàng") {
            boolean hov = false;
            {
                setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                setFont(new Font("SansSerif", Font.BOLD, 13));
                setForeground(GOLD);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true; repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                if (hov) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(new Color(255, 255, 255, 30));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        cartBtn.setPreferredSize(new Dimension(170, 38));
        cartBtn.addActionListener(e -> {
            cartPanel.setVisible(!cartPanel.isVisible());
            revalidate();
        });

        header.add(logoArea, BorderLayout.WEST);
        header.add(cartBtn,  BorderLayout.EAST);
        add(header, BorderLayout.NORTH);
    }

    // ══════════════════════════════════════════════════════════
    //  BODY  (menu trái + giỏ hàng phải)
    // ══════════════════════════════════════════════════════════
    private void buildBody() {
        // CartPanel dùng Order và JFrame hiện tại
        cartPanel = new CartPanel(order, this);
        cartPanel.setPreferredSize(new Dimension(330, 0));
        cartPanel.setVisible(false);   // ẩn mặc định, toggle bằng nút header

        // MenuPanel: dùng constructor 2 tham số (tương thích file cũ lẫn mới)
        menuPanel = new MenuPanel(menu, order);
        menuPanel.setCartPanel(cartPanel);   // gắn cartPanel sau

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BG);
        body.add(menuPanel, BorderLayout.CENTER);
        body.add(cartPanel, BorderLayout.EAST);

        add(body, BorderLayout.CENTER);
    }

    // ══════════════════════════════════════════════════════════
    //  FOOTER
    // ══════════════════════════════════════════════════════════
    private void buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(BROWN_DARK);
        footer.setPreferredSize(new Dimension(0, 32));
        footer.setBorder(new EmptyBorder(0, 24, 0, 24));

        JLabel lbl = new JLabel("© 2026 MeoMeoTea  —  Hotline: 1900-MeoMeo");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lbl.setForeground(new Color(160, 120, 70));

        JLabel inv = new JLabel(
            "Kho — Đá: " + Inventory.da
            + "  Đường: " + Inventory.duong
            + "  Trà sữa: " + Inventory.traSua
            + "  |  Trân Châu: " + Inventory.soLuong[0]
            + "  Bánh Flan: " + Inventory.soLuong[1]
            + "  Hạt Thủy Tinh: " + Inventory.soLuong[2]
            + "  Thạch: " + Inventory.soLuong[3]
            + "  TC Trắng: " + Inventory.soLuong[4]);
        inv.setFont(new Font("SansSerif", Font.PLAIN, 12));
        inv.setForeground(new Color(160, 120, 70));

        footer.add(lbl, BorderLayout.WEST);
        footer.add(inv, BorderLayout.EAST);
        add(footer, BorderLayout.SOUTH);
    }

    // ══════════════════════════════════════════════════════════
    //  ENTRY POINT
    // ══════════════════════════════════════════════════════════
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(MainFrame::new);
    }
}