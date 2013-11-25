package play_gui.listener;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;
import play_gui.MainWindow;
import play_gui.PlayGuiController;

public class FileDropListener implements DropTargetListener{
	
	private MainWindow mainWindow;
	
	public FileDropListener(MainWindow mainWindow){
		this.mainWindow = mainWindow;
	}

	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {
	}

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

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
