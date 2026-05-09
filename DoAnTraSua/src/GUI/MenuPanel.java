package GUI;

import model.Drinks;
import model.Toppings;
import model.Order;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MenuPanel extends JPanel {

    // ── Model ───────────────────────────────────────────────
    private model.Menu menu;
    private Order      order;
    private CartPanel  cartPanel;

    // ── Colors ──────────────────────────────────────────────
    static final Color BG         = new Color(250, 247, 242);
    static final Color BROWN      = new Color(101,  67, 33);
    static final Color BROWN_DARK = new Color(60,   35, 10);
    static final Color GOLD       = new Color(196, 155, 66);
    static final Color CREAM      = new Color(255, 248, 235);
    static final Color RED_PRICE  = new Color(200,  50, 50);
    static final Color RED_BTN    = new Color(192,  57, 43);
    static final Color CARD_BG    = Color.WHITE;
    static final Color BORDER_C   = new Color(230, 215, 195);
    static final Color HOVER_CARD = new Color(255, 248, 238);

    // ── Đường dẫn ảnh (đặt trong src/resources/) ───────────
    private static final String[] DRINK_IMAGES = {
        "/resources/trasua_truyenthong.jpg",
        "/resources/trasua_thaixanh.png",
        "/resources/trasua_socola.jpg",
        "/resources/trasua_matcha.jpg"
    };

    private static final String[] DRINK_EMOJI  = { "\uD83E\uDD64", "\uD83C\uDF75", "\uD83C\uDF6B", "\uD83C\uDF35" };
    private static final Color[]  DRINK_COLORS = {
        new Color(0xFFF3E0), new Color(0xE8F5E9), new Color(0xFCE4EC), new Color(0xE8F5E9)
    };

    public MenuPanel(model.Menu menu, Order order, CartPanel cartPanel) {
        this.menu      = menu;
        this.order     = order;
        this.cartPanel = cartPanel;
        setLayout(new BorderLayout());
        setBackground(BG);
        buildUI();
    }

    public MenuPanel(model.Menu menu, Order order) {
        this(menu, order, null);
    }

    public void setCartPanel(CartPanel cp) {
        this.cartPanel = cp;
    }

    // ── Load ảnh từ resources ───────────────────────────────
    private Image loadImage(String path) {
        try {
            java.net.URL url = getClass().getResource(path);
            if (url != null) {
                return new ImageIcon(url).getImage();
            }
            System.out.println("Không tìm thấy ảnh: " + path);
        } catch (Exception e) {
            System.out.println("Lỗi load ảnh: " + path + " — " + e.getMessage());
        }
        return null;
    }

    private void buildUI() {
        add(buildSectionTitle(), BorderLayout.NORTH);
        add(buildGridScroll(),   BorderLayout.CENTER);
    }

    private JPanel buildSectionTitle() {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(BG);
        wrap.setBorder(new EmptyBorder(20, 24, 8, 24));

        JLabel sub = new JLabel("MeoMeoTea", SwingConstants.CENTER);
        sub.setFont(new Font("Serif", Font.ITALIC, 18));
        sub.setForeground(new Color(160, 120, 70));

        JLabel title = new JLabel("MENU", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(BROWN_DARK);

        JPanel deco = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(GOLD);
                int cx = getWidth() / 2, y = getHeight() / 2;
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawLine(cx - 120, y, cx - 14, y);
                g2.drawLine(cx + 14,  y, cx + 120, y);
                g2.fillOval(cx - 6, y - 6, 12, 12);
                g2.dispose();
            }
        };
        deco.setOpaque(false);
        deco.setPreferredSize(new Dimension(0, 20));

        JPanel toppingBanner = buildToppingBanner();

        JPanel center = new JPanel(new GridLayout(4, 1, 0, 4));
        center.setOpaque(false);
        center.add(sub);
        center.add(title);
        center.add(deco);
        center.add(toppingBanner);

        wrap.add(center, BorderLayout.CENTER);
        return wrap;
    }

    private JPanel buildToppingBanner() {
        JPanel banner = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        banner.setBackground(new Color(255, 248, 230));
        banner.setBorder(new CompoundBorder(
            new LineBorder(BORDER_C, 1, true),
            new EmptyBorder(4, 16, 4, 16)
        ));

        JLabel lbl = new JLabel("\u2736  Ưu đãi :  ");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(BROWN);
        banner.add(lbl);

        JLabel disc = new JLabel("  Chỉ từ 5 ly giảm 10% ");
        disc.setFont(new Font("SansSerif", Font.BOLD, 12));
        disc.setForeground(RED_PRICE);
        banner.add(disc);

        return banner;
    }

    private JScrollPane buildGridScroll() {
        JPanel grid = new JPanel(new GridLayout(1, menu.drink.length, 16, 16));
        grid.setBackground(BG);
        grid.setBorder(new EmptyBorder(16, 24, 24, 24));

        for (int i = 0; i < menu.drink.length; i++) {
            grid.add(buildDrinkCard(menu.drink[i], i));
        }

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(BG);
        scroll.getViewport().setBackground(BG);
        return scroll;
    }

    private JPanel buildDrinkCard(Drinks drink, int idx) {
        String emoji    = DRINK_EMOJI [idx % DRINK_EMOJI.length];
        Color  bgCircle = DRINK_COLORS[idx % DRINK_COLORS.length];

        // Load ảnh tương ứng
        Image drinkImage = loadImage(DRINK_IMAGES[idx % DRINK_IMAGES.length]);

        JPanel card = new JPanel(new BorderLayout(0, 10)) {
            boolean hov = false;
            {
                setBackground(CARD_BG);
                setBorder(new CompoundBorder(
                    new LineBorder(BORDER_C, 1, true),
                    new EmptyBorder(18, 16, 18, 16)
                ));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited (MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? HOVER_CARD : CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                if (hov) {
                    g2.setColor(GOLD);
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 14, 14);
                }
                g2.dispose();
            }
        };

        // ── imgPanel: hiển thị ảnh clip hình tròn ──────────
        JPanel imgPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int sz = Math.min(getWidth(), getHeight()) - 24;
                int x  = (getWidth()  - sz) / 2;
                int y  = (getHeight() - sz) / 2;

                // Bóng đổ
                g2.setColor(new Color(0, 0, 0, 25));
                g2.fillOval(x + 5, y + 7, sz, sz);

                // Clip hình tròn
                Shape circle = new Ellipse2D.Float(x, y, sz, sz);
                g2.setClip(circle);

                if (drinkImage != null) {
                    // Vẽ ảnh thật vào hình tròn (cover-fit: giữ tỉ lệ, fill đầy)
                    int iw = drinkImage.getWidth(this);
                    int ih = drinkImage.getHeight(this);
                    if (iw > 0 && ih > 0) {
                        double scale = Math.max((double) sz / iw, (double) sz / ih);
                        int drawW = (int)(iw * scale);
                        int drawH = (int)(ih * scale);
                        int drawX = x + (sz - drawW) / 2;
                        int drawY = y + (sz - drawH) / 2;
                        g2.drawImage(drinkImage, drawX, drawY, drawW, drawH, this);
                    }
                } else {
                    // Fallback: màu nền + emoji nếu không load được ảnh
                    g2.setColor(bgCircle);
                    g2.fillOval(x, y, sz, sz);
                    g2.setClip(null);
                    g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, sz / 2));
                    FontMetrics fm = g2.getFontMetrics();
                    int ex = (getWidth()  - fm.stringWidth(emoji)) / 2;
                    int ey = (getHeight() + fm.getAscent() - fm.getDescent()) / 2 - 4;
                    g2.setColor(new Color(0, 0, 0, 200));
                    g2.drawString(emoji, ex, ey);
                }

                // Viền tròn vàng
                g2.setClip(null);
                g2.setColor(GOLD);
                g2.setStroke(new BasicStroke(2.5f));
                g2.drawOval(x, y, sz, sz);

                g2.dispose();
            }
        };
        imgPanel.setOpaque(false);
        imgPanel.setPreferredSize(new Dimension(0, 160));

        JLabel nameLbl = new JLabel(drink.ten().toUpperCase(), SwingConstants.CENTER);
        nameLbl.setFont(new Font("Serif", Font.BOLD, 15));
        nameLbl.setForeground(BROWN_DARK);

        JLabel priceLbl = new JLabel(
            String.format("M: %,.0f\u0111  |  L: %,.0f\u0111", drink.priceM, drink.priceL).replace(',', '.'),
            SwingConstants.CENTER);
        priceLbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        priceLbl.setForeground(RED_PRICE);

        JButton orderBtn = buildRoundBtn("+ Chọn Size & Topping", RED_BTN, Color.WHITE);

        orderBtn.addActionListener(e ->
            new ToppingPanel(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                drink, order, cartPanel
            )
        );

        JPanel btnRow = new JPanel(new GridLayout(1, 1, 0, 6));
        btnRow.setOpaque(false);
        btnRow.add(orderBtn);

        JPanel info = new JPanel(new GridLayout(3, 1, 0, 6));
        info.setOpaque(false);
        info.add(nameLbl);
        info.add(priceLbl);
        info.add(btnRow);

        card.add(imgPanel, BorderLayout.CENTER);
        card.add(info,     BorderLayout.SOUTH);
        return card;
    }

    private JButton buildRoundBtn(String text, Color bg, Color fg) {
        return new JButton(text) {
            boolean hov = false;
            {
                setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
                setFont(new Font("SansSerif", Font.BOLD, 13));
                setForeground(fg);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setPreferredSize(new Dimension(0, 36));
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

    private void handleAddDrink(Drinks drink) {
        order.addItem(drink);
        if (cartPanel != null) {
        cartPanel.refresh();
        cartPanel.setVisible(true);
        SwingUtilities.getWindowAncestor(this).revalidate();
        }
    }
}