package Server;

import Server.DTO.Album;
import Server.DTO.Utwor;
import com.example.web_app.Index;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Server {

    Connection connection = null;
    boolean created = false;
    public Server(){
        try {
            connection = DriverManager.getConnection("jdbc:derby:MusicDataBase;create=true");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(5);
        }
    }

    public int CloseServer(){
        try {
            connection.close();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    public void generateTables(){
        // section to drop if exists
        if (created){
            return;
        }
        try {
            //TODO: add names to this so you can check from then in execution
            String [] SQLCommands = {
                    "ALTER TABLE Utwor DROP CONSTRAINT Utwor_Gatunek",
                    "ALTER TABLE Utwor DROP CONSTRAINT Utwor_Album",
                    "ALTER TABLE Utwor_wykonawca DROP CONSTRAINT Utwor_wykonawca_Utwor",
                    "ALTER TABLE Utwor_wykonawca DROP CONSTRAINT Utwor_wykonawca_Wykonawca",
                    "ALTER TABLE Utwory_Instrumenty DROP CONSTRAINT Utwory_Instrumenty_Instrumenty",
                    "ALTER TABLE Utwory_Instrumenty DROP CONSTRAINT Utwory_Instrumenty_Utwor",
                    "DROP TABLE Album",
                    "DROP TABLE Gatunek",
                    "DROP TABLE Instrumenty",
                    "DROP TABLE Utwor",
                    "DROP TABLE Utwor_wykonawca",
                    "DROP TABLE Utwory_Instrumenty",
                    "DROP TABLE Wykonawca",
            };

            Statement statement = connection.createStatement();
            for (String command : SQLCommands
                 ) {
                try{
                    System.out.println("command is " + command);
                    statement.executeUpdate(command);
                    System.out.println("finished command correctly");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }

        // section to create them
        try {
            List<String> SQLCommands = new ArrayList<>();
            String createAlbum  = "CREATE TABLE Album (\n" +
                    "    IdAlbum int  NOT NULL,\n" +
                    "    Nazwa varchar(50)  NOT NULL,\n" +
                    "    Rok int  NOT NULL,\n" +
                    "    CONSTRAINT Album_pk PRIMARY KEY  (IdAlbum)\n" +
                    ")";
            SQLCommands.add(createAlbum);
            String createGatunek = "CREATE TABLE Gatunek (\n" +
                    "    IdGatunek int  NOT NULL,\n" +
                    "    Nazwa varchar(50)  NOT NULL,\n" +
                    "    CONSTRAINT Gatunek_pk PRIMARY KEY  (IdGatunek)\n" +
                    ")";
            SQLCommands.add(createGatunek);
            String createInstrumenty = "CREATE TABLE Instrumenty (\n" +
                    "    IdInstrument int  NOT NULL,\n" +
                    "    Nazwa varchar(20)  NOT NULL,\n" +
                    "    CONSTRAINT Instrumenty_pk PRIMARY KEY  (IdInstrument)\n" +
                    ")";
            SQLCommands.add(createInstrumenty);
            String createUtwor = "CREATE TABLE Utwor (\n" +
                    "    IdUtwor int  NOT NULL,\n" +
                    "    Nazwa varchar(50)  NOT NULL,\n" +
                    "    czas_secs int  NOT NULL,\n" +
                    "    Tekst varchar(100)  NOT NULL,\n" +
                    "    Album_IdAlbum int  NOT NULL,\n" +
                    "    Gatunek_IdGatunek int  NOT NULL,\n" +
                    "    CONSTRAINT Utwor_pk PRIMARY KEY  (IdUtwor)\n" +
                    ")";
            SQLCommands.add(createUtwor);
            String createUtwor_wykonawca = "CREATE TABLE Utwor_wykonawca (\n" +
                    "    Utwor_IdUtwor int  NOT NULL,\n" +
                    "    Wykonawca_IdWykonawca int  NOT NULL,\n" +
                    "    CONSTRAINT Utwor_wykonawca_pk PRIMARY KEY  (Utwor_IdUtwor,Wykonawca_IdWykonawca)\n" +
                    ")";
            SQLCommands.add(createUtwor_wykonawca);
            String createUtwor_Instrumenty = "CREATE TABLE Utwory_Instrumenty (\n" +
                    "    Utwor_IdUtwor int  NOT NULL,\n" +
                    "    Instrumenty_IdInstrument int  NOT NULL,\n" +
                    "    CONSTRAINT Utwory_Instrumenty_pk PRIMARY KEY  (Utwor_IdUtwor,Instrumenty_IdInstrument)\n" +
                    ")";
            SQLCommands.add(createUtwor_Instrumenty);
            String createWykonawca = "CREATE TABLE Wykonawca (\n" +
                    "    IdWykonawca int  NOT NULL,\n" +
                    "    Nazwa varchar(50)  NOT NULL,\n" +
                    "    CONSTRAINT Wykonawca_pk PRIMARY KEY  (IdWykonawca)\n" +
                    ")";
            SQLCommands.add(createWykonawca);
            String alterUtwor = "ALTER TABLE Utwor ADD CONSTRAINT Utwor_Album\n" +
                    "    FOREIGN KEY (Album_IdAlbum)\n" +
                    "    REFERENCES Album (IdAlbum)";
            SQLCommands.add(alterUtwor);
            String alterGatunek = "ALTER TABLE Utwor ADD CONSTRAINT Utwor_Gatunek\n" +
                    "    FOREIGN KEY (Gatunek_IdGatunek)\n" +
                    "    REFERENCES Gatunek (IdGatunek)";
            SQLCommands.add(alterGatunek);
            String alterUtwor_wykonawca = "ALTER TABLE Utwor_wykonawca ADD CONSTRAINT Utwor_wykonawca_Utwor\n" +
                    "    FOREIGN KEY (Utwor_IdUtwor)\n" +
                    "    REFERENCES Utwor (IdUtwor)";
            SQLCommands.add(alterUtwor_wykonawca);
            String alterUtwor_wykonawca_wykonawca = "ALTER TABLE Utwor_wykonawca ADD CONSTRAINT Utwor_wykonawca_Wykonawca\n" +
                    "    FOREIGN KEY (Wykonawca_IdWykonawca)\n" +
                    "    REFERENCES Wykonawca (IdWykonawca)";
            SQLCommands.add(alterUtwor_wykonawca_wykonawca);
            String alterUtwory_instrumenty_instrumenty = "ALTER TABLE Utwory_Instrumenty ADD CONSTRAINT Utwory_Instrumenty_Instrumenty\n" +
                    "    FOREIGN KEY (Instrumenty_IdInstrument)\n" +
                    "    REFERENCES Instrumenty (IdInstrument)";
            SQLCommands.add(alterUtwory_instrumenty_instrumenty);
            String alterUtwory_instrumenty = "ALTER TABLE Utwory_Instrumenty ADD CONSTRAINT Utwory_Instrumenty_Utwor\n" +
                    "    FOREIGN KEY (Utwor_IdUtwor)\n" +
                    "    REFERENCES Utwor (IdUtwor)";
            SQLCommands.add(alterUtwory_instrumenty);


            Statement statement = connection.createStatement();
            for (String command : SQLCommands
                 ) {
                try {
                    System.out.println("Executing command : " + command);
                    statement.executeUpdate(command);
                    System.out.println("Finished correctly");
                }catch (Exception e){
                    System.out.println("Error with command");
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // section to seed
    }

    boolean tableExists(String tableName){
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null,null,tableName,new String[]{"Table"});
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }

    }
    public void seed2() throws SQLException {
        if (created){
            return;
        }
        System.out.println("Started seeding");
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO Album (IdAlbum, Nazwa, ROK) VALUES (1, 'Social clues', 2019)");
        statement.executeUpdate("INSERT INTO Album (IdAlbum, Nazwa, ROK) VALUES (2, 'Jar of flies', 1994)");

        // Seed data for Gatunek table
        statement.executeUpdate("INSERT INTO Gatunek (IdGatunek, Nazwa) VALUES (1, 'Pop')");
        statement.executeUpdate("INSERT INTO Gatunek (IdGatunek, Nazwa) VALUES (2, 'Rock')");
        statement.executeUpdate("INSERT INTO Gatunek (IdGatunek, Nazwa) VALUES (3, 'Hip Hop')");
        statement.executeUpdate("INSERT INTO Gatunek (IdGatunek, Nazwa) VALUES (4, 'Country')");

        // Seed data for Instrumenty table
        statement.executeUpdate("INSERT INTO Wykonawca (IdWykonawca, Nazwa) VALUES (1, 'Cage the Elephant')");
        statement.executeUpdate("INSERT INTO Wykonawca (IdWykonawca, Nazwa) VALUES (2, 'Alice in Chains')");



        statement.executeUpdate("INSERT INTO Instrumenty (IdInstrument, Nazwa) VALUES (1, 'Bass')");
        statement.executeUpdate("INSERT INTO Instrumenty (IdInstrument, Nazwa) VALUES (2, 'Guitar')");
        statement.executeUpdate("INSERT INTO Instrumenty (IdInstrument, Nazwa) VALUES (3, 'Drums')");


        // Seed data for Utwor table
        statement.executeUpdate("INSERT INTO Utwor (IdUtwor,Nazwa, czas_secs, Tekst, Album_IdAlbum, Gatunek_IdGatunek) " +
                "VALUES (1, 'It dawned on me', 193, 'And there is no one else but me', 1, 2)");
        statement.executeUpdate("INSERT INTO Utwor (IdUtwor, Nazwa, czas_secs, Tekst, Album_IdAlbum, Gatunek_IdGatunek) " +
                "VALUES (2, 'Black Madonna', 226, 'Soft glow on the city', 2, 2)");

        // Seed data for Utwor_wykonawca table
        statement.executeUpdate("INSERT INTO Utwor_wykonawca (Utwor_IdUtwor, Wykonawca_IdWykonawca) VALUES (1, 1)");
        statement.executeUpdate("INSERT INTO Utwor_wykonawca (Utwor_IdUtwor, Wykonawca_IdWykonawca) VALUES (2, 2)");

        // Seed data for Utwory_Instrumenty table
        statement.executeUpdate("INSERT INTO Utwory_Instrumenty (Utwor_IdUtwor, Instrumenty_IdInstrument) VALUES (1, 1)");
        statement.executeUpdate("INSERT INTO Utwory_Instrumenty (Utwor_IdUtwor, Instrumenty_IdInstrument) VALUES (1, 2)");
        statement.executeUpdate("INSERT INTO Utwory_Instrumenty (Utwor_IdUtwor, Instrumenty_IdInstrument) VALUES (1, 3)");
        statement.executeUpdate("INSERT INTO Utwory_Instrumenty (Utwor_IdUtwor, Instrumenty_IdInstrument) VALUES (2, 1)");
        statement.executeUpdate("INSERT INTO Utwory_Instrumenty (Utwor_IdUtwor, Instrumenty_IdInstrument) VALUES (2, 2)");
        statement.executeUpdate("INSERT INTO Utwory_Instrumenty (Utwor_IdUtwor, Instrumenty_IdInstrument) VALUES (2, 3)");
        System.out.println("Finished seeding");


        String query = "SELECT * FROM Wykonawca";
        List<Integer> bandList = new ArrayList<>();
        try (PreparedStatement statement2 = connection.prepareStatement(query)) {
            //statement.setString(1,  bandName );
            ResultSet resultSet = statement2.executeQuery();
            System.out.println("result set length is " + resultSet.getFetchSize());
            while (resultSet.next()) {
                System.out.print("result = ");
                System.out.println(resultSet.getInt("IdWykonawca") + " " + resultSet.getString("Nazwa"));
                int id = resultSet.getInt("IdWykonawca");
                String ID = resultSet.getString("IdWykonawca");
                bandList.add(Integer.parseInt(ID));
            }

            // Use the bandList as per your requirement
        } catch (SQLException e) {

            e.printStackTrace();
        }



        created = true;
    }

    public void seed() throws SQLException {
        //I will not check if table is null, but this should be implemented
        String[] instruments = {"Piano","Guitar","Violin","Drums","Flute",
                "Saxophone","Trumpet","Cello","Electric Bass","Clarinet",
                "Acoustic Guitar","Trombone","Ukulele","Double Bass","Electric Guitar",
                "Keyboard","Harp","French Horn","Viola","Accordion"};

        String[] Generes = {"Pop","Rock","Hip Hop","Country","Jazz","Electronic","R&B","Classical","Grunge","Metal"};
        String [] Band = {"Placebo","Cage the elephant","Alice in chains","Rick Astley","Calla"};
        Album [] Albums = {
                new Album(1,"Meds",2006),
                new Album(2,"Social clues",2019),
                new Album(3,"Jar of flies",1994),
                new Album(4,"Dirt",1992),
                new Album(5,"Collisions",2005),
                new Album(6,"Televise",2003),
        };

        Utwor[] Songs = {
                new Utwor("It dawned on me",5,1,193,"And there is no one else but me "),
                new Utwor("This better go as planned",5,238,2005,"Did you mean it"),
                new Utwor("No excuses",3,8,255,"Everyday it's something"),
                new Utwor("Rotten apple",3,8,418,"Arrogance is potent hey"),
                new Utwor("Social cues",2,0,219,"Choose your favorite vice"),
                new Utwor("Black Madonna",2,0,226,"Soft glow on the city")

        };
        Statement statement = connection.createStatement();

//        for (int i = 0; i <instruments.length; i++) {
//            try{
//                statement.executeUpdate("INSERT INTO INSTRUMENTY VALUES("+i+",'"+instruments[i]+"')");
//
//            }catch (Exception e){
//                // Cant update because this value is already there
//            }
//
//        }




        // VERSION 2
//        String sql = "INSERT INTO Instrumenty VALUES (?, ?)";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        for (int i = 0; i < instruments.length; i++) {
//            // Set the values for the prepared statement
//            statement.setInt(1, i);  // Assuming i is the index
//            statement.setString(2, instruments[i]);  // Assuming instrumenty[i] is the instrument name
//
//            // Execute the update
//            statement.executeUpdate();
//        }
//        statement.close();
//        connection.close();

    }


    //TODO: create all getters and inserts
    public List<String> getInstruments(){
        ArrayList<String> instruments = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM INSTRUMENTY");
            while (resultSet.next()) {
                    instruments.add(resultSet.getString("Nazwa"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instruments;
    }

    public String[] getSongsByBand(String searchText) {
        getIdbyBand(searchText).forEach(System.out::println);
        return new String[]{"TEST"};
    }

    private List<Integer> getIdbyBand(String bandName){
        //String query = "SELECT IdWykonawca FROM Wykonawca WHERE Nazwa like ?";
        String query = "SELECT * FROM Wykonawca";
        List<Integer> bandList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            //statement.setString(1,  bandName );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.print("result = ");
                System.out.println(resultSet.toString());
                int id = resultSet.getInt("IdWykonawca");
                bandList.add(id);
            }

            // Use the bandList as per your requirement
        } catch (SQLException e) {

            e.printStackTrace();
        }
        System.out.println("====");
        bandList.forEach(System.out::println);
        System.out.println("====");
        return bandList;
    }
}
