package GUI;

import model.Drink;
import model.Drinks;
import model.Toppings;
import model.Order;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ReceiptDialog – popup hóa đơn nhiệt (thermal receipt style)
 * Gọi: new ReceiptDialog(parentFrame, order).onConfirmed(...);
 */
public class Receiptdialog extends JDialog {

    private static final Color PAPER_BG    = new Color(253, 252, 248);
    private static final Color INK         = new Color(20,  18,  15);
    private static final Color INK_MUTED   = new Color(120, 112, 98);
    private static final Color STAMP_COLOR = new Color(180, 40,  20);
    private static final Color OVERLAY_BG  = new Color(20,  10,   2, 200);
    private static final int   CARD_W      = 360;

    private final Order  order;
    private final JFrame parent;
    private Runnable onConfirmed;

    public Receiptdialog(JFrame parent, Order order) {
        super(parent, "Hoa dDon Thanh Toan", true);
        this.parent = parent;
        this.order  = order;
        buildDialog();
    }

    public Receiptdialog onConfirmed(Runnable r) {
        this.onConfirmed = r;
        setVisible(true);  // mở dialog SAU khi callback đã được gán
        return this;
    }

    // ══════════════════════════════════════════════════════════
    //  DIALOG – phủ toàn cửa sổ parent
    // ══════════════════════════════════════════════════════════
    private void buildDialog() {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        getRootPane().setOpaque(false);
        setSize(parent.getSize());
        setLocation(parent.getLocation());

        JPanel overlay = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(OVERLAY_BG);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        overlay.setOpaque(false);
        overlay.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { dispose(); }
        });

        overlay.add(buildReceiptCard());
        setContentPane(overlay);
        // setVisible được gọi sau khi onConfirmed đã được gán
    }

    // ══════════════════════════════════════════════════════════
    //  CARD GIẤY NHIỆT
    // ══════════════════════════════════════════════════════════
    private JPanel buildReceiptCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(CARD_W, 600));
        card.addMouseListener(new MouseAdapter() {});

        card.add(buildTeeth(true),  BorderLayout.NORTH);
        card.add(buildBody(),       BorderLayout.CENTER);
        card.add(buildTeeth(false), BorderLayout.SOUTH);
        return card;
    }

    // ── Răng cưa ────────────────────────────────────────────
    private JPanel buildTeeth(final boolean top) {
        return new JPanel() {
            @Override public Dimension getPreferredSize() { return new Dimension(CARD_W, 14); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), r = 7, h = getHeight();
                GeneralPath p = new GeneralPath();
                if (top) {
                    p.moveTo(0, h); p.lineTo(0, r);
                    for (int x = 0; x + r * 2 <= w; x += r * 2)
                        p.curveTo(x + r * 0.5, -r * 0.3, x + r * 1.5, -r * 0.3, x + r * 2, r);
                    p.lineTo(w, h);
                } else {
                    p.moveTo(0, 0); p.lineTo(0, h - r);
                    for (int x = 0; x + r * 2 <= w; x += r * 2)
                        p.curveTo(x + r * 0.5, h + r * 0.3, x + r * 1.5, h + r * 0.3, x + r * 2, h - r);
                    p.lineTo(w, 0);
                }
                p.closePath();
                g2.setColor(PAPER_BG);
                g2.fill(p);
                g2.dispose();
            }
        };
    }

    // ── Body ────────────────────────────────────────────────
    private JPanel buildBody() {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(PAPER_BG);
        body.setBorder(new EmptyBorder(4, 20, 4, 20));

        body.add(centerLbl("MEOMEOTEA",                      boldF(16)));
        body.add(centerLbl("123 Nguyen Trai, Q.1, TP.HCM",  plainF(11)));
        body.add(centerLbl("DT: 1900-MeoMeo",                plainF(11)));
        body.add(gap(4));
        body.add(dashed());

        body.add(gap(3));
        body.add(centerLbl("HOA DON THANH TOAN", boldF(13)));
        body.add(gap(3));
        body.add(dashed());

        String soHD = String.format("%04d", (int)(Math.random() * 9000) + 1000);
        String ngay = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String gio  = new SimpleDateFormat("HH:mm:ss").format(new Date());

        body.add(gap(3));
        body.add(row("So HD  :", soHD));
        body.add(row("Ngay   :", ngay));
        body.add(row("Gio    :", gio));
        body.add(row("Thu ngan:", "ADMIN"));
        body.add(row("Khach  :", "Khach le"));
        body.add(gap(3));
        body.add(solid());

        body.add(tableHeader());
        body.add(solid());

        int idx = 1;
        for (Drink d : order.item) {
            body.add(itemRow(idx++, getName(d), d.getPrice()));
        }
        body.add(solid());

        double sub = 0;
        for (Drink d : order.item) sub += d.getPrice();
        boolean disc = order.soLuong >= 5;
        double  cut  = disc ? Math.round(sub * 0.1) : 0;
        double  total = sub - cut;

        body.add(row("T.Cong (" + order.soLuong + " ly):", fmtVND(sub)));
        if (disc) body.add(rowRed("Giam gia (10%):", "- " + fmtVND(cut)));
        body.add(dashed());
        body.add(grandRow(total));
        body.add(gap(2));
        body.add(centerLbl(toViWords(total), italicF(11)));
        body.add(stamp());
        body.add(dashed());
        body.add(gap(3));
        body.add(centerLbl("Cam On Quy Khach - Hen Gap Lai", plainF(11)));
        body.add(centerLbl("*** MeoMeoTea ***",               plainF(11)));
        body.add(gap(10));
        body.add(btnRow());
        body.add(gap(8));
        return body;
    }

    // ══════════════════════════════════════════════════════════
    //  COMPONENT BUILDERS
    // ══════════════════════════════════════════════════════════

    private JPanel centerLbl(String text, Font font) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(font);
        l.setForeground(font.isBold() ? INK : INK_MUTED);
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(PAPER_BG);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        p.add(l);
        return p;
    }

    private JPanel row(String left, String right) {
        return rowColor(left, right, INK_MUTED, INK);
    }
    private JPanel rowRed(String left, String right) {
        return rowColor(left, right, INK_MUTED, new Color(180, 40, 20));
    }
    private JPanel rowColor(String left, String right, Color lc, Color rc) {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        p.setBackground(PAPER_BG);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 18));
        JLabel lL = new JLabel(left),  lR = new JLabel(right);
        lL.setFont(plainF(12)); lR.setFont(plainF(12));
        lL.setForeground(lc);   lR.setForeground(rc);
        p.add(lL, BorderLayout.WEST);
        p.add(lR, BorderLayout.EAST);
        return p;
    }

    private JPanel tableHeader() {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        p.setBackground(PAPER_BG);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        p.setBorder(new EmptyBorder(3, 0, 3, 0));
        JLabel lN = new JLabel("TEN HANG"), lR = new JLabel("SL     T.TIEN");
        lN.setFont(boldF(11)); lR.setFont(boldF(11));
        lN.setForeground(INK); lR.setForeground(INK);
        p.add(lN, BorderLayout.WEST);
        p.add(lR, BorderLayout.EAST);
        return p;
    }

    private JPanel itemRow(int idx, String name, double price) {
        JPanel w = new JPanel();
        w.setLayout(new BoxLayout(w, BoxLayout.Y_AXIS));
        w.setBackground(PAPER_BG);

        // Tên món dùng JTextArea để tự wrap
        JTextArea ta = new JTextArea(idx + ") " + name);
        ta.setFont(plainF(12)); ta.setForeground(INK); ta.setBackground(PAPER_BG);
        ta.setEditable(false); ta.setFocusable(false);
        ta.setLineWrap(true); ta.setWrapStyleWord(true);
        ta.setBorder(null); ta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel numRow = new JPanel(new BorderLayout(8, 0));
        numRow.setBackground(PAPER_BG);
        numRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        numRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 16));
        JLabel lQ = new JLabel("  1"), lP = new JLabel(fmtVND(price));
        lQ.setFont(plainF(12)); lP.setFont(plainF(12));
        lQ.setForeground(INK_MUTED); lP.setForeground(INK);
        numRow.add(lQ, BorderLayout.WEST);
        numRow.add(lP, BorderLayout.EAST);

        w.add(ta);
        w.add(numRow);
        w.add(Box.createVerticalStrut(4));
        return w;
    }

    private JPanel grandRow(double grand) {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        p.setBackground(PAPER_BG);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        JLabel lL = new JLabel("TIEN MAT"), lR = new JLabel(fmtVND(grand));
        lL.setFont(boldF(15)); lR.setFont(boldF(15));
        lL.setForeground(INK); lR.setForeground(INK);
        p.add(lL, BorderLayout.WEST);
        p.add(lR, BorderLayout.EAST);
        return p;
    }

    private JPanel stamp() {
        JPanel p = new JPanel() {
            @Override public Dimension getPreferredSize() { return new Dimension(CARD_W - 40, 54); }
            @Override public Dimension getMaximumSize()  { return new Dimension(Integer.MAX_VALUE, 54); }
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cx = getWidth() - 28, cy = getHeight() / 2, r = 22;
                Color sc = new Color(STAMP_COLOR.getRed(), STAMP_COLOR.getGreen(), STAMP_COLOR.getBlue(), 130);
                g2.setColor(sc);
                g2.setStroke(new BasicStroke(1.8f));
                g2.drawOval(cx - r, cy - r, r * 2, r * 2);
                g2.setFont(new Font("Courier New", Font.BOLD, 7));
                g2.rotate(Math.toRadians(-15), cx, cy);
                String[] lines = {"DA", "THANH", "TOAN"};
                FontMetrics fm = g2.getFontMetrics();
                int sy = cy - fm.getHeight() + 4;
                for (String ln : lines) {
                    g2.drawString(ln, cx - fm.stringWidth(ln) / 2, sy);
                    sy += fm.getHeight();
                }
                g2.dispose();
            }
        };
        p.setBackground(PAPER_BG);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        return p;
    }

    private JPanel btnRow() {
        JButton ok    = makeBtn("XAC NHAN DAT HANG", new Color(55, 30, 8), Color.WHITE);
        JButton close = makeBtn("DONG", PAPER_BG, INK_MUTED);
        close.setBorder(new LineBorder(new Color(185, 175, 155), 1, true));
        ok   .addActionListener(e -> { dispose(); if (onConfirmed != null) onConfirmed.run(); });
        close.addActionListener(e -> dispose());
        JPanel p = new JPanel(new GridLayout(1, 2, 10, 0));
        p.setBackground(PAPER_BG);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        p.add(ok); p.add(close);
        return p;
    }

    private JButton makeBtn(String text, Color bg, Color fg) {
        return new JButton(text) {
            boolean hov;
            {
                setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                setFont(new Font("Courier New", Font.BOLD, 11));
                setForeground(fg);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? bg.darker() : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                super.paintComponent(g);
                g2.dispose();
            }
        };
    }

    // ── Dividers / spacers ───────────────────────────────────
    private JPanel dashed() {
        JPanel p = new JPanel() {
            @Override public Dimension getPreferredSize() { return new Dimension(CARD_W, 10); }
            @Override public Dimension getMaximumSize()  { return new Dimension(Integer.MAX_VALUE, 10); }
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(INK_MUTED);
                g2.setStroke(new BasicStroke(0.8f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 1f, new float[]{4f, 3f}, 0f));
                g2.drawLine(0, 5, getWidth(), 5);
                g2.dispose();
            }
        };
        p.setBackground(PAPER_BG);
        return p;
    }

    private JPanel solid() {
        JPanel p = new JPanel() {
            @Override public Dimension getPreferredSize() { return new Dimension(CARD_W, 8); }
            @Override public Dimension getMaximumSize()  { return new Dimension(Integer.MAX_VALUE, 8); }
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(70, 65, 55));
                g2.setStroke(new BasicStroke(1f));
                g2.drawLine(0, 4, getWidth(), 4);
                g2.dispose();
            }
        };
        p.setBackground(PAPER_BG);
        return p;
    }

    private Component gap(int h) { return Box.createRigidArea(new Dimension(0, h)); }

    // ── Helpers ──────────────────────────────────────────────
    private String getName(Drink d) {
        if (d instanceof Drinks)   return ((Drinks)   d).ten();
        if (d instanceof Toppings) return ((Toppings) d).ten();
        return d.toString();
    }

    private String fmtVND(double v) {
        return String.format("%,.0fd", v).replace(',', '.');
    }

    private String toViWords(double amount) {
        long n = Math.round(amount);
        if (n == 0) return "Khong dong /.";
        long nghin = n / 1000, le = (n % 1000) / 100;
        return (le == 0) ? nghin + " nghin dong /." : nghin + " nghin " + le + " tram dong /.";
    }

    private Font plainF(int s)  { return new Font("Courier New", Font.PLAIN,  s); }
    private Font boldF (int s)  { return new Font("Courier New", Font.BOLD,   s); }
    private Font italicF(int s) { return new Font("Courier New", Font.ITALIC, s); }
}