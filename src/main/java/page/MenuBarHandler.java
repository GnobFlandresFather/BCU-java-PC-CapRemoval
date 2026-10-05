package page;

import io.BCUWriter;
import main.MainBCU;
import main.Opts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuBarHandler {
    private static final JMenuBar bar = new JMenuBar();

    private static final List<JMenuItem> fileItems = new ArrayList<>();

    public static JMenuBar getBar() {
        return bar;
    }

    public static void initialize() {
        setFileItems();
        MainFrame.F.setJMenuBar(bar);
    }

    private static void setFileItems() {
        JMenu menu = new JMenu("File");
        JMenu history = new JMenu("History");

        bar.add(menu);
        bar.add(history);

        int shortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuItem save = new JMenuItem("Save All");
        save.setAccelerator(KeyStroke.getKeyStroke('S', shortcut));
        save.addActionListener(e -> {
            BCUWriter.writeData();
            Opts.pop("Successfully saved data.", "Save Confirmation");
        });

        JMenuItem back = new JMenuItem("Go Back");
        back.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        back.addActionListener(e -> {
            if(MainFrame.getPanel().getBackButton() != null) {
                MainFrame.getPanel().getBackButton().doClick();
            }
        });

        JMenuItem home = new JMenuItem("Main Menu");
        home.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0));
        home.addActionListener(e -> {
            if(MainFrame.getPanel().getBackButton() != null)  {
                do {
                    MainFrame.getPanel().getBackButton().doClick();
                } while (MainFrame.getPanel().getBackButton() != null);
            }
            else {
                home.setEnabled(true);
            } //Genuinely Godawful solution, unfortunately I do not know how to code properly, please forgive me.
            // Also has a visual glitch where it shows on the main page (Clicking it does nothing, that's what the else condition is for), it still works perfectly fine otherwise.
        });

        save.setEnabled(false);
        back.setEnabled(false);
        home.setEnabled(true);

        menu.add(save);
        history.add(back);
        history.add(home);

        fileItems.add(save);
        fileItems.add(back);
    }

    public static JMenuItem getFileItem(String n) {
        for (JMenuItem i : fileItems) {
            if (i.getText().equals(n))
                return i;
        }

        if(MainBCU.loaded) {
            System.out.println("Missing menu item: " + n);
        }

        return null;
    }
}
