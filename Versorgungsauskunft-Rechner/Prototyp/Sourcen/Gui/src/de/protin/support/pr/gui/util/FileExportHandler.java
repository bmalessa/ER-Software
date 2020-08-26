package de.protin.support.pr.gui.util;

import java.io.File;
import java.io.FileWriter;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class FileExportHandler {

	public void exportToJsonFile(Shell shell, String jsonString) {
		
		boolean done = false;
		
		while (!done) {
			FileDialog fd = new FileDialog(shell, SWT.SAVE);
			fd.setFilterNames(new String[] { "Plain text file (*.json)"});
			fd.setFilterExtensions(new String[] { "*.json"});
			String lastPath = ".";
			if (lastPath != null && !lastPath.isEmpty())
				fd.setFileName(lastPath);
			String file = fd.open();
			try {
				if (file != null) {
					File destFile = new File(file);
					boolean overwrite = true;
					if (destFile.exists()) {
						overwrite = MessageDialog.openConfirm(shell, "Overwrite current file?",
								"Would you like to overwrite " + destFile.getName() + "?");
					}
					if (overwrite) {
						
						FileWriter fw = new FileWriter(new File(file));
				        fw.write(jsonString);
				        fw.close();
						done = true;
					}
				} else
					done = true;
			} catch (Exception e) {
				e.printStackTrace();
				MessageBox diag = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
				diag.setMessage("Unable to export to json file ");
				diag.open();
			}
			
		}
	}
	
}
