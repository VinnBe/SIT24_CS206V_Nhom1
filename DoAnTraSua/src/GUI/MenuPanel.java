package GUI;

import model.Drinks;
import model.Toppings;
import model.Order;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import static java.time.zone.ZoneRulesProvider.refresh;
import java.util.function.Supplier;

public class MenuPanel extends JPanel {

    private model.Menu menu;
    private Order      order;
    private CartPanel  cartPanel;
    private Image cartImg; 
    static final Color BG         = new Color(250, 247, 242);
    static final Color BROWN      = new Color(101,  67, 33);
    static final Color BROWN_DARK = new Color(60,   35, 10);
    static final Color GOLD       = new Color(196, 155, 66);
    static final Color RED_PRICE  = new Color(200,  50, 50);
    static final Color RED_BTN    = new Color(192,  57, 43);
    static final Color CARD_BG    = Color.WHITE;
    static final Color BORDER_C   = new Color(230, 215, 195);
    static final Color HOVER_CARD = new Color(255, 248, 238);
    static final Color SECTION_BG = new Color(245, 238, 225);

    private static final String[] DRINK_IMAGES = {
        "trasua_truyenthong.jpg",
        "trasua_thaixanh.png",
        "trasua_socola.jpg",
        "trasua_matcha.jpg"
    };
    
    private static final String[] TRA_TRAI_CAY_IMAGES = {
    "tra_chanh.jpg",
    "tra_dau.jpg",
    "tra_mangcau.png",
    "tra_oi.png",
    "tra_vai.png"
    };
    
    private static final String[] DA_XAY_IMAGES = {
    "matcha_daxay.png",
    "socola_daxay.jpg"
    };

    private static final String[] TOPPING_IMAGES = {
        "tranchau.jpg",
        "banhflan.jpg",
        "hatthuytinh.jpg",
        "thachcunang.jpg",
        "tranchautrang.jpg"
    };
    private static final String[] DA_XAY_EMOJI  = {"\uD83C\uDF35", "\uD83C\uDF6B"};
    private static final String[] TRA_TRAI_CAY_EMOJI = {"\uD83C\uDF4B", "\uD83C\uDF53", "\uD83C\uDF4A", "\uD83C\uDF50", "\uD83C\uDF47"};
    private static final String[] DRINK_EMOJI  = { "\uD83E\uDD64", "\uD83C\uDF75", "\uD83C\uDF6B", "\uD83C\uDF35" };
    private static final Color[]  DRINK_COLORS = {
        new Color(0xFFF3E0), new Color(0xE8F5E9), new Color(0xFCE4EC), new Color(0xE8F5E9)
    };

    public MenuPanel(model.Menu menu, Order order, CartPanel cartPanel, Image cartImg) {
        this.menu = menu; this.order = order; this.cartPanel = cartPanel; this.cartImg=cartImg;
        setLayout(new BorderLayout());
        setBackground(BG);
        buildUI();
    }
    public MenuPanel(model.Menu menu, Order order) { this(menu, order, null,null); }
    public void setCartPanel(CartPanel cp) { this.cartPanel = cp; }
    
    private Image loadImage(String filename) {
        // Các thư mục sẽ tìm ảnh, theo thứ tự ưu tiên
        String[] searchDirs = {
            "resources" + java.io.File.separator,
            "src" + java.io.File.separator + "resources" + java.io.File.separator,
            "assets"    + java.io.File.separator,
            "images"    + java.io.File.separator,
            ""   // cùng thư mục chạy
        };

        // Lấy tên file (bỏ prefix /resources/ nếu có)
        String name = filename.contains("/") ? filename.substring(filename.lastIndexOf('/') + 1) : filename;

        for (String dir : searchDirs) {
            java.io.File f = new java.io.File(dir + name);
            if (f.exists() && f.isFile()) {
                try {
                    Image img = new ImageIcon(f.getAbsolutePath()).getImage();
                    // Đợi ảnh load xong
                    if (img != null) {
                        java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
                        tracker.addImage(img, 0);
                        tracker.waitForID(0);
                        if (img.getWidth(null) > 0) return img;
                    }
                } catch (Exception ignored) {}
            }
        }

        // Fallback: thử classpath
        try {
            java.net.URL url = getClass().getResource("/resources/" + name);
            if (url == null) url = getClass().getResource("/" + name);
            if (url != null) {
                Image img = new ImageIcon(url).getImage();
                if (img != null && img.getWidth(null) > 0) return img;
            }
        } catch (Exception ignored) {}

        return null;
    }

    private void buildUI() {
        add(buildHeader(), BorderLayout.NORTH);

        // Content toàn bộ cuộn dọc
        class ScrollablePanel extends JPanel implements Scrollable {
            @Override public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
            @Override public int getScrollableUnitIncrement(Rectangle r, int o, int d) { return 16; }
            @Override public int getScrollableBlockIncrement(Rectangle r, int o, int d) { return 16; }
            
            // Dòng lệnh ma thuật: Ép chiều rộng bám sát JScrollPane, không bị cắt lẹm
            @Override public boolean getScrollableTracksViewportWidth() { return true; } 
            
            @Override public boolean getScrollableTracksViewportHeight() { return false; }
        }

        // 2. Khởi tạo content bằng lớp vừa tạo thay vì JPanel mặc định
        JPanel content = new ScrollablePanel();
//        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG);

        content.add(buildSectionHeader("🧋 TRÀ SỮA"));
        content.add(buildDrinkGrid());
        content.add(Box.createVerticalStrut(16));
        content.add(buildSectionHeader("🍋 TRÀ TRÁI CÂY"));
        content.add(buildTraTraiCayGrid());
        content.add(Box.createVerticalStrut(16));
        content.add(buildSectionHeader("🧊 ĐÁ XAY"));
        content.add(buildDaXayGrid());
        content.add(Box.createVerticalStrut(16));
        content.add(buildSectionHeader("🧊 TOPPING"));
        content.add(buildToppingGrid());
        content.add(Box.createVerticalStrut(24));

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(BG);
        scroll.getViewport().setBackground(BG);
        scroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        add(scroll, BorderLayout.CENTER);
    }

    // ── Header logo + banner ──────────────────────────────────
    private JPanel buildHeader() {
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

        // Banner ưu đãi
        JPanel banner = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        banner.setBackground(new Color(255, 248, 230));
        banner.setBorder(new CompoundBorder(new LineBorder(BORDER_C, 1, true), new EmptyBorder(4, 16, 4, 16)));
        JLabel bl = new JLabel("\u2736  Ưu đãi :  ");
        bl.setFont(new Font("SansSerif", Font.BOLD, 12)); bl.setForeground(BROWN);
        JLabel bd = new JLabel("  Chỉ từ 5 ly giảm 10% ");
        bd.setFont(new Font("SansSerif", Font.BOLD, 12)); bd.setForeground(RED_PRICE);
        banner.add(bl); banner.add(bd);

        JPanel center = new JPanel(new GridLayout(4, 1, 0, 4));
        center.setOpaque(false);
        center.add(sub); center.add(title); center.add(deco); center.add(banner);
        wrap.add(center, BorderLayout.CENTER);
        return wrap;
    }

    // ── Section header (thanh ngang có tiêu đề nhóm) ──────────
    private JPanel buildSectionHeader(String text) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(SECTION_BG);
        p.setBorder(new EmptyBorder(10, 24, 10, 24));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Serif", Font.BOLD, 17));
        lbl.setForeground(BROWN_DARK);
        p.add(lbl, BorderLayout.WEST);

        // đường kẻ vàng bên dưới
        JPanel underline = new JPanel() {
            @Override public Dimension getPreferredSize() { return new Dimension(0, 2); }
            @Override protected void paintComponent(Graphics g) {
                g.setColor(GOLD);
                g.fillRect(0, 0, getWidth(), 2);
            }
        };
        underline.setOpaque(false);
        p.add(underline, BorderLayout.SOUTH);
        return p;
    }

    // ── Grid trà sữa (ngang, 4 card) ─────────────────────────
    private JPanel buildDrinkGrid() {
        JPanel grid = new JPanel(new GridLayout(1, menu.drink.length, 16, 16));
        grid.setBackground(BG);
        grid.setBorder(new EmptyBorder(16, 24, 8, 24));
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 380));
        for (int i = 0; i < menu.drink.length; i++) {
            grid.add(buildDrinkCard(menu.drink[i], i));
        }
        return grid;
    }
    
    private JPanel buildTraTraiCayGrid() {
        Drinks[] ttc  = menu.traTraiCay;
        int cols = 4;
        int rows = (int) Math.ceil((double) ttc.length / cols);

        JPanel grid = new JPanel(new GridLayout(rows, cols, 16, 16));
        grid.setBackground(BG);
        grid.setBorder(new EmptyBorder(16, 24, 8, 24));
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, rows * 380 + 40));

        for (int i = 0; i < ttc.length; i++) {
            grid.add(buildTraTraiCayCard(ttc[i], i));
        }
        // điền ô trống nếu số lẻ
        int remainder = ttc.length % cols;
        if (remainder != 0) {
            for (int i = 0; i < cols - remainder; i++) {
                JPanel empty = new JPanel();
                empty.setBackground(BG);
                grid.add(empty);
            }
        }
        return grid;
    }

    private JPanel buildTraTraiCayCard(Drinks drink, int idx) {
        String emoji   = TRA_TRAI_CAY_EMOJI [idx % TRA_TRAI_CAY_EMOJI.length];
        Color  bgColor = DRINK_COLORS        [idx % DRINK_COLORS.length];
        Image  img     = loadImage(TRA_TRAI_CAY_IMAGES[idx % TRA_TRAI_CAY_IMAGES.length]);

        JPanel card = new JPanel(new BorderLayout(0, 10)) {
            boolean hov = false;
            { setBackground(CARD_BG);
            setBorder(new CompoundBorder(
                new LineBorder(BORDER_C, 1, true),
                new EmptyBorder(18, 16, 18, 16)));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hov = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? HOVER_CARD : CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                if (hov) { g2.setColor(GOLD); g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 14, 14); }
                g2.dispose();
            }
        };
        
    JPanel imgPanel = new JPanel() {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            int sz = Math.min(getWidth(), getHeight()) - 24;
            int x = (getWidth()-sz)/2, y = (getHeight()-sz)/2;
            g2.setColor(new Color(0,0,0,25)); g2.fillOval(x+5,y+7,sz,sz);
            Shape circle = new java.awt.geom.Ellipse2D.Float(x,y,sz,sz);
            g2.setClip(circle);
            if (img != null) {
                int iw = img.getWidth(this), ih = img.getHeight(this);
                if (iw > 0 && ih > 0) {
                    double scale = Math.max((double)sz/iw,(double)sz/ih);
                    int dw=(int)(iw*scale), dh=(int)(ih*scale);
                    g2.drawImage(img, x+(sz-dw)/2, y+(sz-dh)/2, dw, dh, this);
                }
            } else {
                g2.setColor(bgColor); g2.fillOval(x,y,sz,sz);
                g2.setClip(null);
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, sz/2));
                FontMetrics fm = g2.getFontMetrics();
                g2.setColor(new Color(0,0,0,200));
                g2.drawString(emoji, (getWidth()-fm.stringWidth(emoji))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2-4);
            }
            g2.setClip(null);
            g2.setColor(GOLD); g2.setStroke(new BasicStroke(2.5f));
            g2.drawOval(x,y,sz,sz);
            g2.dispose();
        }
    };
    imgPanel.setOpaque(false);
    imgPanel.setPreferredSize(new Dimension(0, 160));

    JLabel nameLbl = new JLabel(drink.ten().toUpperCase(), SwingConstants.CENTER);
    nameLbl.setFont(new Font("Serif", Font.BOLD, 15));
    nameLbl.setForeground(BROWN_DARK);

    JLabel priceLbl = new JLabel(
        String.format("M: %,.0fđ  |  L: %,.0fđ", drink.getPriceM(), drink.getPriceL())
              .replace(',', '.'),
        SwingConstants.CENTER);
    priceLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
    priceLbl.setForeground(RED_PRICE);

    JButton orderBtn = buildRoundBtn("+ Chọn Size & Topping", RED_BTN, Color.WHITE);
    orderBtn.addActionListener(e -> new ToppingPanel(
        (JFrame) SwingUtilities.getWindowAncestor(this), drink, order, cartPanel));

    JPanel info = new JPanel(new GridLayout(3, 1, 0, 6));
    info.setOpaque(false);
    info.add(nameLbl); info.add(priceLbl);
    JPanel btnRow = new JPanel(new GridLayout(1,1));
    btnRow.setOpaque(false); btnRow.add(orderBtn);
    info.add(btnRow);

    card.add(imgPanel, BorderLayout.CENTER);
    card.add(info,     BorderLayout.SOUTH);
    return card;
}
    
    // ── Grid đá xay ───────────────────────────────────────────
private JPanel buildDaXayGrid() {
    Drinks[] daXay = menu.daXay;
    int cols = 4;
    int rows = (int) Math.ceil((double) daXay.length / cols);

    JPanel grid = new JPanel(new GridLayout(rows, cols, 16, 16));
    grid.setBackground(BG);
    grid.setBorder(new EmptyBorder(16, 24, 8, 24));
    grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, rows * 380 + 40));

    for (int i = 0; i < daXay.length; i++) {
        grid.add(buildDaXayCard(daXay[i], i));
    }
    // Điền ô trống nếu số lẻ
    int remainder = daXay.length % cols;
    if (remainder != 0) {
        for (int i = 0; i < cols - remainder; i++) {
            JPanel empty = new JPanel();
            empty.setBackground(BG);
            grid.add(empty);
        }
    }
    return grid;
}

// ── Card đá xay ───────────────────────────────────────────
private JPanel buildDaXayCard(Drinks drink, int idx) {
    String emoji   = DA_XAY_EMOJI [idx % DA_XAY_EMOJI.length];
    Color  bgColor = DRINK_COLORS [idx % DRINK_COLORS.length];
    Image  img     = loadImage(DA_XAY_IMAGES[idx % DA_XAY_IMAGES.length]);

    JPanel card = new JPanel(new BorderLayout(0, 10)) {
        boolean hov = false;
        {
            setBackground(CARD_BG);
            setBorder(new CompoundBorder(
                new LineBorder(BORDER_C, 1, true),
                new EmptyBorder(18, 16, 18, 16)));
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

    JPanel imgPanel = new JPanel() {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            int sz = Math.min(getWidth(), getHeight()) - 24;
            int x  = (getWidth() - sz) / 2, y = (getHeight() - sz) / 2;
            // Bóng
            g2.setColor(new Color(0, 0, 0, 25));
            g2.fillOval(x + 5, y + 7, sz, sz);
            // Clip tròn
            Shape oldClip = g2.getClip(); // 1. Lưu lại ranh giới của ScrollPane
            Shape circle = new java.awt.geom.Ellipse2D.Float(x,y,sz,sz);
            g2.clip(circle); // 2. Dùng hàm clip() thay vì setClip()

            if (img != null) {
                int iw = img.getWidth(this), ih = img.getHeight(this);
                if (iw > 0 && ih > 0) {
                    double scale = Math.max((double)sz/iw,(double)sz/ih);
                    int dw=(int)(iw*scale), dh=(int)(ih*scale);
                    g2.drawImage(img, x+(sz-dw)/2, y+(sz-dh)/2, dw, dh, this);
                }
            } else {
                g2.setColor(bgColor); g2.fillOval(x,y,sz,sz);
                // Xóa chữ g2.setClip(null) ở đây nếu có
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, sz/2));
                FontMetrics fm = g2.getFontMetrics();
                g2.setColor(new Color(0,0,0,200));
                g2.drawString(emoji, (getWidth()-fm.stringWidth(emoji))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2-4);
            }
            
            g2.setClip(oldClip); // 3. Trả lại ranh giới gốc thay vì dùng null
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
        String.format("M: %,.0fđ  |  L: %,.0fđ", drink.getPriceM(), drink.getPriceL())
              .replace(',', '.'),
        SwingConstants.CENTER);
    priceLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
    priceLbl.setForeground(RED_PRICE);

    JButton orderBtn = buildRoundBtn("+ Chọn Size & Topping", RED_BTN, Color.WHITE);
    orderBtn.addActionListener(e -> new ToppingPanel(
        (JFrame) SwingUtilities.getWindowAncestor(this), drink, order, cartPanel));

    JPanel info = new JPanel(new GridLayout(3, 1, 0, 6));
    info.setOpaque(false);
    info.add(nameLbl);
    info.add(priceLbl);
    JPanel btnRow = new JPanel(new GridLayout(1, 1));
    btnRow.setOpaque(false);
    btnRow.add(orderBtn);
    info.add(btnRow);

    card.add(imgPanel, BorderLayout.CENTER);
    card.add(info,     BorderLayout.SOUTH);
    return card;
}
    // ── Grid topping (2 cột) ──────────────────────────────────
    private JPanel buildToppingGrid() {
        model.Toppings[] toppings = model.Inventory.getToppings();
        int cols = 3;
        int rows = (int) Math.ceil((double) toppings.length / cols);

        JPanel grid = new JPanel(new GridLayout(rows, cols, 14, 14));
        grid.setBackground(BG);
        grid.setBorder(new EmptyBorder(16, 24, 8, 24));
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, rows * 240 + 40));

        for (int i = 0; i < toppings.length; i++) {
            grid.add(buildToppingCard(toppings[i], i));
        }
        // Điền ô trống nếu số lẻ
        int remainder = toppings.length % cols;
        if (remainder != 0) {
            for (int i = 0; i < cols - remainder; i++) {
                JPanel empty = new JPanel();
                empty.setBackground(BG);
                grid.add(empty);
            }
        }
        return grid;
    }

    // ── Card đồ uống ──────────────────────────────────────────
    private JPanel buildDrinkCard(Drinks drink, int idx) {
        String emoji    = DRINK_EMOJI [idx % DRINK_EMOJI.length];
        Color  bgCircle = DRINK_COLORS[idx % DRINK_COLORS.length];
        Image  drinkImage = loadImage(DRINK_IMAGES[idx % DRINK_IMAGES.length]);

        JPanel card = new JPanel(new BorderLayout(0, 10)) {
            boolean hov = false;
            { setBackground(CARD_BG);
              setBorder(new CompoundBorder(new LineBorder(BORDER_C, 1, true), new EmptyBorder(18, 16, 18, 16)));
              setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
              addMouseListener(new MouseAdapter() {
                  public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                  public void mouseExited (MouseEvent e) { hov = false; repaint(); }
              }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? HOVER_CARD : CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                if (hov) { g2.setColor(GOLD); g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 14, 14); }
                g2.dispose();
            }
        };

        JPanel imgPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                int sz = Math.min(getWidth(), getHeight()) - 24;
                int x = (getWidth() - sz) / 2, y = (getHeight() - sz) / 2;
                g2.setColor(new Color(0, 0, 0, 25)); g2.fillOval(x+5, y+7, sz, sz);
                Shape circle = new Ellipse2D.Float(x, y, sz, sz); g2.setClip(circle);
                if (drinkImage != null) {
                    int iw = drinkImage.getWidth(this), ih = drinkImage.getHeight(this);
                    if (iw > 0 && ih > 0) {
                        double scale = Math.max((double)sz/iw, (double)sz/ih);
                        int dw=(int)(iw*scale), dh=(int)(ih*scale);
                        g2.drawImage(drinkImage, x+(sz-dw)/2, y+(sz-dh)/2, dw, dh, this);
                    }
                } else {
                    g2.setColor(bgCircle); g2.fillOval(x, y, sz, sz); g2.setClip(null);
                    g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, sz/2));
                    FontMetrics fm = g2.getFontMetrics();
                    g2.setColor(new Color(0,0,0,200));
                    g2.drawString(emoji, (getWidth()-fm.stringWidth(emoji))/2,
                        (getHeight()+fm.getAscent()-fm.getDescent())/2-4);
                }
                g2.setClip(null); g2.setColor(GOLD); g2.setStroke(new BasicStroke(2.5f));
                g2.drawOval(x, y, sz, sz); g2.dispose();
            }
        };
        imgPanel.setOpaque(false);
        imgPanel.setPreferredSize(new Dimension(0, 160));

        JLabel nameLbl = new JLabel(drink.ten().toUpperCase(), SwingConstants.CENTER);
        nameLbl.setFont(new Font("Serif", Font.BOLD, 15)); nameLbl.setForeground(BROWN_DARK);

        JLabel priceLbl = new JLabel(
            String.format("M: %,.0f\u0111  |  L: %,.0f\u0111", drink.getPriceM(), drink.getPriceL()).replace(',', '.'),
            SwingConstants.CENTER);
        priceLbl.setFont(new Font("SansSerif", Font.BOLD, 14)); priceLbl.setForeground(RED_PRICE);

        JButton orderBtn = buildRoundBtn("+ Chọn Size & Topping", RED_BTN, Color.WHITE);
        orderBtn.addActionListener(e -> new ToppingPanel(
            (JFrame) SwingUtilities.getWindowAncestor(this), drink, order, cartPanel));

        JPanel info = new JPanel(new GridLayout(3, 1, 0, 6));
        info.setOpaque(false); info.add(nameLbl); info.add(priceLbl);
        JPanel btnRow = new JPanel(new GridLayout(1,1)); btnRow.setOpaque(false); btnRow.add(orderBtn);
        info.add(btnRow);

        card.add(imgPanel, BorderLayout.CENTER);
        card.add(info, BorderLayout.SOUTH);
        return card;
    }

    // ── Card topping ──────────────────────────────────────────
    private JPanel buildToppingCard(model.Toppings tp, int idx) {
//        boolean outOfStock = model.Inventory.soLuong[idx] <= 0;
        Supplier<Boolean> outOfStock =
            () -> model.Inventory.soLuong[idx] <= 0;
        JPanel card = new JPanel(new BorderLayout(0, 8)) {
            boolean hov = false;
            { setBackground(CARD_BG);
              setBorder(new CompoundBorder(new LineBorder(BORDER_C, 1, true), new EmptyBorder(14, 12, 14, 12)));
              setCursor(outOfStock.get() ? Cursor.getDefaultCursor() : Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
              if (!outOfStock.get()) addMouseListener(new MouseAdapter() {
                  public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                  public void mouseExited (MouseEvent e) { hov = false; repaint(); }
              }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = outOfStock.get() ? new Color(245,245,245) : (hov ? HOVER_CARD : CARD_BG);
                g2.setColor(bg); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                if (hov && !outOfStock.get()) { g2.setColor(GOLD); g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 14, 14); }
                g2.dispose();
            }
        };

        // ── Ảnh topping: khung vuông bo góc ─────────────────────
        Image tpImage = loadImage(TOPPING_IMAGES[idx % TOPPING_IMAGES.length]);
        String[] tpEmoji = {"🧆", "🍮", "💎", "🟢", "⚪"};

        JPanel imgPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int arc = 14, pad = 4;
                int w = getWidth() - pad * 2;
                int h = getHeight() - pad * 2;

                // Bóng đổ
                g2.setColor(new Color(0, 0, 0, 18));
                g2.fillRoundRect(pad + 3, pad + 4, w, h, arc, arc);

                // Nền khung
                g2.setColor(tpImage != null ? Color.WHITE : new Color(242, 232, 215));
                g2.fillRoundRect(pad, pad, w, h, arc, arc);
                Shape oldClip = g2.getClip(); // 1. Lưu lại clip

                if (tpImage != null) {
                    // Clip bo góc, vẽ ảnh fill đầy khung
                    Shape clip = new RoundRectangle2D.Float(pad, pad, w, h, arc, arc);
                    g2.clip(clip); // Dùng clip() thay vì setClip()
                    
                    int iw = tpImage.getWidth(null), ih = tpImage.getHeight(null);
                    if (iw > 0 && ih > 0) {
                        double scale = Math.max((double) w / iw, (double) h / ih);
                        int dw = (int)(iw * scale), dh = (int)(ih * scale);
                        g2.drawImage(tpImage,
                            pad + (w - dw) / 2, pad + (h - dh) / 2,
                            dw, dh, this);
                    }
                    
                    g2.setClip(oldClip); // Trả lại ranh giới ngay sau khi vẽ xong ảnh
                    
                    // Overlay mờ nếu hết hàng
                    if (outOfStock.get()) {
                        g2.setColor(new Color(255, 255, 255, 170));
                        g2.fillRoundRect(pad, pad, w, h, arc, arc);
                    }
                } else {
                    // Placeholder: emoji + gợi ý
                    // ĐÃ XÓA g2.setClip(null)
                    int fs = Math.min(w, h) / 3;
                    g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, fs));
                    FontMetrics fm = g2.getFontMetrics();
                    String em = tpEmoji[idx % tpEmoji.length];
                    g2.setColor(new Color(0, 0, 0, 150));
                    g2.drawString(em,
                        pad + (w - fm.stringWidth(em)) / 2,
                        pad + (h + fm.getAscent() - fm.getDescent()) / 2 - 6);
                    g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
                    g2.setColor(new Color(150, 110, 60, 180));
                    String hint = "chưa có ảnh";
                    g2.drawString(hint,
                        pad + (w - g2.getFontMetrics().stringWidth(hint)) / 2,
                        pad + h - 8);
                }

                // Viền khung
                // ĐÃ XÓA g2.setClip(null)
                g2.setColor(outOfStock.get() ? new Color(180, 160, 120) : GOLD);
                g2.setStroke(new BasicStroke(1.8f));
                g2.drawRoundRect(pad, pad, w - 1, h - 1, arc, arc);
                g2.setColor(outOfStock.get() ? new Color(180, 160, 120) : GOLD);
                g2.setStroke(new BasicStroke(1.8f));
                g2.drawRoundRect(pad, pad, w - 1, h - 1, arc, arc);

                g2.dispose();
            }
        };
        imgPanel.setOpaque(false);
        imgPanel.setPreferredSize(new Dimension(0, 130));

        JLabel nameLbl = new JLabel(tp.ten(), SwingConstants.CENTER);
        nameLbl.setFont(new Font("Serif", Font.BOLD, 13));
        nameLbl.setForeground(outOfStock.get() ? new Color(120,120,120,120) : BROWN_DARK);

        JLabel priceLbl = new JLabel(
            "+" + String.format("%,.0f", tp.getPrice()).replace(',', '.') + "đ",
            SwingConstants.CENTER);
        priceLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        priceLbl.setForeground(outOfStock.get() ? new Color(120,120,120,120) : RED_PRICE);

        JButton addBtn;
//        if (outOfStock.get()) {
//            addBtn = buildRoundBtn("Hết", new Color(180, 60, 60), Color.WHITE);
//            addBtn.setEnabled(false);
//        } else {
            addBtn = buildRoundBtn("+ Thêm", new Color(101, 67, 33), GOLD);
//            addBtn.addActionListener(e -> {
//                
//                order.addItem(tp);
//                if (cartPanel != null) {
//                    cartPanel.refresh();
//                    cartPanel.setVisible(true);
////                    SwingUtilities.getWindowAncestor(this).revalidate();
//                    Window parentWindow = SwingUtilities.getWindowAncestor(this);
//                    if (parentWindow != null) {
//                        parentWindow.revalidate(); 
//                        parentWindow.repaint();
//                    }
//                }
//            });
                addBtn.addActionListener(e -> {

                int id = model.Inventory.getIndex(tp.ten());
                int qty = 1;

                int need =(int) (qty * tp.getSoLuongDung());

    // dùng hàm reserve bạn đã có
                if (!model.Inventory.reserve(idx, need)) {
                    JOptionPane.showMessageDialog(this,
                    tp.ten() + " không đủ hàng!",
                    "Kho không đủ",
                    JOptionPane.WARNING_MESSAGE);
                    return;
                }
                order.addItem(tp);
    if (cartPanel != null) {
        cartPanel.refresh();
        cartPanel.setVisible(true);

        Window w = SwingUtilities.getWindowAncestor(this);
        if (w != null) {
            w.revalidate();
            w.repaint();
        }
    }
});
                    

        JPanel info = new JPanel(new GridLayout(3, 1, 0, 4));
        info.setOpaque(false); info.add(nameLbl); info.add(priceLbl);
        JPanel btnRow = new JPanel(new GridLayout(1,1)); btnRow.setOpaque(false); btnRow.add(addBtn);
        info.add(btnRow);

card.add(imgPanel, BorderLayout.CENTER);
        card.add(info, BorderLayout.SOUTH);
        return card;
    }

    private JButton buildRoundBtn(String text, Color bg, Color fg) {
        return new JButton(text) {
            boolean hov = false;
            { setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
              setFont(new Font("SansSerif", Font.BOLD, 13)); setForeground(fg);
              setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
              setPreferredSize(new Dimension(0, 34));
              addMouseListener(new MouseAdapter() {
                  public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                  public void mouseExited (MouseEvent e) { hov = false; repaint(); }
              }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? bg.darker() : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g); g2.dispose();
            }
        };
    }
}