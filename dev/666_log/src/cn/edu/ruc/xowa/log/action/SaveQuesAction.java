package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;

import java.util.List;

public class SaveQuesAction implements Action
{
    private int userId;
    private String entityName;
    private String question;
    private String answer;
    public SaveQuesAction(int userId,String entityName, String question, String answer)
    {
        this.userId = userId;
        this.entityName = entityName;
        this.question = question;
        this.answer = answer;
    }

    @Override
    public void perform()
    {
        GraphBuilder.getInstance().saveQuestions(userId,entityName, question, answer);
    }

    @Override
    public List<String> get()
    {
        return null;
    }
}
