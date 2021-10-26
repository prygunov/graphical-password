package com.demo.graphicpassword;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.List;

public class LoginForm extends JFrame {
    public JPanel rootPanel;
    public JTextField loginField;
    public JButton registerButton;
    public JButton loginButton;
    public JPanel passPanel;
    public JTable passwordTable;
    public JButton resetButton;
    public JCheckBox showClicksCheckBox;
    public JLabel symbolsCounterField;
    public JCheckBox mixCheckBox;

    private final List<Integer> enteredPassword = new ArrayList<>();
    private Object[][] data;

    LoginForm(ApplicationInterface applicationInterface) {
        setContentPane(rootPanel);
        setSize(700, 650);
        setLocationByPlatform(true);
        setTitle("Медицинская информационная система");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        registerButton.addActionListener(e -> {
            if (!enteredPassword.isEmpty() && !loginField.getText().isEmpty())
                applicationInterface.registerUser(new User(loginField.getText(), new ArrayList<>(enteredPassword)));
            else
                JOptionPane.showMessageDialog(this, "Пароль или логин не могут быть пустыми.");
            clearPassword();
        });

        resetButton.addActionListener(e -> clearPassword());

        showClicksCheckBox.addActionListener(e -> {
            if (showClicksCheckBox.isSelected())
                passwordTable.setSelectionBackground(Color.BLUE);
            else
                passwordTable.setSelectionBackground(Color.WHITE);
        });

        mixCheckBox.addActionListener(e -> updateTable());

        loginButton.addActionListener(e -> {
            applicationInterface.login(loginField.getText(), enteredPassword);
            clearPassword();
        });
    }


    void clearPassword(){
        updateTable();
        enteredPassword.clear();
        symbolsCounterField.setText("Набрано символов: " + 0);
    }

    ImageIcon[] loadImages() throws IOException {
        // загрузка картинок для графического пароля
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        int imagesSize = 38;
        ImageIcon[] images = new ImageIcon[imagesSize];
        for (int i = 1; i <= 38; i++) {
            images[i-1] = new ImageIcon(ImageIO.read(classloader.getResourceAsStream(i + ".png")));
        }
        return images;
    }

    private void updateTable(){
        String[] columnNames = {"1", "2","3","4","5","6"};
        boolean mix = mixCheckBox != null && mixCheckBox.isSelected();
        ((DefaultTableModel)passwordTable.getModel()).setDataVector(getData(mix), columnNames);
    }

    private Object[][] getData(boolean random){
        int columns = 6;
        int rows = 6;
        data = new Object[rows][columns];
        try {
            ImageIcon[] imageIcons = loadImages();
            if (!random)
                for (int i = 0; i < rows; i++)
                    System.arraycopy(imageIcons, i * rows, data[i], 0, columns);
            else {
                // случайное заполнение массива символов
                List<ImageIcon> images = new ArrayList<>(Arrays.asList(imageIcons));
                Random r = new Random();
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < columns; j++) {
                        int selectedIndex = r.nextInt(images.size());
                        data[i][j] = images.get(selectedIndex);
                        images.remove(selectedIndex);
                    }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void createUIComponents() {
        //заполнение данными модели таблицы
        DefaultTableModel model = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };

        passwordTable = new JTable(model);
        passwordTable.setRowHeight(70);
        passwordTable.setCellSelectionEnabled(true);
        passwordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        passwordTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                handleSelectionEvent();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        updateTable();
    }

    private int getHashCode(Image image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write((BufferedImage)image, "png", baos);
            return Arrays.hashCode(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    protected void handleSelectionEvent() {
        //обработка выбора символа, вычисление хешкода выбранного символа, запись в последовательность пароля
        int hashCode = getHashCode(((ImageIcon)data[passwordTable.getSelectedRow()][passwordTable.getSelectedColumn()]).getImage());
        enteredPassword.add(hashCode);
        symbolsCounterField.setText("Набрано символов: " +  enteredPassword.size());
    }
}
