package GUI;

import model.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class ToppingPanel extends JDialog {

    static final Color BROWN_DARK = new Color(45,  22,  5);
    static final Color GOLD       = new Color(196, 155, 66);
    static final Color BROWN      = new Color(101,  67, 33);
    static final Color BG         = new Color(252, 248, 240);
    static final Color BORDER_C   = new Color(210, 185, 140);

    public ToppingPanel(JFrame parent, Drinks drink, Order order, CartPanel cartPanel) {
        super(parent, "Tuỳ chọn: " + drink.ten(), true);

        // Lấy toàn bộ topping từ Inventory (không hard-code)
        Toppings[] toppings = Inventory.getToppings();

        int dialogH = 130 + toppings.length * 34 + 290;
        setLayout(new BorderLayout());
        setSize(360, dialogH);
        setLocationRelativeTo(parent);
        setResizable(false);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(180, 140, 80), 2));

        // ── Header ──────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, BROWN_DARK,
                                              getWidth(), 0, new Color(80, 45, 15)));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(0, 56));
        header.setBorder(new EmptyBorder(0, 16, 0, 16));

        JLabel ico = new JLabel("🧋");
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));

        JPanel hText = new JPanel(new GridLayout(2, 1, 0, 2));
        hText.setOpaque(false);
        JLabel h1 = new JLabel(drink.ten().toUpperCase());
        h1.setFont(new Font("Serif", Font.BOLD, 14));
        h1.setForeground(GOLD);
        JLabel h2 = new JLabel("Chọn size và topping tuỳ thích");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        h2.setForeground(new Color(200, 170, 110));
        hText.add(h1); hText.add(h2);

        header.add(ico,   BorderLayout.WEST);
        header.add(hText, BorderLayout.CENTER);

        // ── Body ────────────────────────────────────────────
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(BG);
        body.setBorder(new EmptyBorder(12, 20, 8, 20));

        // Chọn size M / L
        JLabel sizeLabel = new JLabel("☕  Chọn size:");
        sizeLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        sizeLabel.setForeground(BROWN);
        sizeLabel.setAlignmentX(LEFT_ALIGNMENT);
        body.add(sizeLabel);
        body.add(Box.createVerticalStrut(6));

        JRadioButton rbM = new JRadioButton(String.format(
            "Size M  —  %,.0fđ", drink.getPriceM()).replace(',', '.'));
        JRadioButton rbL = new JRadioButton(String.format(
            "Size L  —  %,.0fđ", drink.getPriceL()).replace(',', '.'));
        rbM.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rbL.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rbM.setBackground(BG);
        rbL.setBackground(BG);
        rbM.setSelected(true);  // mặc định M

        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(rbM);
        sizeGroup.add(rbL);

        JPanel sizeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        sizeRow.setBackground(BG);
        sizeRow.setAlignmentX(LEFT_ALIGNMENT);
        sizeRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        sizeRow.add(rbM);
        sizeRow.add(rbL);
        body.add(sizeRow);

        // Divider
        body.add(Box.createVerticalStrut(10));
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_C);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(LEFT_ALIGNMENT);
        body.add(sep);
        body.add(Box.createVerticalStrut(10));

        // Chọn topping
        JLabel tpLabel = new JLabel("🧊  Chọn topping (có thể chọn nhiều):");
        tpLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        tpLabel.setForeground(BROWN);
        tpLabel.setAlignmentX(LEFT_ALIGNMENT);
        body.add(tpLabel);
        body.add(Box.createVerticalStrut(6));

        JCheckBox[] boxes = new JCheckBox[toppings.length];
        for (int i = 0; i < toppings.length; i++) {
            int stock = Inventory.soLuong[i];
            String label = toppings[i].ten()
                + "  —  " + String.format("%,.0f", toppings[i].getPrice()).replace(',', '.') + "đ"
                + (stock <= 0 ? "  [Hết hàng]" : "");
            boxes[i] = new JCheckBox(label);
            boxes[i].setFont(new Font("SansSerif", Font.PLAIN, 13));
            boxes[i].setBackground(BG);
            boxes[i].setEnabled(stock > 0);  // disable nếu hết
            boxes[i].setAlignmentX(LEFT_ALIGNMENT);
            body.add(boxes[i]);
            body.add(Box.createVerticalStrut(4));
        }
    // ── Chọn mức đường & đá (2 cột) ────────────────────
    body.add(Box.createVerticalStrut(10));
    JSeparator sepDuong = new JSeparator();
    sepDuong.setForeground(BORDER_C);
    sepDuong.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    sepDuong.setAlignmentX(LEFT_ALIGNMENT);
    body.add(sepDuong);
    body.add(Box.createVerticalStrut(10));

    String[] mucDuongOpts = {"100%", "70%", "50%", "0%"};
    int[]    mucDuongVals = {100, 70, 50, 0};
    String[] mucDaOpts    = {"Tiêu chuẩn", "Ít đá", "Không đá"};
    int[]    mucDaVals    = {100, 50, 0};

    final int[] selectedDuong = {0}; // index mặc định
    final int[] selectedDa    = {0};

    // Header 2 cột
    JPanel colHeader = new JPanel(new GridLayout(1, 2, 10, 0));
    colHeader.setOpaque(false);
    colHeader.setAlignmentX(LEFT_ALIGNMENT);
    colHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
    JLabel lbDuong = new JLabel("🍬  Mức đường:");
    lbDuong.setFont(new Font("SansSerif", Font.BOLD, 13));
    lbDuong.setForeground(BROWN);
    JLabel lbDa = new JLabel("🧊  Mức đá:");
    lbDa.setFont(new Font("SansSerif", Font.BOLD, 13));
    lbDa.setForeground(BROWN);
    colHeader.add(lbDuong);
    colHeader.add(lbDa);
    body.add(colHeader);
    body.add(Box.createVerticalStrut(6));

    // Panel chứa 2 cột nút
    JPanel colPanel = new JPanel(new GridLayout(1, 2, 10, 0));
    colPanel.setOpaque(false);
    colPanel.setAlignmentX(LEFT_ALIGNMENT);
    colPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        Math.max(mucDuongOpts.length, mucDaOpts.length) * 36 + 4));

    // Cột trái: đường
    JPanel colDuong = new JPanel();
    colDuong.setLayout(new BoxLayout(colDuong, BoxLayout.Y_AXIS));
    colDuong.setOpaque(false);
    JButton[] btnsDuong = new JButton[mucDuongOpts.length];
    for (int i = 0; i < mucDuongOpts.length; i++) {
        final int idx = i;
        btnsDuong[i] = new JButton(mucDuongOpts[i]) {
            boolean hov = false;
            { setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
            setFont(new Font("SansSerif", Font.PLAIN, 12));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setAlignmentX(LEFT_ALIGNMENT);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
            addMouseListener(new MouseAdapter() {
                  public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                  public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            boolean sel = selectedDuong[0] == idx;
            g2.setColor(sel ? BROWN : (hov ? new Color(220, 200, 165) : new Color(235, 220, 195)));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            setForeground(sel ? new Color(255, 220, 120) : BROWN_DARK);
            super.paintComponent(g);
            g2.dispose();
        }
    };
    final int fi = i;
    btnsDuong[i].addActionListener(e -> {
        selectedDuong[0] = fi;
        for (JButton b : btnsDuong) b.repaint();
    });
    colDuong.add(btnsDuong[i]);
    colDuong.add(Box.createVerticalStrut(4));
}

// Cột phải: đá
JPanel colDa = new JPanel();
colDa.setLayout(new BoxLayout(colDa, BoxLayout.Y_AXIS));
colDa.setOpaque(false);
JButton[] btnsDa = new JButton[mucDaOpts.length];
for (int i = 0; i < mucDaOpts.length; i++) {
    final int idx = i;
    btnsDa[i] = new JButton(mucDaOpts[i]) {
        boolean hov = false;
        { setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
          setFont(new Font("SansSerif", Font.PLAIN, 12));
          setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
          setAlignmentX(LEFT_ALIGNMENT);
          setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
          addMouseListener(new MouseAdapter() {
              public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
              public void mouseExited (MouseEvent e) { hov = false; repaint(); }
          }); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            boolean sel = selectedDa[0] == idx;
            g2.setColor(sel ? BROWN : (hov ? new Color(220, 200, 165) : new Color(235, 220, 195)));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            setForeground(sel ? new Color(255, 220, 120) : BROWN_DARK);
            super.paintComponent(g);
            g2.dispose();
        }
    };
    final int fi = i;
    btnsDa[i].addActionListener(e -> {
        selectedDa[0] = fi;
        for (JButton b : btnsDa) b.repaint();
    });
    colDa.add(btnsDa[i]);
    colDa.add(Box.createVerticalStrut(4));
}

colPanel.add(colDuong);
colPanel.add(colDa);
body.add(colPanel);
        // Divider trước số lượng
        body.add(Box.createVerticalStrut(10));
        JSeparator sep2 = new JSeparator();
        sep2.setForeground(BORDER_C);
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep2.setAlignmentX(LEFT_ALIGNMENT);
        body.add(sep2);
        body.add(Box.createVerticalStrut(10));

        // ── Chọn số lượng ────────────────────────────────────
        JLabel qtyLabel = new JLabel("\uD83D\uDD22  Số lượng:");
        qtyLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        qtyLabel.setForeground(BROWN);
        qtyLabel.setAlignmentX(LEFT_ALIGNMENT);
        body.add(qtyLabel);
        body.add(Box.createVerticalStrut(6));

        final int[] qty = {1};

        JButton btnMinus = styledQtyBtn("\u2212");
        JLabel  qtyNum   = new JLabel("1", SwingConstants.CENTER);
        qtyNum.setFont(new Font("SansSerif", Font.BOLD, 16));
        qtyNum.setForeground(BROWN_DARK);
        qtyNum.setPreferredSize(new Dimension(36, 32));
        JButton btnPlus  = styledQtyBtn("+");

        btnMinus.addActionListener(e -> {
            if (qty[0] > 1) { qty[0]--; qtyNum.setText(String.valueOf(qty[0])); }
        });
        btnPlus.addActionListener(e -> {
            qty[0]++; qtyNum.setText(String.valueOf(qty[0]));
        });

        JPanel qtyRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        qtyRow.setBackground(BG);
        qtyRow.setAlignmentX(LEFT_ALIGNMENT);
        qtyRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        qtyRow.add(btnMinus);
        qtyRow.add(qtyNum);
        qtyRow.add(btnPlus);
        body.add(qtyRow);

        // ── Footer: nút bấm ─────────────────────────────────
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setBackground(new Color(245, 240, 230));
        btnPanel.setBorder(new EmptyBorder(10, 20, 14, 20));

        JButton confirmBtn = styledBtn("Thêm vào giỏ", BROWN_DARK, GOLD);
        JButton skipBtn    = styledBtn("Bỏ qua topping", new Color(235, 220, 190), BROWN);

        confirmBtn.addActionListener(e -> {
            for (int q = 0; q < qty[0]; q++) {
                Drinks copy = drink.copy();
                copy.setSize(rbL.isSelected() ? "L" : "M");
                copy.setMucDuong(mucDuongVals[selectedDuong[0]]);  // thêm vào đây
                copy.setMucDa(mucDaVals[selectedDa[0]]);  
                for (int i = 0; i < toppings.length; i++) {
                    if (boxes[i].isSelected()) {
                        copy.themTopping(toppings[i]);
                    }
                }
                order.addItem(copy);
            }
            refreshCart(cartPanel);
            dispose();
        });

        skipBtn.addActionListener(e -> {
            for (int q = 0; q < qty[0]; q++) {
                Drinks copy = drink.copy();
                copy.setSize(rbL.isSelected() ? "L" : "M");      
                order.addItem(copy);
            }
            refreshCart(cartPanel);
            dispose();
        });

        btnPanel.add(confirmBtn);
        btnPanel.add(skipBtn);

        // ── Scroll cho body nếu nhiều topping ───────────────
        JScrollPane scroll = new JScrollPane(body);
        scroll.setBorder(null);
        scroll.setBackground(BG);
        scroll.getViewport().setBackground(BG);
        scroll.getVerticalScrollBar().setUnitIncrement(8);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        add(header,   BorderLayout.NORTH);
        add(scroll,   BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void refreshCart(CartPanel cartPanel) {
        if (cartPanel != null) {
            cartPanel.refresh();
            cartPanel.setVisible(true);
            SwingUtilities.getWindowAncestor(cartPanel).revalidate();
        }
    }

    private JButton styledQtyBtn(String text) {
        return new JButton(text) {
            boolean hov = false;
            {
                setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                setFont(new Font("SansSerif", Font.BOLD, 18));
                setForeground(BROWN_DARK);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setPreferredSize(new Dimension(34, 34));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? new Color(210, 185, 140) : new Color(230, 210, 170));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
                g2.dispose();
            }
        };
    }

    private JButton styledBtn(String text, Color bg, Color fg) {
        return new JButton(text) {
            boolean hov = false;
            {
                setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                setFont(new Font("SansSerif", Font.BOLD, 13));
                setForeground(fg);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setPreferredSize(new Dimension(0, 38));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? bg.darker() : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };
    }
}