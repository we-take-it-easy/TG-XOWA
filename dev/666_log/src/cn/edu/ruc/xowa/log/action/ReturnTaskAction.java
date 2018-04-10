package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;

import java.util.ArrayList;
import java.util.List;

public class ReturnTaskAction implements Action
{
    @Override
    public void perform()
    {

    }

    @Override
    public List<String> get()
    {
        List<String> task = new ArrayList<>();
        task = GraphBuilder.getInstance().GetTask();
        return task;
    }
}
