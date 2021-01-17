/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okosora;

/**
 *
 * @author Acer
 */
public class MmaxObject {
    
    private String type;
    private String span;
    private String text;

    public MmaxObject(String type, String span, String text) {
        this.type = type;
        this.span = span;
        this.text = text;
    }

  
    public MmaxObject() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
    }

   
  
    @Override
    public String toString() {
        return "MmaxObject{" + "type=" + type + ", span=" + span + ", text=" + text + '}';
    }

   
    
    
}
