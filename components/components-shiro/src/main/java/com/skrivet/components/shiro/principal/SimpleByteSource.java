package com.skrivet.components.shiro.principal;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

import java.io.Serializable;

public class SimpleByteSource implements Serializable, ByteSource {

	private static final long serialVersionUID = -175078082198037152L;
	private byte[] sources;

	public byte[] getSources() {
		return sources;
	}

	public void setSources(byte[] sources) {
		this.sources = sources;
	}

	public SimpleByteSource(byte[] sources) {
		this.sources = sources;
	}

	@Override
	public byte[] getBytes() {
		return sources;
	}

	@Override
	public boolean isEmpty() {
		return sources == null || sources.length == 0;
	}

	@Override
	public String toBase64() {
		return Base64.encodeToString(sources);
	}

	@Override
	public String toHex() {
		return Hex.encodeToString(sources);
	}

}
