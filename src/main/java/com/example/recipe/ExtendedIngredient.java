
package com.example.recipe;


import java.io.Serializable;

@SuppressWarnings("unused")
public class ExtendedIngredient implements Serializable {


    private Long id;

    private String originalString;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    public ExtendedIngredient(Long id, String originalString) {
        this.id = id;
        this.originalString = originalString;
    }
}
