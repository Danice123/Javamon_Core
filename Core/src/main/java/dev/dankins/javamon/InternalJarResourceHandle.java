package dev.dankins.javamon;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class InternalJarResourceHandle extends FileHandle {

	private final URL resourceUrl;

	public InternalJarResourceHandle(final FileHandle jarFile, final String resourcePath) {
		try {
			resourceUrl = new URL("jar:file:" + jarFile.path() + "!" + resourcePath);
		} catch (final MalformedURLException e) {
			throw new GdxRuntimeException(
					"File not found: jar:file:" + jarFile.path() + "!" + resourcePath);
		}

		file = new File(resourceUrl.getPath());
		type = FileType.Internal;
	}

	@Override
	public InputStream read() {
		try {
			final JarURLConnection conn = (JarURLConnection) resourceUrl.openConnection();
			return conn.getInputStream();
		} catch (final IOException e1) {
			throw new GdxRuntimeException("File not found: " + resourceUrl);
		}
	}
}
