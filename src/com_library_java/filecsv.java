package com_library_java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;

public class filecsv
{
  public filecsv() {}
  
  private ArrayList<ArrayList<String>> data = new ArrayList();
  
  public boolean ReadFile(String namefile, String divider, String encoding) {
    BufferedReader bufferreader = null;
    String[] tabline = null;
    String line = null;
    int i = 0;
    try
    {
      bufferreader = new BufferedReader(new java.io.InputStreamReader(new FileInputStream(namefile), encoding));
      while ((line = bufferreader.readLine()) != null) {
        if (line.length() > 0) {
          tabline = line.split(divider);
          data.add(new ArrayList());
          for (int j = 0; j < tabline.length; j++) {
            tabline[j] = tabline[j].replaceAll("\"", "");
            ((ArrayList)data.get(i)).add(tabline[j]);
          }
          i++;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (bufferreader != null) {
          bufferreader.close();
        }
      } catch (Exception e2) {
        e2.printStackTrace();
        return false;
      }
    }
    return true;
  }
  


  public boolean WriteFile(String namefile, String divider, ArrayList<ArrayList<String>> datacsv, String encoding)
  {
    if (!DeleteFile(namefile)) { return false;
    }
    try
    {
      BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(namefile), encoding));
      
      for (int i = 0; i < datacsv.size(); i++) {
        String bufferrow = "";
        for (int j = 0; j < ((ArrayList)datacsv.get(i)).size(); j++) {
          bufferrow = bufferrow + "\"" + (String)((ArrayList)datacsv.get(i)).get(j) + "\"";
          if (j == ((ArrayList)datacsv.get(i)).size() - 1) {
            bufferrow = bufferrow + "\n";
          } else {
            bufferrow = bufferrow + divider;
          }
        }
        out.write(bufferrow);
      }
      out.close();
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
  
  public boolean DeleteFile(String namefile)
  {
    java.nio.file.Path path = FileSystems.getDefault().getPath(namefile, new String[0]);
    try {
      java.nio.file.Files.deleteIfExists(path);
      return true;
    } catch (IOException e1) {
      e1.printStackTrace(); }
    return false;
  }
  

  public ArrayList<ArrayList<String>> getDataCsv()
  {
    return data;
  }
}
