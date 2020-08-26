package de.protin.support.pr.gui;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import de.protin.support.pr.domain.besoldung.zuschlag.KindererziehungszeitenZuschlag;
import de.protin.support.pr.domain.besoldung.zuschlag.Para_50_Kindererziehungszeit;
import de.protin.support.pr.domain.utils.DateUtil;
import de.protin.support.pr.domain.utils.KindererziehungszeitenHelper;
import de.protin.support.pr.gui.kez.KezCellModifier;
import de.protin.support.pr.gui.kez.KezLabelProvider;
import de.protin.support.pr.gui.kez.KezPeriodWrapper;
import de.protin.support.pr.gui.text.KezInfoText;
import de.protin.support.pr.gui.text.TextResources;
import de.protin.support.pr.gui.util.ControlConstants;

public class KindererziehungszeitenPart extends TabPart  {

	
	
	private String[] columnNames;
	private Table tableBefore1992;
	private Table tableAfter1991;
	private TableViewer tableViewerBefore1992;
	private TableViewer tableViewerAfter1991;
	private Set<KezPeriodWrapper> childListBefore1992;
	private Set<KezPeriodWrapper> childListAfter1991;
	
	
	
	
	public KindererziehungszeitenPart(Composite parent) {
		super(parent);
		//setPartInfoText(KezInfoText.DEFAULT_TEXT);
		setPartInfoText(this.textResources.getString("DEFAULT_TEXT"));
	}
	
	
	public void initPart(Composite parent, int style) {

		this.childListBefore1992 = new LinkedHashSet<KezPeriodWrapper>();
		this.childListAfter1991 = new LinkedHashSet<KezPeriodWrapper>();

		
		Composite partComposite = new Composite(parent, SWT.BORDER);
		GridLayout layout = new GridLayout(1, false);
		partComposite.setLayout (layout);
		GridData gridData = new GridData (GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH);
		partComposite.setLayoutData (gridData);
		
		
		// Create the table, tableviewer and buttons for KEZ with BirthDate after 31.12.1991
		String label = new String("Zu berücksichtigende Kindererziehungszeiten nach §50 a BeamtVG (geboren nach dem 31.12.1991)");
		this.tableAfter1991 = createTableSection(partComposite, label);
		this.tableViewerAfter1991 = createTableViewer(this.tableAfter1991, childListAfter1991);
		createButtonsAfter1991(partComposite);

		// Create the table, tableviewer and buttons for KEZ with BirthDatebefore 01.01.1992
		label = new String("Zu berücksichtigende Kindererziehungszeiten nach §50 b BeamtVG (geboren vor dem 01.01.1992)");
		this.tableBefore1992 = createTableSection(partComposite, label);
		this.tableViewerBefore1992 = createTableViewer(this.tableBefore1992, childListBefore1992);
		createButtonsBefore1992(partComposite);
	}



	public void loadTextResources() {
		this.textResources = TextResources.getKindererziehungszuschlagDocs();
	}




	private Table createTableSection(Composite parent, String label) {
		Label lbl = new Label(parent, SWT.NONE);
		lbl.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lbl.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lbl.setText(label);
		
		Label lblSeparator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparator.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 10, SWT.BOLD));
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		lblSeparator.setLayoutData(data);
		
		int style = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | 
					SWT.FULL_SELECTION | SWT.HIDE_SELECTION;

		Table table = new Table(parent, style);
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 3;
		table.setLayoutData(gridData);		
					
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		
		
		// 1rd column with task Owner
		TableColumn column = new TableColumn(table, SWT.LEFT, 0);
		column.setText(KindererziehungszeitenHelper.CHILD_NAME);
		column.setWidth(120);
		
		// 2nd column with task Description
		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText(KindererziehungszeitenHelper.BIRTHDATE);
		column.setWidth(80);
		
		// 3th column with task PercentComplete 
		column = new TableColumn(table, SWT.CENTER, 2);
		column.setText(KindererziehungszeitenHelper.DATE_FROM);
		column.setWidth(80);
		
		// 4th column with task PercentComplete 
		column = new TableColumn(table, SWT.CENTER, 3);
		column.setText(KindererziehungszeitenHelper.DATE_TO);
		column.setWidth(80);
		
		// 5th column with task PercentComplete 
		column = new TableColumn(table, SWT.CENTER, 4);
		column.setText(KindererziehungszeitenHelper.AMOUNT_OF_MONTHS);
		column.setWidth(60);
		
		// 6th column with task PercentComplete 
		column = new TableColumn(table, SWT.CENTER, 5);
		column.setText(KindererziehungszeitenHelper.KEZ_MONTHS);
		column.setWidth(80);

		// 6th column with task PercentComplete 
		column = new TableColumn(table, SWT.CENTER, 6);
		column.setText(KindererziehungszeitenHelper.ANSPRUCH);
		column.setWidth(80);
		
		table.setToolTipText(label);
		
		return table;
		
	}





	
	

	private TableViewer createTableViewer(Table table, Set<KezPeriodWrapper> childList) {
		TableViewer tableViewer = new TableViewer(table);
		tableViewer.setUseHashlookup(true);

		this.columnNames = KindererziehungszeitenHelper.getTableColums();
		tableViewer.setColumnProperties(this.columnNames);

		// Create the cell editors
		CellEditor[] editors = new CellEditor[this.columnNames.length];

		// Column 1 : Vorname Kind
		TextCellEditor textEditor = new TextCellEditor(table);  
		((Text) textEditor.getControl()).setTextLimit(25);
		editors[0] = textEditor;

		// Column 2 : Birthday 
		textEditor = new TextCellEditor(table);
		((Text) textEditor.getControl()).setTextLimit(10);
		editors[1] = textEditor;

		// Column 3 : Datum von
		textEditor = new TextCellEditor(table);  
		((Text) textEditor.getControl()).setTextLimit(10);
		editors[2] = textEditor;
	
		// Column 4 : Datum bis
		textEditor = new TextCellEditor(table);
		((Text) textEditor.getControl()).setTextLimit(10);
		editors[3] = textEditor;

		// Column 5 : Anzahl Monate
		textEditor = new TextCellEditor(table);
		((Text) textEditor.getControl()).setTextLimit(2);
		editors[4] = textEditor;
				
		// Column 6 : Anzahl der zulagefähigen Monate
		textEditor = new TextCellEditor(table);  
		((Text) textEditor.getControl()).setTextLimit(2);
		((Text) textEditor.getControl()).addVerifyListener(
				
				new VerifyListener() {
					public void verifyText(VerifyEvent event) {
						try { 
				            // checking valid integer using parseInt() method 
				            Integer.parseInt(event.text); 
				            event.doit = true;
				        }  
				        catch (NumberFormatException e) {
				        	event.doit = false;
				        } 
					}
		});
		editors[5] = textEditor;		
		
		// Column 7 : Voller Anspruch auf KEZ vorhanden
		ComboBoxCellEditor comboEditor = new ComboBoxCellEditor(table, KindererziehungszeitenHelper.getAnspruchLabels(), SWT.READ_ONLY);
		editors[6] = comboEditor;

		// Assign the cell editors to the viewer 
		tableViewer.setCellEditors(editors);
		// Set the cell modifier for the viewer
		tableViewer.setCellModifier(new KezCellModifier(this, childList));
		
		tableViewer.setContentProvider(new KezContentProvider(childList));
		tableViewer.setLabelProvider(new KezLabelProvider());
		
		
		// The input for the table viewer is the instance of TimePeriodList
		//!!! Die Liste darf erst instantiiert werden, wenn der ContentProvider schon existiert.
		//Ansonsten funktoniert die Registrierung der Listener (Change, Add, Remove) nicht.
		childList = new HashSet<KezPeriodWrapper>();
		tableViewer.setInput(childList);
		
		return tableViewer;
	}



	
	private void createButtonsBefore1992(Composite parent) {
		createButtons(parent, this.childListBefore1992, this.tableViewerBefore1992, new String("01.01.1990"));
		
	}


	private void createButtonsAfter1991(Composite parent) {
		createButtons(parent, this.childListAfter1991, this.tableViewerAfter1991, new String("01.01.1992"));
		
	}

	
	private void createButtons(Composite parent, Set<KezPeriodWrapper> childList, TableViewer tableViewer, String defaultBirthdate) {

		Composite btnComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		btnComposite.setLayout (layout);
		btnComposite.setLayoutData (new GridData (GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH));
		
		
		// Create and configure the "Add" button
		Button add = new Button(btnComposite, SWT.PUSH | SWT.CENTER);
		add.setText(ControlConstants.BTN_ADD);
		
		
		add.addSelectionListener(new SelectionAdapter() {
       	
       		// Add a task to the ExampleTaskList and refresh the view
			public void widgetSelected(SelectionEvent e) {
				childList.add(new KezPeriodWrapper(new Para_50_Kindererziehungszeit("Vorname des Kindes", DateUtil.getDate(defaultBirthdate), null, null, true)));
				tableViewer.refresh();
				
				//Text in DocView anpassen
				String tooltip = tableViewer.getTable().getToolTipText();
				
				if(tooltip == null) {
					return;
				}
				
				String hintText = textResources.getString("DEFAULT_TEXT");
				if(tooltip.contains("geboren nach dem 31.12.1991")) {
					hintText = textResources.getString("KEZ_NACH_1991");
				}
				else if(tooltip.contains("geboren vor dem 01.01.1992")) {
					hintText = textResources.getString("KEZ_VOR_1992");
					
				}
				
				setPartInfoText(hintText);
			}
		});

		//	Create and configure the "Delete" button
		Button delete = new Button(btnComposite, SWT.PUSH | SWT.CENTER);
		delete.setText(ControlConstants.BTN_DELETE);
		
		
		delete.addSelectionListener(new SelectionAdapter() {
       	
			//	Remove the selection and refresh the view
			public void widgetSelected(SelectionEvent e) {
				KezPeriodWrapper kez = (KezPeriodWrapper) ((IStructuredSelection) 
						tableViewer.getSelection()).getFirstElement();
				if (kez != null) {
					childList.remove(kez);
					tableViewer.refresh();
				} 	
			}
		});
	}

	
	/**
	 * Return the column names in a collection
	 * 
	 * @return List  containing column names
	 */
	public java.util.List<String> getColumnNames() {
		return Arrays.asList(columnNames);
	}

	
	
	
	/**
	 * Return the array of choices for a multiple choice cell
	 */
	public String[] getChoices(String property) {
		if (KindererziehungszeitenHelper.ANSPRUCH.equals(property))
			return KindererziehungszeitenHelper.getAnspruchLabels();  
		else
			return new String[]{};
	}
	
	
	
	public void refreshViewPart() {
		this.tableViewerAfter1991.refresh();
		this.tableViewerBefore1992.refresh();
	}
	
	
	/**
	 * InnerClass that acts as a proxy for the TimePeriodList 
	 * providing content for the Table. It implements the ITimePeriodListViewer 
	 * interface since it must register changeListeners with the 
	 * TimePeriodList 
	 */
	class KezContentProvider implements IStructuredContentProvider {
		
		private Set<KezPeriodWrapper> childList;
		
		public KezContentProvider( Set<KezPeriodWrapper> childList) {
			this.childList =  childList;
		}
		
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			viewer.refresh();
		}



		// Return the tasks as an array of Objects
		public Object[] getElements(Object parent) {
			if(childList != null) {
				Object[] elements = childList.toArray();
				return elements;
			}
			return new HashSet<Para_50_Kindererziehungszeit>().toArray();
		}



	}


	public KindererziehungszeitenZuschlag getKindererziehungszuschlag(float rentenwert) {
		if(this.childListBefore1992.isEmpty() && this.childListAfter1991.isEmpty()) {
			return null;
		}
		KindererziehungszeitenZuschlag kez = new KindererziehungszeitenZuschlag(rentenwert);
		
		for (KezPeriodWrapper kezPeriodWrapper : childListAfter1991) {
			kez.addKindererziehungszeit(kezPeriodWrapper.getKindererziehungszeit(), KindererziehungszeitenZuschlag.KEZ_NACH_1991);
		}
		for (KezPeriodWrapper kezPeriodWrapper : childListBefore1992) {
			kez.addKindererziehungszeit(kezPeriodWrapper.getKindererziehungszeit(), KindererziehungszeitenZuschlag.KEZ_VOR_1992);
		}
		
		return kez;
	}


	public void clear() {
		this.childListBefore1992.clear();
		this.childListAfter1991.clear();
		this.tableViewerBefore1992.refresh();
		this.tableViewerAfter1991.refresh();
	}


	public void setData(KindererziehungszeitenZuschlag kindererziehungsZuschlag) {
		
		if(kindererziehungsZuschlag == null) {
			return;
		}
		
		Date date_19911231 = DateUtil.getDate("31.12.1991");
		
		Set<Para_50_Kindererziehungszeit> kindererziehungszeiten = kindererziehungsZuschlag.getKindererziehungszeiten();
		
		for (Iterator<Para_50_Kindererziehungszeit> iterator = kindererziehungszeiten.iterator(); iterator.hasNext();) {
			Para_50_Kindererziehungszeit kez = iterator.next();
			
				//Kind nach 31.12.1991 geboren
			if(date_19911231.before(kez.getBirthDate())) {
				childListAfter1991.add(new KezPeriodWrapper(kez));
				
			}
			else {
				childListBefore1992.add(new KezPeriodWrapper(kez));
			}
		}
		
		
		tableViewerBefore1992.refresh();
		tableViewerAfter1991.refresh();
		
	}


	public Table getTableBefore1992() {
		return tableBefore1992;
	}


	public Table getTableAfter1991() {
		return tableAfter1991;
	}


	public TableViewer getTableViewerBefore1992() {
		return tableViewerBefore1992;
	}


	public TableViewer getTableViewerAfter1991() {
		return tableViewerAfter1991;
	}
	
	
	
}
