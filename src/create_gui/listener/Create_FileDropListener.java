package create_gui.listener;



import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

import create_gui.CreateGUIController;
import create_gui.Create_MainWindow;



public class Create_FileDropListener implements DropTargetListener{
	
	private Create_MainWindow mainWindow;
	

	public Create_FileDropListener(Create_MainWindow mainWindow){
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
                CreateGUIController.newGame(this.mainWindow, list.get(0));
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

