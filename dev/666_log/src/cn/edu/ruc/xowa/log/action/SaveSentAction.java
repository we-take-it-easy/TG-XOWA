package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;

import java.util.List;

public class SaveSentAction implements Action
{
    private String entityName;
    private String flag;
    private String deletedSentence;
    public SaveSentAction(String entityName, String flag, String sentence)
    {
        this.entityName = entityName;
        this.flag = flag;
        this.deletedSentence = sentence;
    }

    @Override
    public void perform()
    {
        GraphBuilder.getInstance().saveSentences(entityName, flag, deletedSentence);
    }

    @Override
    public List<String> get()
    {
        return null;
    }
}
