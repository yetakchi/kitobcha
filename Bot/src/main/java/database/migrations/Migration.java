package database.migrations;


public class Migration {

    public static void main(String[] args) {
        new UserMigration().up();
        new SessionMigration().up();
    }
}
