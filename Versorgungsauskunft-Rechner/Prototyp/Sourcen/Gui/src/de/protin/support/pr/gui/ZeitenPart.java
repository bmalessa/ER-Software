package de.protin.support.pr.gui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.FocusCellHighlighter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import de.protin.support.pr.domain.Comment;
import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.utils.TimePeriodHelper;
import de.protin.support.pr.gui.text.TextResources;
import de.protin.support.pr.gui.timeperiod.ITimePeriodListViewer;
import de.protin.support.pr.gui.timeperiod.TimePeriodCellModifier;
import de.protin.support.pr.gui.timeperiod.TimePeriodComparator;
import de.protin.support.pr.gui.timeperiod.TimePeriodLabelProvider;
import de.protin.support.pr.gui.timeperiod.TimePeriodList;
import de.protin.support.pr.gui.timeperiod.TimePeriodWrapper;
import de.protin.support.pr.gui.util.ControlConstants;

public class ZeitenPart extends TabPart  {
	

	
	// Set column names
	private String[] columnNames;
	
	private Table table;
	private TableViewer tableViewer;
	private TimePeriodComparator comparator;
	private Text detailsText;
	
	// Create a TimePeriodList and assign it to an instance variable
	private TimePeriodList timePeriodList;

	
	public ZeitenPart(Composite parent) {
		super(parent);
		setPartInfoText(this.textResources.getString("DEFAULT_TEXT"));
		
	}
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void initPart(Composite parent, int style) {
		
		Composite partComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		partComposite.setLayout (layout);
		GridData gridData = new GridData (GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH);
		partComposite.setLayoutData (gridData);
		
		// Create the table, tableviewer and buttons for time intervals
		createTable(partComposite);
		createTableViewer();
		createButtonsForTableViewer(partComposite);
				
		// Create the text field with details for the selected time interval
		createDetailTextSection(partComposite, new String("Erläuternde Informationen zum ausgewählten Zeitinterval"));
		
		
		addTabListener();
		
		
	}
	
	








	/**
	 * Create the Table
	 */
	protected void createTable(Composite parent) {
		
		int style = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | 
					SWT.FULL_SELECTION | SWT.HIDE_SELECTION;

		//final int NUMBER_COLUMNS = this.columnNames.length;

		table = new Table(parent, style);
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		
		int desiredHeight = table.getItemHeight() * 12 + table.getHeaderHeight();
		gridData.heightHint = desiredHeight;
		gridData.horizontalSpan = 3;
		table.setLayoutData(gridData);		
					
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		

		// 1st column with image/checkboxes - NOTE: The SWT.CENTER has no effect!!
		TableColumn column = new TableColumn(table, SWT.CENTER, 0);		
		column.setText(TimePeriodHelper.NO);
		column.setWidth(30);

		// 2rd column with task Owner
		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText(TimePeriodHelper.ZEITART);
		column.setWidth(250);
		// Add listener to column so tasks are sorted by owner when clicked
		column.addSelectionListener(getSelectionAdapter(column, 1));
		
		// 3nd column with task Description
		column = new TableColumn(table, SWT.LEFT, 2);
		column.setText(TimePeriodHelper.DATE_FROM);
		column.setWidth(80);
		// Add listener to column so tasks are sorted by owner when clicked
		column.addSelectionListener(getSelectionAdapter(column, 2));

		// 4th column with task PercentComplete 
		column = new TableColumn(table, SWT.CENTER, 3);
		column.setText(TimePeriodHelper.DATE_TO);
		column.setWidth(80);
		// Add listener to column so tasks are sorted by owner when clicked
		column.addSelectionListener(getSelectionAdapter(column, 3));
		
		// 5th column with task PercentComplete 
		column = new TableColumn(table, SWT.CENTER, 4);
		column.setText(TimePeriodHelper.FACTOR);
		column.setWidth(60);
		
		// 6th column with task PercentComplete 
		column = new TableColumn(table, SWT.CENTER, 5);
		column.setText(TimePeriodHelper.AMOUNT_OF_DAYS);
		column.setWidth(40);
		// Add listener to column so tasks are sorted by owner when clicked
		column.addSelectionListener(getSelectionAdapter(column, 5));
		
		// 7th column with task PercentComplete 
		column = new TableColumn(table, SWT.CENTER, 6);
		column.setText(TimePeriodHelper.RGF_DAYS);
		column.setWidth(70);

		table.setToolTipText(new String ("Zusammenstellung der ruhgehaltsfähige Zeiten"));
	}

		
	
	/**
	 * Create the TableViewer 
	 */
	protected void createTableViewer() {

		tableViewer = new TableViewer(table);
		tableViewer.setUseHashlookup(true);

		this.columnNames = TimePeriodHelper.getTableColums();
		tableViewer.setColumnProperties(this.columnNames);

		// Create the cell editors
		CellEditor[] editors = new CellEditor[this.columnNames.length];

		// Column 1 : ldf Nr
		TextCellEditor textEditor = new TextCellEditor(table);  
		((Text) textEditor.getControl()).setTextLimit(2);
		editors[0] = textEditor;

		// Column 2 : Zeitart (Combo Box) 
		ComboBoxCellEditor comboEditor = new ComboBoxCellEditor(table, TimePeriodHelper.getTimePeriodTypeLabels(), SWT.READ_ONLY);
		editors[1] = comboEditor;

		// Column 3 : Datum Start (Free text)
		textEditor = new TextCellEditor(table);  
		((Text) textEditor.getControl()).setTextLimit(10);
		editors[2] = textEditor;
	
		// Column 4 : Datum Ende
		textEditor = new TextCellEditor(table);
		((Text) textEditor.getControl()).setTextLimit(10);
		editors[3] = textEditor;

		// Column 5 : Faktor
		textEditor = new TextCellEditor(table);
		((Text) textEditor.getControl()).setTextLimit(10);
		editors[4] = textEditor;

		
		// Column 5 : Anzahl Tage
		textEditor = new TextCellEditor(table);  
		((Text) textEditor.getControl()).setTextLimit(5);
		editors[5] = textEditor;
				
		// Column 6 : Anzahl ruhegehaltsfähige Tage
		textEditor = new TextCellEditor(table);  
		((Text) textEditor.getControl()).setTextLimit(5);
		/*
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
		*/
		editors[6] = textEditor;		
		

		// Assign the cell editors to the viewer 
		tableViewer.setCellEditors(editors);
		// Set the cell modifier for the viewer
		tableViewer.setCellModifier(new TimePeriodCellModifier(this));
		
		tableViewer.setContentProvider(new TimePeriodContentProvider());
		tableViewer.setLabelProvider(new TimePeriodLabelProvider());
		
		// Set the default sorter for the viewer 
		comparator = new TimePeriodComparator();
		tableViewer.setComparator(comparator);
		
		// The input for the table viewer is the instance of TimePeriodList
		//!!! Die Liste darf erst instantiiert werden, wenn der ContnentProvider schon existiert.
		//Ansonsten funktoniert die registrierung der Listener (Change, Add, Remove) nicht.
		timePeriodList = new TimePeriodList();
		tableViewer.setInput(timePeriodList);
		
		
	}
	
	
	
	
	/**
	 * Add the "Add", "Delete" and "Close" buttons
	 * @param parent the parent composite
	 */
	protected void createButtonsForTableViewer(Composite parent) {
		
		Composite btnComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		btnComposite.setLayout (layout);
		btnComposite.setLayoutData (new GridData (GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH));
		
		
		// Create and configure the "Add" button
		Button add = new Button(btnComposite, SWT.PUSH | SWT.CENTER);
		add.setText(ControlConstants.BTN_ADD);
		
		/*
		GridData gridData = new GridData (GridData.HORIZONTAL_ALIGN_BEGINNING);
		gridData.widthHint = 80;
		add.setLayoutData(gridData);
		*/
		add.addSelectionListener(new SelectionAdapter() {
       	
       		// Add a task to the ExampleTaskList and refresh the view
			public void widgetSelected(SelectionEvent e) {
				timePeriodList.addTimePeriod();
				tableViewer.refresh();
				table.setSelection(timePeriodList.getTimePeriods().size()-1);
				
			}
		});

		//	Create and configure the "Delete" button
		Button delete = new Button(btnComposite, SWT.PUSH | SWT.CENTER);
		delete.setText(ControlConstants.BTN_DELETE);
		
		/*
		gridData = new GridData (GridData.HORIZONTAL_ALIGN_BEGINNING);
		gridData.widthHint = 80; 
		delete.setLayoutData(gridData); 
		*/
		
		delete.addSelectionListener(new SelectionAdapter() {
       	
			//	Remove the selection and refresh the view
			public void widgetSelected(SelectionEvent e) {
				ITimePeriod timePeriod = (ITimePeriod) ((IStructuredSelection) 
						tableViewer.getSelection()).getFirstElement();
				if (timePeriod != null) {
					timePeriodList.removeTimePeriod(timePeriod);
					tableViewer.refresh();
				} 				
			}
		});
		
	}
	
	
	protected void createDetailTextSection(Composite partComposite, String label) {
		
		Label labelDetails = new Label(partComposite, SWT.NONE);
		labelDetails.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		labelDetails.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		labelDetails.setText(label);
		
		this.detailsText = new Text(partComposite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		this.detailsText.setLayoutData(gridData);
		
		//Buttons für Detail-Text des selektierten Zeitinterval
		Composite btnComposite = new Composite(partComposite, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		btnComposite.setLayout (layout);
		btnComposite.setLayoutData (new GridData (GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH));
		
		
		// Create and configure the "Add" button
		Button save = new Button(btnComposite, SWT.PUSH | SWT.CENTER);
		save.setText(ControlConstants.BTN_SAVE);
		save.addSelectionListener(new SelectionAdapter() {
       		public void widgetSelected(SelectionEvent e) {
				ITimePeriod timePeriod = (ITimePeriod) ((IStructuredSelection) 
						tableViewer.getSelection()).getFirstElement();
				
				Set<Comment> comments = new HashSet<Comment>();
				comments.add(new Comment(detailsText.getText()));
				timePeriod.setComments(comments);
			}
		});

		//	Create and configure the "Delete" button
		Button delete = new Button(btnComposite, SWT.PUSH | SWT.CENTER);
		delete.setText(ControlConstants.BTN_DELETE);
		delete.addSelectionListener(new SelectionAdapter() {
     		public void widgetSelected(SelectionEvent e) {
				ITimePeriod timePeriod = (ITimePeriod) ((IStructuredSelection) 
						tableViewer.getSelection()).getFirstElement();
				
				detailsText.setText("");
				timePeriod.resetComments();
			}
		});
		
		
		//Selection Listener für den TableViewer zum Aktualiserung der Anzeige, wenn sich das aktuell ausgewählte zeitinterval ändert
		this.table.addSelectionListener(new SelectionAdapter() {
       		public void widgetSelected(SelectionEvent e) {
       			ITimePeriod timePeriod = (ITimePeriod) ((IStructuredSelection) 
						tableViewer.getSelection()).getFirstElement();
				
				Set<Comment> comments = timePeriod.getComments();
				if(comments != null) {
					StringBuffer sb = new StringBuffer();
					for (Iterator iterator = comments.iterator(); iterator.hasNext();) {
						Comment comment = (Comment) iterator.next();
						sb.append(comment.getComment());
						sb.append("\n\n");
					}
					detailsText.setText(sb.toString());
				}
				else {
					detailsText.setText("");
				}
			}
		});
		
		
	}

	
	/**
	 * 
	 */
	public void loadTextResources() {
		this.textResources = TextResources.getRuhegehaltsfaehigeZeitenDocs();
	}


	
	
	/**
	 * InnerClass that acts as a proxy for the TimePeriodList 
	 * providing content for the Table. It implements the ITimePeriodListViewer 
	 * interface since it must register changeListeners with the 
	 * TimePeriodList 
	 */
	class TimePeriodContentProvider implements IStructuredContentProvider, ITimePeriodListViewer {
		
		
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
			if (newInput != null)
				((TimePeriodList) newInput).addChangeListener(this);
			if (oldInput != null)
				((TimePeriodList) oldInput).removeChangeListener(this);
		}

		public void dispose() {
			timePeriodList.removeChangeListener(this);
		}

		// Return the tasks as an array of Objects
		public Object[] getElements(Object parent) {
			Object[] elements = timePeriodList.getTimePeriods().toArray();
			for (int i = 0; i < elements.length; i++) {
				((TimePeriodWrapper)elements[i]).setLfdNummer(i + 1);
			}
			return elements;
		}

		/* (non-Javadoc)
		 * @see ITaskListViewer#addTask(ExampleTask)
		 */
		public void addTimePeriod(ITimePeriod timePeriod) {
			tableViewer.add(timePeriod);
		}

		/* (non-Javadoc)
		 * @see ITaskListViewer#removeTask(ExampleTask)
		 */
		public void removeTimePeriod(ITimePeriod timePeriod) {
			tableViewer.remove(timePeriod);			
		}

		/* (non-Javadoc)
		 * @see ITaskListViewer#updateTask(ExampleTask)
		 */
		public void updateTimePeriod(ITimePeriod timePeriod) {
			tableViewer.update(timePeriod, null);	
		}
	}
	
	
	
	/**
	 * Return the array of choices for a multiple choice cell
	 */
	public String[] getChoices(String property) {
		if (TimePeriodHelper.ZEITART.equals(property))
			return timePeriodList.getTimePeriodLabels();  
		else
			return new String[]{};
	}
	
	
	
	/**
	 * Do the table column sort.
	 * @param column
	 * @param index
	 * @return
	 */
	private SelectionAdapter getSelectionAdapter(final TableColumn column,
            final int index) {
        SelectionAdapter selectionAdapter = new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                comparator.setColumn(index);
                int dir = comparator.getDirection();
                tableViewer.getTable().setSortDirection(dir);
                tableViewer.getTable().setSortColumn(column);
                tableViewer.refresh();
            }
        };
        return selectionAdapter;
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
	 * @return currently selected item
	 */
	public ISelection getSelection() {
		return tableViewer.getSelection();
	}

	/**
	 * Return the ExampleTaskList
	 */
	public TimePeriodList getTimePeriodList() {
		return timePeriodList;	
	}

	/**
	 * Return the parent composite
	 */
	public Control getControl() {
		return table.getParent();
	}


	public Set<ITimePeriod> getTimePeriods() {
		List<ITimePeriod> timePeriods = this.timePeriodList.getTimePeriods();
		Set<ITimePeriod> result = new LinkedHashSet<ITimePeriod>();
		for (ITimePeriod timePeriod : timePeriods) {
			result.add(timePeriod);
		}
		return result;
	}


	public void clear() {
		timePeriodList.getTimePeriods().clear();
		tableViewer.refresh();
	}


	public void setData(Set<ITimePeriod> timePeriods) {
		for (Iterator<ITimePeriod> iterator = timePeriods.iterator(); iterator.hasNext();) {
			ITimePeriod iTimePeriod = iterator.next();
			TimePeriodWrapper timePeriodWrapper = new TimePeriodWrapper(iTimePeriod);
			timePeriodList.getTimePeriods().add(timePeriodWrapper);
		}
		tableViewer.refresh();
	}


	public TableViewer getTableViewer() {
		return tableViewer;     
	}

	
	private void addTabListener() {
		//Ermöglicht das Navigieren in der Tabelle per Tastatur

		TableViewerFocusCellManager focusCellManager =  new TableViewerFocusCellManager( tableViewer, new FocusCellHighlighter (tableViewer) {});
		ColumnViewerEditorActivationStrategy editorActivationStrategy = new ColumnViewerEditorActivationStrategy(tableViewer) {

		     @Override
		     protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
		            ViewerCell cell = (ViewerCell) event.getSource();
		            boolean isRelevant = cell.getColumnIndex() == 1 || cell.getColumnIndex() == 2 ||  cell.getColumnIndex() == 3  || cell.getColumnIndex() == 4 || cell.getColumnIndex() == 6;
		            return isRelevant;
		     }

		};
		
		TableViewerEditor.create(tableViewer, focusCellManager, editorActivationStrategy, ColumnViewerEditor.KEYBOARD_ACTIVATION | ColumnViewerEditor.TABBING_HORIZONTAL
				| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR | ColumnViewerEditor.TABBING_VERTICAL);
		
	}
}
