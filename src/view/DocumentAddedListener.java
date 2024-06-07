package view;

import model.Document;

/**
 * This is the interface for Document Addition Listening
 *
 * @version JDK 21.0
 * @author Bill Lactaoen
 */

public interface DocumentAddedListener {
    /**
     * This is the abstract method for listening for an added document.
     * @param theDocument is the document added.
     */
    void documentAdded(final Document theDocument);
}
