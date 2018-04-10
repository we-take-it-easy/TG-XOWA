package cn.edu.ruc.xowa.log.action;

import java.util.List;

/**
 * Created by hank on 18-1-19.
 */
public interface Action
{
    void perform ();
    List<String> get();
    //Boolean judge();
}
