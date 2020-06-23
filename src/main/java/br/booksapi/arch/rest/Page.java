package br.booksapi.arch.rest;

public class Page {

	private final int size;

	private final int current;

	public Page(int pageSize, int page) {
		this.size = pageSize;
		this.current = page;
	}

	public int getSize() {
		return size;
	}

	public int getCurrent() {
		return current;
	}

}
