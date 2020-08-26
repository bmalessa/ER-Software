package de.protin.support.pr.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import de.protin.support.swt.custom.ScrolledComposite;
import de.protin.support.swt.custom.StyleRange;
import de.protin.support.swt.custom.StyledText;

public class DokumentionPart extends Composite  {
	
	
	private StyledText text;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DokumentionPart(Composite parent, int style) {
		super(parent, SWT.NONE);
		setLayout(new FillLayout());
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		text = new StyledText (scrolledComposite, SWT.V_SCROLL |SWT.BORDER | SWT.READ_ONLY | SWT.WRAP);
		scrolledComposite.setContent(text);
		scrolledComposite.setMinSize(text.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}



	
	
	
	
	public void clearInfoText() {
		text.setText("");
		StyleRange style1 = new StyleRange();
		style1.start = 0;
		style1.length = text.getText().length();
		style1.fontStyle = SWT.BOLD;
		text.setStyleRange(style1);
	}
	
	
	
	public void setPartDefaultText(String defaulText) {
		text.setText(defaulText);
		StyleRange style1 = new StyleRange();
		style1.start = 0;
		style1.length = text.getText().length();
		style1.fontStyle = SWT.ITALIC;
		text.setStyleRange(style1);
	}
	
	

	public void setPartInfoText(String infotext) {
		text.setText(infotext);
		
		StyleRange style1 = new StyleRange();
		style1.start = 0;
		style1.length = text.getText().length();
		style1.fontStyle = SWT.BOLD;
		text.setStyleRange(style1);
	}
	
	
	public void setPartFieldInfoText(String field) {
		text.setText("Informationen zum ausgewählten Feld: \n\n" + field );
		
		StyleRange style1 = new StyleRange();
		style1.start = 0;
		style1.length = text.getText().length();
		style1.fontStyle = SWT.BOLD;
		text.setStyleRange(style1);
	}
	

}
