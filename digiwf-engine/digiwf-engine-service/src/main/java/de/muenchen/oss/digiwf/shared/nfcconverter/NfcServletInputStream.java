/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2019
 */
package de.muenchen.oss.digiwf.shared.nfcconverter;

import org.apache.commons.lang3.NotImplementedException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * ServletInputStream, der von einem Puffer ließt.
 */
class NfcServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream buffer;

    public NfcServletInputStream(final ByteArrayInputStream buffer) {
        this.buffer = buffer;
    }

    @Override
    public int read() throws IOException {
        return buffer.read();
    }

    @Override
    public boolean isFinished() {
        return buffer.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(final ReadListener listener) {
        throw new NotImplementedException("Not implemented");
    }

}
