package cn.edu.ruc.xowa.log.test;

public class TestDB
{
    public static void main(String[] args)
    {
        //GraphBuilder.getInstance().start("123");
        //GraphBuilder.getInstance().saveStepQuestion("test", 22);
    }
    /*
    public static void main(String[] args) throws SQLException
    {
        DBAccess dbAccess = DBAccess.Instance();
        //int res = dbAccess.login("bhq");
        ///System.out.println(res);
        //dbAccess.insertQuesForUser2(res, "test", "test", "test");
        //dbAccess.insertDeletedForPages(res, "test", "flag", "deleted");
        Connection conn = dbAccess.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM xowa_log.explo_ques_ans");
        Random rand = new Random(System.nanoTime());
        PreparedStatement pstmt = conn.prepareStatement("insert into xowa_log.explo_task(entity_name,question,answer,sys_user_id)" +
                "values (?,?,?,?)");
        while(rs.next())
        {
            int uid = 1 + (rand.nextInt(6));
            String entityName = rs.getString("entity_name");
            String question = (String)Sql.getSerializedObject(rs, "question");
            String answer = (String)Sql.getSerializedObject(rs, "answer");
            //System.out.println(uid);
            //System.out.println(entityName);
            //System.out.println(answer);
            //System.out.println(question);

            pstmt.setString(1, entityName);
            pstmt.setString(2, question);
            pstmt.setString(3, answer);
            pstmt.setInt(4, uid);
            pstmt.addBatch();

        }
        pstmt.executeBatch();
        pstmt.close();
        stmt.close();

        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM xowa_log.deleted_sentence");
        pstmt = conn.prepareStatement("insert into xowa_log.deleted_sentence2(explo_task_id,flag_sentence,deleted_sentence)" +
                "values (?,?,?)");
        while(rs.next())
        {
            String entityName = rs.getString("entity_name");
            String flag = (String)Sql.getSerializedObject(rs, "flag_sentence");
            String deleted = (String)Sql.getSerializedObject(rs, "deleted_sentence");
            //System.out.println(entityName);
            //System.out.println(flag);
            //System.out.println(deleted);
            Statement st = conn.createStatement();
            ResultSet rs2 = st.executeQuery("select id from xowa_log.explo_task where entity_name='" + entityName + "'");
            if (rs2.next())
            {
                int tid = rs2.getInt(1);

                pstmt.setInt(1, tid);
                pstmt.setString(2, flag);
                pstmt.setString(3, deleted);
                pstmt.addBatch();

            }
            st.close();


        }
        pstmt.executeBatch();
        pstmt.close();

        conn.close();
    }
    */
}
