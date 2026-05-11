package GUI;

import model.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class CartPanel extends JPanel {

    private Order  order;
    private JFrame parent;
    private JPanel itemsPanel;
    private JLabel totalLabel;
    private JLabel countLabel;

    static final Color BROWN      = new Color(101,  67, 33);
    static final Color BROWN_DARK = new Color(45,   22,  5);
    static final Color GOLD       = new Color(196, 155, 66);
    static final Color CREAM      = new Color(255, 248, 235);
    static final Color RED_PRICE  = new Color(200,  50, 50);
    static final Color BORDER_C   = new Color(220, 200, 170);
    static final Color ROW_BG     = new Color(60,   35, 12);
    static final Color SIDEBAR_BG = new Color(40,   20,  5);

    public CartPanel(Order order, JFrame parent) {
        this.order  = order;
        this.parent = parent;
        setLayout(new BorderLayout(0, 0));
        setBackground(SIDEBAR_BG);
        setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(80, 50, 20)));
        buildUI();
    }

    // ══════════════════════════════════════════════════════════
    //  BUILD UI
    // ══════════════════════════════════════════════════════════
    private void buildUI() {
        add(buildHeader(),      BorderLayout.NORTH);
        add(buildItemsScroll(), BorderLayout.CENTER);
        add(buildFooter(),      BorderLayout.SOUTH);
    }

    // ── Header ───────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout(0, 4));
        header.setBackground(BROWN_DARK);
        header.setBorder(new EmptyBorder(16, 18, 14, 18));

        JLabel title = new JLabel("🛒  Giỏ Hàng");
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setForeground(GOLD);

        countLabel = new JLabel("0 món");
        countLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        countLabel.setForeground(new Color(180, 140, 80));

        JButton closeBtn = new JButton("✕") {
            {
                setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                setFont(new Font("SansSerif", Font.BOLD, 14));
                setForeground(new Color(180, 140, 80));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        };
        closeBtn.addActionListener(e -> {
            setVisible(false);
            parent.revalidate();
        });

        JPanel left = new JPanel(new GridLayout(2, 1, 0, 2));
        left.setOpaque(false);
        left.add(title);
        left.add(countLabel);

        header.add(left,     BorderLayout.CENTER);
        header.add(closeBtn, BorderLayout.EAST);

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.add(header, BorderLayout.CENTER);
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(80, 50, 20));
        wrap.add(sep, BorderLayout.SOUTH);
        return wrap;
    }

    // ── Danh sách món ────────────────────────────────────────
    private JScrollPane buildItemsScroll() {
        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(SIDEBAR_BG);

        showEmptyState();

        JScrollPane scroll = new JScrollPane(itemsPanel);
        scroll.setBorder(null);
        scroll.setBackground(SIDEBAR_BG);
        scroll.getViewport().setBackground(SIDEBAR_BG);
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        return scroll;
    }

    // ── Footer: tổng tiền + nút ──────────────────────────────
    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout(0, 10));
        footer.setBackground(new Color(25, 10, 2));
        footer.setBorder(new EmptyBorder(14, 18, 20, 18));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(80, 50, 20));

        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setOpaque(false);
        totalRow.setBorder(new EmptyBorder(8, 0, 10, 0));

        JLabel totalTxt = new JLabel("Tổng cộng:");
        totalTxt.setFont(new Font("SansSerif", Font.PLAIN, 12));
        totalTxt.setForeground(new Color(160, 120, 70));

        totalLabel = new JLabel("0đ");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        totalLabel.setForeground(GOLD);

        JButton clearBtn = new JButton("Xóa tất cả") {
            {
                setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                setFont(new Font("SansSerif", Font.PLAIN, 11));
                setForeground(new Color(160, 100, 60));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        };
        clearBtn.addActionListener(e -> {
             // hoàn topping
            for (Drink d : order.item) {

                if (d instanceof Drinks) {

                     Drinks drink = (Drinks) d;

                        for (Toppings tp : drink.getDSTopping()) {
                         Inventory.returnTopping(tp.ten());
                        }
                }
                else if (d instanceof Toppings) {

                    Toppings top = (Toppings) d;

                    Inventory.returnTopping(top.ten());
    }
            }
            
    
            order.item.clear();
            order.setSoLuong(0);
            refresh();
              SwingUtilities
        .getWindowAncestor(clearBtn)
         .repaint();
        });

        JPanel priceArea = new JPanel(new GridLayout(2, 1, 0, 2));
        priceArea.setOpaque(false);
        priceArea.add(totalTxt);
        priceArea.add(totalLabel);

        totalRow.add(priceArea, BorderLayout.CENTER);
        totalRow.add(clearBtn,  BorderLayout.EAST);

        JButton orderBtn = new JButton("ĐẶT HÀNG NGAY  ▶") {
            boolean hov = false;
            {
                setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                setFont(new Font("SansSerif", Font.BOLD, 14));
                setForeground(Color.WHITE);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setPreferredSize(new Dimension(0, 46));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? new Color(160, 40, 30) : new Color(192, 57, 43));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        orderBtn.addActionListener(e -> showInvoice());

        footer.add(sep,      BorderLayout.NORTH);
        footer.add(totalRow, BorderLayout.CENTER);
        footer.add(orderBtn, BorderLayout.SOUTH);
        return footer;
    }

    // ══════════════════════════════════════════════════════════
    //  REFRESH
    // ══════════════════════════════════════════════════════════
    public void refresh() {
        itemsPanel.removeAll();

        if (order.item.isEmpty()) {
            showEmptyState();
        } else {
            for (Drink d : order.item) {
                itemsPanel.add(buildItemRow(d));
                itemsPanel.add(Box.createVerticalStrut(1));
            }
        }

        double total = order.getPrice();
        totalLabel.setText(String.format("%,.0fđ", total).replace(',', '.'));
        countLabel.setText(order.getSoLuong() + " món"
            + (order.getSoLuong() >= 5 ? "  🎁 -10%" : ""));

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    private void showEmptyState() {
        itemsPanel.add(Box.createVerticalStrut(40));

        JLabel ico = new JLabel("🛒", SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        ico.setAlignmentX(CENTER_ALIGNMENT);
        ico.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel msg = new JLabel("Giỏ hàng trống", SwingConstants.CENTER);
        msg.setFont(new Font("SansSerif", Font.ITALIC, 14));
        msg.setForeground(new Color(140, 100, 60));
        msg.setAlignmentX(CENTER_ALIGNMENT);
        msg.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel hint = new JLabel("Chọn đồ uống bên trái ☕", SwingConstants.CENTER);
        hint.setFont(new Font("SansSerif", Font.PLAIN, 12));
        hint.setForeground(new Color(100, 70, 40));
        hint.setAlignmentX(CENTER_ALIGNMENT);
        hint.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        itemsPanel.add(ico);
        itemsPanel.add(Box.createVerticalStrut(8));
        itemsPanel.add(msg);
        itemsPanel.add(hint);
    }

    private JPanel buildItemRow(Drink d) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setBackground(ROW_BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        row.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, new Color(70, 40, 15)),
            new EmptyBorder(10, 14, 10, 14)
        ));

        JLabel ico = new JLabel("🧋");
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));

        JPanel info = new JPanel(new GridLayout(2, 1, 0, 2));
        info.setOpaque(false);

        String displayName = (d instanceof Drinks)   ? ((Drinks)   d).ten()
                           : (d instanceof Toppings) ? ((Toppings) d).ten()
                           : d.toString();
        JLabel name = new JLabel(displayName);
        name.setFont(new Font("SansSerif", Font.BOLD, 13));
        name.setForeground(Color.WHITE);

        JLabel price = new JLabel(String.format("%,.0fđ", d.getPrice()).replace(',', '.'));
        price.setFont(new Font("SansSerif", Font.PLAIN, 12));
        price.setForeground(GOLD);

        info.add(name);
        info.add(price);

        JButton del = new JButton("✕") {
            {
                setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                setFont(new Font("SansSerif", Font.PLAIN, 12));
                setForeground(new Color(180, 100, 60));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        };
        del.addActionListener(e -> {
             // hoàn topping
           

        if ( d  instanceof Drinks) {

            Drinks dr = (Drinks) d;

                for (Toppings tp : dr.getDSTopping()) {
                    Inventory.returnTopping(tp.ten());
                }
            }
            else{
                Toppings top = (Toppings) d; 
                Inventory.returnTopping(top.ten());
            }
            
            SwingUtilities
            .getWindowAncestor(del)
            .repaint();
            order.item.remove(d);
            order.setSoLuong(Math.max(0, order.getSoLuong() - 1));
            refresh();
        });

        row.add(ico,  BorderLayout.WEST);
        row.add(info, BorderLayout.CENTER);
        row.add(del,  BorderLayout.EAST);
        return row;
    }

    // ══════════════════════════════════════════════════════════
    //  BƯỚC 1: HÓA ĐƠN – popup giấy nhiệt
    // ══════════════════════════════════════════════════════════
private void showInvoice() {
    if (order.item.isEmpty()) {
        JOptionPane.showMessageDialog(parent,
            "Giỏ hàng đang trống!\nVui lòng chọn món trước.",
            "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    showDeliveryForm();
}

    // ══════════════════════════════════════════════════════════
    //  BƯỚC 2: FORM THÔNG TIN NHẬN HÀNG + THANH TOÁN
    // ══════════════════════════════════════════════════════════
    private void showDeliveryForm() {
        JDialog dialog = new JDialog(parent, "Thông Tin Nhận Hàng", true);
        dialog.setSize(460, 430);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);
        dialog.setLayout(new BorderLayout(0, 0));
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(180, 140, 80), 2));

        // ── Header ──────────────────────────────────────────
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, BROWN_DARK, getWidth(), 0, new Color(80, 45, 15)));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 64));
        headerPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel headerIcon = new JLabel("📦");
        headerIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));

        JPanel headerText = new JPanel(new GridLayout(2, 1, 0, 2));
        headerText.setOpaque(false);
        JLabel h1 = new JLabel("Thông Tin Nhận Hàng");
        h1.setFont(new Font("Serif", Font.BOLD, 17));
        h1.setForeground(GOLD);
        JLabel h2 = new JLabel("Vui lòng điền đầy đủ để hoàn tất đơn hàng");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        h2.setForeground(new Color(200, 170, 110));
        headerText.add(h1);
        headerText.add(h2);

        headerPanel.add(headerIcon,  BorderLayout.WEST);
        headerPanel.add(headerText,  BorderLayout.CENTER);

        // ── Form thông tin ───────────────────────────────────
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(new Color(252, 248, 240));
        formCard.setBorder(new EmptyBorder(18, 24, 10, 24));

        // Tên
        JTextField tfTen   = styledField("Nguyễn Văn A");
        // SĐT
        JTextField tfPhone = styledField("09xxxxxxxx");
        // Địa chỉ
        JTextField tfAddr  = styledField("Số nhà, đường, phường, quận...");

        formCard.add(fieldRow("👤  Họ và tên:", tfTen));
        formCard.add(Box.createVerticalStrut(10));
        formCard.add(fieldRow("📞  Số điện thoại:", tfPhone));
        formCard.add(Box.createVerticalStrut(10));
        formCard.add(fieldRow("📍  Địa chỉ giao hàng:", tfAddr));

        // ── Phương thức thanh toán ───────────────────────────
        JPanel payCard = new JPanel(new BorderLayout(0, 10));
        payCard.setBackground(new Color(252, 248, 240));
        payCard.setBorder(new CompoundBorder(
            new EmptyBorder(12, 24, 8, 24),
            new CompoundBorder(
                new LineBorder(new Color(210, 185, 140), 1, true),
                new EmptyBorder(12, 16, 12, 16)
            )
        ));

        JLabel lblPay = new JLabel("💳  Phương thức thanh toán");
        lblPay.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblPay.setForeground(BROWN_DARK);
        payCard.add(lblPay, BorderLayout.NORTH);

        // Radio buttons với styling
        JRadioButton rbCash = styledRadio("💵  Tiền mặt (COD)",        new Color(40, 120, 60));
        JRadioButton rbMomo = styledRadio("🟣  Ví MoMo",               new Color(160, 30, 120));
        JRadioButton rbVisa = styledRadio("💳  Thẻ Visa / Credit Card", new Color(20, 60, 160));
        rbCash.setSelected(true);

        ButtonGroup payGroup = new ButtonGroup();
        payGroup.add(rbCash);
        payGroup.add(rbMomo);
        payGroup.add(rbVisa);

        JPanel radioRow = new JPanel(new GridLayout(3, 1, 0, 6));
        radioRow.setOpaque(false);
        radioRow.add(rbCash);
        radioRow.add(rbMomo);
        radioRow.add(rbVisa);
        payCard.add(radioRow, BorderLayout.CENTER);

        // ── Nút bấm ─────────────────────────────────────────
        JPanel btnRow = new JPanel(new GridLayout(1, 2, 12, 0));
        btnRow.setBackground(new Color(245, 240, 230));
        btnRow.setBorder(new EmptyBorder(12, 24, 16, 24));

        JButton confirmBtn = new JButton("✅  Xác Nhận Đặt Hàng") {
            boolean hov = false;
            {
                setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                setFont(new Font("SansSerif", Font.BOLD, 13));
                setForeground(Color.WHITE);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setPreferredSize(new Dimension(0, 42));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? new Color(155, 40, 28) : new Color(192, 57, 43));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };

        JButton cancelBtn = new JButton("Hủy") {
            boolean hov = false;
            {
                setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                setFont(new Font("SansSerif", Font.PLAIN, 13));
                setForeground(new Color(100, 70, 40));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? new Color(220, 205, 175) : new Color(235, 220, 190));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };

        confirmBtn.addActionListener(e -> {
            String ten   = tfTen  .getText().trim();
            String phone = tfPhone.getText().trim();
            String addr  = tfAddr .getText().trim();

            // Kiểm tra bỏ trống
            if (ten.isEmpty() || phone.isEmpty() || addr.isEmpty()) {
                shake(dialog);
                JOptionPane.showMessageDialog(dialog,
                    "Vui lòng điền đầy đủ thông tin!",
                    "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra số điện thoại: bắt đầu 0, 9–11 chữ số
            if (!phone.matches("0\\d{8,10}")) {
                JOptionPane.showMessageDialog(dialog,
                    "Số điện thoại không hợp lệ!\n(VD: 0901234567 — bắt đầu bằng 0, 9–11 chữ số)",
                    "Lỗi SĐT", JOptionPane.ERROR_MESSAGE);
                tfPhone.requestFocus();
                return;
            }

            // Lấy phương thức thanh toán
            String phuongThuc = rbCash.isSelected() ? "💵 Tiền mặt (COD)"
                              : rbMomo.isSelected() ? "🟣 Ví MoMo"
                              :                       "💳 Thẻ Visa / Credit Card";

            // Lấy tổng tiền
            String tongTien = String.format("%,.0fđ", order.getPrice()).replace(',', '.');

    dialog.dispose();


    // Mở hóa đơn xác nhận, sau khi confirm mới reset
    new Receiptdialog(parent, order, ten, phone, addr).onConfirmed(() -> {
        order.item.clear();
        refresh();
        setVisible(false);
        parent.revalidate();

        SwingUtilities.invokeLater(() -> {
            parent.toFront();
            parent.requestFocus();
            showSuccessPopup(ten, phone, addr, phuongThuc, tongTien);
        });
    });
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        btnRow.add(confirmBtn);
        btnRow.add(cancelBtn);

        // ── Ghép tất cả ─────────────────────────────────────
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(new Color(252, 248, 240));
        center.add(formCard);
        center.add(payCard);

        dialog.add(headerPanel, BorderLayout.NORTH);
        dialog.add(center,      BorderLayout.CENTER);
        dialog.add(btnRow,      BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // ══════════════════════════════════════════════════════════
    //  POPUP THÀNH CÔNG
    // ══════════════════════════════════════════════════════════
    private void showSuccessPopup(String ten, String phone, String addr,
                                  String phuongThuc, String tongTien) {

        JDialog popup = new JDialog(parent, "", true);
        popup.setUndecorated(true);
        popup.setSize(420, 420);
        popup.setLocationRelativeTo(parent);
        popup.setResizable(false);
        popup.getRootPane().setBorder(BorderFactory.createLineBorder(GOLD, 2, true));

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(40, 20, 5));
        popup.setContentPane(root);

        // ── Header gradient nâu-vàng ─────────────────────────
        JPanel header = new JPanel(new BorderLayout(14, 0)) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, BROWN_DARK,
                                              getWidth(), 0, new Color(100, 60, 15)));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(0, 90));
        header.setBorder(new EmptyBorder(0, 24, 0, 24));

        // Icon check tròn vàng
        JPanel iconCircle = new JPanel() {
            @Override public Dimension getPreferredSize() { return new Dimension(54, 54); }
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GOLD);
                g2.fillOval(0, 0, 52, 52);
                g2.setColor(BROWN_DARK);
                g2.setFont(new Font("SansSerif", Font.BOLD, 26));
                FontMetrics fm = g2.getFontMetrics();
                String ch = "✓";
                g2.drawString(ch, (52 - fm.stringWidth(ch)) / 2,
                              (52 + fm.getAscent() - fm.getDescent()) / 2 - 1);
                g2.dispose();
            }
        };
        iconCircle.setOpaque(false);

        JPanel headText = new JPanel(new GridLayout(2, 1, 0, 4));
        headText.setOpaque(false);
        JLabel h1 = new JLabel("Đặt hàng thành công!");
        h1.setFont(new Font("Serif", Font.BOLD, 20));
        h1.setForeground(GOLD);
        JLabel h2 = new JLabel("MeoMeoTea sẽ giao đến bạn sớm nhất 🧋");
        h2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        h2.setForeground(new Color(210, 180, 110));
        headText.add(h1);
        headText.add(h2);

        header.add(iconCircle, BorderLayout.WEST);
        header.add(headText,   BorderLayout.CENTER);

        // ── Divider răng cưa vàng ────────────────────────────
        JPanel divider = new JPanel() {
            @Override public Dimension getPreferredSize() { return new Dimension(0, 12); }
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(GOLD);
                g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 1f, new float[]{6f, 4f}, 0f));
                g2.drawLine(20, 6, getWidth() - 20, 6);
                g2.dispose();
            }
        };
        divider.setBackground(new Color(40, 20, 5));

        // ── Body: bảng thông tin ─────────────────────────────
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(new Color(40, 20, 5));
        body.setBorder(new EmptyBorder(6, 24, 6, 24));

        String[][] rows2 = {
            {"👤", "Khách hàng",    ten},
            {"📞", "Điện thoại",    phone},
            {"📍", "Địa chỉ",       addr},
            {"💰", "Thanh toán",    phuongThuc},
            {"🧾", "Tổng tiền",     tongTien},
        };

        for (int i = 0; i < rows2.length; i++) {
            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.setOpaque(false);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
            row.setBorder(new EmptyBorder(4, 0, 4, 0));

            // icon + label bên trái
            JLabel lblKey = new JLabel(rows2[i][0] + "  " + rows2[i][1]);
            lblKey.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblKey.setForeground(new Color(200, 165, 90));

            // giá trị bên phải
            boolean isPrice = rows2[i][1].equals("Tổng tiền");
            JLabel lblVal = new JLabel(rows2[i][2]);
            lblVal.setFont(new Font("SansSerif", isPrice ? Font.BOLD : Font.PLAIN, 13));
            lblVal.setForeground(isPrice ? GOLD : Color.WHITE);
            lblVal.setHorizontalAlignment(SwingConstants.RIGHT);

            row.add(lblKey, BorderLayout.WEST);
            row.add(lblVal, BorderLayout.EAST);

            body.add(row);

            // đường kẻ phân cách (trừ dòng cuối)
            if (i < rows2.length - 1) {
                JPanel sep = new JPanel() {
                    @Override public Dimension getPreferredSize() { return new Dimension(0, 1); }
                    @Override public Dimension getMaximumSize()   { return new Dimension(Integer.MAX_VALUE, 1); }
                    @Override protected void paintComponent(Graphics g) {
                        g.setColor(new Color(80, 50, 20));
                        g.fillRect(0, 0, getWidth(), 1);
                    }
                };
                sep.setOpaque(false);
                body.add(sep);
            }
        }

        // ── Footer: nút đóng ─────────────────────────────────
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(30, 12, 2));
        footer.setBorder(new EmptyBorder(14, 24, 18, 24));

        JLabel thanks = new JLabel("Cảm ơn bạn đã tin tưởng MeoMeoTea  ❤", SwingConstants.CENTER);
        thanks.setFont(new Font("Serif", Font.ITALIC, 12));
        thanks.setForeground(new Color(160, 120, 60));

        JButton closeBtn = new JButton("Đóng") {
            boolean hov = false;
            {
                setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                setFont(new Font("SansSerif", Font.BOLD, 14));
                setForeground(BROWN_DARK);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setPreferredSize(new Dimension(120, 38));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? new Color(170, 130, 45) : GOLD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        closeBtn.addActionListener(e -> popup.dispose());

        JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
        btnWrap.setOpaque(false);
        btnWrap.add(closeBtn);

        footer.add(thanks,  BorderLayout.CENTER);
        footer.add(btnWrap, BorderLayout.SOUTH);

        root.add(header,  BorderLayout.NORTH);
        root.add(body,    BorderLayout.CENTER);
        root.add(footer,  BorderLayout.SOUTH);

        popup.setVisible(true);
    }


    // ══════════════════════════════════════════════════════════
    //  HELPER COMPONENTS
    // ══════════════════════════════════════════════════════════

    /** Một hàng label + textfield */
    private JPanel fieldRow(String labelText, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(BROWN_DARK);
        lbl.setPreferredSize(new Dimension(150, 20));

        row.add(lbl,   BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    /** TextField với bo góc, border đẹp */
    private JTextField styledField(String placeholder) {
        JTextField tf = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(new Color(180, 160, 130));
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    Insets ins = getInsets();
                    g2.drawString(placeholder, ins.left + 2,
                        (getHeight() + g2.getFontMetrics().getAscent()
                                     - g2.getFontMetrics().getDescent()) / 2);
                    g2.dispose();
                }
            }
        };
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 175, 130), 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        tf.setBackground(Color.WHITE);
        tf.setPreferredSize(new Dimension(0, 36));
        return tf;
    }

    /** RadioButton có màu accent */
    private JRadioButton styledRadio(String text, Color accent) {
        JRadioButton rb = new JRadioButton(text);
        rb.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rb.setForeground(accent);
        rb.setOpaque(false);
        rb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return rb;
    }

    /** Hiệu ứng rung nhẹ khi validate lỗi */
    private void shake(JDialog dialog) {
        Point orig = dialog.getLocation();
        Timer t = new Timer(30, null);
        int[] count = {0};
        int[] offsets = {-8, 8, -6, 6, -4, 4, -2, 2, 0};
        t.addActionListener(e -> {
            if (count[0] < offsets.length) {
                dialog.setLocation(orig.x + offsets[count[0]], orig.y);
                count[0]++;
            } else {
                dialog.setLocation(orig);
                t.stop();
            }
        });
        t.start();
    }

}