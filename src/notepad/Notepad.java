package notepad;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import notepad.BoyerMoore;
import notepad.RabinKarp;

public class Notepad extends JFrame implements ActionListener {
    JTextArea area;
    JScrollPane pane;
    String text;

    public ArrayList<Integer> positions = new ArrayList<>();
    public int currentPos;
    public  boolean dark = false;
    public int caretPos;
    Notepad(){
        setBounds(0, 0, 1950, 1050);

        JMenuBar menuBar = new JMenuBar();


        Color menuColor = new Color(58, 54, 53);
        UIManager.put("MenuBar.background", Color.black );
        menuBar.setBackground(menuColor);

        JMenu file = new JMenu("File");

        JMenuItem newdoc = new JMenuItem("New");
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        newdoc.addActionListener(this);

        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        open.addActionListener(this);

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        save.addActionListener(this);

        JMenuItem print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        print.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        exit.addActionListener(this);




        file.add(newdoc);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);


        JMenu edit = new JMenu("Edit");

        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        copy.addActionListener(this);

        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        paste.addActionListener(this);

        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        cut.addActionListener(this);

        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        selectAll.addActionListener(this);

        JMenuItem find = new JMenuItem("Find Text");
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        find.addActionListener(this);


        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(selectAll);
        edit.add(find);

        JMenu help = new JMenu("Help");

        JMenuItem about = new JMenuItem("About Notify");
        about.addActionListener(this);

        help.add(about);


        JMenu view = new JMenu ("View");

        //Creating the dark mode option ih the view menu
        JMenuItem darkMode = new JMenuItem("Dark Mode");
        darkMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        darkMode.addActionListener(this);

        JMenuItem lightMode = new JMenuItem("Light Mode");
        lightMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        lightMode.addActionListener(this);

        view.add(darkMode);
        view.add(lightMode);

        //Adding the File, Edit, View and Help menus to the menu bar
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(view);
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


        if( e.getActionCommand().equals("Dark Mode")){




                Color bgcolour = new Color(45, 42, 46);
                area.setBackground(bgcolour);
                area.setForeground(Color.white);
                area.setCaretColor(Color.yellow);
                dark = true;

        }
        else if(e.getActionCommand().equals("Light Mode")){
            area.setBackground(Color.WHITE);
            area.setForeground(Color.BLACK);
            area.setCaretColor(Color.BLACK);
        }
        else if (e.getActionCommand().equals("New")){
            area.setText("");
        }


        else if (e.getActionCommand().equals("Open")){
        JFileChooser openChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
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
            
            JFrame searchFrame = new JFrame();
            JPanel searchPanel = new JPanel();
            searchFrame.setLocationRelativeTo(this);
            JTextField searchBar = new JTextField();
            JLabel searchLabel = new JLabel("Find");
            JTextField replaceBar = new JTextField();
            JLabel replaceLabel = new JLabel("Replace");
            JButton okButton = new JButton("Go");
            JButton replaceButton = new JButton("Replace");

            //   Adding an action listener to the replace button
            replaceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String parentText = area.getText();
                    String searchText = searchBar.getText();
                    String replaceText = replaceBar.getText();
                    if (replaceText != null && (replaceText.length() > 0) && !positions.isEmpty()) {
                        String newString =parentText.replaceFirst(Pattern.quote(searchText),
                                Matcher.quoteReplacement(replaceText));
                        area.setText(newString);
                        area.setCaretPosition(caretPos);
                        positions = RabinKarp.search(searchText, newString, 101);
                        currentPos = 0;
                        System.out.println("\n\n New position");
                        System.out.println(Arrays.toString(positions.toArray()));

                    }
                }
            });
            JButton replaceAllButton = new JButton("Replace all");
            replaceAllButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String temp = "";
                    String parentText = area.getText();
                    String searchText = searchBar.getText();
                    String replaceText = replaceBar.getText();
                    if (replaceText != null && (replaceText.length()  > 0 )){

                        temp = parentText.replace(searchText, replaceText);
                        area.setText(temp);
                        area.setCaretPosition(caretPos);
                        positions = RabinKarp.search(searchText, temp, 101);
                        currentPos = 0;

                    }
                }
            });

            //   Adding an action listener to the find button
            okButton.addActionListener( new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Find button triggered");
                   String searchText = searchBar.getText();
                   System.out.println("Search text: " + searchText);
                   boolean listChecker = positions.isEmpty();
                   if(!listChecker) {
                       positions.clear();
                       System.out.println("\n\n Cleared arraylist \n\n");
                   }
                    if (searchText != null && (searchText.length() > 0)){
                        String parentText = area.getText();
                        positions.addAll(RabinKarp.search(searchText, parentText, 101));
//                        test.setText(Arrays.toString(positions.toArray()));
                        caretPos = positions.get(0);
                        area.setCaretPosition(caretPos);
                        currentPos = 0;



                    }
                }
            });
            JButton previousButton = new JButton("Previous");
            previousButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentPos > 0){
                        caretPos = positions.get(currentPos-1);
                        currentPos -= 1;
                        area.setCaretPosition(caretPos);
                        System.out.println("Current position: " + currentPos);
                    }
                }
            });
            JButton nextButton = new JButton("Next");
            nextButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newText = searchBar.getText();
//                    if(searchText == newText)
                    if (currentPos < positions.size() - 1){
                        caretPos = positions.get(currentPos + 1);
                        currentPos += 1;
                        area.setCaretPosition(caretPos);
                        System.out.println("Curent position: " + currentPos);
                    }
                }
            });
            searchFrame.setSize(400, 250);
            searchFrame.setLayout(null);
            searchFrame.add(searchPanel);

            searchPanel.setLayout(null);
            searchPanel.setBounds(0, 0, 400, 250);
            searchPanel.add(searchBar);

            searchBar.setBounds(90, 60, 250, 30);
            searchPanel.add(searchLabel);
            searchLabel.setBounds(40, 60, 50, 30);
            searchPanel.add(replaceBar);

            replaceBar.setBounds(90, 100, 250, 30);
            searchPanel.add(replaceLabel);
            replaceLabel.setBounds(40, 100, 50, 30);

            searchPanel.add(okButton);
            okButton.setBounds(80, 130, 80, 30);
            searchPanel.add(replaceButton);

            replaceButton.setBounds(180, 130, 80, 30);
            searchPanel.add(replaceAllButton);
            replaceAllButton.setBounds(280, 130, 100, 30);

            searchPanel.add(previousButton);
            previousButton.setBounds(100, 180, 80, 30);
            searchPanel.add(nextButton);

            nextButton.setBounds(260, 180, 80, 30);
            searchFrame.setVisible(true);


        }

        else if(e.getActionCommand().equals("About the notepad")){
            //TODO finish this
            System.out.println("Work in progress");
        }
    }
}
