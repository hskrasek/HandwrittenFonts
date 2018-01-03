package handwrittenfont;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public 	class ImageFilter extends FileFilter
{

	@Override
	public boolean accept(File f) 
	{
		if(f.isDirectory())
		{
			return true;
		}
		
		String ext = getExtension(f);
		
		if(ext != null)
		{
			if(ext.equals("jpeg") || ext.equals("jpg"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	
	private String getExtension(File theFile)
	{
		String fileName = theFile.getName();
		if(fileName.equalsIgnoreCase(""))
		{
			return null;
		}
		String ext = null;
		
		int i = fileName.lastIndexOf('.');
		
		if(i > 0 && i < fileName.length() - 1)
		{
			ext = fileName.substring(i+1).toLowerCase();
		}
		return ext;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Images (.jpg, .jpeg)";
	}
	
}