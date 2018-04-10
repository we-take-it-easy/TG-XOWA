package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;
import cn.edu.ruc.xowa.log.page.Page;

import java.util.List;

/**
 * Created by hank on 18-1-19.
 */
public class LoadPageAction implements Action
{
    private Page page = null;
    //private Boolean flag;
    //public LoadPageAction(){this.flag = false;}
    public LoadPageAction (Page page) { this.page = page; }

    @Override
    public void perform()
    {
        System.out.println("page: "+page.getUrl());
        if (page.getUrl().getType().toString().equals("NORMAL_PAGE_URL"))
        {
            GraphBuilder.getInstance().goTo(page);
        }
        else if(page.getUrl().getType().toString().equals("SEARCH_LIST_URL"))
        {
            GraphBuilder.getInstance().searchTo(page);
        }
    }

    @Override
    public List<String> get() { return null; }

    //@Override
    //public Boolean judge()
    //{
      //  return this.flag;
    //}
}
