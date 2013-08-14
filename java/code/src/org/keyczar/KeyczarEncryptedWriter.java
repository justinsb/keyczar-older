package org.keyczar;

import org.keyczar.exceptions.KeyczarException;
import org.keyczar.interfaces.EncryptedReader;
import org.keyczar.interfaces.KeyczarWriter;

public class KeyczarEncryptedWriter extends KeyczarEncryptedReader implements
    EncryptedReader, KeyczarWriter {

  final KeyczarWriter inner;
  final Encrypter encrypter;

  public KeyczarEncryptedWriter(KeyczarWriter inner, Crypter crypter,
      Encrypter encrypter) {
    super(inner, crypter);

    this.inner = inner;
    this.encrypter = encrypter;
  }

  @Override
  public void setMetadata(String metadata) throws KeyczarException {
    // Metadata is not encrypted
    inner.setMetadata(metadata);
  }

  @Override
  public void setKey(int versionNumber, String key) throws KeyczarException {
    inner.setKey(versionNumber, encrypter.encrypt(key));
  }

}
