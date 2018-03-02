package cn.edu.ruc.xowa.log.database;

import cn.edu.ruc.xowa.log.graph.GraphNode;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBAccess
{
    private static DBAccess instance = null;

    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private Statement statement = null;

    public DBAccess() {
        try {
            DRIVER = "com.mysql.jdbc.Driver";
            URL = "jdbc:mysql://localhost:3306/xowa_log";
            USER = "root";
            PASSWORD = "xly19710908";

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("successfully connected");
        } catch (Exception e) {
            System.err.println("Connection error! errmsg: " + e.getMessage());
        }
    }

    public static DBAccess Instance() {
        if (instance == null) {
            instance = new DBAccess();
        }
        return instance;
    }

    public void close() {
        try {
            if (rs != null)
                rs.close();
            if (statement != null)
                statement.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            System.err.println("Close error! errmsg: " + e.getMessage());
        }
    }

    public void insertSessionAllnodes(String sessionId, Map<String, GraphNode> allNodes) throws SQLException
    {
            pstmt = conn.prepareStatement("INSERT INTO xowa_log.navigation_path(session_id, path)VALUES (?,?)");
            pstmt.setString(1,sessionId);
            Sql.setSerializedObject(pstmt, 2, allNodes);
            pstmt.executeUpdate();
            pstmt.close();
    }

    public Map<String, GraphNode> getSessionAllnodes(String sessionId) throws SQLException
    {
         Map<String, GraphNode> allNodes = new HashMap<>();
         pstmt = conn.prepareStatement("SELECT * FROM xowa_log.navigation_path WHERE session_id = ?");
         pstmt.setString(1, sessionId);
         rs = pstmt.executeQuery();
         pstmt.close();
         while (rs.next())
         {
             Object object = Sql.getSerializedObject(rs, "path");
             if (object == null)
                 break;
             allNodes = (Map<String, GraphNode>) object;
         }
         return allNodes;
    }

    /*public static void main(String[] args)
    {
        DBAccess dbAccess = new DBAccess();
        dbAccess.close();
    }*/
}
