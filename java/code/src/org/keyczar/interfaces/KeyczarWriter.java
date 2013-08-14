package org.keyczar.interfaces;

import org.keyczar.exceptions.KeyczarException;

public interface KeyczarWriter extends KeyczarReader {
  void setMetadata(String metadata) throws KeyczarException;

  void setKey(int versionNumber, String key) throws KeyczarException;
}
