package play_gui.listener;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;
import play_gui.MainWindow;
import play_gui.PlayGuiController;

// TODO: Javadoc kontrollieren
/**
 * The listener interface for receiving fileDrop events.
 * The class that is interested in processing a addValidator
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addaddValidatorListener<code> method. When
 * the addValidator event occurs, that object's appropriate
 * method is invoked.
 *
 * @see addValidatorEvent
 */
public class FileDropListener implements DropTargetListener{
	
	/** The main window. */
	private MainWindow mainWindow;
	
	/**
	 * Instantiates a new file drop listener.
	 *
	 * @param mainWindow the main window
	 */
	public FileDropListener(MainWindow mainWindow){
		this.mainWindow = mainWindow;
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
	 */
	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
	 */
	@Override
	public void dragExit(DropTargetEvent arg0) {
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
	 */
	@Override
	public void dragOver(DropTargetDragEvent arg0) {
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 */
	@Override
	public void drop(DropTargetDropEvent event) {
		event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        try {
            Transferable data = event.getTransferable();
            if (data.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				@SuppressWarnings("unchecked")
				List<File> list = (List<File>) data.getTransferData(DataFlavor.javaFileListFlavor);
                PlayGuiController.newGame(this.mainWindow, list.get(0));
            }
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
	 */
	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
