package awesome.project.simplenumbergenerator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Generated {

    @JsonProperty("generated")
    private List<GeneratedColumn> generatedColumnList;
    private Boolean auto;

    public Generated() {
    }

    public Generated(List<GeneratedColumn> generatedColumnList, Boolean auto) {
        this.generatedColumnList = generatedColumnList;
        this.auto = auto;
    }

    public List<GeneratedColumn> getGeneratedColumnList() {
        return generatedColumnList;
    }

    public void setGeneratedColumnList(List<GeneratedColumn> generatedColumnList) {
        this.generatedColumnList = generatedColumnList;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    public static Generated generatedWithAuto() {
        Generated generated = new Generated();
        generated.setAuto(true);
        return generated;
    }
}
