import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class ProductManager {
    private String jdbcURL = "jdbc:mysql://localhost:3306/test?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

    private static final String INSERT_PRODUCT = "INSERT INTO product" +" VALUES" +"(?,?,?,?,?,?,?,?);";
    private static final String INSERT_TV_DETAIL = "update tivi_detail set overview=?,connection=?,smart_internet=?,image_sound_technology=? " + "where id=?;";
    private static final String SELECT_ALL_LINK_TV = "select id,link from tivi_detail;";


    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertProduct(String id, String type, String name, String brand, int price, String image,int amount, String link) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT);) {
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,type);
            preparedStatement.setString(3,name);
            preparedStatement.setString(4,brand);
            preparedStatement.setInt(5,price);
            preparedStatement.setString(6,image);
            preparedStatement.setInt(7,amount);
            preparedStatement.setString(8,link);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertTVDetail(String id, String overview, String connect, String smart_internet, String image_sound_technology){
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TV_DETAIL);) {
            preparedStatement.setString(1,overview);
            preparedStatement.setString(2,connect);
            preparedStatement.setString(3,smart_internet);
            preparedStatement.setString(4,image_sound_technology);
            preparedStatement.setString(5,id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addDetail() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LINK_TV);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String link = rs.getString("link");
                System.out.println(id + " " + link);
                ArrayList<String> details = CrawlTVDetail.getTVDetail(link);
                String overview = details.get(0);
                String connect = details.get(1);
                String smart_internet = details.get(2);
                String technology = details.get(3);
                ProductManager productManager = new ProductManager();
                productManager.insertTVDetail(id, overview,connect,smart_internet,technology);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ProductManager productManager = new ProductManager();
        productManager.addDetail();
    }
}
