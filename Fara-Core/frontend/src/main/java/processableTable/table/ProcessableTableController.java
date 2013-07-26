package processableTable.table;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import core.ProcessResultListener;
import core.ProcessService;
import org.apache.commons.lang3.StringUtils;
import processableTable.ProcessTableObservable;
import processableTable.ProcessTableStateCalculator;
import processableTable.ProcessTableStates;
import processableTable.table.model.AbstractProcessableTableModel;
import processableTable.table.model.RowState;
import processableTable.toolbar.ProcessToolbarDelegate;
import core.service.ReflectionService;
import state.StateListener;


public class ProcessableTableController<Model extends AbstractProcessableTableModel> extends ProcessTableObservable implements ProcessToolbarDelegate, ProcessResultListener{

	private Model model;
	private ProcessableTableUI ui;
	private boolean play;
	private ProcessService service;
	private ProcessTableStateCalculator stateCalculator;
	private ProcessableTableDelegate delegate;
	private ReflectionService reflectionService;
	
	public ProcessableTableController(final Model model, ProcessService service, StateListener<ProcessTableStates> stateListener, ProcessableTableDelegate delegate) {
		this.delegate = delegate;
		this.model = model;
		this.service = service;
		this.reflectionService = new ReflectionService();
		stateCalculator = new ProcessTableStateCalculator(model);
		stateCalculator.registerListener(stateListener);
		model.initRowStates();
		service.registerResultListener(this);
		ui = new ProcessableTableUI(model);
		ui.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = ui.getTable();
				Point p = e.getPoint();
				int row = table.rowAtPoint(p);
				int column = table.columnAtPoint(p);
				if (column == model.getBreakpointColumn()) {
					model.toggleBreakpoint(row);
				}
			}
		});
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				stateCalculator.calculateState();
			}
		});
		stateCalculator.calculateState();
	}

	public void jumpTo() {
		int selectedRow = ui.getTable().getSelectedRow();
		if (selectedRow >= 0) {
			while (!model.isPointer(selectedRow)) {
				model.setRowStateAtPointer(RowState.SKIPPED);
				if (model.isAfterPointer(selectedRow)) {
					model.rowPointerNext();
				} else if (model.isBeforePointer(selectedRow)) {
					model.rowPointerPrevious();
				}
			}
			model.setRowStateAtPointer(RowState.WAIT);
			model.fireTableRowsUpdated(0, model.getRowCount() - 1);
		}
	}


	public void enableNextRow() {
		if (model.hasMoreRows()) {
			model.rowPointerNext();
			model.setRowStateAtPointer(RowState.WAIT);
			model.updatePointerRow();
		}
	}
	public JComponent getComponent() {
		return ui.getComponent();
	}

	@Override
	public void nextStep() {
		if (!(model.breakpointAtPointer() && play)) {
			model.setRowStateAtPointer(RowState.PROCESSING);
			stateCalculator.calculateState();
			Executors.newSingleThreadExecutor().submit(new Runnable() {
				@Override
				public void run() {
					service.doNextStep(model.getRowAtPointer());
				}
			});
		} else {
			model.removeBreakpointAtPointer();
		}
	}

	@Override
	public void play() {
		this.play = true;
		nextStep();
	}

	@Override
	public void skip() {
		jumpTo();
	}
	
	
	private void prepareAndProceedIfPossible(RowState rowState) {
		if (model.hasMoreRows()) {
			enableNextRow();
			if (!canProceed(rowState)) {
				if(model.breakpointAtPointer()) {
					model.removeBreakpointAtPointer();
				}
				play = false;
			}
			if (play) {
				nextStep();
			}
		}
		else {
			delegate.lastRowProcessed();
			play = false;
		}
	}

	private boolean canProceed(RowState rowState) {
		return rowState != RowState.FAILED && !model.breakpointAtPointer()
				&& (model.hasMoreRows() || model.pointsToLastRow()) ;
	}

	private RowState toRowState(String result) {
		if ("WRONG".equals(result)) {
			return RowState.FAILED;
		} else if ("RIGHT".equals(result)) {
			return RowState.SUCCESS;
		} else if ("IGNORE".equals(result)) {
			return RowState.IGNORED;
		}
		return null;
	}

	@Override
	public void publishResult(String result, String message) {
		RowState rowState = toRowState(result);
		if(message == null) {
			message = "";
		}
		model.publishResult(rowState, message);
		model.updatePointerRow();
		stateCalculator.calculateState();
		prepareAndProceedIfPossible(rowState);
	}

	public void loadNewProcessService(String text) {
		if(StringUtils.isNotEmpty(text)) {
			this.service = reflectionService.loadProcessService(text);
		}
		this.service.registerResultListener(this);
	}

	public void setNewProcessService(ProcessService newProcessService) {
		this.service = newProcessService;
		service.registerResultListener(this);
	}

	public JTable getTable() {
		return ui.getTable();
	}
}
