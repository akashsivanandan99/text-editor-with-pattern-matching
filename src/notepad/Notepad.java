package notepad;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import notepad.BoyerMoore;

public class Notepad extends JFrame implements ActionListener {
    JTextArea area;
    JScrollPane pane;
    String text;
    String parentText;
    Notepad(){
        setBounds(0, 0, 1950, 1050);

        //Declaring a JMenuBar object
        JMenuBar menuBar = new JMenuBar();


        Color menuColor = new Color(58, 54, 53);
        UIManager.put("MenuBar.background", Color.black );
        menuBar.setBackground(menuColor);

        //Creating the File menu in the menu bar
        JMenu file = new JMenu("File");

        //Creating the new file option in the file menu and adding an action listener
        JMenuItem newdoc = new JMenuItem("New");
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        newdoc.addActionListener(this);

        //Creating the open file option in the file menu and adding an action listener
        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        open.addActionListener(this);

        //Creating the save file option in the file menu and adding an action listener
        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        save.addActionListener(this);

        //Creating the print option in the file menu
        JMenuItem print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        print.addActionListener(this);

        //Creating the exit application option in the file menu
        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        exit.addActionListener(this);




        //Adding the menu items to the file menu
        file.add(newdoc);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);


        //Creating the edit menu in the menu bar
        JMenu edit = new JMenu("Edit");

        //Creating the copy option in the Edit menu
        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        copy.addActionListener(this);

        //Creating the paste option in the edit menu
        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        paste.addActionListener(this);

        //Creating the cut option in the edit menu
        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        cut.addActionListener(this);

        //Creating the select all option in the edit menu
        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        selectAll.addActionListener(this);

        //Creating the find text option in the edit menu
        JMenuItem find = new JMenuItem("Find Text");
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        find.addActionListener(this);


        //Adding the menu items to the edit menu
        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(selectAll);
        edit.add(find);


        //Creating a help menu
        JMenu help = new JMenu("Help");

        //Creating the about us option in the help menu
        JMenuItem about = new JMenuItem("About Notify");
        about.addActionListener(this);

        //Adding the menu item to the help menu
        help.add(about);


        //Adding the File, Edit and Help menus to the menu bar
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(help);

        setJMenuBar(menuBar);

        area = new JTextArea();


        area.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);


        pane = new JScrollPane(area);
        pane.setBorder(BorderFactory.createEmptyBorder());add(pane, BorderLayout.CENTER);

    }



    public static void main(String[]args ){
        new Notepad().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getActionCommand().equals("New")){
            area.setText("");
        }


        else if (e.getActionCommand().equals("Open")){
            JFileChooser openChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
            area.setBackground(Color.darkGray);
            openChooser.setFileFilter(filter);
            openChooser.setApproveButtonText("Open");

            int action = openChooser.showOpenDialog(this);
            if(action != JFileChooser.APPROVE_OPTION){
                return;
            }

            File file = openChooser.getSelectedFile();
            try{
                BufferedReader reader = new BufferedReader((new FileReader(file)));
                area.read(reader, null);
            }
            catch(Exception ignored){}



        }

        else if (e.getActionCommand().equals("Save")){
            JFileChooser saveChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
            int action = saveChooser.showSaveDialog(this);
            if(action != JFileChooser.APPROVE_OPTION){
                return;
            }


            File file = new File(saveChooser.getSelectedFile() + ".txt");
            BufferedWriter outFile = null;
            try{
                outFile = new BufferedWriter(new FileWriter(file));
                area.write(outFile);
            }
            catch(Exception ignored){}
        }

        else if (e.getActionCommand().equals("Print")){
            try{
                area.print();
            }
            catch(Exception exception){}
        }
        else if (e.getActionCommand().equals("Exit")){
            System.exit(0);
        }
        else if (e.getActionCommand().equals("Copy")){
            text = area.getSelectedText();
            System.out.println("Text copied: " +text);
        }
        else if (e.getActionCommand().equals("Paste")){
            area.insert(text,area.getCaretPosition());
            System.out.println(text + " inserted");
        }
        else if(e.getActionCommand().equals("Cut")){
            text = area.getSelectedText();
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
        }
        else if(e.getActionCommand().equals("Select All")){
            area.selectAll();
        }
        else if(e.getActionCommand().equals("Find Text")){
            String searchText = JOptionPane.showInputDialog(this,
                    "Enter text to be searched", null);
            if (searchText != null && (searchText.length() > 0)){
                parentText = area.getText();
                BoyerMoore bm = new BoyerMoore();
                int position = bm.findPattern(parentText, searchText);
                area.setCaretPosition(position);

            }
            System.out.println("No input");
        }

        else if(e.getActionCommand().equals("About the notepad")){
//            new About.setVisible(true);
            //TODO finish this
            System.out.println("Work in progress");
        }
    }
}
