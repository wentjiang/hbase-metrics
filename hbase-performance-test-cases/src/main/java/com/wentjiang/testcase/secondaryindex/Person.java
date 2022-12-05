package com.wentjiang.testcase.secondaryindex;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private String id;
    private String passportId;
    private String name;
    private String gender;
}
