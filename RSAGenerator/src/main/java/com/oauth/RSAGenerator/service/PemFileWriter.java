package com.oauth.RSAGenerator.service;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.Key;

public class PemFileWriter {

    private final PemObject pemObject;

    public PemFileWriter(Key key, String description) {
        this.pemObject = new PemObject(description, key.getEncoded());
    }

    public void write(String filename) throws IOException {
        try (PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(filename)))) {
            pemWriter.writeObject(this.pemObject);
        }
    }
}