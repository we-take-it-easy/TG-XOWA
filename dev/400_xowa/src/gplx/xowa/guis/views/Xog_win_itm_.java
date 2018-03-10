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

import cn.edu.ruc.xowa.log.action.*;
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
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.UUID;

public class Xog_win_itm_ {

	private static UUID uuid;

	public static void PopupLoginForm() {
		Shell shell = Display.getDefault().getActiveShell();
		InputDialog input = new InputDialog(shell,
		"User Registration", "Please input your name here:",
		"",null);
		if(input.open()== Window.OK)
		{
			String userName = input.getValue();
			Action loginAction = new LoginAction(userName);
			loginAction.perform();
			//System.out.println(input.getValue());
		}

		Shell choiceshell = new Shell();
		Text text = new Text(choiceshell, SWT.SINGLE | SWT.READ_ONLY);
		text.setText("1. Please choose a module that you wanna try.");

		Button buttonTaskGenerate = new Button(choiceshell, SWT.MULTI | SWT.WRAP);
		buttonTaskGenerate.setBounds(10, 5, 100, 30);
		buttonTaskGenerate.setData("Generate a task", null);
		buttonTaskGenerate.setBackground(choiceshell.getBackground());
		buttonTaskGenerate.setText("Generate a task");

		Button buttonTaskHandle = new Button(choiceshell, SWT.MULTI | SWT.WRAP);
		buttonTaskHandle.setBounds(100, 5, 80, 30);
		buttonTaskHandle.setData("Finish a task", null);
		buttonTaskHandle.setBackground(choiceshell.getBackground());
		buttonTaskHandle.setText("Finish a task");

		buttonTaskGenerate.addListener(SWT.Selection, event ->
		{
			TaskGenerating(choiceshell, buttonTaskGenerate);
		});
		buttonTaskHandle.addListener(SWT.Selection, event ->
		{
			TaskHandling();
		});

		choiceshell.setText("Module Control");
		FormLayout layout = new FormLayout();
		layout.spacing = 5;
		layout.marginHeight = layout.marginWidth = 9;
		choiceshell.setLayout(layout);

		FormData textData = new FormData();
		textData.top = new FormAttachment(0);
		textData.left = new FormAttachment(0);
		textData.right = new FormAttachment(90);
		text.setLayoutData(textData);

		FormData buttonTaskGenerateData = new FormData();
		buttonTaskGenerateData.top = new FormAttachment(text);
		buttonTaskGenerateData.left = new FormAttachment(0);
		buttonTaskGenerateData.right = new FormAttachment(40);
		buttonTaskGenerate.setLayoutData(buttonTaskGenerateData);

		FormData buttonTaskHandleData = new FormData();
		buttonTaskHandleData.top = new FormAttachment(text);
		buttonTaskHandleData.left = new FormAttachment(buttonTaskGenerate);
		buttonTaskHandle.setLayoutData(buttonTaskHandleData);

		/*GridLayout choicelayout = new GridLayout();
		choicelayout.numColumns = 2;
		choiceshell.setLayout(choicelayout);*/
		//Rectangle rectangle = Display.getCurrent().getPrimaryMonitor().getClientArea();
		//popupshell.setLocation((rectangle.width - 286) / 2 + 300, 0);
		//choiceshell.setMenuBar(new Menu(choiceshell, SWT.BAR));
		choiceshell.setSize(320, 480);
		choiceshell.open();
	}
	public static void TaskGenerating(Shell choiceshell, Button button){

		Combo combo = new Combo(choiceshell, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setItems(new String[]{"in other words", "in plain English", "that is to say", "namely"});
		combo.select(0);
		//combo.setData("in other words", "in other words");
		//combo.setData("in plain English", "in plain English");
		//combo.setData("that is to say", "that is to say");
		//combo.setData("namely", "namely");

		/*List list = new List(choiceshell, SWT.MULTI | SWT.WRAP);
		Menu menu = new Menu(choiceshell, SWT.BAR);
		choiceshell.setMenuBar(menu);
		MenuItem taskGenerate = new MenuItem(menu, SWT.CASCADE);
		taskGenerate.setText("task generation");

		Menu taskgenrateMenu = new Menu(choiceshell, SWT.DROP_DOWN);
		taskGenerate.setMenu(taskgenrateMenu);
		Button buttonInOtherWords = new Button(choiceshell, SWT.MULTI | SWT.WRAP);
		buttonInOtherWords.setBounds(10, 45, 100, 30);
		buttonInOtherWords.setData("in other words", null);
		buttonInOtherWords.setBackground(choiceshell.getBackground());
		buttonInOtherWords.setText("in other words");

		Button buttonThatIsToSay = new Button(choiceshell, SWT.MULTI | SWT.WRAP);
		buttonInOtherWords.setBounds(10, 90, 100, 30);
		buttonThatIsToSay.setData("that is to say", null);
		buttonThatIsToSay.setBackground(choiceshell.getBackground());
		buttonThatIsToSay.setText("that is to say");

		Button buttonNamely = new Button(choiceshell, SWT.MULTI | SWT.WRAP);
		buttonNamely.setBounds(10, 45, 135, 30);
		buttonNamely.setData("namely", null);
		buttonNamely.setBackground(choiceshell.getBackground());
		buttonNamely.setText("namely");

		Button buttonInPlainEnglish = new Button(choiceshell, SWT.MULTI | SWT.WRAP);
		buttonInPlainEnglish.setBounds(10, 180, 100, 30);
		buttonInPlainEnglish.setData("in plain English", null);
		buttonInPlainEnglish.setBackground(choiceshell.getBackground());
		buttonInPlainEnglish.setText("in plain English");*/

		/*MenuItem inPlainEnglish = new MenuItem(menu, SWT.CASCADE);
		inPlainEnglish.setText("in plain English");*/

		/*
		FormData listData = new FormData();
		listData.top = new FormAttachment(button);
		listData.left = new FormAttachment(0);
		listData.right = new FormAttachment(40);
		list.setLayoutData(listData);
		*/
	}
	public static void TaskHandling(){
		Shell popupshell = new Shell();
		//Shell popupshell = new Shell(SWT.ON_TOP | SWT.Close);
		popupshell.setText("Session Control");
		popupshell.setSize(286, 80);

		//Rectangle rectangle = Display.getCurrent().getPrimaryMonitor().getClientArea();
		//popupshell.setLocation((rectangle.width - 286) / 2 + 300, 0);
		popupshell.setMenuBar(new Menu(popupshell, SWT.DROP_DOWN));
		popupshell.open();
		//Text text = new Text(popupshell, SWT.MULTI | SWT.WRAP);
		//text.setBounds(10, 10, 180, 40);
		//text.setBackground(popupshell.getBackground());
		//text.setText("Session Control:");
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		popupshell.setLayout(layout);

		Button buttonStart = new Button(popupshell, SWT.MULTI | SWT.WRAP);
		buttonStart.setBounds(10, 5, 80, 30);
		buttonStart.setData("Begin", null);
		buttonStart.setBackground(popupshell.getBackground());
		buttonStart.setText("Begin");

		Button buttonEnd = new Button(popupshell, SWT.MULTI | SWT.WRAP);
		buttonEnd.setBounds(100, 5, 80, 30);
		buttonEnd.setData("Finish", null);
		buttonEnd.setBackground(popupshell.getBackground());
		buttonEnd.setText("Finish");
		buttonEnd.setEnabled(false);

		Button buttonGiveUp = new Button(popupshell, SWT.MULTI | SWT.WRAP);
		buttonGiveUp.setBounds(190, 5, 80, 30);
		buttonGiveUp.setData("Give Up", null);
		buttonGiveUp.setBackground(popupshell.getBackground());
		buttonGiveUp.setText("Give Up");
		buttonGiveUp.setEnabled(false);

		/*
		Button buttonDrawGraph = new Button(popupshell, SWT.MULTI | SWT.WRAP);
		buttonDrawGraph.setBounds(280, 5, 80, 30);
		buttonDrawGraph.setData("Review Navigation", null);
		buttonDrawGraph.setBackground(popupshell.getBackground());
		buttonDrawGraph.setText("Review");
		buttonDrawGraph.setEnabled(false);*/

		buttonStart.addListener(SWT.Selection, event -> {
			//System.out.println("start clicked...");
			//UUID uuid = UUID.randomUUID();
			uuid = UUID.randomUUID();
			Action startAction = new StartSessionAction(uuid.toString());
			startAction.perform();
			buttonStart.setEnabled(false);
			buttonEnd.setEnabled(true);
			buttonGiveUp.setEnabled(true);
		});

		buttonEnd.addListener(SWT.Selection, event -> {
			//System.out.println("finish clicked...");
			Action endAction = new EndSessionAction();
			endAction.perform();
			buttonStart.setEnabled(true);
			buttonEnd.setEnabled(false);
			buttonGiveUp.setEnabled(false);
			//buttonDrawGraph.setEnabled(true);
		});

		buttonGiveUp.addListener(SWT.Selection, event -> {
			//System.out.println("give up clicked...");
			Action giveupAction = new GiveupSessionAction();
			giveupAction.perform();
			buttonStart.setEnabled(true);
			buttonEnd.setEnabled(false);
			buttonGiveUp.setEnabled(false);
			//buttonDrawGraph.setEnabled(true);
		});

		/*
		buttonDrawGraph.addListener(SWT.Selection, event -> {
			Canvas canvas = new Canvas(popupshell, SWT.NONE);
			canvas.setSize(280, 280);
			canvas.setLocation(6,20);
			Action drawpathAction = new DrawpathAction(uuid.toString(), canvas);
			drawpathAction.perform();
			popupshell.setSize(286, 300);
		});*/
	}

	public static void Show_win(Xog_win_itm win) {
		PopupLoginForm();
		Xoae_app app = win.App();
		GfuiWin win_box = win.Win_box();
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
