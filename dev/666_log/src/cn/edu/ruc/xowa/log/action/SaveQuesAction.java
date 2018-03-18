package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;

import java.util.List;

public class SaveQuesAction implements Action
{
    private String userName;
    private String entityName;
    private String question;
    private String answer;
    public SaveQuesAction(String userName,String entityName, String question, String answer)
    {
        this.userName = userName;
        this.entityName = entityName;
        this.question = question;
        this.answer = answer;
    }

    @Override
    public void perform()
    {
        GraphBuilder.getInstance().saveQuestions(userName,entityName, question, answer);
    }

    @Override
    public List<String> get()
    {
        return null;
    }
}
