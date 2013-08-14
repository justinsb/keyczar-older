package org.keyczar;

import org.apache.log4j.Logger;
import org.keyczar.exceptions.KeyczarException;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple in-memory key store
 *
 */
public class MemoryKeyczarStore extends KeyczarStore {
  private static final Logger LOG = Logger.getLogger(MemoryKeyczarStore.class);

  final Map<Integer, String> keys = new HashMap<Integer, String>();
  String metadata;

  @Override
  public void setMetadata(String metadata) throws KeyczarException {
    this.metadata = metadata;
  }

  @Override
  public void setKey(int versionNumber, String key) throws KeyczarException {
    keys.put(versionNumber, key);
  }

  @Override
  public String getKey(int version) throws KeyczarException {
    String key = keys.get(version);
    if (key == null) {
      throw new KeyczarException("Key not found");
    }
    return key;
  }

  @Override
  public String getMetadata() throws KeyczarException {
    if (metadata == null) {
      throw new KeyczarException("Metadata not set");
    }
    return metadata;
  }
}
