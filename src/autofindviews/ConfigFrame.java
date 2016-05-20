package autofindviews;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import autofindviews.AutoFindView.AutoType;
import autofindviews.AutoFindView.OnFoundViewsListener;

public class ConfigFrame{
	private static AutoType autoType;
	private static JFrame jf;
	private static File selectedFile;
	private static JTextArea area;
	public static void createFrame(final Shell parent,File selectedFile)
	{
		ConfigFrame.selectedFile=selectedFile;
		if(jf==null)
		{
			jf = new JFrame();
			jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			jf.setLocation(getWindowScreenSize().width / 2 - 200, getWindowScreenSize().height / 2 - 150);
			jf.setSize(500, 420);
			
			jf.setLayout(new BorderLayout());
			
			//North 
			JPanel jp=new JPanel(new FlowLayout(FlowLayout.CENTER));
			jp.add(new Label("代码位置："));
			
			autoType=AutoType.Field;
			
			
			
			ButtonGroup radioBtnGroup=new ButtonGroup();
			JRadioButton buttonField=new JRadioButton("Feild",true);
			JRadioButton buttonLocal=new JRadioButton("Local");
			JRadioButton buttonViewHolder=new JRadioButton("ViewHolder");
			buttonField.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					autoType=AutoType.Field;
				}
			});
			
			buttonLocal.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					autoType=AutoType.Local;
				}
			});
			buttonViewHolder.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					autoType=AutoType.ViewHolder;
				}
			});
			//将三个加入一组
			radioBtnGroup.add(buttonField);
			radioBtnGroup.add(buttonLocal);
			radioBtnGroup.add(buttonViewHolder);
			
			jp.add(buttonField);
			jp.add(buttonLocal);
			jp.add(buttonViewHolder);
			//将北边的panel加入到窗体中
			jf.add(jp, BorderLayout.NORTH);
			
			
			//中间显示内容区域
			area=new JTextArea();
			JScrollPane jsp=new JScrollPane(area);
			area.setMargin(new Insets(5, 5, 5, 5));
			jf.add(jsp, BorderLayout.CENTER);
			
			//将南边按钮加入到窗体中
			JButton jbButton=new JButton("开始生成");
			jf.add(jbButton, BorderLayout.SOUTH);
			jbButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						AutoFindView.autoFindView(ConfigFrame.selectedFile.getContents(), autoType,new OnFoundViewsListener(){
							public void onFound(String viewCodes)
							{
								area.setText(viewCodes);
							}
						});
					} catch (CoreException e1) {
						e1.printStackTrace();
						MessageDialog.openInformation(parent, "提示", "生成失败，请重试！");
					}
					
				}
			});
		}
		jf.setTitle("自动生成findViewById代码--"+selectedFile.getName());
		area.setText("");
		jf.setVisible(true);
		
		
	}
	private static Dimension getWindowScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
}
