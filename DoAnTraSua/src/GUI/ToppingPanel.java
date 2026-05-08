package GUI;

import model.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ToppingPanel extends JDialog {

    public ToppingPanel(JFrame parent, Drinks drink, Order order, CartPanel cartPanel) {
        super(parent, "Chọn Topping cho: " + drink.ten(), true);

        setLayout(new BorderLayout(10, 10));
        setSize(320, 220);
        setLocationRelativeTo(parent);
        setResizable(false);

        Toppings[] toppings = { new TranChau(), new BanhPlan() };

        // ── Tiêu đề ─────────────────────────────────────────
        JLabel header = new JLabel("  Chọn topping (có thể chọn nhiều):");
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.setBorder(new EmptyBorder(12, 4, 4, 4));

        // ── Danh sách checkbox ───────────────────────────────
        JPanel checkPanel = new JPanel(new GridLayout(0, 1, 5, 8));
        checkPanel.setBorder(new EmptyBorder(4, 20, 4, 20));

        JCheckBox[] boxes = new JCheckBox[toppings.length];
        for (int i = 0; i < toppings.length; i++) {
            boxes[i] = new JCheckBox(
                toppings[i].ten() + "  —  "
                + String.format("%,.0f", toppings[i].getPrice()).replace(',', '.') + "đ"
            );
            boxes[i].setFont(new Font("SansSerif", Font.PLAIN, 13));
            checkPanel.add(boxes[i]);
        }

        // ── Nút bấm ─────────────────────────────────────────
        JButton confirmBtn = new JButton("Thêm vào giỏ");
        confirmBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        confirmBtn.addActionListener(e -> {
            Drinks copy = drink.copy();                  // bản sao, không đụng drink gốc
            for (int i = 0; i < toppings.length; i++) {
                if (boxes[i].isSelected()) {
                    copy.themTopping(toppings[i]);       // thêm từng topping được tick
                }
            }
            order.addItem(copy);
            if (cartPanel != null) {
                cartPanel.refresh();
                cartPanel.setVisible(true);
                SwingUtilities.getWindowAncestor(cartPanel).revalidate();
            }
            dispose();
        });

        JButton skipBtn = new JButton("Không thêm topping");
        skipBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        skipBtn.addActionListener(e -> {
            Drinks copy = drink.copy();
            order.addItem(copy);
            if (cartPanel != null) {
                cartPanel.refresh();
                cartPanel.setVisible(true);
                SwingUtilities.getWindowAncestor(cartPanel).revalidate();
            }
            dispose();
        });

        JPanel btnRow = new JPanel(new GridLayout(2, 1, 0, 6));
        btnRow.setBorder(new EmptyBorder(4, 20, 16, 20));
        btnRow.add(confirmBtn);
        btnRow.add(skipBtn);

        add(header,     BorderLayout.NORTH);
        add(checkPanel, BorderLayout.CENTER);
        add(btnRow,     BorderLayout.SOUTH);

        setVisible(true);
    }
}