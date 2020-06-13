package br.com.catossi.meu_amigo_caminhoneiro.model;

public class Payload {

    String text;
    Object context;
    String output;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getContext() {
        return context;
    }

    public void setContext(Object context) {
        this.context = context;
    }


    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
