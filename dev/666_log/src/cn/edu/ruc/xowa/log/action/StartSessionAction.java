package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;

import java.util.List;

/**
 * Created by hank on 18-1-19.
 */
public class StartSessionAction implements Action
{
    private String sessionId;

    public StartSessionAction (String sessionId)
    {
        this.sessionId = sessionId;
    }

    @Override
    public void perform()
    {
        GraphBuilder.getInstance().start(sessionId);
    }

    @Override
    public List<String> get() { return null; }
}
