package dev.dankins.javamon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class MenuFileHandleResolver extends InternalFileHandleResolver {

	static private final String REGEX = "jar:file:([^!]*)!(.*)";
	static private final Pattern PATTERN = Pattern.compile(REGEX);

	@Override
	public FileHandle resolve(final String fileName) {
		final Matcher matcher = PATTERN.matcher(fileName);
		if (matcher.find()) {
			return new InternalJarResourceHandle(Gdx.files.internal(matcher.group(1)),
					matcher.group(2));
		} else {
			return Gdx.files.internal(fileName);
		}
	}
}
