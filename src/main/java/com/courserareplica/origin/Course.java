package com.courserareplica.origin;

import com.stormpath.sdk.account.Account;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Course tree object is the main UI object of the application. As direct
 * children it will have a collection of chapters that will construct the
 * backbone of the course
 *
 *
 */
public class Course implements Tree {

	// owner - it will retrieve basic data from stormpath for the UI logic
    // todo make it an user dto
	@Getter
	@Setter
	private Account ownerAccount;

	// list if chapters
	@Override
	public <E extends Tree> List<E> children() {
		return null;
	}

	// add chapter
	@Override
	public <E extends Tree> E add(E child) {
		return null;
	}
}
