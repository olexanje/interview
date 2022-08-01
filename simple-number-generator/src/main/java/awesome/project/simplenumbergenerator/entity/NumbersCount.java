package awesome.project.simplenumbergenerator.entity;

public class NumbersCount {

    private Integer count;
    private Boolean auto;


    public NumbersCount() {
    }

    public NumbersCount(int length) {
        setCount(length);
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }
}
