package de.protin.support.pr.gui.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class FileImportHandler {

	
	public String importFromJsonFile(Shell shell) {
		
			String jsonString = null;
		
			FileDialog fd = new FileDialog(shell, SWT.OPEN);
			fd.setText("Import json text");
			fd.setFilterExtensions(new String[] { "*.json" });
			fd.setFilterNames(new String[] { "Plain json text files (*.json)" });
			String selected = fd.open();
			if (selected != null) {
				try {
					Stream<String> lines = Files.lines(Paths.get(selected), StandardCharsets.ISO_8859_1);
					List<String> content = lines.collect(Collectors.toList());
					StringBuffer sb = new StringBuffer();
					content.forEach(x -> sb.append(x));
					
					jsonString = sb.toString();
					
					lines.close();
				} catch (IOException e) {
					e.printStackTrace();
					MessageBox diag = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
					diag.setMessage("Unable to open file ");
					diag.open();
				}
			}
			
			return jsonString;
	}
	
	
}
