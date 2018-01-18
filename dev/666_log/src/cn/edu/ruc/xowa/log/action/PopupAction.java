package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.page.Page;

/**
 * Created by hank on 18-1-19.
 */
public class PopupAction implements Action
{
    private Page page = null;

    public PopupAction (Page page)
    {
        this.page = page;
    }

    @Override
    public void perform()
    {
        System.out.println("popup page:");
        System.out.println("url:  " + page.getUrl());
        System.out.println("prev: " + page.getPrevious());
        System.out.println("root: " + page.getRoot());
        System.out.println("#link:" + page.getLinks().size());
    }
}
