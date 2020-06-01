package core;

import connection.DBConnection;
import connection.MySqlConn;
import connection.PostgresConn;
import util.ConfigParams;
import util.ConfigReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager {
    private DBConnection conn;
    private List<ConfigParams> configParams;
    private Map<String, DBConnection> connectionList;
    private static ConnectionManager connectionManager;
    static {
        connectionManager = new ConnectionManager();
    }

    public Map<String, DBConnection> getConnectionList() {
        return connectionList;
    }

    private void createCon(DBConnection c, ConfigParams cfg) {
        conn = c;
        connectionList.put(cfg.getAlias(), conn);
    }

    public static ConnectionManager getManager() {
        return connectionManager;
    }


    private ConnectionManager() {
        connectionList = new HashMap<>();
        configParams = ConfigReader.getConfigList();
        for (ConfigParams cfg : configParams) {
            switch (cfg.getVendor()) {
                case "mysql" :
                    createCon(new MySqlConn(cfg), cfg);
                    break;
                case "postgres" :
                    createCon(new PostgresConn(cfg), cfg);
                    break;
            }
        }
    }


}
