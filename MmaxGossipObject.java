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
public class MmaxGossipObject {
    
    private String type;
    private String span;
    private boolean normative;
    private boolean relation;
    private String polarity;
    private String source;
    private String target1;
    private String target2;
    private String text;
    private String reaction;
    private String chain;

    public MmaxGossipObject() {
    }

    public MmaxGossipObject(String type, String span, boolean normative, boolean relation, String polarity, String source, String target1, String target2, String text, String reaction, String chain) {
        this.type = type;
        this.span = span;
        this.normative = normative;
        this.relation = relation;
        this.polarity = polarity;
        this.source = source;
        this.target1 = target1;
        this.target2 = target2;
        this.text = text;
        this.reaction = reaction;
        this.chain = chain;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
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

    public boolean isNormative() {
        return normative;
    }

    public void setNormative(boolean normative) {
        this.normative = normative;
    }

    public boolean isRelation() {
        return relation;
    }

    public void setRelation(boolean relation) {
        this.relation = relation;
    }

    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget1() {
        return target1;
    }

    public void setTarget1(String target1) {
        this.target1 = target1;
    }

    public String getTarget2() {
        return target2;
    }

    public void setTarget2(String target2) {
        this.target2 = target2;
    }

    @Override
    public String toString() {
        return "MmaxGossipObject{" + "type=" + type + ", span=" + span + ", normative=" + normative + ", relation=" + relation + ", polarity=" + polarity + ", source=" + source + ", target1=" + target1 + ", target2=" + target2 + ", text=" + text + ", reaction=" + reaction + ", chain=" + chain + '}';
    }

  
    
    
    
    
}
