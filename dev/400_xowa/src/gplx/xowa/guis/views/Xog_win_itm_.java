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
import cn.edu.ruc.xowa.log.database.DBAccess;
import cn.edu.ruc.xowa.log.graph.GraphBuilder;
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
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class Xog_win_itm_ {

	private static UUID uuid;
	private static String UserName;
	private static int userId;

	public static void PopupLoginForm() {
		Shell shell = Display.getDefault().getActiveShell();
		InputDialog input = new InputDialog(shell,
		"User Registration", "Please input your name here:",
		"",null);
		if(input.open()== Window.OK)
		{
			String userName = input.getValue();
			UserName = userName;
			userId = -1;
			try
			{
				userId = DBAccess.Instance().login(userName);
				if (userId < 0)
				{
					userId = DBAccess.Instance().register(userName);
					MessageBox messageBox = new MessageBox(new Shell(), SWT.ABORT);
					messageBox.setText("Login");
					messageBox.setMessage("Create new user: " + userName + ", please remember it.\nClick OK to continue.");
					messageBox.open();
				}
			} catch (SQLException e)
			{
				System.err.println("login exception...");
				e.printStackTrace();
				System.exit(1);
			}
			Action loginAction = new LoginAction(userName);
			loginAction.perform();
			//System.out.println(input.getValue());
		}

		OpenChoiceshell();
	}

	public static void OpenChoiceshell()
	{
		Shell choiceshell = new Shell();
		choiceshell.setText("Module Controller");
		FormLayout layout = new FormLayout();
		layout.spacing = 6;
		layout.marginHeight = layout.marginWidth = 9;
		choiceshell.setLayout(layout);

		Text text = new Text(choiceshell, SWT.SINGLE | SWT.READ_ONLY);
		text.setText("Step 1. Please choose a module that you wanna enjoy.");

		Button buttonTaskGenerate = new Button(choiceshell, SWT.MULTI | SWT.WRAP | SWT.TOGGLE);
		//buttonTaskGenerate.setBounds(10, 5, 100, 30);
		buttonTaskGenerate.setData("Generate a task", null);
		buttonTaskGenerate.setBackground(choiceshell.getBackground());
		buttonTaskGenerate.setText("Generate a task");

		Button buttonTaskHandle = new Button(choiceshell, SWT.MULTI | SWT.WRAP | SWT.TOGGLE);
		//buttonTaskHandle.setBounds(100, 5, 80, 30);
		buttonTaskHandle.setData("Finish a task", null);
		buttonTaskHandle.setBackground(choiceshell.getBackground());
		buttonTaskHandle.setText("Finish a task");

		/*
		Button buttonReset = new Button(choiceshell, SWT.MULTI | SWT.WRAP | SWT.TOGGLE);
		buttonReset.setData("Reset", null);
		buttonReset.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		buttonReset.setText("Reset");
		*/

		buttonTaskGenerate.addListener(SWT.Selection, event ->
		{
			TaskGenerating(choiceshell, buttonTaskGenerate);
			buttonTaskGenerate.setEnabled(false);
			buttonTaskHandle.setEnabled(false);
		});
		buttonTaskHandle.addListener(SWT.Selection, event ->
		{
			TaskHandling(choiceshell, buttonTaskGenerate);
			buttonTaskHandle.setEnabled(false);
			buttonTaskGenerate.setEnabled(false);
		});
		/*
		buttonReset.addListener(SWT.Selection, event -> {
		    buttonTaskGenerate.setEnabled(true);
		    buttonTaskHandle.setEnabled(true);

            choiceshell.setSize(400, 480);
			choiceshell.layout();
			choiceshell.redraw();

        });*/

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

		/*FormData buttonResetData = new FormData();
		buttonResetData.top = new FormAttachment(text);
        buttonResetData.left = new FormAttachment(buttonTaskHandle);
        buttonReset.setLayoutData(buttonResetData);
        buttonReset.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent selectionEvent)
			{
				//super.widgetSelected(selectionEvent);
			}
		});*/
		/*GridLayout choicelayout = new GridLayout();
		choicelayout.numColumns = 2;
		choiceshell.setLayout(choicelayout);*/
		//Rectangle rectangle = Display.getCurrent().getPrimaryMonitor().getClientArea();
		//popupshell.setLocation((rectangle.width - 286) / 2 + 300, 0);
		//choiceshell.setMenuBar(new Menu(choiceshell, SWT.BAR));
		choiceshell.setSize(550, 200);
		choiceshell.open();
	}

	public static void TaskGenerating(Shell choiceshell, Button button){
		Text text2 = new Text(choiceshell, SWT.WRAP | SWT.READ_ONLY);
		text2.setText("Step 2. You choose the task generation module. Please select a phrase below.");
		FormData textData = new FormData();
		textData.top = new FormAttachment(button);
		textData.left = new FormAttachment(0);
		textData.right = new FormAttachment(90);
		text2.setLayoutData(textData);

		final Combo combo = new Combo(choiceshell, SWT.DROP_DOWN | SWT.READ_ONLY);
		String[] Items = {"-----------","in other words", "in plain English", "that is to say", "namely"};
		combo.setItems(Items);
		combo.select(0);
		//combo.setBounds(10,80, 200,50);
		FormData comboData = new FormData();
		comboData.top = new FormAttachment(text2);
		comboData.left = new FormAttachment(0);
		comboData.right = new FormAttachment(90);
		combo.setLayoutData(comboData);

		combo.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                List<String> entityResults = null;
                if (combo.getText().equals("in other words"))
                {
                    //pass "in other words" to BG and return back entity names
                    String keyword = "in other words";
                    Action returnEntities= new ReturnEntitiesAction(keyword);
                    entityResults = returnEntities.get();
                }else if (combo.getText().equals("in plain English"))
                {
                    //pass "in plain English" to BG and return back entity names
                    String keyword = "in plain English";
                    Action returnEntities = new ReturnEntitiesAction(keyword);
                    entityResults = returnEntities.get();
                }else if (combo.getText().equals("that is to say"))
                {
                    //pass "that is to say" to BG and return back entity names
                    String keyword = "that is to say";
                    Action returnEntities = new ReturnEntitiesAction(keyword);
                    entityResults = returnEntities.get();
                }else if (combo.getText().equals("namely"))
                {
                    //pass "namely" to BG and return back entity names
                    String keyword = "namely";
                    Action returnEntities = new ReturnEntitiesAction(keyword);
                    entityResults = returnEntities.get();
                }
                Text text3 = new Text(choiceshell, SWT.WRAP | SWT.READ_ONLY);
				text3.setText("Step 3. Wikipedia pages of the following entities " +
						"have the selected phrase, please choose one and get the corresponding " +
						"entity page in XOWA.");
				FormData text3Data = new FormData();
				text3Data.top = new FormAttachment(combo);
				text3Data.left = new FormAttachment(0);
				text3Data.right = new FormAttachment(90);
				text3.setLayoutData(text3Data);

				ScrolledComposite scrolledComposite = new ScrolledComposite(choiceshell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
				//scrolledComposite.setBounds();
				Composite composite = new Composite(scrolledComposite, SWT.NONE);
				composite.setSize(550, 500);

				scrolledComposite.setContent(composite);

                String entityNames = new String();
                int maxLength = 0;
                for (String entity : entityResults){
                	if (entity.length() > 1)
					{
						entityNames += entity +"\n";
						if (entity.length() > maxLength)
						{
							maxLength = entity.length();
						}
					}
				}
				Text entities = new Text(composite, SWT.BORDER | SWT.WRAP |SWT.MULTI);

				//下面这几行设置entities的宽度，并设置不允许编辑
				int compositeWidth = 12*maxLength;
				if (compositeWidth < 320)
				{
					compositeWidth = 320;
				}
				int compoSiteHight = 20*entityResults.size();
				GridData data = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
				data.widthHint = compositeWidth;  // Some width
                data.heightHint = compoSiteHight;
				entities.setLayoutData(data);
				entities.setEditable(false);

                entities.setText(entityNames.trim());

				// 必须加这四行
				composite.setLayout(new GridLayout(1, true));
				scrolledComposite.setMinSize(compositeWidth, compoSiteHight);
				scrolledComposite.setExpandVertical(true);
				scrolledComposite.setExpandHorizontal(true);

                FormData scrolledCompositeData = new FormData();
                scrolledCompositeData.top = new FormAttachment(text3);
                scrolledCompositeData.left = new FormAttachment(0);
                scrolledCompositeData.right = new FormAttachment(90);
                scrolledCompositeData.height = 100;
                scrolledComposite.setLayoutData(scrolledCompositeData);

                Text text4 = new Text(choiceshell, SWT.WRAP | SWT.READ_ONLY);
                text4.setText("Step 4. After selecting an entity in step3, go to the \"Edit\" tab in corresponding XOWA wikipedia page, and find the sentence containing phrase " +
						"in step 2. " +
						"\n" +
						"(1) Read and understand this sentence in context. \n" +
						"(2) Generate a question according to your understanding to this sentence. This means that your answer could be answered by this sentence. \n" +
						"(3) Input your question in the question box and the answer the answer box.)");
                FormData text4Data = new FormData();
                text4Data.top = new FormAttachment(scrolledComposite);
                text4Data.left = new FormAttachment(0);
                text4Data.right = new FormAttachment(90);
                text4.setLayoutData(text4Data);

                /*Label labelQuestion = new Label(choiceshell, SWT.NONE);
                labelQuestion.setText("Question");
                FormData labelQuesData = new FormData();
                labelQuesData.top = new FormAttachment(text4);
				labelQuesData.left = new FormAttachment(0);
				labelQuestion.setLayoutData(labelQuesData);*/
				Text textEntity = new Text(choiceshell, SWT.BORDER);
				textEntity.setMessage("Input the selected entity.");
				FormData textEntityData = new FormData();
				textEntityData.top = new FormAttachment(text4);
				textEntityData.left = new FormAttachment(0);
				textEntityData.right = new FormAttachment(90);
				textEntity.setLayoutData(textEntityData);
				Text textQues = new Text(choiceshell, SWT.BORDER | SWT.MULTI);
				textQues.setMessage("Input your question here.");
				FormData textInputData = new FormData();
				textInputData.top = new FormAttachment(textEntity);
				textInputData.left = new FormAttachment(0);
				textInputData.right = new FormAttachment(90);
				textInputData.height = 60;
				textQues.setLayoutData(textInputData);

				/*Label labelAnswer = new Label(choiceshell, SWT.NONE);
				labelAnswer.setText("Answer");
				FormData labelAnsData = new FormData();
				labelAnsData.top = new FormAttachment(textQues);
				labelAnsData.left = new FormAttachment(0);
				labelAnswer.setLayoutData(labelAnsData);*/

				Text textAnswer = new Text(choiceshell, SWT.BORDER);
				textAnswer.setMessage("Input answer of your question.");
				FormData textAnswerData = new FormData();
				textAnswerData.top = new FormAttachment(textQues);
				textAnswerData.left = new FormAttachment(0);
				textAnswerData.right = new FormAttachment(90);
				textAnswer.setLayoutData(textAnswerData);

				Button buttonSubmit = new Button(choiceshell, SWT.ABORT);
				buttonSubmit.addSelectionListener(new SelectionAdapter()
				{
					@Override
					public void widgetSelected(SelectionEvent selectionEvent)
					{
						Action saveQuesAction = new SaveQuesAction(userId, textEntity.getText(), textQues.getText(), textAnswer.getText());
						saveQuesAction.perform();

						buttonSubmit.setEnabled(false);

						Text text5 = new Text(choiceshell, SWT.WRAP | SWT.READ_ONLY);
						text5.setText("Step 5. Delete the sentence containing entity in step 2 from the XOWA page.\n" +
								"Save the sentence you wanna delete before you do that.");
						Text deletedSentence = new Text(choiceshell, SWT.BORDER);
						deletedSentence.setMessage("Input the sentence you wanna delete.");
						Text flagSentence = new Text(choiceshell, SWT.BORDER);
						flagSentence.setMessage("Input the sentence just before the deleted sentence");

						FormData text5Data = new FormData();
						text5Data.top = new FormAttachment(buttonSubmit);
						text5Data.left = new FormAttachment(0);
						text5Data.right = new FormAttachment(90);
						text5.setLayoutData(text5Data);

						FormData flagSentenceData = new FormData();
						flagSentenceData.top = new FormAttachment(text5);
						flagSentenceData.left = new FormAttachment(0);
						flagSentenceData.right = new FormAttachment(90);
						flagSentence.setLayoutData(flagSentenceData);

						FormData deletedSentenceData = new FormData();
						deletedSentenceData.top = new FormAttachment(flagSentence);
						deletedSentenceData.left = new FormAttachment(0);
						deletedSentenceData.right = new FormAttachment(90);
						deletedSentence.setLayoutData(deletedSentenceData);

						Button buttonSubmit2 = new Button(choiceshell, SWT.ABORT);
						buttonSubmit2.addSelectionListener(new SelectionAdapter()
						{
							@Override
							public void widgetSelected(SelectionEvent selectionEvent)
							{
								Action saveDeletedSentence = new SaveSentAction(userId, textEntity.getText(), flagSentence.getText(), deletedSentence.getText());
								saveDeletedSentence.perform();
								MessageBox messageBox = new MessageBox(choiceshell, SWT.ABORT);
								messageBox.setText("Task Generation");
								messageBox.setMessage("Task generation finished.\nClick OK to reset.");
								messageBox.open();
								buttonSubmit2.setEnabled(false);
								choiceshell.close();
								OpenChoiceshell();
							}
						});
						buttonSubmit2.setText("Delete sentence");
						FormData buttonSubmit2Data= new FormData();
						buttonSubmit2Data.top = new FormAttachment(deletedSentence);
						buttonSubmit2Data.right = new FormAttachment(90);
						buttonSubmit2.setLayoutData(buttonSubmit2Data);
						Point size = choiceshell.getSize();
						if (size.x == 550)
						{
							size.y += 150;
							choiceshell.setSize(size);
						}
						//choiceshell.setSize(550, 750);
						choiceshell.layout();
						choiceshell.redraw();
					}
				});
				buttonSubmit.setText("Save task");
				FormData buttonSubmitData= new FormData();
				buttonSubmitData.top = new FormAttachment(textAnswer);
				buttonSubmitData.right = new FormAttachment(90);
				buttonSubmit.setLayoutData(buttonSubmitData);
				/*if(input.open()== Window.OK)
				{
					String question = input.getValue();
					Action saveQuesAction = new SaveQuesAction(question);
					saveQuesAction.perform();
					//System.out.println(input.getValue());
				}*/
				choiceshell.setSize(550, 600);
                choiceshell.layout();
                choiceshell.redraw();
            }
        });
		choiceshell.layout();
		choiceshell.redraw();
	}
	public static void TaskHandling(Shell choiceshell, Button button){
		//从数据库中随机抽取一个问题显示出来
		Action returnTask = new ReturnTaskAction();
		List<String> task = returnTask.get();

        Text text2 = new Text(choiceshell, SWT.WRAP | SWT.READ_ONLY);
        text2.setText("Step 2. You choose to handle a task. \n" +
				"Please finish the task below. You may also need to answer some questions during the process. \n" +
				"Click on \"Begin\" to start search. \n" +
                "Click on \"Finish\" to finish you explorations.\n" +
                "Click on \"Give up\" when you wanna give up.");
        FormData textData = new FormData();
        textData.top = new FormAttachment(button);
        textData.left = new FormAttachment(0);
        textData.right = new FormAttachment(90);
        text2.setLayoutData(textData);

        Text textTask = new Text(choiceshell, SWT.WRAP | SWT.READ_ONLY);
        textTask.setText(task.get(0));
        FormData taskData = new FormData();
        taskData.top = new FormAttachment(text2);
        taskData.left = new FormAttachment(0);
        taskData.right = new FormAttachment(90);
        textTask.setLayoutData(taskData);

		Button buttonStart = new Button(choiceshell, SWT.MULTI | SWT.WRAP | SWT.TOGGLE);
		//buttonStart.setBounds(10, 5, 80, 30);
		buttonStart.setData("Begin", null);
		buttonStart.setBackground(choiceshell.getBackground());
		buttonStart.setText("Begin");

		Button buttonEnd = new Button(choiceshell, SWT.MULTI | SWT.WRAP | SWT.TOGGLE);
		//buttonEnd.setBounds(100, 5, 80, 30);
		buttonEnd.setData("Finish", null);
		buttonEnd.setBackground(choiceshell.getBackground());
		buttonEnd.setText("Finish");
		buttonEnd.setEnabled(false);

		Button buttonGiveUp = new Button(choiceshell, SWT.MULTI | SWT.WRAP | SWT.TOGGLE);
		//buttonGiveUp.setBounds(190, 5, 80, 30);
		buttonGiveUp.setData("Give Up", null);
		buttonGiveUp.setBackground(choiceshell.getBackground());
		buttonGiveUp.setText("Give Up");
		buttonGiveUp.setEnabled(false);
		/*
		Button buttonDrawGraph = new Button(popupshell, SWT.MULTI | SWT.WRAP);
		buttonDrawGraph.setBounds(280, 5, 80, 30);
		buttonDrawGraph.setData("Review Navigation", null);
		buttonDrawGraph.setBackground(popupshell.getBackground());
		buttonDrawGraph.setText("Review");
		buttonDrawGraph.setEnabled(false);*/

		////问题1, 用户阅读完题目后，对解决该问题有没有一个明确的方向感：directed/undirected/others，选择题
		Text qus1 = new Text(choiceshell, SWT.WRAP | SWT.READ_ONLY);
		qus1.setText("Q1: Please choose a case to describe your plan(goal) of solving it? ");
		//text3.setText("Step 3. Start exploration.......\n"+"User Name: "+ UserName);
		FormData q1TxtData = new FormData();
		q1TxtData.top = new FormAttachment(buttonStart);
		q1TxtData.left = new FormAttachment(0);
		q1TxtData.right = new FormAttachment(90);
		qus1.setLayoutData(q1TxtData);
		qus1.setVisible(false);

		Group groupQ1 = new Group(choiceshell, SWT.NONE|SWT.WRAP);
		FormData groupQ1Data = new FormData();
		groupQ1Data.top= new FormAttachment(qus1);
		groupQ1Data.left=new FormAttachment(0);
		groupQ1Data.right=new FormAttachment(100);
		groupQ1.setLayoutData(groupQ1Data);
		groupQ1.setLayout(new RowLayout());
		groupQ1.setVisible(false);

		Button undirected = new Button(groupQ1, SWT.RADIO);
		undirected.setText("undirected(I want to learn something specific about my topic)");
		undirected.setSelection(false);

		Button directed = new Button(groupQ1, SWT.RADIO);
		directed.setText("directed(tell me about my topic)");
		directed.setSelection(false);

		Button unknown = new Button(groupQ1, SWT.RADIO);
		unknown.setText("others(ones that we were unable to classify)");
		unknown.setSelection(false);

		// 问题2
		Text qus2 = new Text(choiceshell, SWT.WRAP | SWT.READ_ONLY);
		//qus2.setText("Q2: Do you already have a query(a well-formed phrase or a well-defined concept) in mind?");
		qus2.setText("Q2: Do you have a specific question in mind?");
		FormData q2TxtData = new FormData();
		q2TxtData.top = new FormAttachment(groupQ1);
		q2TxtData.left = new FormAttachment(0);
		q2TxtData.right = new FormAttachment(90);
		qus2.setLayoutData(q2TxtData);
		qus2.setVisible(false);

		Group groupQ2 = new Group(choiceshell, SWT.NONE);
		FormData groupQ2Data = new FormData();
		groupQ2Data.top= new FormAttachment(qus2);
		groupQ2Data.left=new FormAttachment(0);
		groupQ2Data.right=new FormAttachment(100);
		groupQ2.setLayoutData(groupQ2Data);
		groupQ2.setLayout(new RowLayout());
		groupQ2.setVisible(false);

		Button specificYes = new Button(groupQ2, SWT.RADIO);
		specificYes.setText("yes");
		specificYes.setSelection(false);

		Button specificNo = new Button(groupQ2, SWT.RADIO);
		specificNo.setText("no");
		specificNo.setSelection(false);

		//问题3
		Text qus3 = new Text(choiceshell, SWT.READ_ONLY | SWT.WRAP);
		qus3.setText("Q3: Please express your current information need(goal/question) in terms of natural language.\n" +
				"It might be in forms like: \n" +
				"–  I want to know how to make pasta. I want to know why bears hibernate. \n" +
				"–  I want to know something about lung cancer. I want to know who Micheal Jackson is. \n" +
				"–  I want to know the capital city of Kenya. I want to know who invented the telephone.\n" +
				"–  I want the list of countries in Latin America. I want the names of some programming languages. \n" +
				"If none of the above, could you formulate a question expressing your information need?");
		FormData q3TxtData = new FormData();
		q3TxtData.top = new FormAttachment(groupQ2);
		q3TxtData.left = new FormAttachment(0);
		q3TxtData.right = new FormAttachment(90);
		qus3.setLayoutData(q3TxtData);
		qus3.setVisible(false);

		Text qus3input = new Text(choiceshell, SWT.BORDER | SWT.MULTI);
		FormData txtData1 = new FormData();
		txtData1.top = new FormAttachment(qus3);
		txtData1.left = new FormAttachment(0);
		txtData1.right = new FormAttachment(90);
		txtData1.height = 40;
		qus3input.setLayoutData(txtData1);
		qus3input.setVisible(false);

		Button subqs123 = new Button(choiceshell, SWT.MULTI | SWT.WRAP | SWT.TOGGLE);
		subqs123.setText("submit Q1,Q2,Q3");
		FormData subData = new FormData();
		subData.top = new FormAttachment(qus3input);
		subData.right = new FormAttachment(90);
		subqs123.setLayoutData(subData);
		subqs123.setVisible(false);

		//question 5 and 6, along with the answer of the exploration task.
		Text answerTxt = new Text(choiceshell, SWT.WRAP | SWT.READ_ONLY);
		answerTxt.setText("Answer:");
		FormData answerTxtData = new FormData();
		answerTxtData.top = new FormAttachment(subqs123);
		answerTxtData.left = new FormAttachment(0);
		answerTxtData.right = new FormAttachment(90);
		answerTxt.setLayoutData(answerTxtData);
		answerTxt.setVisible(false);

		Text answer = new Text(choiceshell, SWT.BORDER);
		FormData answerData = new FormData();
		answerData.left = new FormAttachment(0);
		answerData.right = new FormAttachment(90);
		answerData.top = new FormAttachment(answerTxt);
		answer.setLayoutData(answerData);
		answer.setVisible(false);

		Text finalQsTxt = new Text(choiceshell, SWT.WRAP | SWT.READ_ONLY);
		finalQsTxt.setText("Do you think a multi-facet filter would help you during the exploring process?");
		FormData txtData = new FormData();
		txtData.left = new FormAttachment(0);
		txtData.right = new FormAttachment(90);
		txtData.top = new FormAttachment(answer);
		finalQsTxt.setLayoutData(txtData);
		finalQsTxt.setVisible(false);

		Text finalQs = new Text(choiceshell, SWT.BORDER);
		FormData qsData = new FormData();
		qsData.left = new FormAttachment(0);
		qsData.right = new FormAttachment(90);
		qsData.top = new FormAttachment(finalQsTxt);
		finalQs.setLayoutData(qsData);
		finalQs.setVisible(false);

		Button subqs56 = new Button(choiceshell, SWT.MULTI | SWT.WRAP | SWT.TOGGLE);
		subqs56.setText("submit Q5,Q6");
		FormData subData2 = new FormData();
		subData2.top = new FormAttachment(finalQs);
		subData2.right = new FormAttachment(90);
		subqs56.setLayoutData(subData2);
		subqs56.setVisible(false);

		subqs123.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent selectionEvent)
			{
				//提交问题123到数据库
				String q3 = qus3input.getText();
				String plan = null;
				if (directed.getSelection())
				{
					plan = "directed";
				}
				else if (undirected.getSelection())
				{
					plan = "undirected";
				}
				else
				{
					plan = "unknown";
				}
				String specific = null;
				if (specificYes.getSelection())
				{
					specific = "yes";
				}
				else
				{
					specific = "no";
				}
				GraphBuilder.getInstance().saveSessionQuestions(plan, specific, q3);
			}
		});

		subqs56.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent selectionEvent)
			{
				// update the database.
			}
		});

		SelectionAdapter selectionQ2 = new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent selectionEvent)
			{
				qus3.setVisible(true);
				qus3input.setVisible(true);
				subqs123.setVisible(true);
			}
		};

		specificYes.addSelectionListener(selectionQ2);

		specificNo.addSelectionListener(selectionQ2);

		SelectionAdapter selectionQ1 = new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent selectionEvent)
			{
				qus2.setVisible(true);
				groupQ2.setVisible(true);
			}
		};

		undirected.addSelectionListener(selectionQ1);

		directed.addSelectionListener(selectionQ1);

		unknown.addSelectionListener(selectionQ1);

		buttonStart.addListener(SWT.Selection, event -> {
			//System.out.println("start clicked...");
			//UUID uuid = UUID.randomUUID();
			uuid = UUID.randomUUID();
			Action startAction = new StartSessionAction(uuid.toString());
			startAction.perform();
			buttonStart.setEnabled(false);
			buttonEnd.setEnabled(true);
			buttonGiveUp.setEnabled(true);

			qus1.setVisible(true);
			groupQ1.setVisible(true);
		});

		buttonEnd.addListener(SWT.Selection, event -> {
			//System.out.println("finish clicked...");
			Action endAction = new EndSessionAction();
			endAction.perform();
			buttonStart.setEnabled(true);
			buttonEnd.setEnabled(false);
			buttonGiveUp.setEnabled(false);
			//buttonDrawGraph.setEnabled(true);

			answerTxt.setVisible(true);
			answer.setVisible(true);
			finalQsTxt.setVisible(true);
			finalQs.setVisible(true);
			subqs56.setVisible(true);
		});

		buttonGiveUp.addListener(SWT.Selection, event -> {
			//System.out.println("give up clicked...");
			Action giveupAction = new GiveupSessionAction();
			giveupAction.perform();
			buttonStart.setEnabled(true);
			buttonEnd.setEnabled(false);
			buttonGiveUp.setEnabled(false);
			//buttonDrawGraph.setEnabled(true);

			finalQsTxt.setVisible(true);
			finalQs.setVisible(true);
			subqs56.setVisible(true);
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
        FormData buttonStartData = new FormData();
        buttonStartData.top = new FormAttachment(textTask);
        buttonStartData.left = new FormAttachment(0);
        buttonStartData.right = new FormAttachment(30);
        buttonStart.setLayoutData(buttonStartData);

        FormData buttonEndData = new FormData();
        buttonEndData.top = new FormAttachment(textTask);
        buttonEndData.left = new FormAttachment(buttonStart);
        buttonEnd.setLayoutData(buttonEndData);

        FormData buttonGiveUpData = new FormData();
        buttonGiveUpData.top = new FormAttachment(textTask);
        buttonGiveUpData.left = new FormAttachment(buttonEnd);
        buttonGiveUp.setLayoutData(buttonGiveUpData);

        choiceshell.setSize(550, 880);
        choiceshell.layout();
        choiceshell.redraw();
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
