package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;

import java.util.List;

/**
 * Created by hank on 18-1-19.
 */
public class EndSessionAction implements Action
{
    @Override
    public void perform()
    {
        GraphBuilder.getInstance().end();
    }

    @Override
    public List<String> get()
    {
        return null;
    }
}
