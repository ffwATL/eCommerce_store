package com.ffwatl.manage.entities.users.phone;


import javax.persistence.*;

@Entity
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Code code;

    private int number;

    public long getId() {
        return id;
    }

    public Code getCode() {
        return code;
    }

    public int getNumber() {
        return number;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
