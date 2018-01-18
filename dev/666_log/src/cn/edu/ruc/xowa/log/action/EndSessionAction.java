package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;

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
}
