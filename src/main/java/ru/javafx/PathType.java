
package ru.javafx;

public enum PathType {
    
    WORDS("words");

    private final String path;

    private PathType(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
          
}
