package com.ffwatl.admin.entities.users.address;

import javax.persistence.*;

@Entity
@Table(name = "zip_codes")
public class ZipCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}