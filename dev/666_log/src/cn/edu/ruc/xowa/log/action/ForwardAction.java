package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;
import cn.edu.ruc.xowa.log.page.Page;

import java.util.List;

/**
 * Created by hank on 18-1-19.
 */
public class ForwardAction implements Action
{
    private Page fromPage = null;
    private Page toPage = null;

    public ForwardAction (Page from, Page to)
    {
        this.fromPage = from;
        this.toPage = to;
    }

    @Override
    public void perform()
    {
        GraphBuilder.getInstance().goForward(fromPage, toPage);
    }

    @Override
    public List<String> get() { return null; }
}
