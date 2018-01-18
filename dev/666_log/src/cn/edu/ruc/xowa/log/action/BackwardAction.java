package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;
import cn.edu.ruc.xowa.log.page.Page;

/**
 * Created by hank on 18-1-19.
 */
public class BackwardAction implements Action
{
    private Page fromPage = null;
    private Page toPage = null;

    public BackwardAction (Page from, Page to)
    {
        this.fromPage = from;
        this.toPage = to;
    }

    @Override
    public void perform()
    {
        GraphBuilder.getInstance().goBackward(fromPage, toPage);
    }
}
