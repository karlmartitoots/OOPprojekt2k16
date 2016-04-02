import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Alex on 27/03/2016.
 */
public class Andmebaas {
    public void createBase() {
        Connection connection;
        Statement statement;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:kaardid.db");
            statement = connection.createStatement();
            String sql = "CREATE TABLE KAART(NAME CHAR(50) PRIMARY KEY NOT NULL, ATT INT NOT NULL, HP INT NOT NULL, COST INT NOT NULL, EFFECT TXT NOT NULL, FLAVOR TXT NOT NULL);";

            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void addKaart(Kaart kaart) {
        String name = "'" + kaart.getMinionname() + "'";
        int att = kaart.getAttack();
        int life = kaart.getLife();
        int cost = kaart.getCost();
        String effect = "'" + kaart.getEffect() + "'";
        String flavor = "'" + kaart.getFlavor() + "'";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:kaardid.db");
             Statement statement = connection.createStatement()) {
            Class.forName("org.sqlite.JDBC");
            connection.setAutoCommit(false);

            String sql = "INSERT INTO KAART(NAME,ATT,HP,COST,EFFECT,FLAVOR) VALUES("
                    + name + ", "
                    + att + ", "
                    + life + ", "
                    + cost + ", "
                    + effect + ","
                    + flavor + ");";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Kaart readKaart(String name) {
        Kaart kaart = null;
        Connection connection;
        Statement statement;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:kaardid.db");
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "SELECT * FROM KAART WHERE NAME='" + name + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            kaart = new Kaart(resultSet.getString("NAME"), resultSet.getInt("ATT"), resultSet.getInt("HP"), resultSet.getInt("COST"), resultSet.getString("EFFECT"), resultSet.getString("FLAVOR"));
            statement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return kaart;
    }
}
