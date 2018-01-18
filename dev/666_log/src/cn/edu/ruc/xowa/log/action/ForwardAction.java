package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.page.Page;

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
        System.out.println("forward:");
        System.out.println("from page " + fromPage.getUrl());
        System.out.println("go forward to page " + toPage.getUrl());
    }
}
