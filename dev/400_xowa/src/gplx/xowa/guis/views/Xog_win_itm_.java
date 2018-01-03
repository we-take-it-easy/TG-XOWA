/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.guis.views;

import gplx.Keyval_;
import gplx.String_;
import gplx.gfui.controls.elems.GfuiElem;
import gplx.gfui.controls.standards.GfuiBtn;
import gplx.gfui.controls.standards.GfuiComboBox;
import gplx.gfui.controls.standards.GfuiTextBox;
import gplx.gfui.controls.standards.Gfui_grp;
import gplx.gfui.controls.windows.GfuiWin;
import gplx.gfui.draws.FontAdp;
import gplx.gfui.imgs.IconAdp;
import gplx.gfui.kits.core.Gfui_kit;
import gplx.xowa.Xoae_app;
import gplx.xowa.guis.langs.Xol_font_info;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;

public class Xog_win_itm_ {

	public static void PopupLoginForm() {
		Shell shell = Display.getDefault().getActiveShell();
		InputDialog input = new InputDialog(shell,
		"User Registration", "Please input your name here:",
		"",null);
		if(input.open()== Window.OK)
		{
			System.out.println(input.getValue());
		}

		Shell popupshell = new Shell(SWT.ON_TOP | SWT.Close);
		popupshell.setText("Session Control");
		//Text text = new Text(popupshell, SWT.MULTI | SWT.WRAP);
		//text.setBounds(10, 10, 180, 40);
		//text.setBackground(popupshell.getBackground());
		//text.setText("Session Control:");

		Button buttonStart = new Button(popupshell, SWT.MULTI | SWT.WRAP);
		buttonStart.setBounds(10, 5, 80, 30);
		buttonStart.setData("Begin", null);
		buttonStart.setBackground(popupshell.getBackground());
		buttonStart.setText("Begin");
		buttonStart.addListener(SWT.Selection, event -> System.out.println("start clicked..."));

		Button buttonEnd = new Button(popupshell, SWT.MULTI | SWT.WRAP);
		buttonEnd.setBounds(100, 5, 80, 30);
		buttonEnd.setData("Finish", null);
		buttonEnd.setBackground(popupshell.getBackground());
		buttonEnd.setText("Finish");
		buttonEnd.addListener(SWT.Selection, event -> System.out.println("end clicked..."));

		Button buttonGiveUp = new Button(popupshell, SWT.MULTI | SWT.WRAP);
		buttonGiveUp.setBounds(190, 5, 80, 30);
		buttonGiveUp.setData("Give Up", null);
		buttonGiveUp.setBackground(popupshell.getBackground());
		buttonGiveUp.setText("Give Up");
		buttonGiveUp.addListener(SWT.Selection, event -> {
			System.out.println("give up clicked...");
		});

		popupshell.setSize(286, 48);

		Rectangle rectangle = Display.getCurrent().getPrimaryMonitor().getClientArea();
		System.out.println(rectangle.width + ", " + rectangle.height);

		popupshell.setLocation((rectangle.width - 286) / 2, 0);
		popupshell.setMenuBar(new Menu(popupshell, SWT.BAR));
		popupshell.open();
	}

	public static void Show_win(Xog_win_itm win) {
		PopupLoginForm();
		Xoae_app app = win.App(); GfuiWin win_box = win.Win_box();			
		win_box.Focus_able_(false);
		app.Gui_mgr().Nightmode_mgr().Enabled_by_cfg();
		Xog_startup_win_.Startup(app, win_box);

		win_box.Icon_(IconAdp.file_or_blank(app.Fsys_mgr().Bin_xowa_dir().GenSubFil_nest("file", "app.window", "app_icon.png")));
	}
	public static void Show_widget(boolean show, GfuiElem box, GfuiElem btn) {
		int box_w, btn_w;
		if (show) {
			box_w = Toolbar_txt_w;
			btn_w = Toolbar_btn_w;
		}
		else {
			box_w = 0;
			btn_w = 0;
		}
		box.Layout_data_(new gplx.gfui.layouts.swts.Swt_layout_data__grid().Hint_w_(box_w).Hint_h_(Toolbar_grp_h));	// WORKAROUND.SWT: need to specify height, else SWT will shrink textbox on re-layout when showing / hiding search / allpages; DATE:2017-03-28
		btn.Layout_data_(new gplx.gfui.layouts.swts.Swt_layout_data__grid().Hint_w_(btn_w).Align_w__fill_());
	}
	public static Gfui_grp new_grp(Xoae_app app, Gfui_kit kit, GfuiElem win, String id) {
		return kit.New_grp(id, win);
	}
	public static GfuiBtn new_btn(Xoae_app app, Gfui_kit kit, GfuiElem win, String id) {
		return kit.New_btn(id, win);
	}
	public static GfuiComboBox new_cbo(Xoae_app app, Gfui_kit kit, GfuiElem win, FontAdp ui_font, String id, boolean border_on) {
		GfuiComboBox rv = kit.New_combo(id, win, Keyval_.new_(GfuiTextBox.CFG_border_on_, border_on));
		rv.TextMgr().Font_(ui_font);
		return rv;
	}
	public static GfuiTextBox new_txt(Xoae_app app, Gfui_kit kit, GfuiElem win, FontAdp ui_font, String id, boolean border_on) {
		GfuiTextBox rv = kit.New_text_box(id, win, Keyval_.new_(GfuiTextBox.CFG_border_on_, border_on));
		rv.TextMgr().Font_(ui_font);
		return rv;
	}
	public static void Update_tiptext(Xoae_app app, GfuiElem elem, int tiptext_id) {
		elem.TipText_(Xog_win_itm_.new_tiptext(app, tiptext_id));
	}
	public static void Font_update(Xog_win_itm win, Xol_font_info itm_font) {
		FontAdp gui_font = win.Url_box().TextMgr().Font();
		if (!itm_font.Eq(gui_font)) {
			FontAdp new_font = itm_font.To_font();
			win.Url_box().TextMgr().Font_(new_font);
			win.Search_box().TextMgr().Font_(new_font);
			win.Allpages_box().TextMgr().Font_(new_font);
			win.Find_box().TextMgr().Font_(new_font);
			win.Prog_box().TextMgr().Font_(new_font);
			win.Info_box().TextMgr().Font_(new_font);
			win.Tab_mgr().Tab_mgr().TextMgr().Font_(new_font);
		}
	}
	public static String new_tiptext(Xoae_app app, int id) {return String_.new_u8(app.Usere().Lang().Msg_mgr().Val_by_id(app.Usere().Wiki(), id));}
	public static final int 
	  Toolbar_grp_h = 24
	, Toolbar_txt_w = 160
	, Toolbar_btn_w = 16;
}
