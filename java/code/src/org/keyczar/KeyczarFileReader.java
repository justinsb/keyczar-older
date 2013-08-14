/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keyczar;

import org.keyczar.exceptions.KeyczarException;
import org.keyczar.i18n.Messages;
import org.keyczar.interfaces.KeyczarReader;
import org.keyczar.interfaces.KeyczarWriter;
import org.keyczar.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Reads metadata and key files from the given location.
 *
 * @author steveweis@gmail.com (Steve Weis)
 *
 */
public class KeyczarFileReader implements KeyczarReader, KeyczarWriter {
  private String location;
  static final String META_FILE = "meta";

  public KeyczarFileReader(String fileLocation) {
    if (fileLocation != null && !fileLocation.endsWith(File.separator)) {
      fileLocation += File.separator;
    }
    location = fileLocation;
  }

  @Override
  public String getKey(int version) throws KeyczarException {
    return readFile(location + version);
  }
  
  @Override
  public String getKey() throws KeyczarException {
    KeyMetadata metadata = KeyMetadata.read(getMetadata());
	
    return getKey(metadata.getPrimaryVersion().getVersionNumber());
  }

  @Override
  public String getMetadata() throws KeyczarException {
    return readFile(location + META_FILE);
  }

  public boolean hasMetadata() {
    String filename = location + META_FILE;
    File metaFile = new File(filename);
    return metaFile.exists();
  }

  private String readFile(String filename) throws KeyczarException {
    try {
      File file = new File(filename);
      FileInputStream fis = new FileInputStream(file);
      try {
        byte[] contents = Util.readStreamFully(fis);
        return new String(contents, Util.UTF_8);
      } finally {
        fis.close();
      }
    } catch (IOException e) {
      throw new KeyczarException(Messages.getString(
          "KeyczarFileReader.FileError", filename), e);
    }
  }

  /**
   * Utility function to write given data to a file at given location.
   *
   * @param data String data to be written
   * @param location String pathname of destination file
   * @throws KeyczarException if unable to write to file.
   */
  public static void writeFile(String data, File outputFile)
      throws KeyczarException {
    try {
      FileOutputStream fos = new FileOutputStream(outputFile);
      try {
        fos.write(data.getBytes(Util.UTF_8));
      } finally {
        fos.close();
      }
    } catch (IOException e) {
      throw new KeyczarException(Messages.getString(
          "KeyczarTool.UnableToWrite", outputFile.toString()), e);
    }
  }

  @Override
  public void setMetadata(String metadata) throws KeyczarException {
    File file = new File(this.location, KeyczarFileReader.META_FILE);
    writeFile(metadata, file);
  }

  @Override
  public void setKey(int versionNumber, String key) throws KeyczarException {
    File file = new File(this.location, Integer.toString(versionNumber));
    writeFile(key, file);
  }
}