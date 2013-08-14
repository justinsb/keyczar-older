package org.keyczar;

import org.apache.log4j.Logger;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.interfaces.KeyczarReader;
import org.keyczar.interfaces.KeyczarWriter;

/**
 * Store of metadata on keys, e.g. on disk or in memory.
 * 
 * Allows reading and writing.
 */
public abstract class KeyczarStore implements KeyczarReader, KeyczarWriter {
  private static final Logger LOG = Logger.getLogger(KeyczarStore.class);

  @Override
  public String getKey() throws KeyczarException {
    KeyMetadata metadata = KeyMetadata.read(getMetadata());

    return getKey(metadata.getPrimaryVersion().getVersionNumber());
  }
}
