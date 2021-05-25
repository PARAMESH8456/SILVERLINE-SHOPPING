package DataBaseHandler;

import java.sql.*;

public class DataBaseHandler {
    private final String url = "jdbc:mysql://localhost:3306/silverline";
    private final String userName = "root";
    private final String password = "8456";
    private Connection connection;
    private Statement statement;
    private static DataBaseHandler handler = null;
    private DataBaseHandler(){
        createConnection();
        new Thread(() -> {
            setUpMobileTable();
            setUpElectronicsTable();
        }).start();
    }

    public static DataBaseHandler getInstance(){

        if(handler == null){
            handler = new DataBaseHandler();
        }
        return handler;
    }

    public void createConnection(){
        try {
            connection = DriverManager.getConnection(url, userName, password);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void setUpMobileTable(){
        String Table_Name = "mobile";
        try {
            statement = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet rs = dbm.getTables(null,null,Table_Name.toUpperCase(),null);

            if(rs.next()){
                System.out.println("Table "+Table_Name+" already exists.");
            }else{
                String query = "CREATE TABLE `silverline`.`mobile` (\n" +
                               "  `product_id` INT NOT NULL,\n" +
                               "  `product_name` VARCHAR(200) NULL,\n" +
                               "  `model` VARCHAR(45) NULL,\n" +
                               "  `price` DOUBLE NULL,\n" +
                               "  `image` VARCHAR(300) NULL,\n" +
                               "  PRIMARY KEY (`product_id`),\n" +
                               "  UNIQUE INDEX `product_id_UNIQUE` (`product_id` ASC) VISIBLE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setUpElectronicsTable(){
        String Table_Name = "electronics";
        try {
            statement = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet rs = dbm.getTables(null,null,Table_Name.toUpperCase(),null);

            if(rs.next()){
                System.out.println("Table "+Table_Name+" already exists.");
            }else{
                String query = "CREATE TABLE `silverline`.`electronics` (\n" +
                               "  `product_id` INT NOT NULL,\n" +
                               "  `product_name` VARCHAR(200) NULL,\n" +
                               "  `model` VARCHAR(45) NULL,\n" +
                               "  `price` DOUBLE NULL,\n" +
                               "  `image` VARCHAR(300) NULL,\n" +
                               "  PRIMARY KEY (`product_id`),\n" +
                               "  UNIQUE INDEX `product_id_UNIQUE` (`product_id` ASC) VISIBLE);";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet execQuery(String q){
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public  ResultSet execSpecialQuery(int t,String key){
        ResultSet rs = null;
        PreparedStatement ps = null;
        switch (t){
            case 1:
                try {
                    ps = connection.prepareStatement("select * from mobile where product_name like ?");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    ps = connection.prepareStatement("select * from electronics where product_name like ?");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
        }
        if(ps != null){
            try {
                ps.setString(1, "%" + key + "%");
                return ps.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
