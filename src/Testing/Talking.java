package Testing;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Talking extends JFrame {

    private JPanel contentPane;
    private JTextField wordsField;


    /**
     * Launch the application.
     */
    public static void main(String[] args) throws IOException {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Talking frame = new Talking();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Talking() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 50, 500, 500);
        contentPane.add(scrollPane);

        ArrayList talkingWords = new ArrayList();
        JList wordList = new JList();
        scrollPane.setViewportView(wordList);

        wordsField = new JTextField();
        wordsField.setBounds(50, 600, 360, 36);
        contentPane.add(wordsField);
        wordsField.setColumns(10);

        Socket socket = new Socket("127.0.0.1", 8089);
        PrintStream writer = new PrintStream(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(()->{
            while (true){
                String message = null;
                try {
                    message = reader.readLine();
                    if (message!=null&&message.equals("bye!")) {
                        talkingWords.add("robot：" + message);
                        wordList.setListData(talkingWords.toArray());
                        socket.shutdownInput();
                        break;
                    }else {
                        talkingWords.add("robot：" + message);
                        wordList.setListData(talkingWords.toArray());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        JButton sendButton = new JButton("Send");
        sendButton.setBounds(420, 600, 120, 36);
        contentPane.add(sendButton);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String line = wordsField.getText();
                    wordsField.setText("");
                    if(line.equals("bye!")){
                        writer.println(line);
                        talkingWords.add(line);
                        wordList.setListData(talkingWords.toArray());
                        socket.shutdownOutput();
                        sendButton.setEnabled(false);
                    }else {
                        writer.println(line);
                        talkingWords.add(line);
                        wordList.setListData(talkingWords.toArray());
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
