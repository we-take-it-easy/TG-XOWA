package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;

import java.util.List;

/**
 * Created by hank on 18-1-19.
 */
public class LoginAction implements Action
{
    private String userName;

    public LoginAction (String userName)
    {
        this.userName = userName;
    }

    @Override
    public void perform()
    {
        GraphBuilder.getInstance().setUserName(userName);
    }

    @Override
    public List<String> get() { return null; }
}
