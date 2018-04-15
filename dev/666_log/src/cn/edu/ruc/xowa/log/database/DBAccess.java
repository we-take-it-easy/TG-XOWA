package cn.edu.ruc.xowa.log.database;

import cn.edu.ruc.xowa.log.graph.GraphNode;
import cn.edu.ruc.xowa.log.graph.SerializableGraphNode;

import java.sql.*;
import java.util.*;

public class DBAccess
{
    private static DBAccess instance = null;
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private Statement statement = null;

    public DBAccess() {
        try {
            Class.forName(DBConfig.DRIVER);
            conn = DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
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

    public Connection getConnection ()
    {
        return this.conn;
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

    public int login (String userName) throws SQLException
    {
        pstmt = conn.prepareStatement("SELECT id FROM xowa_log.sys_user WHERE user_name=?");
        pstmt.setString(1, userName);
        rs = pstmt.executeQuery();
        int userId = -1;
        if (rs.next())
        {
            userId = rs.getInt(1);
        }
        pstmt.close();
        return userId;
    }

    public int register (String userName) throws SQLException
    {
        pstmt = conn.prepareStatement("INSERT INTO xowa_log.sys_user (user_name) VALUES(?)");
        pstmt.setString(1, userName);
        pstmt.execute();
        pstmt.close();
        return login(userName);
    }

    public void insertSessionAllnodes(String sessionId, String userName, Map<String, GraphNode> allNodes, double gini) throws SQLException
    {
        Map<String, SerializableGraphNode> serializableAllNodes = new HashMap<>();

        for (Map.Entry<String, GraphNode> entry : allNodes.entrySet())
        {
            serializableAllNodes.put(entry.getKey(), new SerializableGraphNode(entry.getValue()));
        }

        pstmt = conn.prepareStatement("INSERT INTO xowa_log.navigation_path(session_id, user_name, path, gini)VALUES (?,?,?,?)");
        pstmt.setString(1,sessionId);
        pstmt.setString(2, userName);
        Sql.setSerializedObject(pstmt, 3, serializableAllNodes);
        pstmt.setDouble(4, gini);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void insertQuesForUser(String userName,String entityName, String question, String answer) throws SQLException
    {
        pstmt = conn.prepareStatement("INSERT INTO xowa_log.explo_ques_ans(user_name, entity_name, question, answer)VALUES (?,?,?,?)");
        pstmt.setString(1,userName);
        pstmt.setString(2,entityName);
        Sql.setSerializedObject(pstmt, 3, question);
        Sql.setSerializedObject(pstmt, 4, answer);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void insertStepQuestion(String sessionId, String curQus, String description, int certainty, String changed) throws SQLException
    {
        pstmt = conn.prepareStatement("INSERT INTO xowa_log.step_question(session_id, curQus, description, certainty, changed)VALUES (?,?,?,?,?)");
        pstmt.setString(1, sessionId);
        pstmt.setString(2, curQus);
        pstmt.setString(3, description);
        pstmt.setInt(4, certainty);
        pstmt.setString(5, changed);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void insertSessionQuestion(String sessionId, String plan, String specific, String question) throws SQLException
    {
        pstmt = conn.prepareStatement("INSERT INTO xowa_log.session_question(session_id, whether_directed, specific_qus, info_need)VALUES (?,?,?,?)");
        pstmt.setString(1, sessionId);
        pstmt.setString(2, plan);
        pstmt.setString(3, specific);
        pstmt.setString(4, question);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void insertQuesForUser2(int userId, String entityName, String question, String answer) throws SQLException
    {
        pstmt = conn.prepareStatement("INSERT INTO xowa_log.explo_task(sys_user_id, entity_name, question, answer)VALUES (?,?,?,?)");
        pstmt.setInt(1, userId);
        pstmt.setString(2, entityName);
        pstmt.setString(3, question);
        pstmt.setString(4, answer);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void insertDeletedForPages(String entityName, String flag, String deletedSentence) throws SQLException
    {
        pstmt = conn.prepareStatement("INSERT INTO xowa_log.deleted_sentence(entity_name, flag_sentence, deleted_sentence)VALUES (?,?,?)");
        pstmt.setString(1,entityName);
        Sql.setSerializedObject(pstmt,2,flag);
        Sql.setSerializedObject(pstmt, 3, deletedSentence);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void insertDeletedForPages2(int userId, String entityName, String flag, String deletedSentence) throws SQLException
    {
        pstmt = conn.prepareStatement("SELECT max(id) FROM xowa_log.explo_task WHERE sys_user_id=? AND entity_name=?");
        pstmt.setInt(1, userId);
        pstmt.setString(2, entityName);
        rs = pstmt.executeQuery();
        int taskId = -1;
        if (rs.next())
        {
            taskId = rs.getInt(1);
        }
        if (taskId < 0)
        {
            throw new SQLException("task id not found in explo_ques_ans: " + taskId);
        }
        pstmt.close();
        pstmt = conn.prepareStatement("INSERT INTO xowa_log.deleted_sentence2(explo_task_id, flag_sentence, deleted_sentence)VALUES (?,?,?)");
        pstmt.setInt(1,taskId);
        pstmt.setString(2,flag);
        pstmt.setString(3, deletedSentence);
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
    public List<List<String>> getSentencesForEntity(String entityName) throws SQLException
    {
        List<List<String>> sentenceList = new ArrayList<>();
        List<String> sentences = new ArrayList<>();
        pstmt = conn.prepareStatement("SELECT * FROM xowa_log.deleted_sentence WHERE entity_name = ?");
        pstmt.setString(1, entityName);
        rs = pstmt.executeQuery();
        pstmt.close();
        return sentenceList;
    }

    public List<String> getTasksForUserName(String userName) throws SQLException
    {
        List<String> task = new ArrayList<>();
        pstmt = conn.prepareStatement("SELECT * FROM xowa_log.explo_ques_ans WHERE user_name = ?");
        pstmt.setString(1, userName);
        rs = pstmt.executeQuery();
        while(rs.next())
        {
            task.add(rs.getString("question"));
        }
        return task;
    }

    public List<String> getRandomTask()
    {
        List<String> task = new ArrayList<>();
        try
        {
            pstmt = conn.prepareStatement("SELECT question FROM xowa_log.explo_task ORDER BY RAND() LIMIT 1");
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                task.add(rs.getString("question"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return task;
    }
    /*
    public static void main(String[] args)
    {
        DBAccess db = new DBAccess();
        List<String> task = new ArrayList<>();
        try
        {
            task = db.getTasksForUserName("baobao0");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("tasks: "+task);
    }*/

    /*
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
    }*/
}