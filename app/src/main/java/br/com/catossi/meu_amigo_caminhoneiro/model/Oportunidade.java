package br.com.catossi.meu_amigo_caminhoneiro.model;

public class Oportunidade {

    private String vaga;
    private String percentage;
    private String curso1;
    private String curso2;
    private String faculdade;
    private String tempoEmpresa;


    public Oportunidade() {

    }

    public Oportunidade(String vaga, String percentage, String curso1, String curso2, String faculdade, String tempoEmpresa) {
        this.vaga = vaga;
        this.percentage = percentage;
        this.curso1 = curso1;
        this.curso2 = curso2;
        this.faculdade = faculdade;
        this.tempoEmpresa = tempoEmpresa;
    }

    public String getVaga() {
        return vaga;
    }

    public void setVaga(String vaga) {
        this.vaga = vaga;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getCurso1() {
        return curso1;
    }

    public void setCurso1(String curso1) {
        this.curso1 = curso1;
    }

    public String getCurso2() {
        return curso2;
    }

    public void setCurso2(String curso2) {
        this.curso2 = curso2;
    }

    public String getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(String faculdade) {
        this.faculdade = faculdade;
    }

    public String getTempoEmpresa() {
        return tempoEmpresa;
    }

    public void setTempoEmpresa(String tempoEmpresa) {
        this.tempoEmpresa = tempoEmpresa;
    }
}
