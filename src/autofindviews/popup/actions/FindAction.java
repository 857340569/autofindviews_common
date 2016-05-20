package autofindviews.popup.actions;

import org.eclipse.core.internal.resources.File;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import autofindviews.ConfigFrame;


public class FindAction implements IObjectActionDelegate {

	private Shell shell;
	private File selectedFile;
	/**
	 * Constructor for Action1.
	 */
	public FindAction() {
		super();
	}
	


		/**
		 * step1
		 * 
		 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
		 */
		public void setActivePart(IAction action, IWorkbenchPart targetPart) {
			shell = targetPart.getSite().getShell();
		}

		/**
		 * step2
		 * @see IActionDelegate#selectionChanged(IAction, ISelection)
		 */
		public void selectionChanged(IAction action, ISelection selection) {
			selectedFile=null;
			if(!selection.isEmpty()&&selection instanceof StructuredSelection)
			{
				StructuredSelection mySelection=(StructuredSelection)selection;
				Object selectObj=mySelection.getFirstElement();
				if(selectObj instanceof File)
				{
					selectedFile=(File)selectObj;
					
				}
			}
		}

		/**
		 * step3
		 * 
		 * @see IActionDelegate#run(IAction)
		 */
		public void run(IAction action) {
			if(selectedFile==null)
			{
				MessageDialog.openInformation(shell, "信息提示", "请选择文件");
				return;
			}
//			System.out.println(selectedFile.getFullPath().toOSString());//\test\src\test\test.xml
//			System.out.println(selectedFile.getFullPath().toString());///test/src/test/test.xml
			ConfigFrame.createFrame(shell,selectedFile);
		}

	}
