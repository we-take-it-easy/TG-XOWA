package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;

import java.util.List;

/**
 * Created by hank on 18-1-19.
 */
public class GiveupSessionAction implements Action
{
    @Override
    public void perform()
    {
        GraphBuilder.getInstance().clear();
    }

    @Override
    public List<String> get() { return null; }
}
