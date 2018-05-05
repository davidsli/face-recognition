package tyust.gjl.facerecognition.entity.dto;

import tyust.gjl.facerecognition.entity.Person;

/**
 * @author : coderWu
 * @date : Created on 15:43 2018/5/5
 */
public class Detect {

    private Person person;
    private double compareValue;

    public Person getPerson() {
        return person;
    }

    public Detect setPerson(Person person) {
        this.person = person;
        return this;
    }

    public double getCompareValue() {
        return compareValue;
    }

    public Detect setCompareValue(double compareValue) {
        this.compareValue = compareValue;
        return this;
    }
}
