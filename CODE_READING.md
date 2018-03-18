页面上按钮的回调函数绑定在gplx.xowa.guis.bnds.Xog_bnd_mgr.java的Add_system_bnds方法中完成。

gplx.xowa.guis.views.Xog_win_itm_中的Show_win方法为打开xowa主窗体时调用的方法。

gplx.xowa.htmls.modules.popups.Xow_popup_mgr中的Show_init方法在打开一个新的（如果一个popup页面已经被打开过，则不会再调用此方法）
popup页面时调用，可以得到一个popup页面的html内容、URL以及所在tab page的信息，还可以拿到popup history、从而拿到上一个popup页面，
同一个tab页面上的所有popup页面会存储在一个list当中。但是由于xowa中似乎并没有存储树型的popup页面结构，所以还是需要通过解析popup页面的html、配合popup history来构建树结构。

gplx.xowa.guis.views.Load_page_wkr的构造方法在一个页面在tab中打开时被调用，可以拿到一个page的url等信息。

gplx.xowa.guis.views.Xog_html_itm的Show方法可以拿到一个tab中page的html和url.

gplx.xowa.guis.history.Xog_history_stack和Xog_history_mgr中有backward对应的函数，可以得到backward页面的信息。

gplx.xowa.guis.views.Xog_tab_itm的History_mgr在每次load也一个页面的时候都会被调用，在其中可以通过stack成员来获得上一个页面的url（我在stack里面加了一个Prev_page方法），
但是这样依然无法判断一个新打开的tab中的第一个页面是从哪里打开的，所以还需要通过解析页面的html内容来构建这一关系。

创建数据库xowa_log, 表名‘navigation_path’:
CREATE SCHEMA `xowa_log` ;

CREATE TABLE `navigation_path` (
  `session_id` varchar(255) NOT NULL,
  `path` blob,
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1

创建表 `explo_ques_ans` :
CREATE TABLE `explo_ques_ans` (
  `user_name` varchar(45) NOT NULL,
  `entity_name` varchar(255) DEFAULT NULL,
  `question` blob,
  `answer` blob
) ENGINE=InnoDB DEFAULT CHARSET=latin1

创建表 ‘deleted_sentence’: 
CREATE TABLE `deleted_sentence` (
  `entity_name` varchar(45) NOT NULL,
  `flag_sentence` blob,
  `deleted_sentence` blob
) ENGINE=InnoDB DEFAULT CHARSET=latin1
