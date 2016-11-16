package com.courserareplica.origin;

import java.util.List;

/**
 * The interface in a definer for the main methods used in the Tree hierarchy
 *
 */
public interface Tree extends TreeRoot {
	<E extends Tree> List<E> children();

	<E extends Tree> E add(E child);
}
