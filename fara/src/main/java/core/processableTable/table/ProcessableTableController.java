package core.processableTable.table;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;

import javax.swing.JComponent;
import javax.swing.JTable;

import core.processableTable.ProcessResultListener;
import core.processableTable.ProcessService;
import core.processableTable.ProcessTableObservable;
import core.processableTable.ProcessTableStateCalculator;
import core.processableTable.ProcessTableStates;
import core.processableTable.table.model.AbstractProcessableTableModel;
import core.processableTable.table.model.RowState;
import core.processableTable.toolbar.ProcessToolbarDelegate;
import core.state.StateListener;


public class ProcessableTableController extends ProcessTableObservable implements ProcessToolbarDelegate, ProcessResultListener{

	private AbstractProcessableTableModel model;
	private ProcessableTableUI ui;
	private boolean play;
	private final ProcessService service;
	private ProcessTableStateCalculator stateCalculator;
	private ProcessableTableDelegate delegate;
	
	public ProcessableTableController(final AbstractProcessableTableModel model, ProcessService service, StateListener<ProcessTableStates> stateListener, ProcessableTableDelegate delegate) {
		this.delegate = delegate;
		this.model = model;
		this.service = service;
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
}
