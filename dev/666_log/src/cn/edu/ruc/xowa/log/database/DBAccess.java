package cn.edu.ruc.xowa.log.database;

import cn.edu.ruc.xowa.log.graph.GraphNode;
import cn.edu.ruc.xowa.log.graph.SerializableGraphNode;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
            PASSWORD = "111111";

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
        Map<String, SerializableGraphNode> serializableAllNodes = new HashMap<>();

        for (Map.Entry<String, GraphNode> entry : allNodes.entrySet())
        {
            serializableAllNodes.put(entry.getKey(), new SerializableGraphNode(entry.getValue()));
        }

        pstmt = conn.prepareStatement("INSERT INTO xowa_log.navigation_path(session_id, path)VALUES (?,?)");
            pstmt.setString(1,sessionId);
            Sql.setSerializedObject(pstmt, 2, serializableAllNodes);
            pstmt.executeUpdate();
            pstmt.close();
    }

    public Map<String, GraphNode> getSessionAllnodes(String sessionId) throws SQLException
    {
         Map<String, GraphNode> allNodes = new HashMap<>();
         pstmt = conn.prepareStatement("SELECT * FROM xowa_log.navigation_path WHERE session_id = ?");
         pstmt.setString(1, sessionId);
         rs = pstmt.executeQuery();
         //关闭pstmt会把rs也关闭了，所以应该在读完rs之后再关闭pstmt
         //pstmt.close();
         if (rs.next())
         {
             Object object = Sql.getSerializedObject(rs, "path");
             Map<String, SerializableGraphNode> serializableAllNodes =  (Map<String, SerializableGraphNode>) object;
             for (Map.Entry<String, SerializableGraphNode> entry : serializableAllNodes.entrySet())
             {
                 GraphNode graphNode = new GraphNode(entry.getValue().getName(), new HashSet<>(entry.getValue().getLinks()));
                 allNodes.put(entry.getKey(), graphNode);
             }

             for (Map.Entry<String, SerializableGraphNode> entry : serializableAllNodes.entrySet())
             {
                 List<String> parentNames = entry.getValue().getParentNames();
                 List<String> childNames = entry.getValue().getChildNames();
                 GraphNode node = allNodes.get(entry.getKey());
                 for (String parentName : parentNames)
                 {
                     GraphNode parent = allNodes.get(parentName);
                     node.addParents(parent);
                 }

                 for (String childName : childNames)
                 {
                     GraphNode child = allNodes.get(childName);
                     node.addChildren(child);
                 }
             }
         }
         pstmt.close();
         return allNodes;
    }

    public static void main(String[] args)
    {
        // 简单测试一下反序列化
        DBAccess dbAccess = new DBAccess();
        try
        {
            // getSessionAllnodes里面转入的sessionId是从数据库里面查出来的
            Map<String, GraphNode> allNodes = dbAccess.getSessionAllnodes("6cc52666-7eb8-45d8-ae83-48db2a38695e");
            for (Map.Entry<String, GraphNode> entry : allNodes.entrySet())
            {
                System.out.println(entry.getKey() + ", #parents:" + entry.getValue().getParents().size());
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
