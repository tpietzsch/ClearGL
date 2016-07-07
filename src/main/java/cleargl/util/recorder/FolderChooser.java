package cleargl.util.recorder;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FolderChooser extends JFileChooser
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private FolderChooser( final String pChooserTitle, final File pDefaultFolder )
	{
		super();
		setDialogTitle( pChooserTitle );
		setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		setAcceptAllFileFilterUsed( false );
		setFileFilter( new FileFilter()
		{

			@Override
			public boolean accept( final File f )
			{
				return f.isDirectory();
			}

			@Override
			public String getDescription()
			{
				return "Directories only";
			}

		} );
		setCurrentDirectory( pDefaultFolder.getParentFile() );
		setSelectedFile( pDefaultFolder );
	}

	public static File openFolderChooser( final Component pParent,
			final String pChooserTitle,
			final File pDefaultFolder )
	{
		final FolderChooser lFolderChooser = new FolderChooser( pChooserTitle,
				pDefaultFolder );

		if ( lFolderChooser.showOpenDialog( pParent ) == JFileChooser.APPROVE_OPTION )
		{
			return lFolderChooser.getSelectedFile();
		}
		else
		{
			return null;
		}
	}
}
