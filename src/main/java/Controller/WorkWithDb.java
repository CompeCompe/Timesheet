
package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkWithDb {
    //Запрос для изменения таблицы в зависимости от департамента и месяца
    public static String[][] UpdateTimeSheet(String department, String month) throws SQLException {
        //Подключение к базе данных
        final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Timesheet?characterEncoding=utf8", "root", "zxcvasdf");
        final Statement stmt = connection.createStatement();
        //Получение списка рабочих в определенном департаменте
        List<String> workers = new ArrayList<>();
        ResultSet workersInDepartment = stmt.executeQuery("SELECT Name FROM Workers Where Department = \""+ department +"\"");
        while(workersInDepartment.next()){
            workers.add(workersInDepartment.getString(1));
        }
        //Получение объдиненных таблиц для вывода кодификаторов на каждый день в месяце
        String[][] information = new String[workers.size()][33];
        for(int i = 0; i < workers.size(); i++){
            String[] kontroll = new String[33];
            String query = "SELECT Name,Number,Day,Encoding FROM Workers JOIN Calendar ON Workkers_id = idWorkers " +
                    " Join Encodings ON Status = idEncodings where Month = \"" + month +
                    "\" and Name = \""+
                    workers.get(i) +
                    "\"order by Day+0 ASC;";
            ResultSet rs = stmt.executeQuery(query);
            for(int j = 2;j < 33;j++) {
                if (rs.next()) {
                    kontroll[0] = rs.getString(1);
                    kontroll[1] = rs.getString(2);
                    kontroll[rs.getInt(3) + 1] = rs.getString(4);

                }
                if (kontroll[j] == null) {
                    kontroll[j] = "-";
                }
            }
            information[i] = kontroll;
        }

        return information;
    }
}
