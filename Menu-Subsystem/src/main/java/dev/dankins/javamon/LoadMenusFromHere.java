package dev.dankins.javamon;

import java.util.List;

import com.badlogic.gdx.assets.AssetDescriptor;

public interface LoadMenusFromHere {

	public List<Class<?>> load();

	public List<AssetDescriptor<?>> loadResources();

}
