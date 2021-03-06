package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;
import org.htmlparser.util.ParserException;

import java.io.IOException;
import java.util.List;

public class ReturnEntitiesAction implements Action
{
    private String keyword;

    public ReturnEntitiesAction(String keyword){this.keyword = keyword; }

    @Override
    public void perform()
    {

    }

    public List<String> get()
    {
        List<String> entities = null;
        try
        {
            entities = GraphBuilder.getInstance().GetEntityNames(keyword);
        } catch (IOException | ParserException e)
        {
            e.printStackTrace();
        }
        return entities;
    }
}
