package cn.edu.ruc.xowa.log.action;

import org.eclipse.swt.widgets.Canvas;

import java.util.List;

public class DrawpathAction implements Action
{
    private String sessionId;
    private Canvas canvas;

    public DrawpathAction(String sessionId, Canvas canvas)
    {
        this.sessionId = sessionId;
        this.canvas = canvas;
    }

    @Override
    public void perform()
    {
        //GraphBuilder.getInstance().drawPath(sessionId, canvas);
    }

    @Override
    public List<String> get() { return null; }
}
