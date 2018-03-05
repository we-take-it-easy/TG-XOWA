package cn.edu.ruc.xowa.log.ui;

import cn.edu.ruc.xowa.log.graph.GraphNode;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

public class DataVisualizing
{
    private Canvas canvas;

    public DataVisualizing(Canvas canvas)
    {
        this.canvas = canvas;
    }

    public void drawNodes(String node)
    {
        GC gc = new GC(canvas);
        int r = 2;
        String label = node;
        gc.drawOval(65, 10, 30,30);
        gc.drawText(node, 65, 40);
    }

    public void drawPaths(GraphNode graphNode)
    {
    }
}
