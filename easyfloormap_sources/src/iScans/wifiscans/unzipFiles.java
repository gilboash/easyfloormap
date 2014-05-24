/**********************************************************************************************************************
 
  Project:		  Easy Floor Map Locator Android APP

  FILENAME:       unzipFiles.java

  DESCRIPTION:    unzip files class for unpacking maps packages from the online DB 

  Copyright (c) 2013 Gilboa Shveki	(gilboash@gmail.com)
   
  Author: Gilboa Shveki

***********************************************************************************************************************/



package iScans.wifiscans;



import android.util.Log; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.util.zip.ZipEntry; 
import java.util.zip.ZipInputStream; 

/** 
 * 
 * @author jon 
 */ 
public class unzipFiles { 
  private String _zipFile; 
  private String _location,_originlocation; 

  public unzipFiles(String zipFile, String location) { 
    _zipFile = zipFile; 
    _originlocation = location;
    _location = location;

    _dirChecker(""); 
  } 

  public void unzip() { 
    try  { 
      FileInputStream fin = new FileInputStream(_zipFile); 
      ZipInputStream zin = new ZipInputStream(fin); 
      
      ZipEntry ze = null; 
      while ((ze = zin.getNextEntry()) != null) { 
        Log.v("Decompress", "Unzipping " + ze.getName()); 

        if (ze.getName().contains("jpg"))
        {
        	_location = _originlocation+"zz_maps/";
        }
        else
        {
        	_location = _originlocation;
        }
        
        if(ze.isDirectory()) { 
          _dirChecker(ze.getName()); 
        } else { 
          FileOutputStream fout = new FileOutputStream(_location + ze.getName()); 
          for (int c = zin.read(); c != -1; c = zin.read()) { 
            fout.write(c); 
          } 

          zin.closeEntry(); 
          fout.close(); 
        } 

      } 
      zin.close(); 
    } catch(Exception e) { 
      Log.e("Decompress", "unzip", e); 
    } 

  } 

  private void _dirChecker(String dir) { 
    File f = new File(_location + dir); 

    if(!f.isDirectory()) { 
      f.mkdirs(); 
    } 
  } 
} 