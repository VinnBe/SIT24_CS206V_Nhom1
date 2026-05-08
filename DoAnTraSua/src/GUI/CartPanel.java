package GUI;

import model.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

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

        // Separator
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

        // Tổng tiền + nút xóa
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
            order.item.clear();
            order.soLuong = 0;
            refresh();
        });

        JPanel priceArea = new JPanel(new GridLayout(2, 1, 0, 2));
        priceArea.setOpaque(false);
        priceArea.add(totalTxt);
        priceArea.add(totalLabel);

        totalRow.add(priceArea, BorderLayout.CENTER);
        totalRow.add(clearBtn,  BorderLayout.EAST);

        // Nút đặt hàng
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
    //  REFRESH – cập nhật danh sách
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

        // Cập nhật tổng tiền
        double total = order.getPrice();
        totalLabel.setText(String.format("%,.0fđ", total).replace(',', '.'));

        // Cập nhật số lượng
        countLabel.setText(order.soLuong + " món"
            + (order.soLuong >= 5 ? "  🎁 -10%" : ""));

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    // ── Trạng thái giỏ trống ─────────────────────────────────
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

    // ── Một dòng món trong giỏ ───────────────────────────────
    private JPanel buildItemRow(Drink d) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setBackground(ROW_BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        row.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, new Color(70, 40, 15)),
            new EmptyBorder(10, 14, 10, 14)
        ));

        // Icon
        JLabel ico = new JLabel("🧋");
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));

        // Tên + giá
        JPanel info = new JPanel(new GridLayout(2, 1, 0, 2));
        info.setOpaque(false);

        String displayName = (d instanceof Drinks) ? ((Drinks) d).ten()
                           : (d instanceof Toppings) ? ((Toppings) d).ten() : d.toString();
        JLabel name = new JLabel(displayName);
        name.setFont(new Font("SansSerif", Font.BOLD, 13));
        name.setForeground(Color.WHITE);

        JLabel price = new JLabel(String.format("%,.0fđ", d.getPrice()).replace(',', '.'));
        price.setFont(new Font("SansSerif", Font.PLAIN, 12));
        price.setForeground(GOLD);

        info.add(name);
        info.add(price);

        // Nút xóa món
        JButton del = new JButton("✕") {
            {
                setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                setFont(new Font("SansSerif", Font.PLAIN, 12));
                setForeground(new Color(180, 100, 60));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        };
        del.addActionListener(e -> {
            order.item.remove(d);
            order.soLuong = Math.max(0, order.soLuong - 1);
            refresh();
        });

        row.add(ico,  BorderLayout.WEST);
        row.add(info, BorderLayout.CENTER);
        row.add(del,  BorderLayout.EAST);
        return row;
    }

    // ══════════════════════════════════════════════════════════
    //  HÓA ĐƠN – xác nhận đặt hàng
    // ══════════════════════════════════════════════════════════
    private void showInvoice() {
        if (order.item.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                "Giỏ hàng đang trống!\nVui lòng chọn món trước.",
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════╗\n");
        sb.append("║      HÓA ĐƠN THANH TOÁN       ║\n");
        sb.append("╠═══════════════════════════════╣\n");

        for (Drink d : order.item) {
            String name = (d instanceof Drinks)   ? ((Drinks)   d).ten()
                        : (d instanceof Toppings) ? ((Toppings) d).ten()
                        : d.toString();
            sb.append(String.format("  %-22s  %,.0fđ\n", name, d.getPrice()).replace(',', '.'));
        }

        sb.append("╠═══════════════════════════════╣\n");
        if (order.soLuong >= 5)
            sb.append("  🎁 Giảm 10% (từ 5 món trở lên)\n");
        sb.append(String.format("  TỔNG CỘNG:  %,.0fđ\n",
            order.getPrice()).replace(',', '.'));
        sb.append("╚═══════════════════════════════╝\n");
        sb.append("  Xác nhận đặt hàng?");

        int res = JOptionPane.showConfirmDialog(parent,
            sb.toString(), "Xác Nhận Đặt Hàng",
            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (res == JOptionPane.YES_OPTION) {
            order.item.clear();
            order.soLuong = 0;
            refresh();
            JOptionPane.showMessageDialog(parent,
                "✅  Đặt hàng thành công!\n\nCảm ơn bạn đã chọn MeoMeoTea ☕\nCuộc Vui Hết Ý ❤",
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}