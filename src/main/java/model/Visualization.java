package model;

import Controller.WorkWithDb;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Visualization extends JFrame{

    public void setInformation(String[][] information) {
        this.information = information;
    }

    private String[][] information = new String[][]{{"Антон Антоныч","1231","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я","Я"}};

    // Заголовки столбцов
    private Object[] columnsHeader = new String[]{"ФИО", "Табель",
            "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};

    public Visualization() throws SQLException {
        super("Табель");
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }



        // Создание таблицы на основании модели данных
        JTable table = new JTable(createTableModel(information));
        resizableFalse(table);

        JLabel department = new JLabel("Выбор департамента");

        JComboBox<String> departments = new JComboBox<String>(new String[] { "Автоматизация", "Разработка", "Точильня"});

        JLabel month = new JLabel("Выбор месяца");

        JComboBox<String> months = new JComboBox<String>(new String[] { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"});

        JButton update = new JButton("Обновить табель");
        //Изменение таблицы по нажатию
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setInformation(WorkWithDb.UpdateTimeSheet((String)departments.getSelectedItem(),(String)months.getSelectedItem()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
               table.setModel(createTableModel(information));
                resizableFalse(table);
                System.out.println();
            }
        });


        //Добавление элементов на экран
        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(department);
        contents.add(departments);
        contents.add(month);
        contents.add(months);
        contents.add(update);
        contents.add(new JScrollPane(table));


        setContentPane(contents);
        setSize(1500, 500);
        setVisible(true);
        setResizable(false);
        setVisible(true);
    }

    private DefaultTableModel createTableModel(String[][] info){

        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        // Определение столбцов
        tableModel.setColumnIdentifiers(columnsHeader);
        // Наполнение модели данными
        for (int i = 0; i < info.length; i++) {
            tableModel.addRow(info[i]);
        }
        return tableModel;
    }
    //Настройки разверов таблицы и запрет на их изменение
    private void resizableFalse(JTable table) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );


        table.setCellSelectionEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(400);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);

        int columnNumber = table.getColumnModel().getColumnCount();
        for (int i = 0;i < columnNumber;i++){
            table.getColumnModel().getColumn(i).setResizable(false);
            table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
            if(i > 1){
                table.getColumnModel().getColumn(i).setPreferredWidth(50);
            }
        }
    }

}

