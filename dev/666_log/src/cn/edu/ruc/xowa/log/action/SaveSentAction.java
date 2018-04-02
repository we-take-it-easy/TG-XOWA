package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;

import java.util.List;

public class SaveSentAction implements Action
{
    private int userId;
    private String entityName;
    private String flag;
    private String deletedSentence;
    public SaveSentAction(int userId, String entityName, String flag, String sentence)
    {
        this.userId = userId;
        this.entityName = entityName;
        this.flag = flag;
        this.deletedSentence = sentence;
    }

    @Override
    public void perform()
    {
        GraphBuilder.getInstance().saveSentences(userId, entityName, flag, deletedSentence);
    }

    @Override
    public List<String> get()
    {
        return null;
    }
}
