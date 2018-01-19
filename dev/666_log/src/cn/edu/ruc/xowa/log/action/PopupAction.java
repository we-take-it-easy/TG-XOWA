package cn.edu.ruc.xowa.log.action;

import cn.edu.ruc.xowa.log.graph.GraphBuilder;
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
        GraphBuilder.getInstance().popupTo(page);
    }
}
